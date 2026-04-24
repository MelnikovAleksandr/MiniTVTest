package ru.asmelnikov.minitvtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [VideoReportEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MiniTvDatabase : RoomDatabase() {

    abstract fun videoReportDao(): VideoReportDao

}