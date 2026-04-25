package ru.asmelnikov.minitvtest.data.model

import androidx.core.net.toUri
import ru.asmelnikov.minitvtest.data.local.VideoReportEntity
import ru.asmelnikov.minitvtest.domain.model.VideoItem
import ru.asmelnikov.minitvtest.domain.model.VideoReport
import java.time.Instant
import java.time.ZoneId

fun VideoItemDTO.toVideoItem(): VideoItem {
    return VideoItem(
        videoId = videoId,
        uri = "asset:///Videos/${videoIdentifier}".toUri(),
        videoIdentifier = videoIdentifier,
        orderNumber = orderNumber
    )
}

fun VideoReport.toVideoReportEntity(): VideoReportEntity {
    return VideoReportEntity(
        idVideo = idVideo,
        videoName = videoName,
        startTime = startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )
}

fun VideoReportEntity.toVideoReport(): VideoReport {
    return VideoReport(
        idVideo = idVideo,
        videoName = videoName,
        startTime = Instant.ofEpochMilli(startTime)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    )
}