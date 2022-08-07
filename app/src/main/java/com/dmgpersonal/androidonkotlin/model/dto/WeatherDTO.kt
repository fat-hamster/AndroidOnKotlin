package com.dmgpersonal.androidonkotlin.model.dto

import com.google.gson.annotations.SerializedName


data class WeatherDTO(
    @SerializedName("fact")
    val fact: FactDTO,
    @SerializedName("info")
    val info: InfoDTO
)