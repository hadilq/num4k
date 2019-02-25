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
package com.num4k

open class FloatTensor(
    private val dimension: IntArray,
    private val default: Float,
    private val data: FloatArray = FloatArray(dimension.fold(1) { acc, i ->
        if (i < 1) {
            throw IllegalArgumentException("Dimensions must be more than zero. dimension: ${dimension.contentToString()}")
        }
        acc * i
    }) { default }
) : Tensor<Float>() {

    override fun get(vararg position: Int): Float = data[findPosition(position)]

    override fun set(value: Float?, position: IntArray) {
        if (value == null) {
            throw IllegalArgumentException("The value cannot be null")
        }
        data[findPosition(position)] = value
    }

    private fun findPosition(position: IntArray): Int {
        if (position.size != rank()) {
            throw IllegalArgumentException("Cannot retrieve data when position size is ${position.size}, but this tensor rank is ${rank()}")
        }
        return position.foldIndexed(0) { i, acc, p ->
            if (p < 0 || p >= dimension[i]) {
                throw IllegalArgumentException("Position must be more between zero and ${dimension[i]}, but it's $p")
            }
            val j = dimension[i] * acc + p
            j
        }
    }

    override fun rank(): Int = dimension.size

    override fun dimension(): IntArray = dimension.copyOf()

    override fun default(): Float = default

    fun data(): FloatArray = data.copyOf()

    infix fun dot(t: FloatTensor): FloatTensor {
        if (rank() == 2 && t.rank() == 2) {
            return dot(1, 0, t)
        }
        throw IllegalStateException("Rank of the tensor is not 2, so please specify dimensions with `fun dot(i: Int, j: Int, t: Tensor<T>)`")
    }

    fun dot(i: Int, j: Int, t: FloatTensor): FloatTensor = DotFloatTensor(i, j, this, t)

    fun toTypedTensor(): Tensor<Float> {
        val tensor = InMemTensor.create<Float> {
            dimension = dimension()
            default = default()
        }
        forEachIndexed { position, value ->
            tensor.set(value, position)
        }
        return tensor
    }

    companion object {
        fun create1D(init: Builder1D.() -> Unit = {}): FloatTensor = Builder1D(init).build()

        fun create2D(init: Builder2D.() -> Unit = {}): FloatTensor = Builder2D(init).build()

        fun create(init: Builder.() -> Unit = {}): FloatTensor = Builder(init).build()
    }

    class Builder1D constructor(init: Builder1D.() -> Unit) {

        var rows = 1
        var default: Float = Float.MIN_VALUE

        infix fun rows(rows: Int) {
            this.rows = rows
        }

        infix fun default(default: Float) {
            this.default = default
        }

        init {
            init()
        }

        fun build(): FloatTensor {
            return FloatTensor(intArrayOf(rows), default)
        }
    }

    class Builder2D constructor(init: Builder2D.() -> Unit) {

        var rows = 1
        var columns = 1
        var default: Float = Float.MIN_VALUE

        infix fun columns(columns: Int) {
            this.columns = columns
        }

        infix fun rows(rows: Int) {
            this.rows = rows
        }

        infix fun default(default: Float) {
            this.default = default
        }

        init {
            init()
        }

        fun build(): FloatTensor {
            return FloatTensor(intArrayOf(rows, columns), default)
        }
    }

    class Builder constructor(init: Builder.() -> Unit) {

        var dimension: IntArray = intArrayOf(1)
        var default: Float = Float.MIN_VALUE

        infix fun dimension(dimension: IntArray) {
            this.dimension = dimension
        }

        infix fun default(default: Float) {
            this.default = default
        }

        init {
            init()
        }

        fun build(): FloatTensor {
            return FloatTensor(dimension, default)
        }
    }
}