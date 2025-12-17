package com.example.hostelaid;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PreviousFoodWasteActivity extends AppCompatActivity {
    private RecyclerView rvPreviousRequests;
    private TextView tvEmpty;
    private DataStorageHelper dataStorage;
    private PreviousFoodWasteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_requests);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Previous Food Waste Requests");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        rvPreviousRequests = findViewById(R.id.rvPreviousRequests);
        tvEmpty = findViewById(R.id.tvEmpty);

        dataStorage = new DataStorageHelper(this);
        List<FoodWasteRequest> requests = dataStorage.getFoodWasteRequests();

        if (requests.isEmpty()) {
            tvEmpty.setText("No previous food waste requests");
            tvEmpty.setVisibility(android.view.View.VISIBLE);
            rvPreviousRequests.setVisibility(android.view.View.GONE);
        } else {
            tvEmpty.setVisibility(android.view.View.GONE);
            rvPreviousRequests.setVisibility(android.view.View.VISIBLE);
            adapter = new PreviousFoodWasteAdapter(requests);
            rvPreviousRequests.setLayoutManager(new LinearLayoutManager(this));
            rvPreviousRequests.setAdapter(adapter);
        }
    }
}

