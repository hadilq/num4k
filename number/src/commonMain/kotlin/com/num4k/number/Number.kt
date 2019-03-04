package com.num4k.number

import kotlin.math.max

operator fun IntArray.plus(o: IntArray) :IntArray{
    val result = IntArray(max(size, o.size))
    NumberOperator.plus(this, o, result)
    return result
}