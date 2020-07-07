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
    // Get the text from the search box.
    var zipCode = document.getElementById("search-text").value;
    console.log(zipCode);

    // Use a GET request to send it to /search.
    var url = '/search?zipCode=' + zipCode;
    const response = await fetch(url);

    // Retrieve response of nearby stores and their details.
    const message = await response.json();
    console.log(message);

    // TODO: Add and make the cards.
}
