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

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    TagManager mTagManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO
        // Make sure that Analytics tracking has started
        ((MyApplication) getApplication()).startTracking();

        // Load the TagManager container
        loadGTMContainer();
    }

    // Load a TagManager container
    public void loadGTMContainer () {
        // TODO Get the TagManager
        mTagManager = ((MyApplication) getApplication()).getTagManager();

        // Enable verbose logging
        mTagManager.setVerboseLoggingEnabled(true);

        // Load the container
        PendingResult pending =
                mTagManager.loadContainerPreferFresh("GTM-123456",
                        R.raw.gtm_default);

        // Define the callback to store the loaded container
        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {

                // If unsuccessful, return
                if (!containerHolder.getStatus().isSuccess()) {
                    // Deal with failure
                    return;
                }

                // Manually refresh the container holder
                // Can only do this once every 15 minutes or so
                containerHolder.refresh();

                // Set the container holder, only want one per running app
                // We can retrieve it later as needed
                ((MyApplication) getApplication()).setContainerHolder(
                        containerHolder);

            }
        }, 2, TimeUnit.SECONDS);
    }

    public void showDinnerList (View view)  {
        // Start the activity that shows all the dinners
        startActivity(new Intent(this, ShowAllDinnersActivity.class));
    }

    /*
     * Show a pop up menu of food preferences.
     * Menu items are defined in menus/food_prefs_menu.xml
     */
    public void showFoodPrefsMenu(View view) {
        // Utility.showMyToast("I will show you a menu", this);
        android.widget.PopupMenu popup = new android.widget.PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.food_prefs_menu, popup.getMenu());

        // Set the action of the menu
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                getDinnerSuggestion(item.getItemId());
                return true;
            }
        });
        // Show the popup menu
        popup.show();
    }

    /*
     * Suggest dinner for tonight.
     * This method is invoked by the button click action of the food prefs menu.
     * We use the Dinner class to figure out the dinner, based on the food pref.

     */
    public String getDinnerSuggestion(int item) {

        // Get a new Dinner, and use it to get tonight's dinner
        Dinner dinner = new Dinner(this, item);
        String dinnerChoice = dinner.getDinnerTonight();
        // Utility.showMyToast("dinner suggestion: " + dinnerChoice, this);

        // Start an intent to show the dinner suggestion
        // Put the suggested dinner in the Intent's Extras bundle
        Intent dinnerIntent = new Intent(this, ShowDinnerActivity.class);

        dinnerIntent.putExtra(String.valueOf(R.string.selected_dinner), dinnerChoice);
        startActivity(dinnerIntent);

        return dinnerChoice;
    }

    public void showDailySpecial(View view) {
        // Show the food preference menu
        android.widget.PopupMenu popup = new android.widget.PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.food_prefs_menu, popup.getMenu());

        // Set the action of the menu
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Save the food pref in the data layer
                putFoodPrefInDataLayer (item);
                // Show the daily special
                startShowDailySpecialActivity();
                return true;
            }});
        // Show the popup menu
        popup.show();
    }


    public void putFoodPrefInDataLayer (MenuItem item) {
        TagManager myGTM = ((MyApplication) getApplication()).getTagManager();

        DataLayer dl = myGTM.getDataLayer();
        String selectedFoodPref = "unrestricted";
        switch (item.getItemId()) {
            case R.id.vegan_pref:
                selectedFoodPref = "vegan";
                break;
            case R.id.vegetarian_pref:
                selectedFoodPref = "vegetarian";
                break;
            case R.id.fish_pref:
                selectedFoodPref = "fish";
                break;
            case R.id.meat_pref:
                selectedFoodPref = "meat";
                break;
            case R.id.unrestricted_pref:
                selectedFoodPref = "unrestricted";
                break;
            default:
                break;
        }
        dl.push("food_pref", selectedFoodPref);
    }

    public void startShowDailySpecialActivity ()
    {
        // Start an activity to show the daily special
        startActivity(new Intent(this, ShowDailySpecialActivity.class));

        // Get the data layer
        DataLayer dl = mTagManager.getDataLayer();

        // Push an event into the data layer
        // which will trigger sending a hit to Analytics

        dl.pushEvent("openScreen",
            DataLayer.mapOf(
                "screen-name", "Show Daily Special"));
    }
}

