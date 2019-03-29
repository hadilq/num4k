package com.num4k.number

@ExperimentalUnsignedTypes
fun UIntArray.plusInteger(o: UIntArray) = FieldOperators.plusInteger(this, o)

@ExperimentalUnsignedTypes
fun UIntArray.minusInteger(o: UIntArray) = FieldOperators.minusInteger(this, o)

@ExperimentalUnsignedTypes
fun UIntArray.timesInteger(o: UIntArray) = FieldOperators.timesInteger(this, o)

@ExperimentalUnsignedTypes
fun UIntArray.divInteger(o: UIntArray) = FieldOperators.divInteger(this, o)

@ExperimentalUnsignedTypes
fun UIntArray.reminderInteger(o: UIntArray) = FieldOperators.reminderInteger(this, o)

@ExperimentalUnsignedTypes
fun UIntArray.divWithRemInteger(o: UIntArray) = FieldOperators.divWithRemInteger(this, o)

@ExperimentalUnsignedTypes
fun UIntArray.additiveInverse() = FieldOperators.additiveInverses(this)

@ExperimentalUnsignedTypes
fun UIntArray.additiveInverse(size: Int) = FieldOperators.additiveInverses(this, size)

@ExperimentalUnsignedTypes
fun UIntArray.multiplicativeInverse(size: Int) = FieldOperators.multiplicativeInverse(this, size)

@ExperimentalUnsignedTypes
fun UIntArray.integerToString() = FieldOperators.integerToString(this)

@ExperimentalUnsignedTypes
fun UIntArray.equalsInteger(other: UIntArray) = FieldOperators.equalsInteger(this, other)

@ExperimentalUnsignedTypes
fun UIntArray.compareToZero() = FieldOperators.compareToZero(this)

@ExperimentalUnsignedTypes
fun UIntArray.compareTo(other: UIntArray) = FieldOperators.compareTo(this, other)

@ExperimentalUnsignedTypes
fun UInt.integerToString() = FieldOperators.integerToString(this)
