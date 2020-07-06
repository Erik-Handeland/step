 
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

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    ArrayList<UserComment> comments = new ArrayList<UserComment>();

/*
  @Override
    // Hard Coded inputs
  public void init() {
    UserComment temp1 = new UserComment("steve", "Hello World");
    comments.add(temp1);
    UserComment temp2 = new UserComment("woz", "qwerty");
    comments.add(temp2);
    UserComment temp3 = new UserComment("john", "ahel nfei flijnea");
    comments.add(temp3);
  }
*/
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    String json = convertToJsonUsingGson(comments);
    
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {


    UserComment temp = getUserComment(request);// Get the input from the form.
    comments.add(temp);

    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }

    private UserComment getUserComment(HttpServletRequest request)
    {
        String userName = request.getParameter("username"); //"username" is html input name;
        String message = request.getParameter("message");
        UserComment temp = new UserComment(userName, message);
        return temp;
    }

  private String convertToJsonUsingGson(ArrayList<UserComment>  comments) {
    Gson gson = new Gson();
    String json = gson.toJson(comments);
    return json;
  }
}