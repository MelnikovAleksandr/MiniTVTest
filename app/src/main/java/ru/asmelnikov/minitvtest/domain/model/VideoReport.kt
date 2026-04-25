package ru.asmelnikov.minitvtest.domain.model

import java.time.LocalDateTime

data class VideoReport(
    val idVideo: Int,
    val videoName: String,
    val startTime: LocalDateTime
)