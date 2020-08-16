import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.COMPILE_SDK)
    buildToolsVersion("29.0.3")
    defaultConfig {
        applicationId = "com.enxy.weather"
        minSdkVersion(Versions.MIN_SDK)
        targetSdkVersion(Versions.TARGET_SDK)
        versionCode = Versions.VERSION_CODE
        versionName = Versions.VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            // Shrink apk size
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildTypes.forEach {
        // Loading secret API keys from local.properties to BuildConfig
        val properties = Properties().apply {
            load(project.rootProject.file("local.properties").inputStream())
        }
        val apiKeyOpenWeatherMap: String = properties.getProperty("api_key_open_weather_map", "")
        val apiKeyOpenCage: String = properties.getProperty("api_key_open_cage", "")
        it.buildConfigField("String", "API_KEY_OPEN_WEATHER_MAP", apiKeyOpenWeatherMap)
        it.buildConfigField("String", "API_KEY_OPEN_CAGE", apiKeyOpenCage)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.incremental" to "true")
            }
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.KOTLIN)
    implementation(Libs.COROUTINES)

    // UI
    implementation(Libs.MATERIAL_DIALOGS)
    implementation(Libs.MATERIAL)
    implementation(Libs.ANDROID_SUPPORT)
    implementation(Libs.RECYCLERVIEW)
    implementation(Libs.APPCOMPAT)
    implementation(Libs.SWIPEREFRESH_LAYOUT)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.INTERPOLATORS)

    // Tests
    testImplementation(Libs.JUNIT)
    androidTestImplementation(Libs.ARCH_TESTING)
    androidTestImplementation(Libs.JUNIT)
    androidTestImplementation(Libs.TEST_RUNNER)
    androidTestImplementation(Libs.TEST_RULES)
    androidTestImplementation(Libs.ESPRESSO)

    // Architecture Components
    implementation(Libs.KTX_CORE)
    implementation(Libs.KTX_FRAGMENT)
    implementation(Libs.KTX_PREFERENCE)
    implementation(Libs.KTX_LIFECYCLE_VIEWMODEL)
    implementation(Libs.KTX_LIFECYCLE_LIVEDATA)
    implementation(Libs.KTX_LIFECYCLE_EXTENSIONS)

    // Network
    implementation(Libs.GSON)
    implementation(Libs.RETROFIT2)
    implementation(Libs.RETROFIT2_GSON_CONVERTER)

    // Room - Database
    implementation(Libs.ROOM_RUNTIME)
    implementation(Libs.KTX_ROOM)
    annotationProcessor(Libs.ROOM_COMPILER)
    kapt(Libs.ROOM_COMPILER)

    // Koin - Dependency Injection
    implementation(Libs.KOIN)
    implementation(Libs.KOIN_SCOPE)
    implementation(Libs.KOIN_VIEWMODEL)
}
