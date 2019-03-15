package com.num4k.number

@ExperimentalUnsignedTypes
class BigInteger(
    val value: UIntArray
) : Field<BigInteger>, Number(), Comparable<BigInteger> {
    override val additiveIdentity: BigInteger
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val multiplicativeIdentity: BigInteger
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun additiveInverses(): BigInteger = BigInteger(FieldOperators.additiveInverses(value, value.size))

    override fun multiplicativeInverses(): BigInteger {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun times(o: BigInteger): BigInteger {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun div(o: BigInteger): BigInteger {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun minus(o: BigInteger): BigInteger = BigInteger(value.minusInteger(o.value))

    override fun plus(o: BigInteger): BigInteger = BigInteger(value.plusInteger(o.value))

    override fun compareTo(other: BigInteger): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toByte(): Byte {
        if (value.size == 1) {
            return value[0].toInt().toByte()
        }
        throw IllegalStateException("Cannot convert to Byte")
    }

    override fun toChar(): Char {
        if (value.size == 1) {
            return value[0].toInt().toChar()
        }
        throw IllegalStateException("Cannot convert to Char")
    }

    override fun toDouble(): Double {
        if (value.size == 1) {
            return value[0].toInt().toDouble()
        }
        throw IllegalStateException("Cannot convert to Double")
    }

    override fun toFloat(): Float {
        if (value.size == 1) {
            return value[0].toInt().toFloat()
        }
        throw IllegalStateException("Cannot convert to Float")
    }

    override fun toInt(): Int {
        if (value.size == 1) {
            return value[0].toInt()
        }
        throw IllegalStateException("Cannot convert to Int")
    }

    override fun toLong(): Long {
        if (value.size == 1) {
            return value[0].toInt().toLong()
        }
        throw IllegalStateException("Cannot convert to Long")
    }

    override fun toShort(): Short {
        if (value.size == 1) {
            return value[0].toInt().toShort()
        }
        throw IllegalStateException("Cannot convert to Short")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BigInteger

        val bigger = if (value.size > other.value.size) value else other.value
        var smaller = if (value.size > other.value.size) other.value else value

        if (smaller.compareToZero() < 0) {
            smaller = smaller.additiveInverses().additiveInverses(bigger.size)
        }

        (0 until smaller.size).forEach { index ->
            if (smaller[index] != bigger[index]) return false
        }

        (smaller.size until bigger.size).forEach { index ->
            if (bigger[index] != 0u) return false
        }

        return true
    }

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value.integerToString()

    fun toString(radix: Int): String = when (radix) {
        16 -> value.integerToString()
        else -> throw NotImplementedError()
    }

    companion object {
        fun valueOf(i: Int): BigInteger = BigInteger(FieldOperators.integerValueOf(i))

        fun valueOf(l: Long): BigInteger = BigInteger(FieldOperators.integerValueOf(l))

        fun valueOf(s: String): BigInteger = BigInteger(FieldOperators.integerValueOf(s))
    }
}
