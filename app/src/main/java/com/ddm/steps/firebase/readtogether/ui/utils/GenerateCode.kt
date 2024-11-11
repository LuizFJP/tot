package com.ddm.steps.firebase.readtogether.ui.utils

import kotlin.random.Random

fun generateCode(length: Int = 6): String {
    val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return (1..length)
        .map { characters[Random.nextInt(characters.length)] }
        .joinToString("")
}