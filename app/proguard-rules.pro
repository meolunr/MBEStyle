# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# AVLoadingIndicatorView
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# JavaBean
-keep class me.iacn.mbestyle.bean.** {*;}

# Reflect
-keepclassmembers class android.support.design.widget.BottomNavigationView {
  android.support.design.internal.BottomNavigationMenuView mMenuView;
}
-keepclassmembers class android.support.design.internal.BottomNavigationMenuView {
  android.support.design.internal.BottomNavigationItemView[] mButtons;
}
-keepclassmembers class android.support.design.internal.BottomNavigationItemView {
  android.widget.ImageView mIcon;
}