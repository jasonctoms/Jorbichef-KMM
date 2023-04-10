package com.jorbital.jorbichef

import platform.Foundation.NSUUID
import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    actual fun uuid(): String = NSUUID.UUID().UUIDString()
}