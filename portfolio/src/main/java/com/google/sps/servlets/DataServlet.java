 
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

package com.google.sps.servlets;


import com.google.sps.data.UserComment;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@WebServlet("/data")
public class DataServlet extends HttpServlet {
    ArrayList<UserComment> comments = new ArrayList<UserComment>();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); //creates database


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
      
    Query query = new Query("Comments").addSort("Date", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      String username = (String) entity.getProperty("Username");
      String message = (String) entity.getProperty("Message");
     Date date = (Date) entity.getProperty("Date");
      UserComment temp = new UserComment(username, message, date);
      comments.add(temp);
    }


    String json = convertToJsonUsingGson(comments);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }


  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    //gets user data from html form

     UserService userService = UserServiceFactory.getUserService();
     String email = userService.getCurrentUser().getEmail();
    // String userName = request.getParameter("username"); //"username" is html input name;
     String message = request.getParameter("message");
    Date timestamp = new Date();

    Entity commentEntity = new Entity("Comments"); //creates entitiy that stores properties similar to a data structure
    commentEntity.setProperty("Username", email); //sets form data to entry
    commentEntity.setProperty("Message", message);
    commentEntity.setProperty("Date", timestamp);

    datastore.put(commentEntity); //pushes new comment to datastore

    response.sendRedirect("/index.html");
  }

  private String convertToJsonUsingGson(ArrayList<UserComment>  comments) {
    Gson gson = new Gson();
    String json = gson.toJson(comments);
    return json;
  }
}