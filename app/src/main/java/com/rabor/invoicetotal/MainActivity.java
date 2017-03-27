package com.rabor.invoicetotal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
implements OnEditorActionListener {

    // define member variables for the widgets
    private EditText mSubtotalET;
    private TextView mDiscountPercentTV;
    private TextView mDiscountAmountTV;
    private TextView mTotalTV;

    private String mSubtotalString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to the widget
        mSubtotalET = (EditText) findViewById(R.id.mSubtotalET);
        mDiscountPercentTV = (TextView) findViewById(R.id.mDiscountPercentTV);
        mDiscountAmountTV = (TextView) findViewById(R.id.mDiscountAmountTV);
        mTotalTV = (TextView) findViewById(R.id.mTotalTV);

        // set the listener for the event
        mSubtotalET.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

        calculateAndDisplay();

        // hide soft keyboard
        return false;
    }

    private void calculateAndDisplay() {

        // get subtotal from user
        mSubtotalString = mSubtotalET.getText().toString();
        float subtotal;
        if(mSubtotalString.equals("")) {
            subtotal = 0;
        } else {
            subtotal = Float.parseFloat(mSubtotalString);
        }

        // get discount percent
        float discountPercent = 0;
        if(subtotal >= 200) {
            discountPercent = .2f;
        } else if(subtotal >= 100) {
            discountPercent = .1f;
        } else {
            discountPercent = 0;
        }

        // calculate discount
        float discountAmount = subtotal * discountPercent;
        float total = subtotal - discountAmount;

        // display data on the layout
        NumberFormat percent = NumberFormat.getPercentInstance();
        mDiscountPercentTV.setText(percent.format(discountPercent));

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        mDiscountAmountTV.setText(currency.format(discountAmount));
        mTotalTV.setText(currency.format(total));

    }

    public void resetClick(View view) {

        switch (view.getId()) {
            case R.id.resetImageView:
                mSubtotalET.setText("");
                mDiscountPercentTV.setText("00%");
                mDiscountAmountTV.setText("$0.00");
                mTotalTV.setText("$0.00");
                break;
        }
    }
}
