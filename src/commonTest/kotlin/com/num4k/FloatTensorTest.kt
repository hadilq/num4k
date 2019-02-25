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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FloatTensorTest {

    @Test
    fun `create2D column`() {
        val m = FloatTensor.create2D {
            columns = 3
            this default 0f
        }

        assertEquals(m.rank(), 2)
        val dimension = m.dimension()
        assertEquals(dimension[0], 1)
        assertEquals(dimension[1], 3)
        assertEquals(m.data().size, 3)
        assertEquals(m.default(), 0f)
    }

    @Test
    fun `create2D row`() {
        val m = FloatTensor.create2D {
            rows = 3
            default = -10f
        }

        assertEquals(m.rank(), 2)
        val dimension = m.dimension()
        assertEquals(dimension[0], 3)
        assertEquals(dimension[1], 1)
        assertEquals(m.data().size, 3)
    }

    @Test
    fun `2D set get`() {
        val m = FloatTensor.create2D {
            this rows 4
            columns = 5
        }

        m[3, 2] = 5.3f

        assertEquals(m[3, 2], 5.3f)
    }

    @Test
    fun `2D negative column`() {
        try {
            FloatTensor.create2D {
                this rows 4
                this columns -1
            }
        } catch (e: Exception) {
            println(e.message)
            assertTrue("-1" in e.message!!)
            return
        }
        assertFalse(true, "Must throw an exception")
    }

    @Test
    fun `2D get negative position`() {
        val m = FloatTensor.create2D {
            this rows 4
            this columns 3
        }
        try {
            m[3, -4]
        } catch (e: Exception) {
            println(e.message)
            assertTrue("-4" in e.message!!)
            return
        }
        assertFalse(true, "Must throw an exception")
    }

    @Test
    fun `2D set negative position`() {
        val m = FloatTensor.create2D {
            this rows 4
            this columns 3
        }
        try {
            m[3, -4] = 5.6f
        } catch (e: Exception) {
            println(e.message)
            assertTrue("-4" in e.message!!)
            return
        }
        assertFalse(true, "Must throw an exception")
    }

    @Test
    fun create1D() {
        val m = FloatTensor.create1D {
            this rows 3
            this default 1.5f
        }

        assertEquals(m.rank(), 1)
        assertEquals(m.dimension()[0], 3)
        assertEquals(m.data().size, 3)
    }

    @Test
    fun `create1D first element`() {
        val m = FloatTensor.create1D {
            rows = 1
            default = Float.MAX_VALUE
        }
        m[0] = 5.4f

        assertEquals(m[0], 5.4f)
    }

    @Test
    fun `create1D last element`() {
        val m = FloatTensor.create1D {
            rows = 10
            default = Float.MAX_VALUE
        }
        m[9] = 5.8f

        assertEquals(m[9], 5.8f)
        val data = m.data()
        assertEquals(data[data.size - 1], 5.8f)
    }

    @Test
    fun create() {
        val m = FloatTensor.create {
            this dimension intArrayOf(2, 4, 6)
            this default 0.0f
        }

        assertEquals(m.rank(), 3)
        val dimension = m.dimension()
        assertEquals(dimension[0], 2)
        assertEquals(dimension[1], 4)
        assertEquals(dimension[2], 6)
        assertEquals(m.data().size, 2 * 4 * 6)
    }

    @Test
    fun `first element`() {
        val m = FloatTensor.create {
            this dimension intArrayOf(2, 4, 6)
            this default 0.0f
        }
        m[0, 0, 0] = 5.1f

        assertEquals(m[0, 0, 0], 5.1f)
        assertEquals(m.data()[0], 5.1f)
    }

    @Test
    fun `last element`() {
        val m = FloatTensor.create {
            dimension = intArrayOf(2, 4, 6)
            default = Float.MIN_VALUE
        }
        m[1, 3, 5] = 5.9f

        assertEquals(m[1, 3, 5], 5.9f)
        val data = m.data()
        assertEquals(data[data.size - 1], 5.9f)
    }

    @Test
    fun `to float tensor`() {
        val m = InMemTensor.create<Float> {
            dimension = intArrayOf(2, 4, 5)
            default = Float.MIN_VALUE
        }
        val d = m.forEachPosition {
            m.set(it.reduce { acc, i -> acc * i }.toFloat(), it)
        }.toFloatTensor()

        d.forEachIndexed { position, value ->
            assertEquals(value, position.reduce { acc, i -> acc * i }.toFloat())
        }
    }

    @Test
    fun `to typed tensor`() {
        val m = FloatTensor.create {
            dimension = intArrayOf(2, 15, 6)
            default = Float.MIN_VALUE
        }
        m.forEachPosition {
            m.set(it.reduce { acc, i -> acc + i }.toFloat(), it)
        }
        val d = m.toTypedTensor()

        d.forEachIndexed { position, value ->
            assertEquals(value, position.reduce { acc, i -> acc + i }.toFloat())
        }
    }

    @Test
    fun dot() {
        val m = FloatTensor.create {
            dimension = intArrayOf(6, 8, 4, 7)
            default = 1f
        }
        val n = FloatTensor.create {
            dimension = intArrayOf(2, 3, 8, 4)
            default = 1f
        }
        val dot = m.dot(1, 2, n)
        assertEquals(dot.rank(), 6)
        val dimension = dot.dimension()
        assertEquals(dimension[0], 6)
        assertEquals(dimension[1], 4)
        assertEquals(dimension[2], 7)
        assertEquals(dimension[3], 2)
        assertEquals(dimension[4], 3)
        assertEquals(dimension[5], 4)
        dot.forEach { assertEquals(it, 8f) }
    }

    @Test
    fun `dot 2D`() {
        val m = FloatTensor.create {
            dimension = intArrayOf(6, 15)
            default = 1f
        }
        val n = FloatTensor.create {
            dimension = intArrayOf(15, 3)
            default = 1f
        }
        val dot = m dot n
        assertEquals(dot.rank(), 2)
        val dimension = dot.dimension()
        assertEquals(dimension[0], 6)
        assertEquals(dimension[1], 3)
        dot.forEach { assertEquals(it, 15f) }
    }
}
