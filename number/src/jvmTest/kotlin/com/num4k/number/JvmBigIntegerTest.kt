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
}