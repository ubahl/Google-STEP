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

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    // Instantiate return list.
    Collection<TimeRange> availableTimes = new ArrayList<TimeRange>();

    // Attendees.
    Collection<String> attendees = request.getAttendees();
  
    // A list all the events the required attendees have.
    ArrayList<TimeRange> allEvents = new ArrayList<TimeRange>();

    // Duration of meeting.
    long duration = request.getDuration();

    // If an attendee is in a meeting, add it to allEvents.
    for (Event e : events) {
        for (String attendee : attendees) {
            if (e.getAttendees().contains(attendee)) {
                allEvents.add(e.getWhen());
                continue;
            }
        }
    }

    // Make sure allEvents is sorted.
    Collections.sort(allEvents, TimeRange.ORDER_BY_START);

    // Find the free time between all the events.
    int eventIndex = 0;
    // Event[] allEventsArray = Collection.toArray(allEvents);
    int i = TimeRange.START_OF_DAY;
    while (i <= TimeRange.END_OF_DAY) {

        TimeRange time;

        int start = i;
        int end;

        if (eventIndex >= allEvents.size()) {
            end = TimeRange.END_OF_DAY + 1;
            i = end;
        } else {
            time = allEvents.get(eventIndex);
            end = time.start();
            i = time.end(); 
        }

        TimeRange freePer = TimeRange.fromStartEnd(start, end, false);
        if (freePer.duration() >= duration) {
            availableTimes.add(freePer);
        }

        eventIndex++;

        // Adjust i to next start time
        while (allEvents.size() > eventIndex) {
            time = allEvents.get(eventIndex);
            if (time.contains(i)) {
                i = time.end(); 
                eventIndex++;
            } else if (i > time.end()) { // Containing
                eventIndex++;
            } else {
                break;
            }
        }
    }

    return availableTimes;   
  }
}

    // // Duration of meeting.
    // long duration = request.getDuration();

    // // Number of 30 minutes in a day.
    // final int NUM_30S = 24 * 2;
    
    // // go through all times
    // for (int i = 0; i < NUM_30S; i++) {
    //     // Create a time slot for this time.
    //     TimeRange currTimeRange = fromStartDuration(i * 30, duration);
    //     // Check if everyone is free at this time slot.
    //     for (int i = 0; allEvents.size(); i++) {
    //         // Get the attendees for this event.

    //         // If a critical attendee is in this event, go to the next event.

    //         // If
            
    //     }
    // }
