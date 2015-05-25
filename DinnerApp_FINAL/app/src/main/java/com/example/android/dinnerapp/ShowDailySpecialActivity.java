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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tagmanager.ContainerHolder;

public class ShowDailySpecialActivity extends Activity {

    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);

    public String mDailySpecial = "Fried egg with kit kat rashers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_daily_special);

        // Set the heading of the info box
        TextView heading_tv = (TextView) findViewById(R.id.textView_info_heading);
        // Should really be a string resource
        heading_tv.setText("Today's special");

        updateDailySpecial();
    }

    public void updateDailySpecial () {
        // Get the container holder
        ContainerHolder holder =
                ((MyApplication) getApplication()).getContainerHolder();

        // Get the daily special from the container holder
        // The keys need to match exactly the keys you set in the Tag Manager interface
        mDailySpecial = holder.getContainer().getString("daily-special");

        // Get the text view we are using in this activity
        TextView tv_daily_special = (TextView) findViewById(R.id.textView_info);

        // Set the text in the text view
        tv_daily_special.setText(mDailySpecial);
    }


    public void orderOnline (View view) {
        // Start an intent to allow the user to order dinner online
        Intent intent = new Intent(this, OrderDinnerActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, mDailySpecial);
        startActivity(intent);
    }
}
