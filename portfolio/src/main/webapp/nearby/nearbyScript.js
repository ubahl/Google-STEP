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
function setUp() {
    allCards = document.getElementById('all-cards');

    // Adds a loading box.
    loadingBox = document.createElement('div');
    loadingBox.setAttribute('id', 'loading-box');

    loadingText = document.createElement('p');
    loadingText.setAttribute('id', 'loading-text');

    var message = "";

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(getSearchResults, noLocation);
        message = "Loading results...";
    } else {
        message = "We can't find you!";
    }

    loadingText.innerText = message;

    loadingBox.appendChild(loadingText);
    allCards.appendChild(loadingBox);
}

/* If the user declines geolocation. */
function noLocation(error) {
    loadingText = document.getElementById('loading-text');
    loadingText.innerText = "We can't find you!";
}

/* If the user allows geolocation, send a GET request to the server with the search term and location. */
async function getSearchResults(position) {
    // Get the text from local storage.
    var searchText = window.localStorage.getItem('searchText');
    // Prepare search text to be GET string query. Add 'boba' to the end of the query.
    // Add a space inbetween if there is text entered.
    searchText = searchText.replace(/ /g, '%20');
    if (searchText.length != 0) {
        searchText += '%20';
    }
    searchText += 'boba';
    console.log('Search Text: ' + searchText);

    // Use a GET request to send text and location to /search.
    var lat = position.coords.latitude;
    var lng = position.coords.longitude;
    console.log('Lat: ' + lat + 'Lng: ' + lng);
    var url = '/search?searchText=' + searchText + "&lat=" + lat + "&lng=" + lng;
    const response = await fetch(url);
    
    // Retrieve response of nearby stores and their details.
    const message = await response.json();
    console.log(message);

    makeAndShowCards(message);
}

/* Make the store cards and add them to the page. */
function makeAndShowCards(message) {
    var allCards = document.getElementById('all-cards');

    // Clear previous cards.
    allCards.innerHTML = "";

    for (var i = 0; i < message.length; i++) {
        // Gets information from the message from the server.
        var name = message[i]['name'];
        var placeId = message[i]['placeId'];
        var photoString = message[i]['photoString'];
        var rating = message[i]['rating'];
        var openNow = message[i]['openNow'];
        var lat = message[i]['latLng']['lat'];
        var lng = message[i]['latLng']['lng'];
        var vicinity = message[i]['vicinity'];

        // Shortens name to 3 words if too long.
        var clippedName = name;
        if (name.length > 10) {
            var words = name.split(' ');
            if (words.length > 2) {
                clippedName = words[0] + ' ' + words[1] + ' ' + words[2];
            }   
        }

        // Extracts city from vicinity
        var vicinitySplit = vicinity.split(/,\s*/);
        if(vicinitySplit.length > 0) {
            vicinity = vicinitySplit[1];
            console.log('city: ' + vicinity);
        }

        // Creates a new card, as well as a new image and name element for the card. 

        // Creates card background.
        newCard = document.createElement('div');
        newCard.setAttribute('class', 'card-background');
        newCard.setAttribute('onclick', 'clickCard(this);');
        newCard.onclick = function() {clickCard(this);};
        // Sets placeid, openNow, rating, name, and latLng attributes in the card.
        newCard.setAttribute('placeId', placeId);
        newCard.setAttribute('openNow', openNow);
        newCard.setAttribute('rating', rating);
        newCard.setAttribute('name', name);
        newCard.setAttribute('lat', lat);
        newCard.setAttribute('lng', lng);
        
        // Creates image in card.
        newIcon = document.createElement('img');
        newIcon.setAttribute('src', photoString);
        newIcon.setAttribute('class', 'card-image');
        newIcon.setAttribute('alt', name);

        // Adds store name to card.
        newName = document.createElement('p');
        newName.setAttribute('class', 'card-name');
        newName.innerText = clippedName;

        // Adds city name to card.
        newCity = document.createElement('p');
        newCity.setAttribute('class', 'card-loc');
        newCity.innerText = vicinity;

        // Adds the new card and its elements to the page.
        newCard.appendChild(newIcon);
        newCard.appendChild(newName);
        newCard.appendChild(newCity);
        allCards.appendChild(newCard);
    }
}

/* Stores the search text locally. */
function storeSearchText() {
    var searchText = document.getElementById('search-text').value;
    window.localStorage.setItem('searchText', searchText);
}

/* On click, store placeId, rating, openNow, lat, and lng. */
function clickCard(card) {
    
    var placeId = card.getAttribute('placeId');
    window.localStorage.setItem('placeId', placeId);

    var rating = card.getAttribute('rating');
    window.localStorage.setItem('rating', rating);

    var name = card.getAttribute('name');
    window.localStorage.setItem('name', name);

    var openNow = card.getAttribute('openNow');
    window.localStorage.setItem('openNow', openNow);

    var lat = card.getAttribute('lat');
    window.localStorage.setItem('lat', lat);

    var lng = card.getAttribute('lng');
    window.localStorage.setItem('lng', lng);

    window.location.href = "../shop/shop.html";
}
