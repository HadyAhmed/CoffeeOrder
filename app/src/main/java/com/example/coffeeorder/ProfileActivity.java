package com.example.coffeeorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {

    public static final String PHONE_KEY = "phone_num";
    public static final String NAME_KEY = "name";
    public static final String EMAIL_KEY = "email";
    private TextInputEditText nameText, emailText, phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameText = findViewById(R.id.name);
        emailText = findViewById(R.id.email);
        phoneNum = findViewById(R.id.phone_num);
        final TextInputLayout emailLayout = findViewById(R.id.email_layout);
        Button submitBtn = findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid(emailText)) {
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    // Pass Values Throw Intent If They Are Valid And Move To MainActivity.
                    intent.putExtra(NAME_KEY, nameText.getText().toString());
                    intent.putExtra(EMAIL_KEY, emailText.getText().toString());
                    intent.putExtra(PHONE_KEY, phoneNum.getText().toString());
                    startActivity(intent);
                } else {
                    emailLayout.setError(getString(R.string.email_error_message));
                }
            }
        });

        // Remove Error When The User Decide To Change The Text Into The TextInputEditText
        emailText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                emailLayout.setError(null);
                return true;
            }
        });
    }

    /**
     * This Will Check The Validation For The Input Mail
     *
     * @param editText where the email will get info
     * @return Validation
     */
    private boolean isValid(TextInputEditText editText) {
        return editText.getText().toString().endsWith("@gmail.com") && !editText.getText().toString().isEmpty();
    }
}
