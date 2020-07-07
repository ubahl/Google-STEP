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

// import {key} from "./googleapikey.js";
// const { key } = require('./googleapikey');

async function getSearchResults()
{
    // get what was searched
    var zipCode = document.getElementById("search-text").value;
    console.log(zipCode);

    // post it to /search
    var url = '/search?zipCode=' + zipCode;
    const response = await fetch(url);

    // retrieve response
    const message = await response.text();
    console.log(message);

    // // get lattitude and logitude of zip code
    // var url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zipCode + "&key=" + key;
    // const reponse = await fetch(url);
    // const message = await response.json();
    // console.log(message);

    // // post it to /search
    // var url = '/search?zipCode=' + zipCode;
    // const response = await fetch(url);

    // // retrieve response
    // const message = await response.text();
    // console.log(message);

    // For card: picture, store name, rating, place id
    // add and make the cards


}
