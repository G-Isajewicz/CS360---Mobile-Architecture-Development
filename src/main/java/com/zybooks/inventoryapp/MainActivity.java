/*
* CS-360 Mobile Architecture & Programming - 23EW2
* Project Three
* Instructor: Jerome DiMarzio
* Author: Greg Isajewicz
*/

package com.zybooks.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;
    private CredsDatabase creds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextUsername = findViewById(R.id.username_entry);
        editTextPassword= findViewById(R.id.password_entry);
        btnLogin = findViewById(R.id.login_button);
        creds = new CredsDatabase(this);

        // Set click listener for the login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login button click
                handleLogin();
            }
        });
    }
    private void handleLogin() {
        // Retrieve the entered username
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //Validate User
        if (username.isEmpty()) {
            // Display error if username is empty
            showToast("Please enter a username.");
            return;
        }

        //Validate Password
        if (password.isEmpty()) {
            //Display Error is password is left blank
            showToast("Please Enter a Password");
        }

        //If validated:
        if (creds.checkUser(username, password)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If user is not in the system - add user, return bool
            boolean isAdded = creds.addUser(username, password);
            if (isAdded) {
                showToast("USER ADDED");
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                // If add user fails
                showToast("ERROR ADDING CREDENTIALS");
            }
        }
    }

    private void showToast(String message) {
        // Helper method to display a toast message
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}