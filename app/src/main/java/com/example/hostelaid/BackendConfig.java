package com.example.hostelaid;

/**
 * Central place for backend endpoints so both write (submit) and read (sync)
 * use the same URLs.
 *
 * TODO: replace placeholder URLs with your deployed Apps Script URLs.
 */
public class BackendConfig {
    public static final String LOGIN_SCRIPT_URL = "https://script.google.com/macros/s/AKfycbw0YHuRdT4h7Dni0TDIstSeHDJGqcTecupk6SedWh_LlYRCNFmqHHvlTGuItxwkl4Eb/exec";
    public static final String FOOD_WASTE_SCRIPT_URL = "https://script.google.com/macros/s/YOUR_FOOD_WASTE_SCRIPT_URL/exec";
    public static final String CLOTHES_DONATION_SCRIPT_URL = "https://script.google.com/macros/s/YOUR_CLOTHES_DONATION_SCRIPT_URL/exec";
    public static final String COOLER_COMPLAINT_SCRIPT_URL = "https://script.google.com/macros/s/YOUR_COOLER_COMPLAINT_SCRIPT_URL/exec";

    private BackendConfig() {}
}

