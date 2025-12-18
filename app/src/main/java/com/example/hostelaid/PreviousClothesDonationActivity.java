package com.example.hostelaid;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PreviousClothesDonationActivity extends AppCompatActivity {
    private RecyclerView rvPreviousDonations;
    private TextView tvEmpty;
    private DataStorageHelper dataStorage;
    private PreviousClothesDonationAdapter adapter;
    private SyncManager syncManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_requests);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Previous Clothes Donations");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        rvPreviousDonations = findViewById(R.id.rvPreviousRequests);
        tvEmpty = findViewById(R.id.tvEmpty);

        dataStorage = new DataStorageHelper(this);
        syncManager = new SyncManager(this);
        loadFromStorage();
        syncManager.syncClothes(new SyncManager.SyncListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(PreviousClothesDonationActivity.this::loadFromStorage);
            }

            @Override
            public void onError(String message) {
                // Keep showing cached data
            }
        });
    }

    private void loadFromStorage() {
        List<ClothesDonation> donations = dataStorage.getClothesDonations();

        if (donations.isEmpty()) {
            tvEmpty.setText("No previous clothes donations");
            tvEmpty.setVisibility(android.view.View.VISIBLE);
            rvPreviousDonations.setVisibility(android.view.View.GONE);
        } else {
            tvEmpty.setVisibility(android.view.View.GONE);
            rvPreviousDonations.setVisibility(android.view.View.VISIBLE);
            adapter = new PreviousClothesDonationAdapter(donations);
            rvPreviousDonations.setLayoutManager(new LinearLayoutManager(this));
            rvPreviousDonations.setAdapter(adapter);
        }
    }
}

