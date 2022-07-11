package com.dmgpersonal.androidonkotlin.utils

import java.io.BufferedReader
import java.util.stream.Collectors

class Utils {
}

const val YANDEX_API_KEY = "X-Yandex-API-Key"

fun getLines(reader: BufferedReader): String {
    return reader.lines().collect(Collectors.joining("\n"))
}