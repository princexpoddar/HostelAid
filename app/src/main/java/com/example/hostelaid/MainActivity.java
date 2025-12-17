package com.example.hostelaid;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.card.MaterialCardView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MaterialCardView cardFoodWaste, cardClothesDonation, cardCoolerComplaint, cardBlogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        cardFoodWaste = findViewById(R.id.cardFoodWaste);
        cardClothesDonation = findViewById(R.id.cardClothesDonation);
        cardCoolerComplaint = findViewById(R.id.cardCoolerComplaint);
        cardBlogs = findViewById(R.id.cardBlogs);

        // Set click listeners
        cardFoodWaste.setOnClickListener(v -> startActivity(new Intent(this, FoodWasteActivity.class)));
        cardClothesDonation.setOnClickListener(v -> startActivity(new Intent(this, ClothesDonationActivity.class)));
        cardCoolerComplaint.setOnClickListener(v -> startActivity(new Intent(this, CoolerComplaintActivity.class)));
        cardBlogs.setOnClickListener(v -> startActivity(new Intent(this, BlogListActivity.class)));
    }
}
