package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@WebServlet("/index")
public class FormServlet extends HttpServlet {

      @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    PrintWriter out = response.getWriter();
    out.println("<h1>Shoutbox</h1>");

    // Only logged-in users can see the form
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
    out.println("<p>Hello " + userService.getCurrentUser().getEmail() + "!</p>");
      out.println("<p>Type a message and click submit:</p>");
      out.println("<form method=\"POST\" action=\"/data\">");
      out.println("<textarea name=\"text\"></textarea>");
      out.println("<br/>");
      out.println("<button>Submit</button>");
      out.println("</form>");
    // out.getElementById("form").innerHTML = "hello";
    /*out.getElementById("form").innerHTML = "<p>Hello <p>Type a message and click submit:</p>
    <form action='/data' method='POST'>
    label for='message'>Message:</label><br>
    <input type='text' name='message'><br>
    <br><button>Submit</button>
    </form>"; */
   
    } else {
      String loginUrl = userService.createLoginURL("/shoutbox");
      out.println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
    }
   
  }
}

