package com.example.pokedexandroid.utils

fun String?.capitalizeTheFirstLetter(): String = "${this?.first()?.uppercase()}${this?.replaceFirst(this.first().toString(), "")}"