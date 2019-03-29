package com.num4k.number

@ExperimentalUnsignedTypes
abstract class BigDecimal(
    val value: UIntArray
) : Number(), Field<BigDecimal>, Comparable<BigDecimal> {

    abstract fun toString(radix: Int): String

    companion object {
        fun valueOf(i: Int): BigDecimal = BigDecimalImpl(FieldOperators.integerValueOf(i))

        fun valueOf(l: Long): BigDecimal = BigDecimalImpl(FieldOperators.integerValueOf(l))

        fun valueOf(s: String): BigDecimal = BigDecimalImpl(FieldOperators.integerValueOf(s))

        fun valueOf(u: UIntArray): BigDecimal = BigDecimalImpl(u)
    }
}