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

class TransposeTensor<T>(private val i: Int, private val j: Int, private val original: Tensor<T>) : Tensor<T>() {

    private lateinit var d: IntArray

    init {
        if (i < 0 || i >= original.rank() || j < 0 || j >= original.rank()) {
            throw IllegalArgumentException("i and j must be in range of zero to ${original.rank()}")
        }
    }

    override fun get(vararg position: Int): T? {
        @Suppress("ReplaceGetOrSet")
        return original.get(*substitutePositions(position))
    }

    override fun set(value: T?, position: IntArray) {
        original.set(value, substitutePositions(position))
    }

    private fun substitutePositions(position: IntArray): IntArray {
        val newPosition = position.copyOf()
        val k = newPosition[i]
        newPosition[i] = newPosition[j]
        newPosition[j] = k
        return newPosition
    }

    override fun rank(): Int = original.rank()

    override fun dimension(): IntArray {
        if (!::d.isInitialized) {
            d = substitutePositions(original.dimension())
        }
        return d
    }

    override fun default(): T? = original.default()
}