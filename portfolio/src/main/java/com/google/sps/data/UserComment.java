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

package com.google.sps.data;
import java.util.Date;

/** Class containing server statistics. */
public final class UserComment {

    String userName;
    Date postDate; 
    String userMessage;

  public UserComment(String userName, String userMessage) {
    this.userName = userName;

    this.postDate = new Date();
    this.userMessage = userMessage;
  }

  public UserComment(String userName, String userMessage, Date time) {
    this.userName = userName;
    this.postDate = time;
    this.userMessage = userMessage;
  }

   public UserComment() {
    this.postDate = null;
    this.userName = null;
    this.userMessage = null;
  }

  public String getUserName() {
    return userName;
  }

  public Date getDate() {
    return postDate;
  }

  public String getUserMessage() {
    return userMessage;
  }
}