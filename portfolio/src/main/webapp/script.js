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


/* changes current window to reviews.html */
function reviewsPage()
{
    window.location.href = "reviews.html";
}

/* gets a random welcome message from /welcome serverlet and displays it in the toptext */
async function getRandomMessage() {
  const response = await fetch('/welcome');
  const message = await response.text();
  document.getElementById('top-text').innerText = message;
}

/* gets the reviews from /data servlet */
async function getReviewData() {
  const response = await fetch('/data');
  const message = await response.json();
  reviewBox = document.getElementById('white-background');

  for(var i = 0; i < message.length; i++) {
    // reviewBox.innerHTML += "<div id='review-box'> <p id='review-text'>" + message[i] + "</p></div>";
    newComment = document.createElement('div');
    newComment.setAttribute('id', 'review-text');
    newComment.innerText = message[i]['reviewText'];

    newUser = document.createElement('div');
    newUser.setAttribute('id', 'reviewer-info');
    newUser.innerText = message[i]['name'];
    
    newBox = document.createElement('p');
    newBox.setAttribute('id', 'review-box');
    
    newBox.appendChild(newUser);
    newBox.appendChild(newComment);
    reviewBox.appendChild(newBox);
  }
}

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() 
{
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!', '안녕하세요!', 'नमस्ते'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
