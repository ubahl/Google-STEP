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

import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlacesSearchResult;
import com.google.gson.Gson;
import com.google.maps.model.RankBy;

/* Servlet that accesses Google Places API and Google Geocoding API to search for nearby boba places */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    Key myKey;
    GeoApiContext geoApiContext;
    final int SEARCH_RADIUS = 25000; // Radius to search for boba shops

    @Override
    public void init() {
        myKey = new Key();
        geoApiContext = new GeoApiContext.Builder().apiKey(myKey.getKey()).build();
    } 

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Gets the zip code sent from the search bar and converts it to lattitude and longitude.
        String zipCode = request.getParameter("zipCode");
        LatLng latLng = getLatLng(zipCode);

        // Searches this lattitude and longitude for boba places nearby.
        ArrayList<StoreCard> cards = getCardsInfo(latLng, "boba", SEARCH_RADIUS);

        // Convert to JSON and send.
        String json = listToJson(cards);
        response.setContentType("application/json");
        response.getWriter().println(json); 

    }

    /* Gets lattitude and logitude of zip code 
       Uses Geocoding API https://developers.google.com/maps/documentation/geocoding/intro (Region Biasing)*/
    public LatLng getLatLng(String zipCode) {
        GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, zipCode).awaitIgnoreError();
        return results[0].geometry.location;
    }

    /* Searches this lattitude and longitude for boba places nearby
       Place Search API https://developers.google.com/places/web-service/search (Nearby Search) */
    public ArrayList<StoreCard> getCardsInfo(LatLng latLng, String nameToSearch, int radiusToSearch) {

        NearbySearchRequest nearbyShops = PlacesApi.nearbySearchQuery(geoApiContext, latLng);
        nearbyShops.name(nameToSearch).radius(radiusToSearch);
        PlacesSearchResult[] results = nearbyShops.awaitIgnoreError().results;

        ArrayList<StoreCard> cards = new ArrayList<StoreCard>();
        for (int i = 0; i < results.length; i++) {
            StoreCard newCard = new StoreCard(results[i], geoApiContext);
            cards.add(newCard);
        }
        return cards;
    }

    /* Convert an ArrayList of StoreCard objects to JSON */
    private String listToJson(ArrayList<StoreCard> alist) {
        Gson gson = new Gson();
        String json = gson.toJson(alist);
        return json;
    }
    
}
