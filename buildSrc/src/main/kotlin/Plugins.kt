object Plugins {
    const val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradle}"
    const val app = "com.android.application"
    const val library = "com.android.library"

    object Kotlin {
        const val android = "android"
        const val core = "jvm"
        const val extensions = "android.extensions"
        const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val kapt = "kapt"
    }
}
