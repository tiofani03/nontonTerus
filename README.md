# 🎬 NontonTerus

**NontonTerus** is a modern Android application that lets users explore and discover trending, top-rated, and upcoming movies powered by **The Movie Database (TMDB)**. Built with **Jetpack Compose**, it emphasizes clean architecture, reactive state management, and a seamless movie browsing experience.

![NontonTerus Banner](https://github.com/user-attachments/assets/c461a94a-8dce-4d44-94ff-767bdcffc7d7)

---

## 📱 Overview

NontonTerus showcases modern Android development practices using **Jetpack Compose**, **MVI architecture**, and **Redux-style state management**. The app also supports **dynamic theming** and **localization**, making it highly accessible and user-friendly.

[Download APK](https://drive.google.com/file/d/1BBG_1fOPoXF1ezd4Xh2ypYAI2YA7ZeP6/view?usp=sharing)

---

## 🎥 Features

- 🔎 Search movies by title
- 🎬 View detailed movie info (description, rating, videos, etc.)
- 🌐 Real-time data from [TMDB API](https://www.themoviedb.org/documentation/api)
- 🌓 Switch between Light, Dark, and System themes
- 🌍 Multilingual support (English & Indonesian)
- ✨ Smooth animations with Lottie

---

## 🧩 Tech Stack

| Category              | Technology                                       |
|----------------------|--------------------------------------------------|
| UI                   | Jetpack Compose, Material 3                      |
| Navigation           | [Voyager](https://github.com/adrielcafe/voyager)|
| Architecture         | MVI + Redux-style state management              |
| Networking           | Retrofit                                         |
| Data Storage         | Room, DataStore                                  |
| Pagination           | Paging3                                          |
| Dependency Injection | Koin                                             |
| Animations           | Lottie                                           |
| Testing              | JUnit, MockK, Turbine                            |

---

## 🛠️ Setup

To get started:

1. Clone the repository:
   ```bash
   git clone https://github.com/tiofani03/nontonterus.git
   ```
2. Open in **Android Studio**
3. Run the app on a device or emulator

---

## 🌐 Localization

NontonTerus uses a JSON-based localization system.

Supported languages:
- English (`strings_en.json`)
- Bahasa Indonesia (`strings_id.json`)

To add a new language:
1. Create `strings_xx.json` inside the `assets/` folder
2. Add your key-value pairs:
   ```json
   {
     "home": "Home",
     "search": "Search"
   }
   ```

---

## 🎨 Theme Options

Users can choose between:
- ☀️ Light Mode
- 🌙 Dark Mode
- ⚙️ Follow System Settings

Preferences are stored using **DataStore** and applied app-wide.

---

## 📸 Screenshots

| Home | Search | Movie List |
|--------|------|---------|
| ![](https://github.com/user-attachments/assets/45ee80e5-42bb-42e9-9159-5a0302b591ef) | ![](https://github.com/user-attachments/assets/672467fe-c0ca-43d5-b7a1-a3cc19b07d11) | ![](https://github.com/user-attachments/assets/c55c0a40-19ec-4111-8d59-835c95340053)) |

| Detail | Review Detail | Video |
|----------|--------|-------|
| ![](https://github.com/user-attachments/assets/37b844a1-460d-41a9-90d0-398495e0be24) | ![](https://github.com/user-attachments/assets/6e22e295-7628-469f-b8e5-ce8677bf6429) | ![](https://github.com/user-attachments/assets/a3a61a31-b595-4f57-a304-2f5db0a69e3b) |

| Setting | All Reviews | Detail Review |
|----------|--------|-------|
| ![](https://github.com/user-attachments/assets/182a7357-f54b-4307-bf70-9ef0e5d55d94) | ![](https://github.com/user-attachments/assets/7f703464-547e-41a1-9f73-150bbd89ba4b) | ![](https://github.com/user-attachments/assets/11d0e009-c7e0-4858-b579-8274f401c88c) |

---
