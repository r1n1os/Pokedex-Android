package com.example.pokedexandroid.utils

sealed class Resource<T>(val data: T? = null, val nextUrl: String? = null, val message: String? = null) {
    class Success<T>(data: T, nextUrl: String?) : Resource<T>(data, nextUrl)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}