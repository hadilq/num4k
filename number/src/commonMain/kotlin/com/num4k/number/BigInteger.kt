package com.num4k.number

@ExperimentalUnsignedTypes
abstract class BigInteger(
    val value: UIntArray
) : Number(), Field<BigInteger>, Comparable<BigInteger> {

    abstract fun toString(radix: Int): String

    abstract operator fun rem(o: BigInteger): BigInteger

    abstract fun divAndRem(o: BigInteger): Pair<BigIntegerImpl, BigIntegerImpl>

    companion object {
        fun valueOf(i: Int): BigInteger = BigIntegerImpl(FieldOperators.integerValueOf(i))

        fun valueOf(l: Long): BigInteger = BigIntegerImpl(FieldOperators.integerValueOf(l))

        fun valueOf(s: String): BigInteger = BigIntegerImpl(FieldOperators.integerValueOf(s))

        fun valueOf(u: UIntArray): BigInteger = BigIntegerImpl(u)
    }
}