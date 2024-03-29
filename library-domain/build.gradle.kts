import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin(Plugins.Kotlin.core)
    kotlin(Plugins.Kotlin.extensions)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    api(project(Deps.Project.Core.domain))

    implementation(Deps.kotlin)
    implementation(Deps.Rx.java)
    implementation(Deps.Test.kotlinTest)
}
