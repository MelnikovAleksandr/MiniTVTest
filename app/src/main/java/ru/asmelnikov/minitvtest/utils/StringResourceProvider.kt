package ru.asmelnikov.minitvtest.utils

import android.content.Context
import androidx.annotation.StringRes

class StringResourceProvider(private val context: Context) {

    fun getString(
        @StringRes resourceId: Int,
        vararg arguments: Any
    ): String {
        return context.getString(resourceId, *arguments)
    }

}