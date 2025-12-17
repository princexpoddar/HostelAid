package com.example.hostelaid;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PreviousCoolerComplaintActivity extends AppCompatActivity {
    private RecyclerView rvPreviousComplaints;
    private TextView tvEmpty;
    private DataStorageHelper dataStorage;
    private PreviousCoolerComplaintAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_requests);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Previous Cooler Complaints");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        rvPreviousComplaints = findViewById(R.id.rvPreviousRequests);
        tvEmpty = findViewById(R.id.tvEmpty);

        dataStorage = new DataStorageHelper(this);
        List<CoolerComplaint> complaints = dataStorage.getCoolerComplaints();

        if (complaints.isEmpty()) {
            tvEmpty.setText("No previous cooler complaints");
            tvEmpty.setVisibility(android.view.View.VISIBLE);
            rvPreviousComplaints.setVisibility(android.view.View.GONE);
        } else {
            tvEmpty.setVisibility(android.view.View.GONE);
            rvPreviousComplaints.setVisibility(android.view.View.VISIBLE);
            adapter = new PreviousCoolerComplaintAdapter(complaints);
            rvPreviousComplaints.setLayoutManager(new LinearLayoutManager(this));
            rvPreviousComplaints.setAdapter(adapter);
        }
    }
}

