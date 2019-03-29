package com.num4k.number

/**
 * Returns an array of Int containing all of the elements of this collection.
 */
@ExperimentalUnsignedTypes
fun Collection<UInt>.toUIntArray(): UIntArray {
    val result = UIntArray(size)
    var index = 0
    for (element in this)
        result[index++] = element
    return result
}
