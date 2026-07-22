# SmartBiz — Business Management App

A full-stack Android application for Indian merchants to manage inventory, customers, billing, ledger, and expenses.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Android UI | Jetpack Compose (Material 3) |
| Navigation | Navigation Compose |
| State Management | ViewModel + StateFlow |
| Networking | Retrofit 2 + OkHttp |
| Backend | Node.js + Express |
| Database | SQLite (via better-sqlite3) |
| Build Tool | Gradle 8.4 |
| Min SDK | Android 7.0 (API 24) |
| Target SDK | Android 14 (API 34) |

---

## Project Structure

```
app/
├── app/
│   ├── Backend/                  ← Node.js Express backend
│   │   ├── server.js             ← API routes
│   │   ├── database.js           ← SQLite schema + connection
│   │   ├── package.json
│   │   └── smartbiz_master.db    ← SQLite database (auto-created)
│   └── src/main/java/com/example/smartbiz/
│       ├── MainActivity.kt       ← NavHost + auth flow
│       ├── data/
│       │   ├── api/
│       │   │   ├── ApiService.kt       ← Retrofit endpoints
│       │   │   └── RetrofitClient.kt   ← OkHttp + SessionManager
│       │   ├── models/
│       │   │   ├── AuthModels.kt
│       │   │   └── BusinessModels.kt
│       │   └── repository/
│       │       └── SmartBizRepository.kt
│       └── ui/
│           ├── navigation/Screen.kt    ← All route definitions
│           ├── screens/                ← All Compose screens
│           ├── viewmodel/              ← All ViewModels
│           └── theme/                  ← Colors, Typography
```

---

## Prerequisites

Make sure these are installed on your PC before you begin:

- [Android Studio Hedgehog or newer](https://developer.android.com/studio)
- [Node.js v18+](https://nodejs.org/)
- [JDK 17+](https://adoptium.net/)
- Android SDK Platform 34 (install via Android Studio → SDK Manager)
- A physical Android device (API 24+) or Android Emulator

---

## Step 1 — Clone / Open the Project

Open the project folder in Android Studio:

```
File → Open → Select: C:\Users\sande\Desktop\app
```

Wait for Gradle sync to complete.

---

## Step 2 — Start the Backend Server

Open a terminal and navigate to the Backend folder:

```bash
cd C:\Users\sande\Desktop\app\app\Backend
```

Install dependencies (first time only):

```bash
npm install
```

Start the server:

```bash
node server.js
```

You should see:

```
SmartBiz Backend running on port 5001
Connected to SmartBiz Master Database.
```

> **Keep this terminal open while using the app.** The backend must be running for the app to work.

---

## Step 3 — Configure the Backend IP

The app needs to know your PC's IP address so the phone can connect to the backend.

### Find your PC's Wi-Fi IP

**Windows:**
```
ipconfig
```
Look for **Wi-Fi → IPv4 Address** — e.g. `192.168.1.100`

**Mac/Linux:**
```
ifconfig | grep inet
```

### Update the IP in the app

Open this file:

```
app/src/main/java/com/example/smartbiz/data/api/RetrofitClient.kt
```

Change `DEVICE_URL` to your PC's IP:

```kotlin
private const val DEVICE_URL = "http://YOUR_PC_IP:5001"
```

Example:
```kotlin
private const val DEVICE_URL = "http://192.168.1.100:5001"
```

Also make sure this is set to `true` for physical device:

```kotlin
private const val USE_PHYSICAL_DEVICE = true
```

Set it to `false` for Android Emulator (emulator uses `10.0.2.2` automatically).

> **Important:** Your phone and PC must be on the **same Wi-Fi network.**

---

## Step 4 — Build and Run

### Option A — Run via Android Studio

1. Connect your Android phone via USB
2. Enable **USB Debugging** on your phone:
   - Settings → About Phone → tap Build Number 7 times
   - Settings → Developer Options → USB Debugging → ON
3. Select your device from the device dropdown in Android Studio
4. Click the **Run ▶** button

### Option B — Run via Command Line

```bash
cd C:\Users\sande\Desktop\app

# Build the APK
.\gradlew assembleDebug

# Install on connected device
adb install -r app\build\outputs\apk\debug\app-debug.apk

# Launch the app
adb shell am start -n "com.example.smartbiz/.MainActivity"
```

---

## Step 5 — First Launch

1. App opens with the **Splash Screen** (2.5 seconds)
2. You land on the **Login Screen**

### Register a New Account

Tap **"Register Now"** and fill in:

| Field | Example |
|---|---|
| Full Name | Ravi Kumar |
| Email | ravi@myshop.com |
| Phone | 9876543210 |
| Business Name | Ravi General Store |
| Security PIN | 1234 |

Tap **REGISTER NOW** → you are automatically logged in → **Dashboard opens.**

### Login to Existing Account

Enter your email and PIN → tap **LOGIN SECURELY**.

---

## App Navigation

```
Splash
  └── Login ──────────── Register
         │
         ▼ (on success)
      Dashboard
         ├── [Bottom Nav] Customers → Customer Ledger
         ├── [Bottom Nav] Billing   → Invoice Builder
         ├── [Bottom Nav] Inventory → Add Item
         ├── [Bottom Nav] Expenses  → Add Expense
         ├── [Top Bar] Reports
         └── [Top Bar] Profile → Logout
```

---

## API Endpoints Reference

Base URL: `http://<YOUR_PC_IP>:5001`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register new merchant |
| POST | `/api/auth/login` | Login |
| GET | `/api/dashboard/stats` | Sales, udhar, low stock |
| GET/POST | `/api/inventory` | List / Add items |
| DELETE | `/api/inventory/:id` | Delete item |
| GET/POST | `/api/customers` | List / Add customers |
| GET | `/api/ledger/:customerId` | Customer transactions |
| POST | `/api/ledger` | Add gave/got entry |
| GET/POST | `/api/invoices` | List / Create invoice |
| GET/POST | `/api/expenses` | List / Add expense |

---

## Troubleshooting

### App shows "Cannot connect to server"

- Make sure `node server.js` is running on your PC
- Confirm phone and PC are on the **same Wi-Fi network**
- Check the IP in `RetrofitClient.kt` matches your current PC IP
- Run `ipconfig` on your PC to verify the IP hasn't changed
- Try: `curl http://<YOUR_PC_IP>:5001/api/dashboard/stats` from your PC

### Register / Login button not working

- Make sure all required fields are filled
- PIN must be at least 4 digits
- Try once and wait — the button disables while the request is in progress

### Build fails with "SDK 34 not found"

Open Android Studio → **SDK Manager** → **SDK Platforms** → Check **Android 14 (API 34)** → Apply.

### Gradle build fails

```bash
.\gradlew clean assembleDebug
```

### ADB device not found

- Enable USB Debugging on your phone
- Try a different USB cable (use data cable, not charge-only)
- Run `adb devices` — your device should show as `device` not `unauthorized`

---

## Build Commands Reference

```bash
# Clean build
.\gradlew clean

# Debug APK
.\gradlew assembleDebug

# Release APK
.\gradlew assembleRelease

# Run lint
.\gradlew lint

# Check build output
ls app\build\outputs\apk\debug\
```

---

## Environment Notes

| Setting | Value |
|---|---|
| Backend Port | 5001 |
| Emulator URL | `http://10.0.2.2:5001` |
| Physical Device URL | `http://<PC_WIFI_IP>:5001` |
| Session Storage | In-memory (lost on app kill) |
| Database | SQLite file: `Backend/smartbiz_master.db` |
| Compile SDK | 34 |
| Min SDK | 24 (Android 7.0+) |

---

## Test Credentials

If you used the default setup script, a test account exists:

```
Email:    owner@smartbiz.com
PIN:      1234
```

---

*Built with Jetpack Compose + Node.js for Indian Merchants.*
