package com.num4k.number

@ExperimentalUnsignedTypes
class BigIntegerImpl(
    value: UIntArray
) : BigInteger(value) {

    override val additiveIdentity: BigInteger
        get() = BigInteger.valueOf(0)

    override val multiplicativeIdentity: BigInteger
        get() = BigInteger.valueOf(1)

    override fun additiveInverses(): BigIntegerImpl = BigIntegerImpl(FieldOperators.additiveInverses(value, value.size))

    override fun multiplicativeInverses(): BigIntegerImpl =
        BigIntegerImpl(FieldOperators.multiplicativeInverses(value, value.size))

    override fun times(o: BigInteger): BigIntegerImpl = BigIntegerImpl(value.timesInteger(o.value))

    override fun div(o: BigInteger): BigIntegerImpl = BigIntegerImpl(value.divInteger(o.value))

    override fun minus(o: BigInteger): BigIntegerImpl = BigIntegerImpl(value.minusInteger(o.value))

    override fun plus(o: BigInteger): BigIntegerImpl = BigIntegerImpl(value.plusInteger(o.value))

    override fun compareTo(other: BigInteger): Int = value.compareTo(other.value)

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

        return value.equalsInteger((other as BigInteger).value)
    }

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value.integerToString()

    override fun toString(radix: Int): String = when (radix) {
        16 -> value.integerToString()
        else -> throw NotImplementedError()
    }
}
