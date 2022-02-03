package com.example.filmkita.data

interface OperationCallback<T> {
    fun onSuccess(data: List<T>?)
    fun onError(error: String?)
}

interface OperationCallbackDetail<T> {
    fun onSuccess(data: T?)
    fun onError(error: String?)
}