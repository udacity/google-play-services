/*
 * Copyright (C) 2015 The Android Open Source Project
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

import android.content.Context;

import java.util.Random;

public class Dinner {
    public Random randomGen = new Random();

    String [] mealChoices;
    String foodPref;

    public Dinner() {
    }

    public Dinner(Context context, int item) {
        foodPref = getFoodPref(item);

        // Show a vegan meal?
        if (foodPref.equals("vegan")) {
            mealChoices = context.getResources().getStringArray(R.array.vegan_meals);
        }
        // Show a vegetarian meal?
        // Combine vegetarian and vegan meals
        else if (foodPref.equals("vegetarian")) {
            mealChoices = Utility.combine (
                    context.getResources().getStringArray(R.array.vegan_meals),
                    context.getResources().getStringArray(R.array.vegetarian_meals));
        }
        // Show a fish meal?
        else if (foodPref.equals("fish")) {
            mealChoices = context.getResources().getStringArray(R.array.fish_meals);
        }
        // Show a meat meal?
        else if (foodPref.equals("meat")) {
            mealChoices = context.getResources().getStringArray(R.array.meat_meals);
        }
        else {
            // No preference, so pick from all the meal choices
            mealChoices = getAllDinners(context);
    }
    }

    // Utility function to get a random choice from an array
    public String getChoiceFromArray (String [] choices) {
        return choices[randomGen.nextInt(choices.length)];
    }

    public String getDinnerTonight() {
        // Get the dinner
        String dinner = getChoiceFromArray(mealChoices);
        return dinner;
    }

    public String getFoodPref(int item) {
        String menuPref;
        switch (item) {
            case R.id.vegan_pref:
                menuPref = "vegan";
                break;
            case R.id.vegetarian_pref:
                menuPref = "vegetarian";
                break;
            case R.id.fish_pref:
                menuPref =  "fish";
                break;
            case R.id.meat_pref:
                menuPref = "meat";
                break;
            default:
                menuPref = "unrestricted";
        }
        // Utility.showMyToast(menuPref, this);
        return menuPref;
    }

    public String [] getAllDinners (Context context) {
        // Return all the meal choices
        mealChoices = Utility.combine(
                context.getResources().getStringArray(R.array.vegan_meals),
                context.getResources().getStringArray(R.array.vegetarian_meals),
                context.getResources().getStringArray(R.array.fish_meals),
                context.getResources().getStringArray(R.array.meat_meals)
        );
        return mealChoices;
    }

}


