# Retrofit
-keep class retrofit2.** { *; }

# OkHttp
-keep class okhttp3.** { *; }

# Gson
-keep class com.google.gson.** { *; }

# Models
-keep class com.zgrzyt.mobile.data.model.** { *; }

# Kotlin metadata
-keepattributes Signature
-keepattributes RuntimeVisibleAnnotations

# Compose
-keep class androidx.compose.** { *; }

# ViewModels
-keep class * extends androidx.lifecycle.ViewModel

# Prevent warnings
-dontwarn okhttp3.**
-dontwarn retrofit2.**