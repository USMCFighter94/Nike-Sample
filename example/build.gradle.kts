import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.app)
    kotlin(Plugins.Kotlin.android)
    kotlin(Plugins.Kotlin.extensions)
    kotlin(Plugins.Kotlin.kapt)
}

android {
    compileSdkVersion(Sdk.compile)

    defaultConfig {
        applicationId = "dev.brevitz.nike"
        minSdkVersion(Sdk.min)
        targetSdkVersion(Sdk.target)
        versionCode = Versions.versionCode
        versionName = Versions.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
            isMinifyEnabled = false
        }

        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("debug").java.srcDirs("src/debug/kotlin")
        getByName("release").java.srcDirs("src/release/kotlin")
        getByName("test").apply {
            java.srcDirs("src/test/kotlin")
            resources.srcDirs("src/test/resources")
        }
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(project(Deps.Project.Core.data))
    implementation(project(Deps.Project.Core.domain))
    implementation(project(Deps.Project.Core.ui))
    implementation(project(Deps.Project.Library.ui))
    implementation(project(Deps.Project.Library.domain))
    implementation(project(Deps.Project.strings))

    kapt(Deps.Dagger.compiler)
    implementation(Deps.Dagger.core)
    implementation(Deps.kotlin)
    implementation(Deps.navigation)
    implementation(Deps.navigationUi)
    implementation(Deps.timber)

    androidTestImplementation(Deps.Test.androidCore)
    androidTestImplementation(Deps.Test.androidXJUnit)
    androidTestImplementation(Deps.Test.espresso)
    androidTestImplementation(Deps.Test.espressoContrib)
    androidTestImplementation(Deps.Retrofit.core)
    androidTestImplementation(Deps.Test.rules)
    androidTestImplementation(Deps.Test.runner)
    androidTestImplementation(Deps.Test.rxIdler)
}
