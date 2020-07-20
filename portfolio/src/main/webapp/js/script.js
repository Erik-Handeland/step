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

// Messages
function getMessages() {
    fetch('/data').then(response => response.text()).then((message) => {
    var data = eval(message); 

    for (i in data)
    {
        var li = document.createElement("li");
        li.innerHTML = "<span id='username' >" + data[i].userName+ "</span>" + 
        "<span id='date'>   &nbsp;" + data[i].postDate + "</span> <br>" 
        + "<span id='message'>" + data[i].userMessage + "</span>";
        document.getElementById("comments").appendChild(li);

    }

  });
}


// Charts

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

/** Creates a chart and adds it to the page. */
function drawChart() {
  const data = new google.visualization.DataTable();
  data.addColumn('string', 'Month');
  data.addColumn('number', 'Count');
        data.addRows([
          ['March', 10],
          ['April', 5],
          ['May', 15]
        ]);

  const options = {
    'title': 'Comment Count',
    'width':500,
    'height':400
   // 'position':center
  };

  const chart = new google.visualization.PieChart(
      document.getElementById('chart-container'));
  chart.draw(data, options);
}

//Blobstore

function fetchBlobstoreUrlAndShowForm() {
  fetch('/blobstore-upload-url')
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const messageForm = document.getElementById('my-form');
        messageForm.action = imageUploadUrl;
        messageForm.classList.remove('hidden');
      });
}