object Deps {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val navigation = "android.arch.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "android.arch.navigation:navigation-ui-ktx:${Versions.navigation}"

    object Dagger {
        const val core = "com.google.dagger:dagger:${Versions.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    }

    object Epoxy {
        const val core = "com.airbnb.android:epoxy:${Versions.epoxy}"
        const val paging = "com.airbnb.android:epoxy-paging:${Versions.epoxy}"
    }

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"

    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    const val phrase = "com.squareup.phrase:phrase:${Versions.phrase}"

    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"

    object Project {
        object Core {
            const val data = ":core-data"
            const val domain = ":core-domain"
            const val ui = ":core-ui"
        }

        object Library {
            const val data = ":library-data"
            const val domain = ":library-domain"
            const val ui = ":library-ui"
        }

        const val strings = ":strings"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val converter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val rxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    }

    object Rx {
        const val android = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
        const val binding = "com.jakewharton.rxbinding2:rxbinding-kotlin:${Versions.rxBinding}"
        const val java = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
        const val kotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    }

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    object Test {
        const val androidCore = "androidx.test:core:${Versions.androidTestCore}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
        const val androidXJUnit = "androidx.test.ext:junit:${Versions.androidXJUnit}"
        const val kotlinTest = "io.kotlintest:kotlintest-runner-junit5:${Versions.kotlinTest}"
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
        const val runner = "androidx.test:runner:${Versions.testRunner}"
        const val rules = "androidx.test:rules:${Versions.testRules}"
        const val rxIdler = "com.squareup.rx.idler:rx2-idler:${Versions.rxIdler}"
    }
}
