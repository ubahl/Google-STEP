package com.google.sps.servlets;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Servlet that returns user account data. */
/* If no data (not logged in), loggedIn = false. */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();

    if (userService.isUserLoggedIn()) {
        String urlToRedirectToAfterUserLogsOut = "/";
        User user = userService.getCurrentUser();
        UserAccount userAccount = new UserAccount(userService, user, urlToRedirectToAfterUserLogsOut);

        response.setContentType("application/json");
        String json = userAccount.toJson();
        response.getWriter().println(json);
    } 
    else {
        String urlToRedirectToAfterUserLogsIn = "/";
        UserAccount userAccount = new UserAccount(userService, urlToRedirectToAfterUserLogsIn);

        response.setContentType("application/json");
        String json = userAccount.toJson();
        response.getWriter().println(json);
    }
  }

}