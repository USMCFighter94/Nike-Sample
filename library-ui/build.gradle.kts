import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.library)
    kotlin(Plugins.Kotlin.android)
    kotlin(Plugins.Kotlin.extensions)
    kotlin(Plugins.Kotlin.kapt)
}

android {
    compileSdkVersion(Sdk.compile)

    defaultConfig {
        minSdkVersion(Sdk.min)
        targetSdkVersion(Sdk.target)
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }

        getByName("release") {
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
    api(project(Deps.Project.Core.ui))
    implementation(project(Deps.Project.Library.data))
    implementation(project(Deps.Project.Library.domain))
    implementation(project(Deps.Project.strings))

    implementation(Deps.appCompat)
    implementation(Deps.cardView)
    implementation(Deps.constraintLayout)
    implementation(Deps.coreKtx)
    kapt(Deps.Dagger.compiler)
    implementation(Deps.Dagger.core)
    implementation(Deps.Epoxy.core)
    implementation(Deps.Epoxy.paging)
    implementation(Deps.kotlin)
    implementation(Deps.material)
    implementation(Deps.phrase)
    implementation(Deps.picasso)
    implementation(Deps.Rx.binding)
    implementation(Deps.Rx.java)
    implementation(Deps.Rx.kotlin)
    implementation(Deps.timber)

    testImplementation(Deps.Test.kotlinTest)
    testImplementation(Deps.Test.mockk)
}
