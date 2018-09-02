package com.example.coffeeorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Constant Values For Coffee Price And Flavors
    private static final int COFFEE_PRICE = 3;
    private static final int CREAM_FLAVOR = 2;
    private static final double CHOCOLATE_FLAVOR = 2.5;
    // Constant Values For Keys Used Bundles.
    private static final String QUANTITY_KEY = "quantity";
    private static final String TOTAL_PRICE_KEY = "total_price";
    private static final String QUANTITY_NUM_KEY = "number_of_coffees";
    private static final String REPORT_KEY = "report";
    // Variables Holds Values From ProfileActivity.
    private String name, email, phoneNum;

    private TextView coffeeQuantity, totalPrice, finalReport;
    private RadioButton chocolate;
    private int quantity = 0;
    private CardView aboutUs;
    private boolean sendMail = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find Local References For The Views
        Button increaseBtn = findViewById(R.id.increase_quantity);
        Button decreaseBtn = findViewById(R.id.decrease_quantity);
        Button orderBtn = findViewById(R.id.order_button);
        Button hideDetails = findViewById(R.id.hide);
        RadioButton cream = findViewById(R.id.cream);
        // Find Global References For The Views
        coffeeQuantity = findViewById(R.id.quantity);
        totalPrice = findViewById(R.id.total_price);
        finalReport = findViewById(R.id.total_report);
        chocolate = findViewById(R.id.chocolate);
        aboutUs = findViewById(R.id.about_card_view);

        // Save Activity State For The Life Cycle
        doState(savedInstanceState);

        // Action For Increase Button Clicked
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity < 50) {
                    quantity++;
                    showQuantity(quantity);
                    showPrice(quantity);
                } else {
                    Toast.makeText(MainActivity.this, R.string.check_over_required, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Action For Decrease Button Clicked
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity == 0) {
                    Toast.makeText(MainActivity.this, R.string.check_zero_required, Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    showQuantity(quantity);
                    showPrice(quantity);
                }
            }
        });

        hideDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutUs.setVisibility(View.INVISIBLE);
            }
        });
        // Action For Radio Button Clicks
        chocolate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrice(quantity);
            }
        });
        cream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrice(quantity);
            }
        });

        name = getIntent().getStringExtra(ProfileActivity.NAME_KEY);
        email = getIntent().getStringExtra(ProfileActivity.EMAIL_KEY);
        phoneNum = getIntent().getStringExtra(ProfileActivity.PHONE_KEY);

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity != 0) {
                    checkInputs();
                    displayReport();
                    if (sendMail) {
                        // TODO : Ask The User Within Dialog If He Need To Send E-Mail With Total Report.
                        // Placeholder For Debugging.
                        Toast.makeText(MainActivity.this, "Email Will be Sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "No Mail Provided", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Please Add Coffees To Make Order", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

    }

    /**
     * This Will Format The Whole Order And View It Into finalReport View From {@link MainActivity}
     */
    private void displayReport() {
        String report = "Hello:\t" + name
                + "\nEmail:\t" + email
                + "\nPhone Number:\t" + phoneNum
                + "\nNumber Of Coffees:\t" + quantity
                + "\nFlavor:\t" + flavorSelected()
                + "\nTotal Price:\t" + totalPrice.getText().toString();
        finalReport.setText(report);
    }

    /**
     * @return The Selected Flavor Name
     */
    private String flavorSelected() {
        if (chocolate.isChecked()) {
            return "Chocolate";
        } else return "Cream";
    }

    /**
     * This Will Check If The Inserted Inputs Are Empty Or Not
     * If They Are, Then They Will Generate Sample Text
     */
    private void checkInputs() {
        if (name == null || name.isEmpty()) {
            name = "Customer" + new Random(1000).nextInt();
        }
        if (phoneNum == null || phoneNum.isEmpty()) {
            phoneNum = "No Phone Number";
        }
        if (email == null || email.isEmpty()) {
            email = "No Mail";
            sendMail = !sendMail;
        }
    }

    /**
     * This Method Is Responsible For Getting Saved Instances When Activity is recycled.
     *
     * @param savedInstanceState saved bundles.
     */
    private void doState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            quantity = savedInstanceState.getInt(QUANTITY_NUM_KEY);
            coffeeQuantity.setText(savedInstanceState.getString(QUANTITY_KEY));
            totalPrice.setText(savedInstanceState.getString(TOTAL_PRICE_KEY));
            finalReport.setText(savedInstanceState.getString(REPORT_KEY));
        }
    }

    /**
     * Show The Number Of Coffees
     *
     * @param numberOfCoffees current number
     */
    private void showQuantity(int numberOfCoffees) {
        coffeeQuantity.setText(NumberFormat.getInstance().format(numberOfCoffees));
    }

    /**
     * Will Calculate The Whole Price And Show it
     *
     * @param coffees number of coffees
     */
    private void showPrice(int coffees) {
        double flavor;
        if (chocolate.isChecked()) {
            flavor = CHOCOLATE_FLAVOR + COFFEE_PRICE;
        } else {
            flavor = CREAM_FLAVOR + COFFEE_PRICE;
        }
        totalPrice.setText(NumberFormat.getCurrencyInstance().format(coffees * flavor));
    }

    /**
     * @return Inflater Layout For Menu Items
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    /**
     * Handle Menu Items Clicked
     *
     * @param item in the menu layout
     * @return the action of clicked item.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_menu:
//                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
            case R.id.about:
                aboutUs.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Save The Bundles For The Activity States.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(QUANTITY_NUM_KEY, quantity);
        outState.putString(QUANTITY_KEY, coffeeQuantity.getText().toString());
        outState.putString(TOTAL_PRICE_KEY, totalPrice.getText().toString());
        outState.putString(REPORT_KEY, finalReport.getText().toString());
    }
}
