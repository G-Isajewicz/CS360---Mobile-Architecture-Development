/*
 * CS-360 Mobile Architecture & Programming - 23EW2
 * Project Three
 * Instructor: Jerome DiMarzio
 * Author: Greg Isajewicz
 */


package com.zybooks.inventoryapp;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class InventoryAdapter extends ArrayAdapter<InventoryItem> {

    private Context context;
    public InventoryDatabase inventoryDB;
    public List<InventoryItem> items;

    public InventoryAdapter(Context context, InventoryDatabase database) {
        super(context, 0);
        this.context = context;
        this.inventoryDB = database;

        /*      NOTE:
         The following items are added for testing purposes and use demonstration.
         These items can be deleted in the application - BUT they will automatically repopulate
         to the bottom of the list. New items created in the app can be deleted as normal and will
         not repopulate.
         */
       // inventoryDB.addInventoryItem(new InventoryItem("Test1", 10));
       // inventoryDB.addInventoryItem(new InventoryItem("Test2", 20));
       // inventoryDB.addInventoryItem(new InventoryItem("Test3", 30));

        loadItems();
    }
    private void loadItems() {
        clear();
        items = inventoryDB.getAllInventoryItems();
        addAll(items);
        notifyDataSetChanged();
    }
    public List<InventoryItem> getItemList(){
        return items;}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        InventoryItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inventory_item_layout, parent, false);
        }

        // Populating list
        TextView itemNameTextView = convertView.findViewById(R.id.itemNameTextView);
        EditText quantityTextView = convertView.findViewById(R.id.quantityTextView);
        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);

        // Populate the data into the template
        itemNameTextView.setText(item.getItemName());
        quantityTextView.setText(String.valueOf(item.getQuantity()));

        quantityTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean isFocus) {
                if (!isFocus) {
                    //FIXME: Set up handling for input controls / valid input checks
                    // hideKeyboard(v);
                    // Change item in array
                    item.setQuantity(Integer.parseInt(quantityTextView.getText().toString().trim()));
                    // Change item in DB
                    inventoryDB.updateInventoryItem(item);
                }
            }
        });

       deleteButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               // Seperated into different call in order to add verification option later
                showDeleteItemDialog(position);

           }
       });

        // Return the completed view to render on screen
        return convertView;
    }

    public void showDeleteItemDialog(int position) {
        InventoryItem itemToDelete = items.get(position);
        String itemNameToDelete = itemToDelete.getItemName();
        //delete from DB by name - variable added for error reporting
        boolean DBdelete = inventoryDB.deleteInventoryItem(itemNameToDelete);
        if (!DBdelete) {
            Toast.makeText(this.getContext(), " DB DELETE ERROR", Toast.LENGTH_LONG).show();
        }
         //delete from local list
        items.remove(position);
        notifyDataSetChanged();
        Toast.makeText(this.getContext(), "ITEM DELETED", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this.getContext(), DatabaseActivity.class);
        context.startActivity(intent);
    }
}

