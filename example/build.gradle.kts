import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.app)
    kotlin(Plugins.Kotlin.android)
}

android {
    compileSdkVersion(Sdk.compile)

    defaultConfig {
        applicationId = "dev.brevitz.nike"
        minSdkVersion(Sdk.min)
        targetSdkVersion(Sdk.target)
        versionCode = Versions.versionCode
        versionName = Versions.versionName
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
    }

    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
    }
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(project(Deps.Project.Library.ui))

    implementation(Deps.kotlin)
    implementation(Deps.navigation)
    implementation(Deps.navigationUi)
    implementation(Deps.timber)
}
