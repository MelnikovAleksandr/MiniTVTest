package ru.asmelnikov.minitvtest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoReportDao {

    @Insert
    suspend fun insertReport(report: VideoReportEntity)

    @Query("SELECT * FROM reports ORDER BY startTime DESC")
    fun getAllReports(): Flow<List<VideoReportEntity>>

}