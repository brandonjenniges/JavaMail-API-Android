# JavaMail API for Android

Example using JavaMailAPI to send emails with gmail account in Android

JavaMail API Documentation
--------------

https://java.net/projects/javamail/pages/Android

Pre-requisites
--------------
- Android SDK v10

Getting Started
---------------

This project uses the Gradle build system. To build this project, use the
"gradlew build" command or use "Import Project" in Android Studio.

Adding JavaMail API to your project
--------------
Add to build.gradle file

```
android {
     packagingOptions {
         pickFirst 'META-INF/LICENSE.txt' // picks the JavaMail license file
     }
 }
 
 repositories { 
     jcenter()
     maven {
         url "https://maven.java.net/content/groups/public/"
     }
 }
 
 dependencies {
     compile 'com.sun.mail:android-mail:1.5.5'
     compile 'com.sun.mail:android-activation:1.5.5'
 }
 ```
 Add Internet permission to AndroidManifest.xml
 ```
 <uses-permission android:name="android.permission.INTERNET" />
 ```
 Add Mail.java to project
 
Known issues
--------------
- Doesn't work with Gmail accounts that use Two-factor authentication

License
--------------
https://java.net/projects/javamail/pages/License
