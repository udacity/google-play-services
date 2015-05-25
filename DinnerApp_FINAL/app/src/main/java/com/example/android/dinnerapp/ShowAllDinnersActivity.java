/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.dinnerapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.HitBuilders;

public class ShowAllDinnersActivity extends ListActivity {

    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all_dinners);

        // Start timing how long it takes to display the list of products
        long startTime = System.nanoTime();

        // Get the array of all the dinners
        Dinner dinner = new Dinner();
        String [] allDinners = dinner.getAllDinners(this);

        // Create an array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.show_dinner_in_row, R.id.textview_dinner_row, allDinners);

        // Attach the ArrayAdapter to the list view
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        // Stop timing how long it takes to display the list of products
        long stopTime = System.nanoTime();

        long elapsedTime = (stopTime - startTime) / 1000000;
        /*
        Utility.showMyToast("Elapsed time -- " + Long.toString(elapsedTime)
                + "milliseconds", this);
                */

        // Report to analytics how long it took to display this list
        sendAnalyticsTimingHit(elapsedTime);
    }

    public void sendAnalyticsTimingHit (long t ) {
        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        // Build and send timing data
        tracker.send(new HitBuilders.TimingBuilder()
                .setCategory("List all dinners")
                .setValue(t)
                .setLabel("display duration")
                .setVariable("duration")
                .build());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        // Do something when a list item is clicked
        super.onListItemClick(l, v, position, id);

        String value = (String) getListView().getItemAtPosition(position);

        // Utility.showMyToast("selected dinner is " + value, this);
        Intent intent = new Intent(this, OrderDinnerActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, value);

        startActivity(intent);
    }


}
