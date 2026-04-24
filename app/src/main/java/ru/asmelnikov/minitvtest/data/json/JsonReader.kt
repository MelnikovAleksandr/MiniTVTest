package ru.asmelnikov.minitvtest.data.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.asmelnikov.minitvtest.data.model.VideoItemDTO
import ru.asmelnikov.minitvtest.domain.model.ErrorsTypes
import ru.asmelnikov.minitvtest.domain.model.Resource
import java.io.IOException

interface JsonReader {

    fun readMediaList(): Resource<List<VideoItemDTO>>

    class Base(private val context: Context) : JsonReader {

        private companion object {
            private const val FILE_NAME = "medialist.json"
        }

        override fun readMediaList(): Resource<List<VideoItemDTO>> {
            return try {
                val jsonString = context.assets.open(FILE_NAME)
                    .bufferedReader()
                    .use { it.readText() }

                val listType = object : TypeToken<List<VideoItemDTO>>() {}.type
                val result: List<VideoItemDTO> = Gson().fromJson(jsonString, listType)
                Resource.Success(result)
            } catch (e: IOException) {
                Resource.Error(errors = ErrorsTypes.ParseError(message = e.message))
            }
        }
    }
}
