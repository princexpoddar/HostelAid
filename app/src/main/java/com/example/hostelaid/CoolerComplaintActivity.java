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
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CoolerComplaintActivity extends AppCompatActivity {
    private AutoCompleteTextView spinnerHostel, spinnerFloor, spinnerProblem;
    private EditText etCoolerNumber;
    private Button btnSubmitComplaint, btnViewPrevious;
    private DataStorageHelper dataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooler_complaint);

        spinnerHostel = findViewById(R.id.spinnerHostel);
        spinnerFloor = findViewById(R.id.spinnerFloor);
        etCoolerNumber = findViewById(R.id.etCoolerNumber);
        spinnerProblem = findViewById(R.id.spinnerProblem);
        btnSubmitComplaint = findViewById(R.id.btnSubmitComplaint);
        btnViewPrevious = findViewById(R.id.btnViewPrevious);

        String[] hostels = {"Srisailam (IVH)", "Gangotri (GH)", "Aravali", "Shivalik", "Satpura", "Nilgiri"};
        ArrayAdapter<String> hostelAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_dropdown_item_1line, hostels);
        spinnerHostel.setAdapter(hostelAdapter);
        spinnerHostel.setThreshold(1);
        spinnerHostel.setOnClickListener(v -> spinnerHostel.showDropDown());

        String[] floors = {"Ground Floor", "1st Floor", "2nd Floor", "3rd Floor"};
        ArrayAdapter<String> floorAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_dropdown_item_1line, floors);
        spinnerFloor.setAdapter(floorAdapter);
        spinnerFloor.setThreshold(1);
        spinnerFloor.setOnClickListener(v -> spinnerFloor.showDropDown());

        String[] problems = {
            "Not Working",
            "Leaking",
            "No Water",
            "Water Not Cold",
            "Water Too Cold",
            "Dirty Water",
            "Other"
        };
        ArrayAdapter<String> problemAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_dropdown_item_1line, problems);
        spinnerProblem.setAdapter(problemAdapter);
        spinnerProblem.setThreshold(1);
        spinnerProblem.setOnClickListener(v -> spinnerProblem.showDropDown());

        dataStorage = new DataStorageHelper(this);

        btnSubmitComplaint.setOnClickListener(v -> submitComplaint());

        btnViewPrevious.setOnClickListener(v -> viewPreviousComplaints());
    }

    private void submitComplaint() {
        String selectedHostel = spinnerHostel.getText().toString();
        String selectedFloor = spinnerFloor.getText().toString();
        String coolerNumber = etCoolerNumber.getText().toString().trim();
        String selectedProblem = spinnerProblem.getText().toString();

        if (selectedHostel.isEmpty()) {
            Toast.makeText(this, "Please select a hostel", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedFloor.isEmpty()) {
            Toast.makeText(this, "Please select a floor", Toast.LENGTH_SHORT).show();
            return;
        }

        if (coolerNumber.isEmpty()) {
            Toast.makeText(this, "Please enter cooler number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedProblem.isEmpty()) {
            Toast.makeText(this, "Please select problem type", Toast.LENGTH_SHORT).show();
            return;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        CoolerComplaint complaint = new CoolerComplaint(selectedHostel, selectedFloor, coolerNumber, selectedProblem, timestamp);
        dataStorage.saveCoolerComplaint(complaint);

        submitToBackend(selectedHostel, selectedFloor, coolerNumber, selectedProblem);
    }

    private void submitToBackend(String hostel, String floor, String coolerNumber, String problem) {
        OkHttpClient client = new OkHttpClient();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "cooler_complaint");
            jsonObject.put("hostel", hostel);
            jsonObject.put("floor", floor);
            jsonObject.put("cooler_number", coolerNumber);
            jsonObject.put("problem", problem);
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));

            RequestBody body = RequestBody.create(
                    jsonObject.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(BackendConfig.COOLER_COMPLAINT_SCRIPT_URL)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(CoolerComplaintActivity.this, "Complaint saved locally. Backend sync failed.", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(() -> {
                        Toast.makeText(CoolerComplaintActivity.this, "Complaint submitted successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error preparing data", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewPreviousComplaints() {
        Intent intent = new Intent(this, PreviousCoolerComplaintActivity.class);
        startActivity(intent);
    }
}
