package com.num4k.tensor

import kotlin.test.Test
import kotlin.test.assertEquals

class IntTensorTestJS {

    @Test
    fun create2DColumn() {
        val m = IntTensor.create2D {
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
}