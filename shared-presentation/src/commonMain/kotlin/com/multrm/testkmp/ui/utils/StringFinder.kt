package com.multrm.testkmp.ui.utils

import org.jetbrains.compose.resources.StringResource
import weatherkmp.shared_presentation.generated.resources.Res
import weatherkmp.shared_presentation.generated.resources.allStringResources

object StringFinder {
    fun findStringResource(key: String): StringResource? {
        return Res.allStringResources[key]
    }
}