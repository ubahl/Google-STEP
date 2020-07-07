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

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.maps.GeocodingApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeoApiContext.Builder;
import com.google.maps.GeocodingApi.Response;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.net.URL;

// Servlet that accesses Google Places API for Place Search - Nearby Search 
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    Key myKey;
    GeoApiContext geoApiContext;

    @Override
    public void init() {
        myKey = new Key();
        // geoApiContextBuilder = GeoApiContext.Builder();
        // geoApiContextBuilder.apiKey(apiKey.getKey());
        // geoApiContext = geoApiContextBuilder.build();

        // GeoApiContext.Builder builder = GeoApiContext.Builder(null);

        // geoApiContext = builder.apiKey(myKey.getKey()).build();
        // geoApiContext = new GeoApiContext().setApiKet(myKey.getKey());

        geoApiContext = new GeoApiContext.Builder().apiKey(myKey.getKey()).build();
    } 

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Gets the zip code sent from the search bar.
        String zipCode = request.getParameter("zipCode");

        // GeocodingApi zipGeocode = new GeocodingApi();
        // zipGeocode.geocode(geoApiContext, zipCode);

        GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, zipCode).awaitIgnoreError();



        response.setContentType("text");
        response.getWriter().println(results[0].geometry.location); 

        // // Gets the zip code sent from the search bar.
        // String zipCode = request.getParameter("zipCode");

        // // Searches the Google Maps Platform for places nearby
        // // Place Search API https://developers.google.com/places/web-service/search (Nearby Search)

        // // Inputs:
        // // API Key - accessed from Key class to keep Key private
        // String key = apiKey.getKey();

        // // Location - converts zip code given to lat and long
        // String location = getLocation(zipCode, key);

        // // Radius - 15 miles (around 25000 meters)
        // String radius = "25000";

        // // Name - boba (search for boba)
        // String name = "boba";

        

        
        // // When clicked on, do deeper Place Details search


        // // Return Boba Places
        // response.setContentType("application/json");
        // response.getWriter().println(location); 

    }
}

//     // Uses Geocoding API https://developers.google.com/maps/documentation/geocoding/intro#RegionCodes (Region Basing)
//     public String getLocation(String zipCode, String key) {
//         // Build URL
//         String stringURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zipCode + "&key=" + key;

//         HttpURLConnection con;

//         try {

//             URL url;
//             try {
//                 url = new URL(stringURL);
//             } catch(MalformedURLException ex) {
//             con = (HttpURLConnection) url.openConnection();

//             con.setRequestMethod("GET");

//             StringBuilder content;

//             try (BufferedReader in = new BufferedReader(
//                     new InputStreamReader(con.getInputStream()))) {

//                 String line;
//                 content = new StringBuilder();

//                 while ((line = in.readLine()) != null) {

//                     content.append(line);
//                     content.append(System.lineSeparator());
//                 }
//             }

//             return content.toString();

//         } finally {

//             con.disconnect();
//         }
//     }
// }
