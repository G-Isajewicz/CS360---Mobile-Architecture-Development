/*
 * CS-360 Mobile Architecture & Programming - 23EW2
 * Project Three
 * Instructor: Jerome DiMarzio
 * Author: Greg Isajewicz
 */


package com.zybooks.inventoryapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class HomeActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_menu);

        Button viewInventoryButton = findViewById(R.id.view_data);
        Button requestAlertsButton = findViewById(R.id.request_sms);
        Button logoutButton = findViewById(R.id.logout_button);

        viewInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DatabaseActivity when "View Inventory" button is pressed
                Intent intent = new Intent(HomeActivity.this, DatabaseActivity.class);
                startActivity(intent);
            }
        });

        requestAlertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    // Request SMS permission
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                } else {
                    Toast.makeText(HomeActivity.this, "Permission Already Allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MainActivity (or wherever you want to go) when "Logout" button is pressed
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "GRANTED. Notifications will be sent. ", Toast.LENGTH_LONG).show();
            } else {
                // Permission denied, inform the user or adjust the app behavior
                // disable SMS-related features)
                Toast.makeText(this, "DENIED. Notifications will not be sent.", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void sendSMS() {

    }

}
