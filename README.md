# HostelAid

A sustainable hostel management Android application designed to help students manage food waste, donate clothes, report water cooler issues, and learn about eco-friendly practices.

## Features

### 🍽️ Food Waste Management
- Report food waste from different hostels
- Track food items and quantities
- Submit reports to NGOs
- View previous food waste reports

### 👕 Clothes Donation
- Donate unused clothes with detailed information
- Specify cloth type, size, and quantity
- Submit donations to NGOs
- Track donation history

### 💧 Water Cooler Complaint System
- Report water cooler issues
- Select hostel, floor, and cooler number
- Choose problem type (leaking, not working, etc.)
- View complaint history

### 📚 Eco Blogs
- Read educational blogs about sustainable living
- Learn about reducing carbon footprint
- Get tips on zero-waste lifestyle
- Understand the importance of sustainable living

### 🔐 User Authentication
- Secure login with college email (@iiitm.ac.in)
- Google Apps Script backend integration
- Password validation

## Tech Stack

- **Language**: Java
- **Platform**: Android (API 24+)
- **UI**: Material Design Components
- **Networking**: OkHttp
- **Storage**: SharedPreferences with Gson
- **Backend**: Google Apps Script (for login and data submission)

## Prerequisites

- Android Studio (latest version recommended)
- JDK 11 or higher
- Android SDK (API 24+)
- Internet connection for backend services

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/HostelAid.git
   cd HostelAid
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository folder
   - Click "OK"

3. **Configure Backend URLs**
   - Open `LoginActivity.java` and update the `scriptUrl` with your Google Apps Script URL
   - Open `FoodWasteActivity.java` and update the `scriptUrl` for food waste submissions
   - Open `ClothesDonationActivity.java` and update the `scriptUrl` for clothes donation
   - Open `CoolerComplaintActivity.java` and update the `scriptUrl` for cooler complaints

4. **Sync Gradle**
   - Android Studio will automatically sync Gradle files
   - If not, go to `File > Sync Project with Gradle Files`

5. **Build and Run**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

## Project Structure

```
HostelAid/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/hostelaid/
│   │   │   │   ├── Activities/
│   │   │   │   │   ├── SplashActivity.java
│   │   │   │   │   ├── LoginActivity.java
│   │   │   │   │   ├── MainActivity.java
│   │   │   │   │   ├── FoodWasteActivity.java
│   │   │   │   │   ├── ClothesDonationActivity.java
│   │   │   │   │   ├── CoolerComplaintActivity.java
│   │   │   │   │   ├── BlogListActivity.java
│   │   │   │   │   ├── BlogDetailActivity.java
│   │   │   │   │   └── Previous*Activity.java
│   │   │   │   ├── Adapters/
│   │   │   │   ├── Models/
│   │   │   │   └── DataStorageHelper.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   ├── values/
│   │   │   │   └── drawable/
│   │   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
└── README.md
```

## Backend Setup

This app uses Google Apps Script for backend services. To set up:

1. Create a Google Apps Script project
2. Deploy as a web app
3. Update the script URLs in the respective activity files
4. Configure the script to handle POST requests and store data in Google Sheets

Example Apps Script structure:
```javascript
function doPost(e) {
  const data = JSON.parse(e.postData.contents);
  // Process data and save to Google Sheets
  return ContentService.createTextOutput("success");
}
```

## Configuration

### Update Backend URLs

Replace the placeholder URLs in these files:
- `LoginActivity.java` - Line 33
- `FoodWasteActivity.java` - Line 25
- `ClothesDonationActivity.java` - Line 25
- `CoolerComplaintActivity.java` - Line 18

### Hostel Names

Update hostel names in:
- `FoodWasteActivity.java` - Line 41
- `ClothesDonationActivity.java` - Line 41
- `CoolerComplaintActivity.java` - Line 32

## Features in Detail

### Local Storage
- All submissions are saved locally using SharedPreferences
- Data persists even if backend sync fails
- View previous submissions anytime

### Offline Support
- App works offline for viewing previous submissions
- Backend sync happens when online
- Failed syncs are handled gracefully

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Known Issues

- Backend URLs need to be configured before use
- Some features may require additional backend setup

## Future Enhancements

- [ ] Quiz feature for eco-awareness
- [ ] Eco game for engagement
- [ ] Push notifications for complaint status
- [ ] Statistics dashboard
- [ ] Multi-language support
- [ ] Dark mode



