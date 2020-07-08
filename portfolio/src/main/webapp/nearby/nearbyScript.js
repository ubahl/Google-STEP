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

async function getSearchResults() {
    // Get the text from local storage.
    var zipCode = window.localStorage.getItem('searchText');
    console.log(zipCode);

    // Use a GET request to send it to /search.
    var url = '/search?zipCode=' + zipCode;
    const response = await fetch(url);

    // Retrieve response of nearby stores and their details.
    const message = await response.json();
    console.log(message);

    // Add and make the cards.
    allCards = document.getElementById('all-cards');

    // Clear previous cards
    allCards.innerHTML = "";

    for (var i = 0; i < message.length; i++) {
        // Gets information from the message from the server.
        var name = message[i]['name'];
        var placeId = message[i]['placeId'];
        var encodedPhotoString = message[i]['encodedPhoto'];
        var photoLoc = "data:image/png;base64," + encodedPhotoString;

        // Creates a new card, as well as a new image and name element for the card. Sets a placeid attribute in the card.
        newCard = document.createElement('div');
        newCard.setAttribute('class', 'card-background');
        newCard.setAttribute('placeId', placeId);
        
        newIcon = document.createElement('img');
        newIcon.setAttribute('src', photoLoc);
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
