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
            val c = a + b
        } catch (ignore: IllegalStateException) {
            return
        }
        throw AssertionError("Failed")
    }

    @Test
    fun plusMinOverflow() {
        val a = BigInteger.valueOf(Int.MIN_VALUE)
        val b = BigInteger.valueOf(-1)
        try {
            val c = a + b
        } catch (ignore: IllegalStateException) {
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
            val c = a + b
        } catch (ignore: IllegalStateException) {
            return
        }
        throw AssertionError("Failed")
    }

    @Test
    fun longMinPlusOverflow() {
        val a = BigInteger.valueOf(Long.MIN_VALUE)
        val b = BigInteger.valueOf(-1L)
        try {
            val c = a + b
        } catch (ignore: IllegalStateException) {
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
}