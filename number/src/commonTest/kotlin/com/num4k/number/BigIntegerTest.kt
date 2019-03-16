package com.num4k.number

import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalUnsignedTypes
class BigIntegerTest {

    @Test
    fun plus() {
        val a = BigInteger.valueOf(3)
        val b = BigInteger.valueOf(4)
        val c = a + b
        assertEquals(BigInteger.valueOf(7), c)
    }

    @Test
    fun plusZero() {
        val a =
            BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B")
        val b = BigInteger.valueOf(0)
        val c = a + b
        assertEquals(
            BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"),
            c
        )
    }

    @Test
    fun plusNegative() {
        val a = BigInteger.valueOf(3)
        val b = BigInteger.valueOf(-4)
        val c = a + b
        assertEquals(BigInteger.valueOf(-1), c)
    }

    @Test
    fun plusNegativeBoth() {
        val a = BigInteger.valueOf(-3)
        val b = BigInteger.valueOf(-4)
        val c = a + b
        assertEquals(BigInteger.valueOf(-7), c)
    }

    @Test
    fun plusMaxOverflow() {
        val a = BigInteger.valueOf(Int.MAX_VALUE)
        val b = BigInteger.valueOf(1)
        try {
            a + b
        } catch (ignore: ArithmeticException) {
            return
        }
        throw AssertionError("Failed")
    }

    @Test
    fun plusMinOverflow() {
        val a = BigInteger.valueOf(Int.MIN_VALUE)
        val b = BigInteger.valueOf(-1)
        try {
            a + b
        } catch (ignore: ArithmeticException) {
            return
        }
        throw AssertionError("Failed")
    }

    @Test
    fun longPlus() {
        val a = BigInteger.valueOf(3L)
        val b = BigInteger.valueOf(4L)
        val c = a + b
        assertEquals(BigInteger.valueOf(7), c)
    }

    @Test
    fun longPlusNegative() {
        val a = BigInteger.valueOf(3L)
        val b = BigInteger.valueOf(-4L)
        val c = a + b
        assertEquals(BigInteger.valueOf(-1), c)
    }

    @Test
    fun longPlusNegativeBoth() {
        val a = BigInteger.valueOf(-3L)
        val b = BigInteger.valueOf(-4L)
        val c = a + b
        assertEquals(BigInteger.valueOf(-7), c)
    }

    @Test
    fun longMaxPlusOverflow() {
        val a = BigInteger.valueOf(Long.MAX_VALUE)
        val b = BigInteger.valueOf(1L)
        try {
            a + b
        } catch (ignore: ArithmeticException) {
            return
        }
        throw AssertionError("Failed")
    }

    @Test
    fun longMinPlusOverflow() {
        val a = BigInteger.valueOf(Long.MIN_VALUE)
        val b = BigInteger.valueOf(-1L)
        try {
            a + b
        } catch (ignore: ArithmeticException) {
            return
        }
        throw AssertionError("Failed")
    }

    @Test
    fun minus() {
        val a = BigInteger.valueOf(4)
        val b = BigInteger.valueOf(3)
        val c = a - b
        assertEquals(BigInteger.valueOf(1), c)
    }

    @Test
    fun minusNegative() {
        val a = BigInteger.valueOf(4)
        val b = BigInteger.valueOf(3)
        val c = b - a
        assertEquals(BigInteger.valueOf(-1), c)
    }

    @Test
    fun minusNegativeBoth() {
        val a = BigInteger.valueOf(-4)
        val b = BigInteger.valueOf(-3)
        val c = b - a
        assertEquals(BigInteger.valueOf(1), c)
    }

    @Test
    fun longMinus() {
        val a = BigInteger.valueOf(4L)
        val b = BigInteger.valueOf(3L)
        val c = a - b
        assertEquals(BigInteger.valueOf(1), c)
    }

    @Test
    fun longMinusNegative() {
        val a = BigInteger.valueOf(4L)
        val b = BigInteger.valueOf(3L)
        val c = b - a
        assertEquals(BigInteger.valueOf(-1), c)
    }

    @Test
    fun longMinusNegativeBoth() {
        val a = BigInteger.valueOf(-4L)
        val b = BigInteger.valueOf(-3L)
        val c = b - a
        assertEquals(BigInteger.valueOf(1), c)
    }

    @Test
    fun parseToString() {
        val a = BigInteger.valueOf("4456A0B")
        assertEquals("4456A0B", a.toString())
    }

    @Test
    fun parseToLongString() {
        val a = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B")
        assertEquals("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B", a.toString())
    }

    @Test
    fun parseToStringPlus() {
        val a = BigInteger.valueOf("+4456A0B")
        assertEquals("4456A0B", a.toString())
    }

    @Test
    fun parseToLongStringPlus() {
        val a = BigInteger.valueOf("+4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B")
        assertEquals("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B", a.toString())
    }

    @Test
    fun parseToStringMinus() {
        val a = BigInteger.valueOf("-4456A0B")
        assertEquals("-4456A0B", a.toString())
    }

    @Test
    fun parseToLongStringMinus() {
        val a = BigInteger.valueOf("-4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B")
        assertEquals("-4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B", a.toString())
    }

    @Test
    fun times() {
        val a = BigInteger.valueOf(3)
        val b = BigInteger.valueOf(4)
        val c = a * b
        assertEquals(BigInteger.valueOf(12), c)
    }

    @Test
    fun timesOne() {
        val a =
            BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B")
        val b = BigInteger.valueOf(1)
        val c = a * b
        assertEquals(
            BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"),
            c
        )
    }

    @Test
    fun timesNegative() {
        val a = BigInteger.valueOf(3)
        val b = BigInteger.valueOf(-4)
        val c = a * b
        assertEquals(BigInteger.valueOf(-12), c)
    }

    @Test
    fun timesNegativeBoth() {
        val a = BigInteger.valueOf(-3)
        val b = BigInteger.valueOf(-4)
        val c = a * b
        assertEquals(BigInteger.valueOf(12), c)
    }

    @Test
    fun timesMaxOverflow() {
        val a = BigInteger.valueOf(Int.MAX_VALUE)
        val b = BigInteger.valueOf(2)
        try {
            a * b
        } catch (ignore: ArithmeticException) {
            return
        }
        throw AssertionError("Failed")
    }

    @Test
    fun timesMinOverflow() {
        val a = BigInteger.valueOf(Int.MIN_VALUE)
        val b = BigInteger.valueOf(2)
        try {
            a * b
        } catch (ignore: ArithmeticException) {
            return
        }
        throw AssertionError("Failed")
    }

    @Test
    fun longTimes() {
        val a = BigInteger.valueOf(3L)
        val b = BigInteger.valueOf(4L)
        val c = a * b
        assertEquals(BigInteger.valueOf(12), c)
    }

    @Test
    fun longTimesNegative() {
        val a = BigInteger.valueOf(3L)
        val b = BigInteger.valueOf(-4L)
        val c = a * b
        assertEquals(BigInteger.valueOf(-12), c)
    }

    @Test
    fun longTimesNegativeBoth() {
        val a = BigInteger.valueOf(-3L)
        val b = BigInteger.valueOf(-4L)
        val c = a * b
        assertEquals(BigInteger.valueOf(12), c)
    }

    @Test
    fun longMaxTimesOverflow() {
        val a = BigInteger.valueOf(Long.MAX_VALUE)
        val b = BigInteger.valueOf(2)
        try {
            a * b
        } catch (ignore: ArithmeticException) {
            return
        }
        throw AssertionError("Failed")
    }

    @Test
    fun longMinTimesOverflow() {
        val a = BigInteger.valueOf(Long.MIN_VALUE)
        val b = BigInteger.valueOf(2)
        try {
            a * b
        } catch (ignore: ArithmeticException) {
            return
        }
        throw AssertionError("Failed")
    }

    @Test
    fun compare() {
        val a = BigInteger.valueOf(4)
        val b = BigInteger.valueOf(2)
        assertEquals(1, a.compareTo(b))
    }

    @Test
    fun compareEqual() {
        val a = BigInteger.valueOf(5)
        val b = BigInteger.valueOf(5)
        assertEquals(0, a.compareTo(b))
    }

    @Test
    fun compareEqualHighPrecision() {
        val a = BigInteger.valueOf(5) + BigInteger.valueOf(UIntArray(15))
        val b = BigInteger.valueOf(5)
        assertEquals(0, a.compareTo(b))
    }

    @Test
    fun compareEqualNegative() {
        val a = BigInteger.valueOf(-23)
        val b = BigInteger.valueOf(-23)
        assertEquals(0, a.compareTo(b))
    }

    @Test
    fun compareEqualNegativeHighPrecision() {
        val a = BigInteger.valueOf(-23) + BigInteger.valueOf(UIntArray(15))
        val b = BigInteger.valueOf(-23)
        assertEquals(0, a.compareTo(b))
    }

    @Test
    fun compareEqualZero() {
        val a = BigInteger.valueOf(0)
        val b = BigInteger.valueOf(0)
        assertEquals(0, a.compareTo(b))
    }

    @Test
    fun compareEqualZeroHighPrecision() {
        val a = BigInteger.valueOf(UIntArray(15))
        val b = BigInteger.valueOf(0)
        assertEquals(0, a.compareTo(b))
    }

    @Test
    fun compareEqualBigNumber() {
        val a = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B")
        val b = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B")
        assertEquals(0, a.compareTo(b))
    }

    @Test
    fun compareEqualBigNumberHighPrecisionFirst() {
        val a = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B") + BigInteger.valueOf(
            UIntArray(15)
        )
        val b = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B")
        assertEquals(0, a.compareTo(b))
    }

    @Test
    fun compareEqualBigNumberHighPrecisionSecond() {
        val a = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B")
        val b = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B") + BigInteger.valueOf(
            UIntArray(15)
        )
        assertEquals(0, a.compareTo(b))
    }

    @Test
    fun compareBigNumberSecondGreater() {
        val a = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B")
        val b = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0C")
        assertEquals(-1, a.compareTo(b))
    }

    @Test
    fun compareBigNumberFirstGreater() {
        val a = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0D")
        val b = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0C")
        assertEquals(1, a.compareTo(b))
    }

    @Test
    fun compareBigNumberSecondGreaterHighPrecisionFirst() {
        val a = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B") + BigInteger.valueOf(
            UIntArray(15)
        )
        val b = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0C")
        assertEquals(-1, a.compareTo(b))
    }

    @Test
    fun compareBigNumberFirstGreaterHighPrecisionFirst() {
        val a = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0D") + BigInteger.valueOf(
            UIntArray(15)
        )
        val b = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0C")
        assertEquals(1, a.compareTo(b))
    }

    @Test
    fun compareBigNumberSecondGreaterHighPrecisionSecond() {
        val a = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B")
        val b = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0C") + BigInteger.valueOf(
            UIntArray(15)
        )
        assertEquals(-1, a.compareTo(b))
    }

    @Test
    fun compareBigNumberFirstGreaterHighPrecisionSecond() {
        val a = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0D")
        val b = BigInteger.valueOf("4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0C") + BigInteger.valueOf(
            UIntArray(15)
        )
        assertEquals(1, a.compareTo(b))
    }
}