package ru.asmelnikov.minitvtest.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.asmelnikov.minitvtest.data.json.JsonReader
import ru.asmelnikov.minitvtest.data.local.VideoReportDao
import ru.asmelnikov.minitvtest.data.model.toVideoItem
import ru.asmelnikov.minitvtest.data.model.toVideoReport
import ru.asmelnikov.minitvtest.data.model.toVideoReportEntity
import ru.asmelnikov.minitvtest.domain.model.ErrorsTypes
import ru.asmelnikov.minitvtest.domain.model.Resource
import ru.asmelnikov.minitvtest.domain.model.VideoItem
import ru.asmelnikov.minitvtest.domain.model.VideoReport
import ru.asmelnikov.minitvtest.domain.repository.VideoRepository

class VideoRepositoryImpl(
    private val videoReportDao: VideoReportDao,
    private val jsonReader: JsonReader
) : VideoRepository {

    private companion object {
        private const val TAG = "VideoRepository"
    }

    override fun getMediaList(): Resource<List<VideoItem>> {
        return when (val result = jsonReader.readMediaList()) {
            is Resource.Success -> {
                val videoItems = result.data?.map { it.toVideoItem() } ?: emptyList()
                Resource.Success(videoItems)
            }

            is Resource.Error -> {
                Resource.Error(errors = result.errors)
            }
        }
    }

    override suspend fun insertReport(report: VideoReport): Resource<Boolean> {
        return try {
            videoReportDao.insertReport(report.toVideoReportEntity())
            Log.i(TAG, "Success insert $report")
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(
                data = false,
                errors = ErrorsTypes.InsertDataBaseError(message = e.message)
            )
        }
    }

    override fun getAllReports(): Flow<List<VideoReport>> {
        return videoReportDao.getAllReports().map { it.map { report -> report.toVideoReport() } }
    }

}