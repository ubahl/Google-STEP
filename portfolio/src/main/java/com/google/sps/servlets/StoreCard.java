/* Store Card:
Holds the information that goes onto the search results cards:
- Place ID
- Store Name
- Image
- Rating
*/

package com.google.sps.servlets;

import java.net.URL;
import com.google.maps.model.PlacesSearchResult;

public class StoreCard {
    String placeId ="";
    String name = "";
    URL icon;
    float rating = 0.0f;

    public StoreCard(PlacesSearchResult store) {
        placeId = store.placeId;
        name = store.name;
        icon = store.icon;
        rating = store.rating;
    }
}
