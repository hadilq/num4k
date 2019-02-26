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

import kotlin.test.Test
import kotlin.test.assertEquals

class TransposeTensorTest {

    @Test
    fun `2D transpose`() {
        val t = InMemTensor.create2D<Int>().transpose()
        assertEquals(t.rank(), 2)
        assertEquals(t.dimension()[0], 1)
        assertEquals(t.dimension()[1], 1)
    }

    @Test
    fun `2D rectangle transpose`() {
        val t = InMemTensor.create2D<Int> {
            this rows 2
            this columns 5
        }.transpose()
        assertEquals(t.rank(), 2)
        assertEquals(t.dimension()[0], 5)
        assertEquals(t.dimension()[1], 2)
    }

    @Test
    fun `2D rectangle transpose set get`() {
        val t = InMemTensor.create2D<Int> {
            this rows 2
            this columns 5
        }
        t[1, 4] = 6
        val tt = t.transpose()

        assertEquals(tt.rank(), 2)
        assertEquals(tt[4, 1], 6)
    }

    @Test
    fun `3D rectangle transpose set get`() {
        val t = InMemTensor.create<Int> {
            this dimension intArrayOf(3, 5, 2)
        }
        t[1, 4, 0] = 6
        val tt = t.transpose(1, 2)

        assertEquals(tt.rank(), 3)
        assertEquals(tt[1, 0, 4], 6)
    }

    @Test
    fun `transpose of one dimensional Tensor`() {
        val t = InMemTensor.create1D<Int> {
            rows = 7
        }
        t[5] = 6
        val tt = t.transpose()

        assertEquals(tt.rank(), 2)
        assertEquals(tt.dimension()[0], 1)
        assertEquals(tt.dimension()[1], 7)
        assertEquals(tt[0, 5], 6)
    }
}