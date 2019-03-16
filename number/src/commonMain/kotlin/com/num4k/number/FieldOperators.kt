package com.num4k.number

import kotlin.math.max

@ExperimentalUnsignedTypes
object FieldOperators {

    private val HEX_ARRAY = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F".split(",").toTypedArray()
    private val SIGN_MASK = 1u shl 31

    fun plusInteger(first: UIntArray, second: UIntArray): UIntArray {
        val condition = first.size >= second.size
        val bigger = if (condition) first else second
        val smaller = (if (condition) second else first).let {
            if (it.compareToZero() < 0) {
                it.additiveInverses().additiveInverses(bigger.size)
            } else {
                it
            }
        }
        val digits = UIntArray(bigger.size)
        var carry = 0uL
        bigger.forEachIndexed { index, v ->
            carry += v.toULong() + if (index >= smaller.size) {
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
            throw ArithmeticException(
                "Overflow on sum on ${bigger.integerToString()} and ${smaller.integerToString()} " +
                        "with $carry carry and the sign of result is $resultSign"
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
        val sign = when {
            s[0] == '-' -> {
                ss = s.removePrefix("-")
                false
            }
            s[0] == '+' -> {
                ss = s.removePrefix("+")
                true
            }
            else -> true
        }

        val result = ArrayList<UInt>()
        ss.reversed().chunked(8).forEach { d ->
            var u = 0u
            d.forEachIndexed { i, c ->
                val index = HEX_ARRAY.indexOf(c.toString())
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
            vs = HEX_ARRAY[(vv and 0xFu).toInt()] + vs
            vv = vv shr 4
        }
        return vs
    }


    fun compareToZero(i: UIntArray): Int {
        i.forEach { v ->
            if (v != 0u) {
                return if (i[i.size - 1] and SIGN_MASK == SIGN_MASK) -1 else 1
            }
        }
        return 0
    }

    fun compareTo(first: UIntArray, second: UIntArray): Int {
        val firstSign = first.compareToZero()
        val secondSign = second.compareToZero()
        return when {
            firstSign > 0 && secondSign < 0 -> 1
            firstSign < 0 && secondSign > 0 -> -1
            firstSign > 0 && secondSign == 0 -> 1
            firstSign < 0 && secondSign == 0 -> -1
            firstSign == 0 && secondSign < 0 -> 1
            firstSign == 0 && secondSign > 0 -> -1
            firstSign == 0 && secondSign == 0 -> 0
            else -> {
                val condition = first.size >= second.size
                val bigger = if (condition) first else second
                val smaller = (if (condition) second else first).let { smaller ->
                    if (smaller.compareToZero() < 0) {
                        smaller.additiveInverses().additiveInverses(bigger.size)
                    } else {
                        UIntArray(bigger.size) { index ->
                            if (index < smaller.size) {
                                smaller[index]
                            } else {
                                0u
                            }
                        }
                    }
                }
                run {
                    bigger.reversed().forEachIndexed { index, v ->
                        val u = smaller[smaller.size - 1 - index]
                        if (v > u) {
                            return@run if (condition) 1 else -1
                        } else if (u > v) {
                            return@run if (condition) -1 else 1
                        }
                    }
                    0
                }
            }
        }
    }

    fun multiplicativeInverses(@Suppress("UNUSED_PARAMETER") a: UIntArray, size: Int): UIntArray = UIntArray(size)

    fun timesInteger(first: UIntArray, second: UIntArray): UIntArray {
        val condition = first.size >= second.size
        val bigger = if (condition) first else second
        val smaller = (if (condition) second else first).let {
            if (it.compareToZero() < 0) {
                it.additiveInverses().additiveInverses(bigger.size)
            } else {
                it
            }
        }
        var sumDigits = UIntArray(bigger.size)
        var multiDigits = UIntArray(bigger.size)
        var carry = 0uL
        smaller.forEachIndexed { si, v ->
            run multiply@{
                bigger.forEachIndexed { bi, u ->
                    carry += v.toULong() * u.toULong()
                    if (si + bi >= bigger.size) {
                        return@multiply
                    }
                    multiDigits[si + bi] = (carry and 0xFFFFFFFFu).toUInt()
                    carry = (carry shr 32)
                }
            }

            sumDigits = sumDigits.plusInteger(multiDigits)
            multiDigits = UIntArray(bigger.size)
            carry = 0uL
        }

        val biggerSign = bigger.compareToZero()
        val smallerSign = smaller.compareToZero()
        val resultSign = sumDigits.compareToZero()
        if ((biggerSign > 0 && smallerSign > 0 && resultSign <= 0) ||
            (biggerSign < 0 && smallerSign > 0 && resultSign >= 0) ||
            (biggerSign > 0 && smallerSign < 0 && resultSign >= 0) ||
            (biggerSign < 0 && smallerSign < 0 && resultSign <= 0)
        ) {
            throw ArithmeticException(
                "Overflow on multiplying on ${bigger.integerToString()} and ${smaller.integerToString()} " +
                        "with $carry carry"
            )
        }

        return sumDigits
    }

    fun divInteger(first: UIntArray, second: UIntArray): UIntArray {
        TODO()
    }

}