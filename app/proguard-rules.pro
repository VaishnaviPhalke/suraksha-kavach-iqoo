# Suraksha Kavach ProGuard / R8 Rules
-keepattributes *Annotation*
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}
