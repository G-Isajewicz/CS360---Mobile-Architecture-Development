/*
 * CS-360 Mobile Architecture & Programming - 23EW2
 * Project Three
 * Instructor: Jerome DiMarzio
 * Author: Greg Isajewicz
 */


package com.zybooks.inventoryapp;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity {
    public List<InventoryItem> inventoryItems = new ArrayList<>();
    private InventoryAdapter adapter;
    private InventoryDatabase inventoryDB = new InventoryDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_display);


        //Floating Action Button - Add Item
        FloatingActionButton fabAddItem = findViewById(R.id.fab_AddItem);
        FloatingActionButton fabBackButton = findViewById(R.id.fab_Back);

        // Create the adapter
        adapter = new InventoryAdapter(this, inventoryDB);

        adapter.setDropDownViewResource(R.layout.inventory_item_layout);

        // Attach the adapter to the ListView
        ListView listView = findViewById(R.id.inventoryListView);
        listView.setAdapter(adapter);

        //Set up FAB back button
        fabBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MainActivity (or wherever you want to go) when "Logout" button is pressed
                Intent intent = new Intent(DatabaseActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set up the FAB click listener for add item
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the dialog for adding a new item
                showAddItemDialog();
            }
        });
        adapter.notifyDataSetChanged();
    }

        private void showAddItemDialog () {
            // Create the dialog
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.add_item);

            // Get references to views in the dialog
            EditText editTextItemName = dialog.findViewById(R.id.editTextItemName);
            EditText editTextQuantity = dialog.findViewById(R.id.editTextQuantity);
            Button btnAddItem = dialog.findViewById(R.id.btnAddItem);

            // Set up the button click listener
            btnAddItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the entered item name and quantity
                    String itemName = editTextItemName.getText().toString();
                    String quantityStr = editTextQuantity.getText().toString();

                    try {
                        // Validate and add the new item to the list
                        if (!itemName.isEmpty() && !quantityStr.isEmpty()) {
                            int quantity = Integer.parseInt(quantityStr);
                            InventoryItem item = new InventoryItem(itemName, quantity);
                            //Add item to Array
                            inventoryItems.add(item);
                            //Add item to DB
                            inventoryDB.addInventoryItem(item);
                            adapter.add(item);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss(); // Close the dialog
                        } else {
                            // Handle validation error (e.g., show a message)
                            Toast.makeText(DatabaseActivity.this, "Please enter valid data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Handle the case where the quantity is not a valid integer
                        Log.e("DatabaseActivity", "NumberFormatException: " + e.getMessage());
                        Toast.makeText(DatabaseActivity.this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        // Handle other exceptions
                        Log.e("DatabaseActivity", "Exception: " + e.getMessage());
                        Toast.makeText(DatabaseActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
        }


    }

