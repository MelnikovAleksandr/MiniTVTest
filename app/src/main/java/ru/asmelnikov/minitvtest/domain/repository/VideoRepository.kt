package ru.asmelnikov.minitvtest.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.asmelnikov.minitvtest.domain.model.Resource
import ru.asmelnikov.minitvtest.domain.model.VideoItem
import ru.asmelnikov.minitvtest.domain.model.VideoReport

interface VideoRepository {

    fun getMediaList(): Resource<List<VideoItem>>

    suspend fun insertReport(report: VideoReport): Resource<Boolean>

    fun getAllReports(): Flow<List<VideoReport>>

}