# Weather
Minimalistic Android weather app written in Kotlin. This project is my attempt to make the code clean and readable.

Status: **In development**

# Screenshots
<img  src="/screenshots/main.jpg?raw=true"  width=33% /> <img  src="/screenshots/search.jpg?raw=true"  width=33% />

# Libraries & Dependencies
* [Retrofit](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc.
* [Dagger 2](https://github.com/google/dagger) - Fast dependency injector for Java and Android.
* [Room](https://developer.android.com/topic/libraries/architecture/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access.
* [Gson](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation.
* [Material Design Components](https://material.io/develop/android/) - Material CardView, Bottom AppBar
# How to use this project
1. Create API keys:
    * [OpenWeatherMap](https://openweathermap.org/api) to display weather
    * [OpenCage Geocoder](https://opencagedata.com/api) to find locations by name

2. Edit `local.properties` file. Add your API keys here:

```kotlin
// ...
api_key_open_weather_map = "OPEN_WEATHER_MAP_API_KEY"
api_key_open_cage = "OPEN_CAGE_API_KEY"
```

3. Rebuild project

# Credits
Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a>
