package com.num4k.number

@ExperimentalUnsignedTypes
fun UIntArray.plusInteger(o: UIntArray) = FieldOperators.plusInteger(this, o)

@ExperimentalUnsignedTypes
fun UIntArray.minusInteger(o: UIntArray) = FieldOperators.minusInteger(this, o)

@ExperimentalUnsignedTypes
fun UIntArray.additiveInverses() = FieldOperators.additiveInverses(this)

@ExperimentalUnsignedTypes
fun UIntArray.additiveInverses(size: Int) = FieldOperators.additiveInverses(this, size)

@ExperimentalUnsignedTypes
fun UIntArray.integerToString() = FieldOperators.integerToString(this)

@ExperimentalUnsignedTypes
fun UIntArray.compareToZero() = FieldOperators.compareToZero(this)

@ExperimentalUnsignedTypes
fun UInt.integerToString() = FieldOperators.integerToString(this)