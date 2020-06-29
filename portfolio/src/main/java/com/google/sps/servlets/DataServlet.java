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

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    /* Convert an ArrayList of Review objects to JSON */
    private String listToJson(ArrayList<Review> alist) {
        Gson gson = new Gson();
        String json = gson.toJson(alist);
        return json;
    }

    /* On the GET request, retrieves all the comments from Datastore and writes them in JSON */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // Retrieves the Review comments with a Query
        Query query = new Query("Review");
        PreparedQuery results = datastore.prepare(query);

        // Converts the comments to Review objects and temporarily stores them
        ArrayList<Review> reviews = new ArrayList<Review>();

        for(Entity entity : results.asIterable()) {
            String name = (String) entity.getProperty("name");
            String reviewText = (String) entity.getProperty("reviewText");

            Review review = new Review(name, reviewText);
            reviews.add(review);
        }

        // Converts and sends them as JSON
        String json = listToJson(reviews);
        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    /* On the POST command, stores the name and review as a Review entity in Datastore */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // Creates Review entity
        Entity newReview = new Entity("Review");
        newReview.setProperty("name", request.getParameter("name"));
        newReview.setProperty("reviewText", request.getParameter("review"));

        // Store in Datastore and redirects back to Reviews page (where GET command activates and displays the review)
        datastore.put(newReview);
        response.sendRedirect("/reviews.html");
    }
}
