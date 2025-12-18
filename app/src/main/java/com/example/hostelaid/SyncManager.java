package com.example.hostelaid;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Handles two-way sync: pull latest data from backend and merge into local cache.
 * Expects Apps Script endpoints to support GET with ?action=list&type=<...>&email=<...>
 * returning JSON arrays compatible with the local models.
 */
public class SyncManager {
    private static final String TAG = "HostelAidSync";
    private static final Gson gson = new Gson();

    private final OkHttpClient client = new OkHttpClient();
    private final DataStorageHelper storageHelper;
    private final SessionManager sessionManager;

    public interface SyncListener {
        void onSuccess();
        void onError(String message);
    }

    public SyncManager(Context context) {
        this.storageHelper = new DataStorageHelper(context.getApplicationContext());
        this.sessionManager = new SessionManager(context.getApplicationContext());
    }

    public void syncAllAsync(SyncListener listener) {
        syncFoodWaste(new SyncListener() {
            @Override
            public void onSuccess() {
                syncClothes(new SyncListener() {
                    @Override
                    public void onSuccess() {
                        syncCooler(new SyncListener() {
                            @Override
                            public void onSuccess() {
                                listener.onSuccess();
                            }

                            @Override
                            public void onError(String message) {
                                listener.onError(message);
                            }
                        });
                    }

                    @Override
                    public void onError(String message) {
                        listener.onError(message);
                    }
                });
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
    }

    // Used by WorkManager (blocking)
    public boolean syncAllBlocking() {
        return syncFoodWasteBlocking() && syncClothesBlocking() && syncCoolerBlocking();
    }

    private String currentUserEmail() {
        return sessionManager.getEmail();
    }

    /* -------------------- Food Waste -------------------- */
    public void syncFoodWaste(SyncListener listener) {
        HttpUrl url = buildListUrl(BackendConfig.FOOD_WASTE_SCRIPT_URL, "food_waste");
        if (url == null) {
            listener.onError("Food waste URL invalid");
            return;
        }

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Food sync failed", e);
                listener.onError("Food waste sync failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    listener.onError("Food waste sync error " + response.code());
                    return;
                }
                try {
                    String body = response.body().string();
                    List<FoodWasteRequest> remote = parseFoodWaste(body);
                    mergeAndSaveFoodWaste(remote);
                    listener.onSuccess();
                } catch (JSONException e) {
                    listener.onError("Food waste parse error");
                }
            }
        });
    }

    private boolean syncFoodWasteBlocking() {
        try {
            HttpUrl url = buildListUrl(BackendConfig.FOOD_WASTE_SCRIPT_URL, "food_waste");
            if (url == null) return false;
            Response response = client.newCall(new Request.Builder().url(url).build()).execute();
            if (!response.isSuccessful()) return false;
            List<FoodWasteRequest> remote = parseFoodWaste(response.body().string());
            mergeAndSaveFoodWaste(remote);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Food waste blocking sync failed", e);
            return false;
        }
    }

    private List<FoodWasteRequest> parseFoodWaste(String raw) throws JSONException {
        JSONArray array = new JSONArray(raw);
        List<FoodWasteRequest> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String hostel = obj.optString("hostel", "");
            String timestamp = obj.optString("timestamp", "");
            List<FoodItem> items = new ArrayList<>();
            if (obj.has("items")) {
                Type type = new TypeToken<List<FoodItem>>(){}.getType();
                items = gson.fromJson(obj.getJSONArray("items").toString(), type);
            } else {
                // Fallback: item_0, item_1 string entries
                int count = obj.optInt("item_count", 0);
                for (int j = 0; j < count; j++) {
                    String rawItem = obj.optString("item_" + j, "");
                    items.add(new FoodItem(rawItem, 0));
                }
            }
            list.add(new FoodWasteRequest(hostel, items, timestamp));
        }
        return list;
    }

    private void mergeAndSaveFoodWaste(List<FoodWasteRequest> remote) {
        List<FoodWasteRequest> local = storageHelper.getFoodWasteRequests();
        List<FoodWasteRequest> merged = new ArrayList<>();
        Set<String> dedupe = new HashSet<>();

        for (FoodWasteRequest request : remote) {
            String key = String.format(Locale.US, "%s|%s|%d", request.getHostel(), request.getTimestamp(), request.getItems().size());
            if (dedupe.add(key)) {
                merged.add(request);
            }
        }
        for (FoodWasteRequest request : local) {
            String key = String.format(Locale.US, "%s|%s|%d", request.getHostel(), request.getTimestamp(), request.getItems().size());
            if (dedupe.add(key)) {
                merged.add(request);
            }
        }
        storageHelper.replaceFoodWasteRequests(merged);
    }

    /* -------------------- Clothes -------------------- */
    public void syncClothes(SyncListener listener) {
        HttpUrl url = buildListUrl(BackendConfig.CLOTHES_DONATION_SCRIPT_URL, "clothes_donation");
        if (url == null) {
            listener.onError("Clothes URL invalid");
            return;
        }

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Clothes sync failed", e);
                listener.onError("Clothes sync failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    listener.onError("Clothes sync error " + response.code());
                    return;
                }
                try {
                    String body = response.body().string();
                    List<ClothesDonation> remote = parseClothes(body);
                    mergeAndSaveClothes(remote);
                    listener.onSuccess();
                } catch (JSONException e) {
                    listener.onError("Clothes parse error");
                }
            }
        });
    }

    private boolean syncClothesBlocking() {
        try {
            HttpUrl url = buildListUrl(BackendConfig.CLOTHES_DONATION_SCRIPT_URL, "clothes_donation");
            if (url == null) return false;
            Response response = client.newCall(new Request.Builder().url(url).build()).execute();
            if (!response.isSuccessful()) return false;
            List<ClothesDonation> remote = parseClothes(response.body().string());
            mergeAndSaveClothes(remote);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Clothes blocking sync failed", e);
            return false;
        }
    }

    private List<ClothesDonation> parseClothes(String raw) throws JSONException {
        JSONArray array = new JSONArray(raw);
        List<ClothesDonation> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String hostel = obj.optString("hostel", "");
            String timestamp = obj.optString("timestamp", "");
            List<ClothItem> items = new ArrayList<>();
            if (obj.has("items")) {
                Type type = new TypeToken<List<ClothItem>>(){}.getType();
                items = gson.fromJson(obj.getJSONArray("items").toString(), type);
            } else {
                int count = obj.optInt("item_count", 0);
                for (int j = 0; j < count; j++) {
                    String rawItem = obj.optString("item_" + j, "");
                    items.add(new ClothItem(rawItem, "", 0));
                }
            }
            list.add(new ClothesDonation(hostel, items, timestamp));
        }
        return list;
    }

    private void mergeAndSaveClothes(List<ClothesDonation> remote) {
        List<ClothesDonation> local = storageHelper.getClothesDonations();
        List<ClothesDonation> merged = new ArrayList<>();
        Set<String> dedupe = new HashSet<>();

        for (ClothesDonation donation : remote) {
            String key = String.format(Locale.US, "%s|%s|%d", donation.getHostel(), donation.getTimestamp(), donation.getItems().size());
            if (dedupe.add(key)) {
                merged.add(donation);
            }
        }
        for (ClothesDonation donation : local) {
            String key = String.format(Locale.US, "%s|%s|%d", donation.getHostel(), donation.getTimestamp(), donation.getItems().size());
            if (dedupe.add(key)) {
                merged.add(donation);
            }
        }
        storageHelper.replaceClothesDonations(merged);
    }

    /* -------------------- Cooler -------------------- */
    public void syncCooler(SyncListener listener) {
        HttpUrl url = buildListUrl(BackendConfig.COOLER_COMPLAINT_SCRIPT_URL, "cooler_complaint");
        if (url == null) {
            listener.onError("Cooler URL invalid");
            return;
        }

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Cooler sync failed", e);
                listener.onError("Cooler sync failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    listener.onError("Cooler sync error " + response.code());
                    return;
                }
                try {
                    String body = response.body().string();
                    List<CoolerComplaint> remote = parseCooler(body);
                    mergeAndSaveCooler(remote);
                    listener.onSuccess();
                } catch (JSONException e) {
                    listener.onError("Cooler parse error");
                }
            }
        });
    }

    private boolean syncCoolerBlocking() {
        try {
            HttpUrl url = buildListUrl(BackendConfig.COOLER_COMPLAINT_SCRIPT_URL, "cooler_complaint");
            if (url == null) return false;
            Response response = client.newCall(new Request.Builder().url(url).build()).execute();
            if (!response.isSuccessful()) return false;
            List<CoolerComplaint> remote = parseCooler(response.body().string());
            mergeAndSaveCooler(remote);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Cooler blocking sync failed", e);
            return false;
        }
    }

    private List<CoolerComplaint> parseCooler(String raw) throws JSONException {
        JSONArray array = new JSONArray(raw);
        List<CoolerComplaint> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String hostel = obj.optString("hostel", "");
            String floor = obj.optString("floor", "");
            String coolerNumber = obj.optString("cooler_number", "");
            String problem = obj.optString("problem", "");
            String timestamp = obj.optString("timestamp", "");
            list.add(new CoolerComplaint(hostel, floor, coolerNumber, problem, timestamp));
        }
        return list;
    }

    private void mergeAndSaveCooler(List<CoolerComplaint> remote) {
        List<CoolerComplaint> local = storageHelper.getCoolerComplaints();
        List<CoolerComplaint> merged = new ArrayList<>();
        Set<String> dedupe = new HashSet<>();

        for (CoolerComplaint complaint : remote) {
            String key = String.format(Locale.US, "%s|%s|%s|%s", complaint.getHostel(), complaint.getFloor(), complaint.getCoolerNumber(), complaint.getTimestamp());
            if (dedupe.add(key)) {
                merged.add(complaint);
            }
        }
        for (CoolerComplaint complaint : local) {
            String key = String.format(Locale.US, "%s|%s|%s|%s", complaint.getHostel(), complaint.getFloor(), complaint.getCoolerNumber(), complaint.getTimestamp());
            if (dedupe.add(key)) {
                merged.add(complaint);
            }
        }
        storageHelper.replaceCoolerComplaints(merged);
    }

    private HttpUrl buildListUrl(String baseUrl, String type) {
        HttpUrl parsed = HttpUrl.parse(baseUrl);
        if (parsed == null) {
            Log.e(TAG, "Invalid base URL: " + baseUrl);
            return null;
        }
        return parsed.newBuilder()
                .addQueryParameter("action", "list")
                .addQueryParameter("type", type)
                .addQueryParameter("email", currentUserEmail())
                .build();
    }
}

