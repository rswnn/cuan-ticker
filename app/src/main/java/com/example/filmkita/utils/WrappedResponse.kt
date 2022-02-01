package com.example.filmkita.utils

import com.google.gson.annotations.SerializedName

data class WrappedResponse<T>(
    @SerializedName("data") var data: List<T>?=null
)

data class WrappedResponseDetail<T>(
    @SerializedName("data") var data: T?=null
)