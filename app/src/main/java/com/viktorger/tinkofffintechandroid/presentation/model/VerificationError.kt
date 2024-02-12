package com.viktorger.tinkofffintechandroid.presentation.model

import kotlin.random.Random

// This class is made to fake item equality,
// so the StateFlow in PopularViewModel can emit twice per same id
sealed class VerificationError {
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return Random.nextInt()
    }
}

class IntWrapper(val number: Int): VerificationError()
