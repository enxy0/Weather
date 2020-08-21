object Versions {
    // Project
    const val COMPILE_SDK = 29
    const val TARGET_SDK = 29
    const val MIN_SDK = 23

    // App version
    const val VERSION_CODE = 163
    const val VERSION_NAME = "1.0.0-release"

    // Kotlin
    const val KOTLIN = "1.4.0"
    const val COROUTINES = "1.3.7"

    // Libs
    const val RETROFIT2 = "2.9.0"
    const val RETROFIT2_GSON = "2.9.0"
    const val GSON = "2.8.6"
    const val ROOM = "2.2.5"
    const val MATERIAL_DIALOGS = "3.3.0"
    const val KOIN = "2.1.5"
    const val KTX_CORE = "1.3.1"
    const val KTX_FRAGMENT = "1.2.5"
    const val KTX_PREFERENCES = "1.1.1"
    const val KTX_LIFECYCLE = "2.2.0"
    const val JUNIT = "1.1.1"
    const val ARCH_TESTING = "2.1.0"
    const val TEST_RUNNER = "1.2.0"
    const val ESPRESSO = "3.2.0"
    const val MATERIAL = "1.3.0-alpha02"
    const val ANDROID_SUPPORT = "1.0.0"
    const val RECYCLERVIEW = "1.1.0"
    const val APPCOMPAT = "1.1.0"
    const val SWIPEREFRESH_LAYOUT = "1.1.0"
    const val CONSTRAINT_LAYOUT = "1.1.3"
    const val INTERPOLATORS = "1.0.0"
}

object Libs {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN}"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    const val RETROFIT2 = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT2}"
    const val RETROFIT2_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT2_GSON}"
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val MATERIAL_DIALOGS = "com.afollestad.material-dialogs:input:${Versions.MATERIAL_DIALOGS}"
    const val KOIN = "org.koin:koin-android:${Versions.KOIN}"
    const val KOIN_SCOPE = "org.koin:koin-android-scope:${Versions.KOIN}"
    const val KOIN_VIEWMODEL = "org.koin:koin-android-viewmodel:${Versions.KOIN}"
    const val KTX_ROOM = "androidx.room:room-ktx:${Versions.ROOM}"
    const val KTX_CORE = "androidx.core:core-ktx:${Versions.KTX_CORE}"
    const val KTX_FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.KTX_FRAGMENT}"
    const val KTX_PREFERENCE = "androidx.preference:preference-ktx:${Versions.KTX_PREFERENCES}"
    const val KTX_LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.KTX_LIFECYCLE}"
    const val KTX_LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.KTX_LIFECYCLE}"
    const val KTX_LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:${Versions.KTX_LIFECYCLE}"
    const val JUNIT = "androidx.test.ext:junit:${Versions.JUNIT}"
    const val ARCH_TESTING = "androidx.arch.core:core-testing:${Versions.ARCH_TESTING}"
    const val TEST_RUNNER = "androidx.test:runner:${Versions.TEST_RUNNER}"
    const val TEST_RULES = "androidx.test:rules:${Versions.TEST_RUNNER}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val ANDROID_SUPPORT = "androidx.legacy:legacy-support-core-utils:${Versions.ANDROID_SUPPORT}"
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLERVIEW}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val SWIPEREFRESH_LAYOUT = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.SWIPEREFRESH_LAYOUT}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val INTERPOLATORS = "androidx.interpolator:interpolator:${Versions.INTERPOLATORS}"
}