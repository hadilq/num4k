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

@Suppress("UNCHECKED_CAST")
open class InMemTensor<T> private constructor(
    private val dimension: IntArray,
    private val default: T?,
    private val data: Array<Any?> = Array(dimension.fold(1) { acc, i ->
        if (i < 1) {
            throw IllegalArgumentException("Dimensions must be more than zero. dimension: ${dimension.contentToString()}")
        }
        acc * i
    }) { default }
) : Tensor<T>() {

    override fun get(vararg position: Int): T? = data[findPosition(position)] as T?

    override fun set(value: T?, position: IntArray) {
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

    override fun default(): T? = default

    fun data(): Array<Any?> = data.copyOf()

    fun <R> map(block: (T?) -> R?): Tensor<R> {
        val tensor = create<R> {
            this dimension dimension()
            this default block(default())
        }
        forEachIndexed { position, value ->
            tensor.set(block(value), position)
        }
        return tensor
    }

    companion object {
        fun <T> create1D(init: Builder1D<T>.() -> Unit = {}): InMemTensor<T> = Builder1D(init).build()

        fun <T> create2D(init: Builder2D<T>.() -> Unit = {}): InMemTensor<T> = Builder2D(init).build()

        fun <T> create(init: Builder<T>.() -> Unit = {}): InMemTensor<T> = Builder(init).build()
    }

    class Builder1D<T> constructor(init: Builder1D<T>.() -> Unit) {

        var rows = 1
        var default: T? = null

        infix fun rows(rows: Int) {
            this.rows = rows
        }

        infix fun default(default: T?) {
            this.default = default
        }

        init {
            init()
        }

        fun build(): InMemTensor<T> {
            return InMemTensor(intArrayOf(rows), default)
        }
    }

    class Builder2D<T> constructor(init: Builder2D<T>.() -> Unit) {

        var rows = 1
        var columns = 1
        var default: T? = null

        infix fun columns(columns: Int) {
            this.columns = columns
        }

        infix fun rows(rows: Int) {
            this.rows = rows
        }

        infix fun default(default: T?) {
            this.default = default
        }

        init {
            init()
        }

        fun build(): InMemTensor<T> {
            return InMemTensor(intArrayOf(rows, columns), default)
        }
    }

    class Builder<T> constructor(init: Builder<T>.() -> Unit) {

        var dimension: IntArray = intArrayOf(1)
        var default: T? = null

        infix fun dimension(dimension: IntArray) {
            this.dimension = dimension
        }

        infix fun default(default: T?) {
            this.default = default
        }

        init {
            init()
        }

        fun build(): InMemTensor<T> {
            return InMemTensor(dimension, default)
        }
    }
}