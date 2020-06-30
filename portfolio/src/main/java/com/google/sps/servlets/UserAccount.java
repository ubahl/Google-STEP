/* UserAccount Class:
Holds the contents of a User's Account:
- Nickname
- Email
- User ID
*/

package com.google.sps.servlets;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.gson.Gson;

public class UserAccount {
    boolean loggedIn = false;
    String nickname = "";
    String email = "";
    String userID = "";
    String url = "";


    public UserAccount(UserService userService, User curr_user, String url) {
        loggedIn = true;
        nickname = curr_user.getNickname();
        email = curr_user.getEmail();
        userID = curr_user.getUserId();
        this.url = userService.createLogoutURL(url);
    }

    public UserAccount(UserService userService, String url) {
        boolean loggedIn = false;
        String nickname = "";
        String email = "";
        String userID = "";
        this.url = userService.createLoginURL(url);
    }

    /* Convert User object to JSON */
    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
