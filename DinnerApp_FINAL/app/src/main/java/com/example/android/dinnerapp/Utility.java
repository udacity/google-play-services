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

import android.content.Context;
import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Utility {

    public static void showMyToast (String toastText, Context appContext) {

        // Show a toast
        // Context context = getApplicationContext();
        // CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(appContext, toastText, duration);
        toast.show();

    }

    public static String[] combine(String[] a, String[] b){
        int length = a.length + b.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static String[] combine(String[] a, String[] b, String[] c, String[] d){
        return combine(combine(a, b), combine(c, d));
    }

    // The ID of a dinner is encoded in the first two characters
    public static String getDinnerId (String dinner) {

        return dinner.substring(0,2);
    }

    // Gets the current time as a String
    public static String getCurrentTime () {
        Date curDate = new Date();
        SimpleDateFormat format = format = new SimpleDateFormat("dd-M hh:mm:ss");
        return format.format(curDate);
    }

    public static String getUniqueTransactionId (String productId) {
        return ("T-" + getCurrentTime() + productId );
    }


}
