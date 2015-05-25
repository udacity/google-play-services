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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;


public class OrderDinnerActivity extends Activity {
    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);

    String thisDinner;
    String thisDinnerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_dinner);
    }

    protected void onStart() {
        super.onStart();

        // Set the heading
        TextView heading_tv = (TextView) findViewById(R.id.textView_info_heading);
        heading_tv.setText(getResources().getText(R.string.order_online_heading));

        // Set the text
        TextView tv = (TextView) findViewById(R.id.textView_info);

        String dinner = getIntent().getStringExtra(selectedDinnerExtrasKey);
        tv.setText("This is where you will order the selected dinner: \n\n" +
                dinner);
        String dinnerId = Utility.getDinnerId(dinner);

        thisDinner = dinner;
        thisDinnerId = dinnerId;

        // TODO update sendViewProductHit to get dinner and dinnerId
        // out of the class variables
        sendViewProductHit(dinner, dinnerId);
    }

    public void sendViewProductHit(String dinner, String dinnerId) {
        Product product = new Product()
                .setName("dinner")
                .setPrice(5)
                .setVariant(dinner)
                .setId(dinnerId)
                .setQuantity(1);

        ProductAction productAction =
                new ProductAction(ProductAction.ACTION_DETAIL);

        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("View Order Dinner screen")
                .setLabel(dinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }

    public void addDinnerToCart (View view) {
        // Code goes here to add the dinner to the cart
        // do not implement now!
        Utility.showMyToast("I will add the dinner " +
                thisDinner + "to the cart", this);

        // Also send an Analytics hit
        sendAddToCartHit();

        // Show the start checkout button
        Button button = (Button) findViewById(R.id.start_checkout_btn);
        button.setVisibility(View.VISIBLE);

        // Hide this add to cart button
        button = (Button) findViewById(R.id.add_to_cart_btn);
        button.setVisibility(View.INVISIBLE);
    }


    public void sendAddToCartHit() {
        Product product = new Product()
                .setName("dinner")
                .setPrice(5)
                .setVariant(thisDinner)
                .setId(thisDinnerId)
                .setQuantity(1);

        ProductAction productAction =
                new ProductAction(ProductAction.ACTION_ADD);

        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("Add dinner to cart")
                .setLabel(thisDinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }

    public void startCheckout (View view) {
        // Code goes here to add the dinner to the cart
        // do not implement now!
        Utility.showMyToast("You have started the checkout process", this);

        // Also send an Analytics hit
        sendStartCheckoutHit();

        // Show and hide buttons appropriately
        Button button = (Button) findViewById(R.id.start_checkout_btn);
        button.setVisibility(View.INVISIBLE);

        button = (Button) findViewById(R.id.checkout_step_2_btn);
        button.setVisibility(View.VISIBLE);
    }

    // Start checkout
    // We are faking the cart
    // Assume that the currently selected dinner is in the cart
    public void sendStartCheckoutHit() {
        Product product = new Product()
                .setName("dinner")
                .setPrice(5)
                .setVariant(thisDinner)
                .setId(thisDinnerId)
                .setQuantity(1);

        ProductAction productAction =
                new ProductAction(ProductAction.ACTION_CHECKOUT);

        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("Start checkout")
                .setLabel(thisDinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }

    public void getPaymentInfo (View view) {
        // Code goes here to add the dinner to the cart
        // do not implement now!
        Utility.showMyToast("Give me your payment info", this);

        // Also send an Analytics hit
        sendPaymentInfoHit();

        // Show and hide buttons appropriately
        Button button = (Button) findViewById(R.id.checkout_step_2_btn);
        button.setVisibility(View.INVISIBLE);

        button = (Button) findViewById(R.id.purchase_btn);
        button.setVisibility(View.VISIBLE);
    }

    public void sendPaymentInfoHit() {
        Product product = new Product()
                .setName("dinner")
                .setPrice(5)
                .setVariant(thisDinner)
                .setId(thisDinnerId)
                .setQuantity(1);

        ProductAction productAction =
                new ProductAction(ProductAction.ACTION_CHECKOUT_OPTION)
                        .setCheckoutStep(2);

        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("Get payment")
                .setLabel(thisDinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }

    public void purchaseCart (View view) {
        // Code goes here to add the dinner to the cart
        // do not implement now!
        Utility.showMyToast("Purchasing now!", this);

        // Also send an Analytics hit
        sendPurchaseHit();
    }

    // Assume that the currently selected dinner is in the cart
    public void sendPurchaseHit() {

        // In production code, would need to iterate
        // over all the products in the cart
        // Here we assume that the currently selected dinner
        // is the only thing in the cart
        Product product = new Product()
                .setName("dinner")
                .setPrice(5)
                .setVariant(thisDinner)
                .setId(thisDinnerId)
                .setQuantity(1);

        // Get a unique transaction ID
        String tID = Utility.getUniqueTransactionId(thisDinnerId);
        ProductAction productAction =
                new ProductAction(ProductAction.ACTION_PURCHASE)
                        .setTransactionId(tID);

        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("Purchase")
                .setLabel(thisDinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }

}


