package com.example.android.justjava;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    CheckBox whippedCreamCheckBox, chocolateCheckBox;

    Boolean hasWhippedCream, hasChocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        // Figure out if the user wants whipped cream topping
        whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants choclate topping
        chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        hasChocolate = chocolateCheckBox.isChecked();

        // Get user's name
        EditText nameEditText = (EditText) findViewById(R.id.name_field);
        String name = nameEditText.getText().toString();

        // Display the order summary on the screen
        String message = getString(R.string.order_summary_name, name) + "\n" +
                getString(R.string.order_summary_whipped_cream, hasWhippedCream) + "\n" +
                getString(R.string.order_summary_chocolate, hasChocolate) + "\n" +
                "Quantity :" + quantity + "\n" +
                getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(calculatePrice()))
                + "\n" + getString(R.string.thank_you);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */

    private int calculatePrice() {

        // First calculate the price of one cup of coffee
        int priceBase = 5;

        // If the user wants whipped cream, add $1 per cup
        if (hasWhippedCream)
            priceBase += 1;

        // If the user wants chocolate, add $2 per cup
        if (hasChocolate)
            priceBase += 2;

        // Calculate the total order price by multiplying by the quantity
        return priceBase * quantity;
    }

    /**
     * This method increases the given quantity value by one on the screen.
     */
    public void increment(View view) {
        if (quantity < 100)
            quantity++;
        else
            Toast.makeText(MainActivity.this, R.string.toast_for_demand_more_100_coffees, Toast.LENGTH_SHORT).show();
        displayQuantity(quantity);
    }

    /**
     * This method decreases the given quantity value by one  on the screen.
     */
    public void decrement(View view) {
        if (quantity >= 2)
            quantity--;
        else
            Toast.makeText(MainActivity.this, R.string.toast_for_demand_less_than_1_coffee, Toast.LENGTH_SHORT).show();
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(" " + number);
    }
}

