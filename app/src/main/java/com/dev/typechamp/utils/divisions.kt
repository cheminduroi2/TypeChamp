package com.dev.typechamp.utils

val DIVISIONS = listOf("Lightweight", "Middleweight", "Heavyweight")

const val LOWER_BOUND = "lowerBound"
const val UPPER_BOUND = "upperBound"

val DIVISION_WORD_RANGE = mapOf(
    "Lightweight" to mapOf(
        LOWER_BOUND to 10,
        UPPER_BOUND to 24
    ),
    "Middleweight" to mapOf(
        LOWER_BOUND to 25,
        UPPER_BOUND to 39
    ),
    "Heavyweight" to mapOf(
        LOWER_BOUND to 40,
        UPPER_BOUND to 54
    )
)