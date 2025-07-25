package com.multrm.testkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform