/**
 * Copyright 2019 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.num4k.tensor

abstract class Tensor<T> {

    abstract operator fun get(vararg position: Int): T?

    operator fun set(vararg position: Int, value: T?) = set(value, position)

    abstract fun set(value: T?, position: IntArray)

    abstract fun rank(): Int

    abstract fun dimension(): IntArray

    abstract fun default(): T?

    fun transpose(): Tensor<T> {
        if (rank() == 1) {
            return TransposeOneDimensionalTensor(this)
        }
        if (rank() == 2) {
            return transpose(0, 1)
        }
        throw IllegalStateException("Rank of the tensor is not 2, so please specify dimensions with `fun transpose(i: Int, j: Int)`")
    }

    fun transpose(i: Int, j: Int): Tensor<T> = TransposeTensor(i, j, this)

    fun forEach(block: (T?) -> Unit): Tensor<T> = forEachPosition { block(get(*it)) }

    fun forEachIndexed(block: (IntArray, T?) -> Unit): Tensor<T> = forEachPosition { block(it, get(*it)) }

    fun forEachPosition(block: (IntArray) -> Unit): Tensor<T> {
        iterate(block, dimension(), IntArray(rank()) { 0 }, 0)
        return this
    }

    private fun iterate(block: (IntArray) -> Unit, dimension: IntArray, position: IntArray, iterOn: Int) {
        if (iterOn == dimension.size) {
            block(position)
            return
        }
        (0 until dimension[iterOn]).forEach {
            position[iterOn] = it
            iterate(block, dimension, position, iterOn + 1)
        }
    }

    fun <R> map(block: (T?) -> R?): Tensor<R> {
        val tensor = InMemTensor.create<R> {
            this dimension dimension()
            this default block(default())
        }
        forEachIndexed { position, value ->
            tensor.set(block(value), position)
        }
        return tensor
    }

}