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

class TransposeOneDimensionalTensor<T>(private val original: Tensor<T>) : Tensor<T>() {


    init {
        if (original.rank() != 1) {
            throw IllegalArgumentException("The original tensor must be rank one, but it's ${original.rank()}")
        }
    }
    private val d = intArrayOf(1, original.dimension()[0])

    override fun get(vararg position: Int): T? {
        if (position.size != 2) {
            throw IndexOutOfBoundsException("Length of position must be 2, but it's ${position.size}")
        }
        if (position[0] != 0) {
            throw IndexOutOfBoundsException("Index is ${position[0]} which is not zero")
        }
        return original[position[1]]
    }

    override fun set(value: T?, position: IntArray) {
        if (position.size != 2) {
            throw IndexOutOfBoundsException("Length of position must be 2, but it's ${position.size}")
        }
        if (position[0] != 0) {
            throw IndexOutOfBoundsException("Index is ${position[0]} which is not zero")
        }
        original[position[1]] = value
    }

    override fun rank(): Int = 2

    override fun dimension(): IntArray = d

    override fun default(): T? = original.default()
}