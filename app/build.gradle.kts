import com.google.protobuf.gradle.proto
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("com.google.gms.google-services")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.kiitracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kiitracker"
        minSdk = 24
        targetSdk = 35
        versionCode = 6
        versionName = "1.0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("int", "VERSION_CODE", "$versionCode")
        buildConfigField("String", "VERSION_NAME", "\"$versionName\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets.getByName("main") {
        proto {
            srcDir("src/main/proto")
        }
        val buildDir = layout.buildDirectory.get().asFile
        java.srcDir(buildDir.resolve("generated/source/proto/main/java"))
        kotlin.srcDir(buildDir.resolve("generated/source/proto/main/kotlin"))
    }
}
composeCompiler {
    featureFlags = setOf(
        ComposeFeatureFlag.OptimizeNonSkippingGroups
    )
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.activity.compose)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifeCycle.runtime.compose)
    implementation(libs.lifeCycle.viewmodel.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material.icons.extended)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.play.services.auth)

    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.kotlinx.serialization.json)

    // Dagger - Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Credential Manager
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)
    implementation(libs.protobuf.kotlin.lite)
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

ksp {
    arg("dagger.hilt.android.internal.disableAndroidSuperclassValidation", "true")
}