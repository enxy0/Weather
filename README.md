# Weather

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/34e655757758466dbb00961b0c79e7be)](https://app.codacy.com/manual/enxy0/Weather?utm_source=github.com&utm_medium=referral&utm_content=enxy0/Weather&utm_campaign=Badge_Grade_Dashboard)

Minimalistic Android weather app written in Kotlin with MVVM architecture. This project is my attempt to make the code clean and readable.  
Simple and light design makes easy to see the necessary data.

**Status:** In development *(1.0.0-alpha-2)* ⚙️

## Goals
- [x] Display weather forecasts for different locations ☁️
- [x] Save locations to favourites ⭐
- [ ] Support different units 📏
- [ ] Add dark theme 🌙
- [ ] Add animations 🔥

## Screenshots
<img  src="https://raw.githubusercontent.com/enxy0/Weather/development/screenshots/main.jpg?raw=true"  width=23% /> <img  src="https://raw.githubusercontent.com/enxy0/Weather/development/screenshots/favourite.jpg?raw=true"  width=23% /> <img  src="https://raw.githubusercontent.com/enxy0/Weather/development/screenshots/search.jpg?raw=true"  width=23% /> <img  src="https://raw.githubusercontent.com/enxy0/Weather/development/screenshots/settings.jpg?raw=true"  width=23% />

## Libraries & Dependencies
*   [Retrofit](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc.
*   [Dagger 2](https://github.com/google/dagger) - Fast dependency injector for Java and Android.
*   [Room](https://developer.android.com/topic/libraries/architecture/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access.
*   [Gson](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation.
*   [Material Design Components](https://material.io/develop/android/) - Material CardView, Bottom AppBar

## How to use this project
1.  Create API keys:
    *   [OpenWeatherMap](https://openweathermap.org/api) to display weather
    *   [OpenCage Geocoder](https://opencagedata.com/api) to find locations by name

2.  Edit `local.properties` file. Add your API keys here:

```kotlin
// ...
api_key_open_weather_map = "OPEN_WEATHER_MAP_API_KEY"
api_key_open_cage = "OPEN_CAGE_API_KEY"
```

3.  Rebuild project

## Credits
Icons made by [Freepik](https://www.flaticon.com/authors/freepik) from [www.flaticon.com](https://www.flaticon.com/)
