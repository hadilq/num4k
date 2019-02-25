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

@Suppress("ReplaceGetOrSet")
class DotLongTensor(
    private val i: Int,
    private val j: Int,
    private val first: LongTensor,
    private val second: LongTensor
) : LongTensor(
    run {
        val list = first.dimension().filterIndexed { index, _ -> index != i }.toMutableList()
        list.addAll(second.dimension().filterIndexed { index, _ -> index != j })
        list.toIntArray()
    },
    first.default()
) {

    init {
        if (i < 0 || i >= first.rank()) {
            throw IllegalArgumentException("i must be in range of zero to ${first.rank()}")
        }
        if (j < 0 || j >= second.rank()) {
            throw IllegalArgumentException("j must be in range of zero to ${second.rank()}")
        }
        if (first.dimension()[i] != second.dimension()[j]) {
            throw IllegalArgumentException("The dimensions, $i, $j, needs to have the same length to be able to sum up")
        }
    }

    private val sumMax = first.dimension()[i]

    override fun get(vararg position: Int): Long {
        if (position.size != rank()) {
            throw IllegalArgumentException("Cannot retrieve data when position size is ${position.size}, but this tensor rank is ${rank()}")
        }
        if (super.get(*position) == default()) {
            val firstPositionList = position.filterIndexed { index, _ -> index < first.rank() - 1 }.toMutableList()
            firstPositionList.add(i, 0)
            val secondPositionList = position.filterIndexed { index, _ -> index >= first.rank() - 1 }.toMutableList()
            secondPositionList.add(j, 0)
            val firstPosition = firstPositionList.toIntArray()
            val secondPosition = secondPositionList.toIntArray()

            super.set((0 until sumMax).foldIndexed(0L) { index, acc, _ ->
                firstPosition[i] = index
                secondPosition[j] = index

                acc.plus(first.get(*firstPosition).times(second.get(*secondPosition)))
            }, position)
        }
        return super.get(*position)
    }

    override fun default(): Long {
        if (first.default() != second.default()) {
            throw IllegalArgumentException("The default value of both tensor must be the same. ${first.default()} != ${second.default()}")
        }
        return super.default()
    }
}

