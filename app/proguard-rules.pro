#OkHttp Rules for firebase
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

-keepclassmembers class com.kiitracker.domain.** {
      *;
}

-if class androidx.credentials.CredentialManager
-keep class androidx.credentials.playservices.** {
  *;
}

# Keep DataStore fields
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite* {
   <fields>;
}

-dontwarn com.google.rpc.Status
-dontwarn com.google.type.LatLng$Builder
-dontwarn com.google.type.LatLng