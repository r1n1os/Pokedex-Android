package com.example.pokedexandroid.navigations




object Navigator {
    private var result: Any? = null

    fun setResult(data: Any) {
        result = data
    }

    fun <T> consumeResult(): T? {
        val currentResult = result as? T
        result = null
        return currentResult
    }
}