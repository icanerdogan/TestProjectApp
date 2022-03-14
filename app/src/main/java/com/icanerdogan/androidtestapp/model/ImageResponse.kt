package com.icanerdogan.androidtestapp.model

data class ImageResponse(
    val hits : List<ImageResult>,
    val total : Int,
    val totalHints : Int
)
