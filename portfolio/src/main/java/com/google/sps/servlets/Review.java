/* Review Class:
Holds the contents of a Review:
- Name
- Review Text
*/

package com.google.sps.servlets;

public class Review {
    String reviewText = "";
    String name = "";

    public Review(String name, String reviewText)
    {
        this.reviewText = reviewText;
        this.name = name;
    }
}
