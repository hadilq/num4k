package com.num4k.number

import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalUnsignedTypes
class JvmBigIntegerTest {

    @Test
    fun plus() {
        val s = "4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"
        val t = "4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"
        val ak = BigInteger.valueOf(s)
        val aj = java.math.BigInteger(s, 16)
        val bk = BigInteger.valueOf(t)
        val bj = java.math.BigInteger(t, 16)
        val ck = ak + bk
        val cj = aj + bj
        assertEquals(cj.toString(16).toUpperCase(), ck.toString(16))
    }

    @Test
    fun plusDifferentSign() {
        val s = "-4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"
        val t = "4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"
        val ak = BigInteger.valueOf(s)
        val aj = java.math.BigInteger(s, 16)
        val bk = BigInteger.valueOf(t)
        val bj = java.math.BigInteger(t, 16)
        val ck = ak + bk
        val cj = aj + bj
        assertEquals(cj.toString(16).toUpperCase(), ck.toString(16))
    }

    @Test
    fun plusDifferentSignHighPrecision() {
        val s = "-4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"
        val t = "4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"
        val ak = BigInteger.valueOf(s) + BigInteger.valueOf(UIntArray(15))
        val aj = java.math.BigInteger(s, 16)
        val bk = BigInteger.valueOf(t)
        val bj = java.math.BigInteger(t, 16)
        val ck = ak + bk
        val cj = aj + bj
        assertEquals(cj.toString(16).toUpperCase(), ck.toString(16))
    }

    @Test
    fun times() {
        val s = "4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"
        val t = "4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"
        val ak = BigInteger.valueOf(s) + BigInteger.valueOf(UIntArray(40)) // Increase the precision to avoid overflow
        val aj = java.math.BigInteger(s, 16)
        val bk = BigInteger.valueOf(t)
        val bj = java.math.BigInteger(t, 16)
        val ck = ak * bk
        val cj = aj * bj
        assertEquals(cj.toString(16).toUpperCase(), ck.toString(16))
    }

    @Test
    fun times1() {
        val s = "4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0D"
        val t = "4456A0B4456A0B4456A0B4456A0B4456A0B4"
        val ak = BigInteger.valueOf(s) + BigInteger.valueOf(UIntArray(40)) // Increase the precision to avoid overflow
        val aj = java.math.BigInteger(s, 16)
        val bk = BigInteger.valueOf(t)
        val bj = java.math.BigInteger(t, 16)
        val ck = ak * bk
        val cj = aj * bj
        assertEquals(cj.toString(16).toUpperCase(), ck.toString(16))
    }

    @Test
    fun timesDifferentSign() {
        val s = "-4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"
        val t = "4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B4456A0B"
        val ak = BigInteger.valueOf(s) + BigInteger.valueOf(UIntArray(40)) // Increase the precision to avoid overflow
        val aj = java.math.BigInteger(s, 16)
        val bk = BigInteger.valueOf(t)
        val bj = java.math.BigInteger(t, 16)
        val ck = ak * bk
        val cj = aj * bj
        assertEquals(cj.toString(16).toUpperCase(), ck.toString(16))
    }
}