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

public class ClothesDonationActivity extends AppCompatActivity {
    private AutoCompleteTextView spinnerHostel, etClothType, etClothSize;
    private EditText etQuantity;
    private Button btnAddItem, btnSubmitDonation, btnViewPrevious;
    private RecyclerView rvClothItems;
    private ClothItemAdapter adapter;
    private List<ClothItem> clothItems;
    private DataStorageHelper dataStorage;
    private final String scriptUrl = "https://script.google.com/macros/s/YOUR_CLOTHES_DONATION_SCRIPT_URL/exec";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_donation);

        // Initialize views
        spinnerHostel = findViewById(R.id.spinnerHostel);
        etClothType = findViewById(R.id.etClothType);
        etClothSize = findViewById(R.id.etClothSize);
        etQuantity = findViewById(R.id.etQuantity);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnSubmitDonation = findViewById(R.id.btnSubmitDonation);
        btnViewPrevious = findViewById(R.id.btnViewPrevious);
        rvClothItems = findViewById(R.id.rvClothItems);

        // Setup hostel dropdown
        String[] hostels = {"Srisailam (IVH)", "Gangotri (GH)", "Aravali", "Shivalik", "Satpura", "Nilgiri"};
        ArrayAdapter<String> hostelAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_dropdown_item_1line, hostels);
        spinnerHostel.setAdapter(hostelAdapter);
        spinnerHostel.setThreshold(1);
        spinnerHostel.setOnClickListener(v -> spinnerHostel.showDropDown());

        // Setup cloth type dropdown
        String[] clothTypes = {"T-Shirts", "Shirts", "Pants", "Jeans", "Sweaters", "Jackets", "Dresses", "Skirts", "Shoes", "Accessories"};
        ArrayAdapter<String> clothTypeAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_dropdown_item_1line, clothTypes);
        etClothType.setAdapter(clothTypeAdapter);
        etClothType.setThreshold(1);
        etClothType.setOnClickListener(v -> etClothType.showDropDown());

        // Setup cloth size dropdown
        String[] sizes = {"XS", "S", "M", "L", "XL", "XXL", "XXXL"};
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_dropdown_item_1line, sizes);
        etClothSize.setAdapter(sizeAdapter);
        etClothSize.setThreshold(1);
        etClothSize.setOnClickListener(v -> etClothSize.showDropDown());

        // Setup RecyclerView
        clothItems = new ArrayList<>();
        adapter = new ClothItemAdapter(clothItems, this::removeClothItem);
        rvClothItems.setLayoutManager(new LinearLayoutManager(this));
        rvClothItems.setAdapter(adapter);
        
        // Initialize data storage
        dataStorage = new DataStorageHelper(this);

        // Add item button click
        btnAddItem.setOnClickListener(v -> addClothItem());

        // Submit donation button click
        btnSubmitDonation.setOnClickListener(v -> submitDonation());

        // View previous donations button click
        btnViewPrevious.setOnClickListener(v -> viewPreviousDonations());
    }

    private void addClothItem() {
        String clothType = etClothType.getText().toString().trim();
        String clothSize = etClothSize.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();

        if (clothType.isEmpty() || clothSize.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            clothItems.add(new ClothItem(clothType, clothSize, quantity));
            adapter.notifyItemInserted(clothItems.size() - 1);
            
            // Clear input fields
            etClothType.setText("");
            etClothSize.setText("");
            etQuantity.setText("");
            etClothType.requestFocus();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeClothItem(int position) {
        clothItems.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void submitDonation() {
        String selectedHostel = spinnerHostel.getText().toString();
        
        if (selectedHostel.isEmpty()) {
            Toast.makeText(this, "Please select a hostel", Toast.LENGTH_SHORT).show();
            return;
        }

        if (clothItems.isEmpty()) {
            Toast.makeText(this, "Please add at least one cloth item", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save locally
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        ClothesDonation donation = new ClothesDonation(selectedHostel, new ArrayList<>(clothItems), timestamp);
        dataStorage.saveClothesDonation(donation);

        // Submit to backend
        submitToBackend(selectedHostel, clothItems);
    }

    private void submitToBackend(String hostel, List<ClothItem> items) {
        OkHttpClient client = new OkHttpClient();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "clothes_donation");
            jsonObject.put("hostel", hostel);
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
            
            // Add items
            for (int i = 0; i < items.size(); i++) {
                ClothItem item = items.get(i);
                jsonObject.put("item_" + i, item.getClothType() + " - " + item.getSize() + " - Qty: " + item.getQuantity());
            }
            jsonObject.put("item_count", items.size());

            RequestBody body = RequestBody.create(
                    jsonObject.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(scriptUrl)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(ClothesDonationActivity.this, "Donation saved locally. Backend sync failed.", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(() -> {
                        Toast.makeText(ClothesDonationActivity.this, "Donation submitted successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error preparing data", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewPreviousDonations() {
        Intent intent = new Intent(this, PreviousClothesDonationActivity.class);
        startActivity(intent);
    }
}
