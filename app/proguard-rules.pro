## 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-dontwarn android.support.v4.**
-dontwarn android.os.**

-keep class android.support.v4.** { *; }
-keep class android.os.** { *; }
-keep class rx.internal.** { *; }
-keep interface rx.internal.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**

# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
	native <methods>;
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持类成员
-keepclassmembers class * extends android.app.Activity {
	public void *(android.view.View);
}
# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {
	public static **[] values();
	public static ** valueOf(java.lang.String);
}
# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
	public static final android.os.Parcelable$Creator *;
}

## 这里第三方JAR不混淆
-keep class cn.yunzhisheng.** {*;}
-keep interface cn.yunzhisheng.** {*;}
-keep class android.support.** {*;}
-keep class com.google.zxing.** {*;}
-keep interface android.support.** {*;}
-keep interface com.google.zxing.** {*;}

## 这里第三方JAR不警告
#-dontwarn class cn.yunzhisheng.**
#-dontwarn interface cn.yunzhisheng.**
#-dontwarn class android.support.**
#-dontwarn class com.google.zxing.**
#-dontwarn interface android.support.**
#-dontwarn interface com.google.zxing.**

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,Annotation,Synthetic,EnclosingMethod

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
-keepclassmembers class **.R$* {
    public static <fields>;
}
# Serializables
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# Remove Logging
-assumenosideeffects class android.util.Log {
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** d(...);
    public static *** v(...);
}
-libraryjars src/main/jniLibs/armeabi/libyzstts.so
-libraryjars src/main/jniLibs/armeabi-v7a/libyzstts.so

-keep public class com.google.gson.Gson { *; }
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *;}
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class * implements com.google.gson.JsonDeserializer

-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-dontwarn rx.observable.**
-keep class rx.observable.** { *; }
-dontwarn rx.Single.**
-keep class rx.Single.** { *; }
-dontwarn rx.internal.**
-keep class rx.internal.** { *; }
-dontwarn rx.schedulers.**
-keep class rx.schedulers.** { *; }
-dontwarn sun.misc.**
-dontwarn okio.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#-libraryjars libs/usc.jar

#netty
-keepclasseswithmembers class io.netty.** {
    *;
}
-dontwarn io.netty.**
-dontwarn sun.**

#lombook
-dontwarn lombok.**
-keep class lombok.** { *; }

#fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
