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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class InterstitialActivity extends Activity {

    private Button mShowButton;
    private InterstitialAd mInterstitial;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        mShowButton = (Button) findViewById(R.id.showButton);
        mShowButton.setEnabled(false);
    }

    public void loadInterstitial(View unusedView) {
        mShowButton.setEnabled(false);
        mShowButton.setText(getResources().getString(R.string.interstitial_loading));

        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        mInterstitial.setAdListener(new ToastAdListener(this) {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mShowButton.setText(getResources().getString(R.string.interstitial_show));
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                mShowButton.setText(getErrorReason());
            }
        });

        AdRequest ar = new AdRequest.Builder().build();
        mInterstitial.loadAd(ar);
    }

    public void showInterstitial(View unusedView) {
        if (mInterstitial.isLoaded()) {
            mInterstitial.show();
        }

        mShowButton.setText(getResources().getString(R.string.interstitial_not_ready));
        mShowButton.setEnabled(false);
    }
}



