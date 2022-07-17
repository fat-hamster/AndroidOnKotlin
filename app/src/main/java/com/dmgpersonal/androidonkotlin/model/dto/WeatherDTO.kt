package com.dmgpersonal.androidonkotlin.model.dto


data class WeatherDTO(
    val fact: FactDTO,
    val info: InfoDTO,
//    пока не нужно
//    val forecast: ForecastDTO,
//    val now: Int,
//    @SerializedName("now_dt")
//    val nowDt: String
)