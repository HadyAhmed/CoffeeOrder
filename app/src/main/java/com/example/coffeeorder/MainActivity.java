package com.example.coffeeorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final int COFFEE_PRICE = 5;
    private Button increaseBtn, decreaseBtn, orderBtn;
    private TextView coffeeQuantity, totalPrice, finalReport;
    private RadioButton chocolate, cream;
    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find The References For The Views
        increaseBtn = findViewById(R.id.increase_quantity);
        decreaseBtn = findViewById(R.id.decrease_quantity);
        orderBtn = findViewById(R.id.order_button);
        coffeeQuantity = findViewById(R.id.quantity);
        totalPrice = findViewById(R.id.total_price);
        finalReport = findViewById(R.id.total_report);
        chocolate = findViewById(R.id.chocolate);
        cream = findViewById(R.id.cream);

        // Action For Increase Button Clicked
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity < 50) {
                    quantity++;
                    showQuantity(quantity);
                    showPrice(quantity);
                } else {
                    Toast.makeText(MainActivity.this, "Wow!, Leave Some Coffee For Others", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Action For Decrease Button Clicked
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity == 0) {
                    Toast.makeText(MainActivity.this, "You Can't Order Empty Cups", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    showQuantity(quantity);
                    showPrice(quantity);
                }
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
        int flavor;
        if (chocolate.isChecked()) {
            flavor = 10 + COFFEE_PRICE;
        } else {
            flavor = 7 + COFFEE_PRICE;
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
                Toast.makeText(this, "Item " + item.getItemId(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mail_menu:
                Toast.makeText(this, "Item " + item.getItemId(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about:
                Toast.makeText(this, "Item " + item.getItemId(), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
