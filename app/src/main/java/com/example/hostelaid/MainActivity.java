package com.example.hostelaid;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.card.MaterialCardView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String PERIODIC_SYNC_NAME = "hostelaid_periodic_sync";
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

        kickOffSync();
    }

    private void kickOffSync() {
        // Schedule periodic background sync (defaults to 30 minutes)
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
                SyncWorker.class,
                30,
                TimeUnit.MINUTES
        ).setConstraints(constraints).build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                PERIODIC_SYNC_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request
        );

        // Also trigger a foreground sync so the user sees latest data right away
        SyncManager syncManager = new SyncManager(this);
        syncManager.syncAllAsync(new SyncManager.SyncListener() {
            @Override
            public void onSuccess() {
                // no-op; UI lists will read merged local cache
            }

            @Override
            public void onError(String message) {
                // Keep silent; periodic worker will retry
            }
        });
    }
}
