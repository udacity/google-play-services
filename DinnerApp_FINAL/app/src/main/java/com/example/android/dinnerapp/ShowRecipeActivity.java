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

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.HitBuilders;



public class ShowRecipeActivity extends Activity {

    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_info);
    }

    protected void onStart() {
        super.onStart();

        // Get tracker.
        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        // Set screen name.
        tracker.setScreenName("Show recipe screen");

        // Send a screen view.
        tracker.send(new HitBuilders.ScreenViewBuilder()
                .build());

        // Set the heading
        TextView heading_tv = (TextView) findViewById(R.id.textView_info_heading);
        heading_tv.setText(getResources().getText(R.string.show_recipe_heading));

        // Set the text
        TextView tv = (TextView) findViewById(R.id.textView_info);
        String dinner = getIntent().getStringExtra(selectedDinnerExtrasKey);
        tv.setText(dinner + "\n\n" + getResources().getText(R.string.recipe));
    }

}
