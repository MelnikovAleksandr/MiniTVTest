package ru.asmelnikov.minitvtest.data.model

import com.google.gson.annotations.SerializedName

data class VideoItemDTO(
    @SerializedName("VideoId")
    val videoId: Int,

    @SerializedName("VideoIdentifier")
    val videoIdentifier: String,

    @SerializedName("OrderNumber")
    val orderNumber: Int
)