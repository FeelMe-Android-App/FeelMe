package com.feelme.feelmeapp.utils

sealed class ResponseApi{
    class Success(var data: Any?) : ResponseApi()
    class Error(val message: Int) : ResponseApi()
}