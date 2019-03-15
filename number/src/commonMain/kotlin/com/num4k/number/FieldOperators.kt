package com.num4k.number

import kotlin.math.max

@ExperimentalUnsignedTypes
object FieldOperators {

    private val hexArray = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F".split(",").toTypedArray()

    fun plusInteger(first: UIntArray, second: UIntArray): UIntArray {
        val condition = first.size >= second.size
        val bigger = if (condition) first else second
        val smaller = if (condition) second else first
        val digits = UIntArray(bigger.size)
        var carry = 0uL
        bigger.forEachIndexed { index, v ->
            carry = v.toULong() + carry + if (index >= smaller.size) {
                0uL
            } else {
                smaller[index].toULong()
            }
            digits[index] = (carry and 0xFFFFFFFFu).toUInt()
            carry = (carry shr 32)

        }
        val biggerSign = bigger.compareToZero()
        val smallerSign = smaller.compareToZero()
        val resultSign = digits.compareToZero()
        if ((biggerSign > 0 && smallerSign > 0 && resultSign < 0) ||
            (biggerSign < 0 && smallerSign < 0 && resultSign > 0)
        ) {
            throw IllegalStateException(
                "Overflow on sum on ${bigger.integerToString()} and ${smaller.integerToString()} " +
                        "with $carry carry, $biggerSign biggerSign, $smallerSign smallerSign and $resultSign resultSign"
            )
        }
        return digits
    }

    fun minusInteger(first: UIntArray, second: UIntArray): UIntArray =
        plusInteger(first, additiveInverses(second, max(first.size, second.size)))

    fun additiveInverses(a: UIntArray): UIntArray = additiveInverses(a, a.size)

    fun additiveInverses(a: UIntArray, size: Int): UIntArray {
        if (size < a.size) {
            throw IllegalArgumentException("Cannot calculate the addictive inverse of ${a.integerToString()} in $size size")
        }
        return UIntArray(size) { index ->
            if (index < a.size) {
                a[index] xor 0xFFFFFFFFu
            } else {
                0xFFFFFFFFu
            }
        }.plusInteger(integerValueOf(1))
    }

    fun integerValueOf(i: Int): UIntArray {
        val array = UIntArray(1)
        array[0] = i.toUInt()
        return array
    }

    fun integerValueOf(l: Long): UIntArray {
        val array = UIntArray(2)
        array[0] = l.toUInt()
        array[1] = (l shr 32).toUInt()
        return array
    }

    fun integerValueOf(s: String): UIntArray {
        var ss = s
        val sign = if (s[0] == '-') {
            ss = s.removePrefix("-")
            false
        } else if (s[0] == '+') {
            ss = s.removePrefix("+")
            true
        } else true

        val result = ArrayList<UInt>()
        ss.reversed().chunked(8).forEach { d ->
            var u = 0u
            d.forEachIndexed { i, c ->
                val index = hexArray.indexOf(c.toString())
                if (index == -1) {
                    throw NumberFormatException("Cannot parse $c to a hex number")
                }
                u += index.toUInt() shl i * 4
            }
            result += u
        }
        val unsigned = result.toUIntArray()
        return if (sign) unsigned else unsigned.additiveInverses()
    }

    fun integerToString(a: UIntArray): String {
        var aa = a
        val sign = if (a.compareToZero() < 0) {
            aa = a.additiveInverses()
            false
        } else {
            true
        }

        var starter = true
        val s = aa.fold("") { acc, v: UInt ->
            "${v.integerToString()}$acc"
        }.dropWhile {
            if (starter && it == '0') {
                true
            } else {
                starter = false
                false
            }
        }
        return if (sign) s else "-$s"
    }

    fun integerToString(v: UInt): String {
        var vs = ""
        var vv = v
        repeat((0 until 8).count()) {
            vs = hexArray[(vv and 0xFu).toInt()] + vs
            vv = vv shr 4
        }
        return vs
    }

    fun compareToZero(i: UIntArray): Int {
        i.forEach { v ->
            if (v != 0u) {
                val sign = 1u shl 31
                return if (i[i.size - 1] and sign == sign) -1 else 1
            }
        }
        return 0
    }
}