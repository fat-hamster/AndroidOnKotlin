package com.dmgpersonal.androidonkotlin.model.dto


import com.google.gson.annotations.SerializedName

data class ForecastDTO(
    @SerializedName("date")
    val date: String,
    @SerializedName("date_ts")
    val dateTs: Int,
    @SerializedName("moon_code")
    val moonCode: Int,
    @SerializedName("moon_text")
    val moonText: String,
    @SerializedName("parts")
    val parts: List<PartDTO>,
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String,
    @SerializedName("week")
    val week: Int
)