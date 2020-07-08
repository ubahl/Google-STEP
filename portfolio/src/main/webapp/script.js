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


/* Changes current window to reviews.html. */
function reviewsPage() {
    window.location.href = "reviews.html";
}

/* Gets a random welcome message from /welcome serverlet and displays it in the toptext. */
async function getRandomMessage() {
  const response = await fetch('/welcome');
  const message = await response.text();
  document.getElementById('top-text').innerText = message;
}

/* Gets the reviews and reviewer name from /data servlet. */
async function getReviewData() {
  const response = await fetch('/data');
  const message = await response.json();
  reviewBox = document.getElementById('white-background');

  for(var i = 0; i < message.length; i++) {
    // Add text.
    newComment = document.createElement('div');
    newComment.setAttribute('id', 'review-text');
    newComment.innerText = message[i]['reviewText'];

    // Add reviewer info.
    newUser = document.createElement('div');
    newUser.setAttribute('id', 'reviewer-info');
    newUser.innerText = message[i]['name'];
    
    // Add review box
    newBox = document.createElement('p');
    newBox.setAttribute('id', 'review-box');
    
    // Add reviewer info and text to box, then add box to page
    newBox.appendChild(newUser);
    newBox.appendChild(newComment);
    reviewBox.appendChild(newBox);
  }
}

/* Adds a random greeting to the page. */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!', '안녕하세요!', 'नमस्ते'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

async function userLogin() {
    const response = await fetch('/login');
    const message = await response.json();
    console.log(message);
    loginMessage = document.getElementById('login-message');

    if(message['loggedIn']) {
        loginMessage.innerHTML = "<p>Hello " + message['email'] + ", <a href=\"" + message['url'] + "\">logout</a>.</p>";
    }
    else {
        loginMessage.innerHTML = "<p>Login <a href=\"" + message['url'] + "\">here</a>.</p>";
    }
}

/* Stores the search text locally. */
function storeSearchText() {
    var searchText = document.getElementById('search-text').value;
    window.localStorage.setItem('searchText', searchText);
}
