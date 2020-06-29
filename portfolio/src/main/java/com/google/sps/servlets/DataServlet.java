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
    private ArrayList<Review> reviews;
  
    @Override
    public void init() {
        reviews = new ArrayList<Review>();
        Review review1 = new Review("ubahl", "This is a great website!");
        Review review2 = new Review("natalie", "I need boba. This website is good.");
        Review review3 = new Review("sanya", "How did I get here");
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
    }


    private String listToJson(ArrayList<Review> alist) {
        Gson gson = new Gson();
        String json = gson.toJson(alist);
        return json;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = listToJson(reviews);

        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Review newReview = new Review(request.getParameter("name"), request.getParameter("review"));
        reviews.add(newReview);

        response.sendRedirect("/reviews.html");
    }
}
