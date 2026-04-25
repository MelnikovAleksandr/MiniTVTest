package ru.asmelnikov.minitvtest.domain.model

import android.net.Uri

data class VideoItem(
    val videoId: Int,
    val videoIdentifier: String,
    val uri: Uri,
    val orderNumber: Int
)