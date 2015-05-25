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
package com.example.adviewer;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

public class ToastAdListener extends AdListener {
    private Context mContext;
    private String mErrorReason;

    public ToastAdListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onAdLoaded() {
        Toast.makeText(mContext, "onAdLoaded()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdOpened() {
        Toast.makeText(mContext, "onAdOpened()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClosed() {
        Toast.makeText(mContext, "onAdClosed()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdLeftApplication() {
        Toast.makeText(mContext, "onAdLeftApplication()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdFailedToLoad(int errorCode) {
        mErrorReason = "";
        switch(errorCode) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                mErrorReason = "Internal error";
                break;
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                mErrorReason = "Invalid request";
                break;
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                mErrorReason = "Network Error";
                break;
            case AdRequest.ERROR_CODE_NO_FILL:
                mErrorReason = "No fill";
                break;
        }
        Toast.makeText(mContext,
                String.format("onAdFailedToLoad(%s)", mErrorReason),
                Toast.LENGTH_SHORT).show();
    }

    public String getErrorReason() {
        return mErrorReason == null ? "" : mErrorReason;
    }

}
