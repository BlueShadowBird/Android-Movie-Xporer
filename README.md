# Movie Xplorer

_This application uses TMDB (The Movie Database) and the TMDB APIs but is not endorsed, certified, or otherwise approved by TMDB._

## Overview

Movie Xplorer is an Android application that allows users to search and retrieve movie information. The project is modularized into three main modules:

- App module (Main application module)
- Core Module (Android Library module, containing application core components)
- Movie Module (Containing movie module features)

## Tech-Stack

This project is built using the following technologies:

### Language:
- Kotlin

### Development Environment:
- IDE: Android Studio

### Dependency Management:
- Dependency Injection: Koin

### Design Patterns:
- MVVM
- Repository

### User Interface:
- Custom Views
- View Binding
- SwipeRefreshLayout

### Networking:
- Retrofit

### Concurrency:
- Data Flow: Coroutine Flow

### Architecture:
- Clean Architecture

### Authentication and Authorization:
- Android OS Authentication (PIN, Password, Biometric, etc.)

### Modularization:
- Dynamic Feature

### Security:
- Obfuscation

### Media:
- Image Loader: Glide
- Video Player (Future)

### User Interface Components:
- Shared Preferences

### Offline/Online Processing:
- Repository pattern for offline/online processing (future)

### Widgets (Future)

### Webview (Future)

### Splash Screens

## Installation

To run the application, follow these steps:

1. **Clone the Repository:**
    - Open a terminal or command prompt.
    - Run the following command to clone the repository:
      ```
      git clone https://github.com/BlueShadowBird/Android-Movie-Xporer
      ```

2. **Create an Account on TMDB:**
    - Navigate to [TMDB website](https://www.themoviedb.org) in your web browser.
    - Click on the "Sign Up" button to create a new account.
    - Follow the instructions to complete the registration process.

3. **Retrieve API Read Access Token:**
    - Once you've successfully created an account and logged in, navigate to your account settings.
    - Find the "API" section or navigate to [https://www.themoviedb.org/settings/api](https://www.themoviedb.org/settings/api).
    - If prompted, agree to the terms of use for using the TMDB API.
    - Click on the "Create" button or similar to generate a new API key.
    - Copy the generated API Read Access Token.

4. **Configure the Application:**
    - Open the Movie Xplorer project in Android Studio.
    - open assest/api.properties in module core.
    - Paste the copied API Read Access Token into the appropriate field in your code.
    - Save the file.

5. **Build and Run the Application:**
    - Once the API Read Access Token is configured, build the project in Android Studio.
    - Ensure that the application is successfully compiled without errors.
    - Run the application on an emulator or a physical device.

6. **Enjoy Movie Xplorer:**
    - Once the application is running, you can start exploring movies, searching for your favorites, and enjoying the features provided by Movie Xplorer.

## Project Structure

The project is organized into the following structure:

## Future Plans

- Implement pagination.
- Implement encryption for shared Preferences and database
- Implement SSL Pinning
- Add favorite(tech-stack: Room Database)
- Add swipeable image and trailer(tech-stack: media player)

## Contributing

Thank you for your interest in contributing! Please note that this project is primarily a showcase of my skills and is not open to external contributions at the moment. However, feel free to fork the project and use it as a reference for your own learning or personal projects.

If you have any questions or suggestions, you can reach out to me directly via [blue_shadow_bird@yahoo.co.id].

## License

This project is licensed under the [MIT License](https://opensource.org/license/mit).
