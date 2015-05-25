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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

public class ShowDinnerActivity extends Activity {

    TextView tv;
    String mDinner;
    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_dinner_suggestion);
    }

    protected void onStart() {
        super.onStart();

        // Set the heading of the info box
        TextView heading_tv = (TextView) findViewById(R.id.textView_info_heading);
        heading_tv.setText(getResources().getText(R.string.dinner_heading));

        // Get the text view
        tv = (TextView) findViewById(R.id.textView_info);

        // Get the dinner suggestion out of the intent
        Intent myIntent = getIntent();
        mDinner = myIntent.getStringExtra(selectedDinnerExtrasKey);
        tv.setText(mDinner);
    }

    public void orderOnline (View view) {
        // Start an intent to allow the user to order dinner online
        Intent intent = new Intent(this, OrderDinnerActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, mDinner);
        startActivity(intent);
    }

    public void removeMeal (View view) {
        // Start an intent to remove the dinner suggestion
        Intent intent = new Intent(this, RemoveMealActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, mDinner);
        startActivity(intent);

        // Send data to Tag Manager
        // Get the data layer
        TagManager tagManager = ((MyApplication) getApplication()).getTagManager();
        DataLayer dl = tagManager.getDataLayer();

        // Push an event into the data layer
        // which will trigger sending a hit to Analytics

        dl.pushEvent("openScreen",
                DataLayer.mapOf(
                        "screen-name", "Dislike Dinner",
                        "selected-dinner", mDinner));

        /*
        // Send a hit to Analytics
        // Create a tracker
        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        // Send an event to Google Analytics
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Dinner actions")
                .setAction("Dislike dinner choice")
                .setLabel(mDinner)
                .build());
                */
    }

    public void showRecipe (View view) {
        // Start an intent to show the recipe for the dinner suggestion
        Intent intent = new Intent(this, ShowRecipeActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, mDinner);
        startActivity(intent);
    }

    // Choose another dinner suggestion
    public void chooseAgain(View view) {
        // Utility.showMyToast("I will show you a menu", this);
        android.widget.PopupMenu popup = new android.widget.PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.food_prefs_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                setDinnerSuggestion(item.getItemId());
                return true;
            }
        });
        // show the popup menu
        popup.show();
    }

    // Get and show a new suggestion for dinner
    // based on the food preference
    public void setDinnerSuggestion(int item) {

        Dinner dinner = new Dinner(this, item);
        mDinner = dinner.getDinnerTonight();
        tv.setText(mDinner);
    }


}
