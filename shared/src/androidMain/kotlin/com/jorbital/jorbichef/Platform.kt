package com.jorbital.jorbichef

actual class Platform actual constructor() {
    actual val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
    actual fun uuid(): String = java.util.UUID.randomUUID().toString()
}
