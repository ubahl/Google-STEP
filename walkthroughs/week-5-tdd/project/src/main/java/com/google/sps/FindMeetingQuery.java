// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

public final class FindMeetingQuery {

  // Algorithm Complexity: 
  // Worst case: O(n^2)
  // Solution: a greedy algorithm which optimizes largest meeting times. Approaches the problem
  // locally, choosing the earliest start time and latest end time, given the next meeting.
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    Collection<TimeRange> availableTimes = new ArrayList<TimeRange>();
    Collection<String> attendees = request.getAttendees();
    ArrayList<TimeRange> allEvents = new ArrayList<TimeRange>();
    long duration = request.getDuration();

    // If a requested attendee is in a meeting, add it to allEvents.
    // O(EA), where E = size of events, A = size of attendees
    for (Event e : events) {
        for (String attendee : attendees) {
            if (e.getAttendees().contains(attendee)) {
                allEvents.add(e.getWhen());
                continue;
            }
        }
    }

    // Make sure allEvents is sorted by start time.
    // O(nlgn)
    Collections.sort(allEvents, TimeRange.ORDER_BY_START);

    // Use i as the beginning of each free period.
    // Then calculate the end and the next start.
    // Worst case: O(n^2).
    int eventIndex = 0;
    int i = TimeRange.START_OF_DAY;
    while (i <= TimeRange.END_OF_DAY) {

        TimeRange time;
        int start = i;
        int end;

        // If there are no more events, end the period at the end of the day.
        // If there are, end at the next event's start time.
        if (eventIndex >= allEvents.size()) {
            end = TimeRange.END_OF_DAY + 1;
            i = end;
        } else {
            time = allEvents.get(eventIndex);
            end = time.start();
            i = time.end(); 
        }

        // Use the calculated start and end values to create the free time period.
        // Add this period if it is long enough for the meeting request.
        TimeRange freePer = TimeRange.fromStartEnd(start, end, false);
        if (freePer.duration() >= duration) {
            availableTimes.add(freePer);
        }

        // Adjust i to the next start time
        // Loops through blocks of events, in the case of overlapping events.
        // Worst case: O(n).
        eventIndex++;
        while (allEvents.size() > eventIndex) {
            time = allEvents.get(eventIndex);

            // An overlapping event.
            if (time.contains(i)) { 
                i = time.end(); 
                eventIndex++;
            } 
            // The next event does not end after the current one.
            else if (i > time.end()) { 
                eventIndex++;
            }
            // This event has a free period after it.
            // Return to outside loop to mark it as the next start time.
            else {
                break;
            }
        }
    }
    return availableTimes;   
  }
}
