package com.example.animalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button LoginButton = (Button) findViewById(R.id.Login);
        LoginButton.setOnClickListener(new gotoPageListener(this, selectDivice.class));

        Button SignupButton =(Button) findViewById(R.id.signup);
        SignupButton.setOnClickListener(new gotoPageListener(this, SgininActivity.class));
    }
}