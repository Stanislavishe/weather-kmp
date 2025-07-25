package com.multrm.testkmp.ui.utils

import android.util.Log

actual class PlatformLogger actual constructor() {
    actual fun logDebug(tag: String, message: String) {
        Log.d(tag, message)
    }
}
