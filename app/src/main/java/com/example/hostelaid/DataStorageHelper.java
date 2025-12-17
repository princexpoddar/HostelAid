package com.example.hostelaid;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataStorageHelper {
    private static final String PREFS_NAME = "HostelAidPrefs";
    private static final String KEY_FOOD_WASTE = "food_waste_requests";
    private static final String KEY_CLOTHES_DONATION = "clothes_donations";
    private static final String KEY_COOLER_COMPLAINTS = "cooler_complaints";
    
    private SharedPreferences prefs;
    private Gson gson;
    
    public DataStorageHelper(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }
    
    // Food Waste Methods
    public void saveFoodWasteRequest(FoodWasteRequest request) {
        List<FoodWasteRequest> requests = getFoodWasteRequests();
        requests.add(0, request); // Add to beginning
        String json = gson.toJson(requests);
        prefs.edit().putString(KEY_FOOD_WASTE, json).apply();
    }
    
    public List<FoodWasteRequest> getFoodWasteRequests() {
        String json = prefs.getString(KEY_FOOD_WASTE, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<FoodWasteRequest>>(){}.getType();
        return gson.fromJson(json, type);
    }
    
    // Clothes Donation Methods
    public void saveClothesDonation(ClothesDonation donation) {
        List<ClothesDonation> donations = getClothesDonations();
        donations.add(0, donation);
        String json = gson.toJson(donations);
        prefs.edit().putString(KEY_CLOTHES_DONATION, json).apply();
    }
    
    public List<ClothesDonation> getClothesDonations() {
        String json = prefs.getString(KEY_CLOTHES_DONATION, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<ClothesDonation>>(){}.getType();
        return gson.fromJson(json, type);
    }
    
    // Cooler Complaint Methods
    public void saveCoolerComplaint(CoolerComplaint complaint) {
        List<CoolerComplaint> complaints = getCoolerComplaints();
        complaints.add(0, complaint);
        String json = gson.toJson(complaints);
        prefs.edit().putString(KEY_COOLER_COMPLAINTS, json).apply();
    }
    
    public List<CoolerComplaint> getCoolerComplaints() {
        String json = prefs.getString(KEY_COOLER_COMPLAINTS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<CoolerComplaint>>(){}.getType();
        return gson.fromJson(json, type);
    }
}

