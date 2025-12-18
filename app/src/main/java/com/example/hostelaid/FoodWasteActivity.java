package com.example.hostelaid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FoodWasteActivity extends AppCompatActivity {
    private AutoCompleteTextView spinnerHostel;
    private EditText etFoodItem, etQuantity;
    private Button btnAddItem, btnReportNGO, btnViewPrevious;
    private RecyclerView rvFoodItems;
    private FoodItemAdapter adapter;
    private List<FoodItem> foodItems;
    private DataStorageHelper dataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_waste);

        spinnerHostel = findViewById(R.id.spinnerHostel);
        etFoodItem = findViewById(R.id.etFoodItem);
        etQuantity = findViewById(R.id.etQuantity);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnReportNGO = findViewById(R.id.btnReportNGO);
        btnViewPrevious = findViewById(R.id.btnViewPrevious);
        rvFoodItems = findViewById(R.id.rvFoodItems);

        String[] hostels = {"Srisailam (IVH)", "Gangotri (GH)", "Aravali", "Shivalik", "Satpura", "Nilgiri"};
        ArrayAdapter<String> hostelAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_dropdown_item_1line, hostels);
        spinnerHostel.setAdapter(hostelAdapter);
        spinnerHostel.setThreshold(1); // Show suggestions after typing 1 character
        spinnerHostel.setOnClickListener(v -> spinnerHostel.showDropDown());

        foodItems = new ArrayList<>();
        adapter = new FoodItemAdapter(foodItems, this::removeFoodItem);
        rvFoodItems.setLayoutManager(new LinearLayoutManager(this));
        rvFoodItems.setAdapter(adapter);
        
        dataStorage = new DataStorageHelper(this);

        btnAddItem.setOnClickListener(v -> addFoodItem());

        btnReportNGO.setOnClickListener(v -> reportToNGO());

        btnViewPrevious.setOnClickListener(v -> viewPreviousRequests());
    }

    private void addFoodItem() {
        String foodItem = etFoodItem.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();

        if (foodItem.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Please enter both food item and quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double quantity = Double.parseDouble(quantityStr);
            foodItems.add(new FoodItem(foodItem, quantity));
            adapter.notifyItemInserted(foodItems.size() - 1);
            
            // Clear input fields
            etFoodItem.setText("");
            etQuantity.setText("");
            etFoodItem.requestFocus();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeFoodItem(int position) {
        foodItems.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void reportToNGO() {
        String selectedHostel = spinnerHostel.getText().toString();
        
        if (selectedHostel.isEmpty()) {
            Toast.makeText(this, "Please select a hostel", Toast.LENGTH_SHORT).show();
            return;
        }

        if (foodItems.isEmpty()) {
            Toast.makeText(this, "Please add at least one food item", Toast.LENGTH_SHORT).show();
            return;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        FoodWasteRequest request = new FoodWasteRequest(selectedHostel, new ArrayList<>(foodItems), timestamp);
        dataStorage.saveFoodWasteRequest(request);

        submitToBackend(selectedHostel, foodItems);
    }

    private void submitToBackend(String hostel, List<FoodItem> items) {
        OkHttpClient client = new OkHttpClient();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "food_waste");
            jsonObject.put("hostel", hostel);
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
            
            // Add items
            for (int i = 0; i < items.size(); i++) {
                FoodItem item = items.get(i);
                jsonObject.put("item_" + i, item.getItemName() + " - " + item.getQuantity() + " kg");
            }
            jsonObject.put("item_count", items.size());

            RequestBody body = RequestBody.create(
                    jsonObject.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(BackendConfig.FOOD_WASTE_SCRIPT_URL)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(FoodWasteActivity.this, "Report saved locally. Backend sync failed.", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(() -> {
                        Toast.makeText(FoodWasteActivity.this, "Report submitted successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error preparing data", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewPreviousRequests() {
        Intent intent = new Intent(this, PreviousFoodWasteActivity.class);
        startActivity(intent);
    }
}
