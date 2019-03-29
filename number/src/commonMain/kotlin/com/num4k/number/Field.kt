package com.num4k.number

interface Field<F> {
    val additiveIdentity: F
    val multiplicativeIdentity: F

    operator fun plus(o: F): F
    operator fun minus(o: F): F
    operator fun times(o: F): F
    operator fun div(o: F): F

    fun additiveInverse(): F
    fun multiplicativeInverse(): F
}