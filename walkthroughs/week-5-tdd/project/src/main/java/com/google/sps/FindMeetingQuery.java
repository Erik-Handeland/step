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

package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.lang.Math;


public final class FindMeetingQuery {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

        ArrayList<TimeRange> potentialTimes = new ArrayList<>();

        //base cases///////////////////////////////////////////////////
        // requested time is larger than one day
        if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) 
        {
            return potentialTimes;
        }
        //No events
        if (events.size() == 0)
        {
            potentialTimes.add(TimeRange.WHOLE_DAY); 
            return potentialTimes;
        }

        ArrayList<TimeRange> timeAvailable = new ArrayList<>();
        timeAvailable = getAvailableTimes(events, request, timeAvailable);

        potentialTimes = getPotentialTimes(timeAvailable, request); 

        return potentialTimes;
    }

   //takes available times and meeting request and returns potential times
    public ArrayList<TimeRange> getPotentialTimes(ArrayList<TimeRange> timeRanges,MeetingRequest request) {
        Collections.sort(timeRanges, TimeRange.ORDER_BY_START);
        int len = timeRanges.size();

        ArrayList<TimeRange> queries = new ArrayList<>();
        if (len == 0) {
            queries.add(TimeRange.WHOLE_DAY); 
            return queries;
        }
        if (len == 1) {
             queries.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, timeRanges.get(0).start(), false));
             queries.add(TimeRange.fromStartEnd(timeRanges.get(0).end(), TimeRange.END_OF_DAY, true));
            return queries;
        }
        
        //remove overlaps
        TimeRange temp = timeRanges.get(0);
        int index = 1;
        while (index < len) {
            if (index == timeRanges.size()) {
                break;
            }
            else if (temp.overlaps(timeRanges.get(index))) {
                int start = temp.start();
                int end = Math.max(temp.end(), timeRanges.get(index).end());
                timeRanges.remove(index);
                timeRanges.remove(index - 1);
                timeRanges.add(index - 1, TimeRange.fromStartEnd(start, end, false));
                temp = timeRanges.get(index - 1);
                len--;
            } 
            else {
                index++;
            }
        }

        int start = TimeRange.START_OF_DAY;
        for (int i = 0; i < timeRanges.size(); i++) {
            if (timeRanges.get(i).start() - start >= request.getDuration()) {
                queries.add(TimeRange.fromStartEnd(start, timeRanges.get(i).start(), false));
            }
            start = timeRanges.get(i).end();
        }

        if (TimeRange.END_OF_DAY - timeRanges.get(timeRanges.size() - 1).end() >= request.getDuration()) {
            queries.add(TimeRange.fromStartEnd(timeRanges.get(timeRanges.size() - 1).end(), TimeRange.END_OF_DAY, true));
        }

        return queries;
    }

    //gets list of available times for  attendees 
    public ArrayList<TimeRange> getAvailableTimes(Collection<Event> events, MeetingRequest request, ArrayList<TimeRange> timeRanges) {
    for (Event item : events) {
            for (String s : request.getAttendees()) {
                if (item.getAttendees().contains(s)) {
                    timeRanges.add(item.getWhen());
                }
            }
        }
        return timeRanges;
    }
}