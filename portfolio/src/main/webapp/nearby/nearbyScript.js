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

/* Get the user's location. */

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(getSearchResults);
    } else {
        document.getElementById("loading-text").innerText = "We can't find you!";
    }
}

/* Send a GET request to the server with the search term and location. */
async function getSearchResults(position) {
    // Get the text from local storage.
    var searchText = window.localStorage.getItem('searchText');
    console.log("Search Text: " + searchText);

    // Use a GET request to send text and location to /search.
    var lat = position.coords.latitude;
    var lng = position.coords.longitude;
    console.log("Lat: " + lat + "Lng: " + lng);
    var url = '/search?searchText=' + searchText + "&lat=" + lat + "&lng=" + lng;
    const response = await fetch(url);

    // Reptrieve response of nearby stores and their details.
    const message = await response.json();
    console.log(message);

    makeAndShowCards(message);
}

/* Make the store cards and add them to the page. */
function makeAndShowCards(message) {
    var allCards = document.getElementById('all-cards');
    // Clear previous cards
    allCards.innerHTML = "";

    for (var i = 0; i < message.length; i++) {
        // Gets information from the message from the server.
        var name = message[i]['name'];

        // Shortens name to 3 words if too long.
        if (name.length > 10) {
            var words = name.split(' ');
            if (words.length > 2) {
                name = words[0] + ' ' + words[1] + ' ' + words[2];
            }   
        }

        var placeId = message[i]['placeId'];
        var photoString = message[i]['photoString'];

        // Creates a new card, as well as a new image and name element for the card. Sets a placeid attribute in the card.
        newCard = document.createElement('div');
        newCard.setAttribute('class', 'card-background');
        newCard.setAttribute('placeId', placeId);
        newCard.setAttribute('onclick', 'clickCard(this);');
        newCard.onclick = function() {clickCard(this);};
        
        newIcon = document.createElement('img');
        newIcon.setAttribute('src', photoString);
        newIcon.setAttribute('class', 'card-image');
        newIcon.setAttribute('alt', name);

        newName = document.createElement('p');
        newName.setAttribute('class', 'card-name');
        newName.innerText = name;

        // Adds the new card and its elements to the page.
        newCard.appendChild(newIcon);
        newCard.appendChild(newName);
        allCards.appendChild(newCard);
    }
}

/* Stores the search text locally. */
function storeSearchText() {
    var searchText = document.getElementById('search-text').value;
    window.localStorage.setItem('searchText', searchText);
}

function clickCard(card) {
    var placeId = card.getAttribute('placeId');
    window.localStorage.setItem('placeId', placeId);
    window.location.href = "../shop/shop.html";
}
