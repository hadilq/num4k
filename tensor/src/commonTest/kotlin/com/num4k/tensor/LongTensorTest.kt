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

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LongTensorTest {

    @Test
    @JsName("create2D_column")
    fun `create2D column`() {
        val m = LongTensor.create2D {
            columns = 3
            this default 0
        }

        assertEquals(m.rank(), 2)
        val dimension = m.dimension()
        assertEquals(dimension[0], 1)
        assertEquals(dimension[1], 3)
        assertEquals(m.data().size, 3)
        assertEquals(m.default(), 0)
    }

    @Test
    @JsName("create2D_row")
    fun `create2D row`() {
        val m = LongTensor.create2D {
            rows = 3
            default = -10
        }

        assertEquals(m.rank(), 2)
        val dimension = m.dimension()
        assertEquals(dimension[0], 3)
        assertEquals(dimension[1], 1)
        assertEquals(m.data().size, 3)
    }

    @Test
    @JsName("test_2D_set_get")
    fun `2D set get`() {
        val m = LongTensor.create2D {
            this rows 4
            columns = 5
        }

        m[3, 2] = 5

        assertEquals(m[3, 2], 5)
    }

    @Test
    @JsName("test_2D_negative_column")
    fun `2D negative column`() {
        try {
            LongTensor.create2D {
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
    @JsName("test_2D_get_negative_position")
    fun `2D get negative position`() {
        val m = LongTensor.create2D {
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
    @JsName("test_2D_set_negative_position")
    fun `2D set negative position`() {
        val m = LongTensor.create2D {
            this rows 4
            this columns 3
        }
        try {
            m[3, -4] = 5
        } catch (e: Exception) {
            println(e.message)
            assertTrue("-4" in e.message!!)
            return
        }
        assertFalse(true, "Must throw an exception")
    }

    @Test
    @JsName("create1D")
    fun create1D() {
        val m = LongTensor.create1D {
            this rows 3
            this default 1
        }

        assertEquals(m.rank(), 1)
        assertEquals(m.dimension()[0], 3)
        assertEquals(m.data().size, 3)
    }

    @Test
    @JsName("create1D_first_element")
    fun `create1D first element`() {
        val m = LongTensor.create1D {
            rows = 1
            default = -1
        }
        m[0] = 5

        assertEquals(m[0], 5)
    }

    @Test
    @JsName("create1D_last_element")
    fun `create1D last element`() {
        val m = LongTensor.create1D {
            rows = 10
            default = -1
        }
        m[9] = 5

        assertEquals(m[9], 5)
        val data = m.data()
        assertEquals(data[data.size - 1], 5)
    }

    @Test
    @JsName("create")
    fun create() {
        val m = LongTensor.create {
            this dimension intArrayOf(2, 4, 6)
            this default 0
        }

        assertEquals(m.rank(), 3)
        val dimension = m.dimension()
        assertEquals(dimension[0], 2)
        assertEquals(dimension[1], 4)
        assertEquals(dimension[2], 6)
        assertEquals(m.data().size, 2 * 4 * 6)
    }

    @Test
    @JsName("first_element")
    fun `first element`() {
        val m = LongTensor.create {
            this dimension intArrayOf(2, 4, 6)
            this default 0
        }
        m[0, 0, 0] = 5

        assertEquals(m[0, 0, 0], 5)
        assertEquals(m.data()[0], 5)
    }

    @Test
    @JsName("last_element")
    fun `last element`() {
        val m = LongTensor.create {
            dimension = intArrayOf(2, 4, 6)
            default = -10
        }
        m[1, 3, 5] = 5

        assertEquals(m[1, 3, 5], 5)
        val data = m.data()
        assertEquals(data[data.size - 1], 5)
    }

    @Test
    @JsName("to_long_tensor")
    fun `to long tensor`() {
        val m = InMemTensor.create<Long> {
            dimension = intArrayOf(2, 4, 5)
            default = Long.MIN_VALUE
        }
        val d = m.forEachPosition {
            m.set(it.reduce { acc, i -> acc * i }.toLong(), it)
        }.toLongTensor()

        d.forEachIndexed { position, value ->
            assertEquals(value, position.reduce { acc, i -> acc * i }.toLong())
        }
    }

    @Test
    @JsName("to_typed_tensor")
    fun `to typed tensor`() {
        val m = LongTensor.create {
            dimension = intArrayOf(2, 15, 6)
            default = Long.MIN_VALUE
        }
        m.forEachPosition {
            m.set(it.reduce { acc, i -> acc + i }.toLong(), it)
        }
        val d = m.toTypedTensor()

        d.forEachIndexed { position, value ->
            assertEquals(value, position.reduce { acc, i -> acc + i }.toLong())
        }
    }

    @Test
    @JsName("dot")
    fun dot() {
        val m = LongTensor.create {
            dimension = intArrayOf(6, 8, 4, 7)
            default = 1
        }
        val n = LongTensor.create {
            dimension = intArrayOf(2, 3, 8, 4)
            default = 1
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
        dot.forEach { assertEquals(it, 8) }
    }

    @Test
    @JsName("dot_2D")
    fun `dot 2D`() {
        val m = LongTensor.create {
            dimension = intArrayOf(6, 15)
            default = 1
        }
        val n = LongTensor.create {
            dimension = intArrayOf(15, 3)
            default = 1
        }
        val dot = m dot n
        assertEquals(dot.rank(), 2)
        val dimension = dot.dimension()
        assertEquals(dimension[0], 6)
        assertEquals(dimension[1], 3)
        dot.forEach { assertEquals(it, 15) }
    }

}
