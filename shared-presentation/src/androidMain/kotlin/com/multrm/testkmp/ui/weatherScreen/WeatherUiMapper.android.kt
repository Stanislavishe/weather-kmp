package com.multrm.testkmp.ui.weatherScreen

import java.net.UnknownHostException

actual fun isUnknownHostException(e: Exception): Boolean {
    return e is UnknownHostException
}