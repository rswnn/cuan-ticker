package com.example.filmkita.data

import com.example.filmkita.model.CoinDetail
import com.example.filmkita.utils.WrappedResponseDetail

interface OperationCallback<T> {
    fun onSuccess(data: List<T>?)
    fun onError(error: String?)
}

interface OperationCallbackDetail<T> {
    fun onSuccess(data: T?)
    fun onError(error: String?)
}