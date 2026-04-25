package ru.asmelnikov.minitvtest.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AnimatedMessageBar {
    var barType by mutableStateOf(BarType.SUCCESS)
    var barMessage by mutableStateOf("")
    var showState by mutableStateOf(false)


    fun animatedMessageBar(
        message: String,
        type: BarType
    ) {
        barMessage = message
        barType = type
        showState = !showState
    }
}

enum class BarType {
    SUCCESS,
    ERROR
}