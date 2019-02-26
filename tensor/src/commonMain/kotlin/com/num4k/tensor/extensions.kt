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

fun Tensor<Int>.toIntTensor(): IntTensor {
    val dimension = dimension()
    val default = default()
        ?: throw IllegalStateException("default of Tensor<Int> cannot be null if it must convert to IntTensor")
    val tensor = IntTensor.create {
        this dimension dimension
        this default default
    }

    forEachIndexed { position, v ->
        val value = v
            ?: throw IllegalStateException("value cannot be null, but on position ${position.contentToString()} it is")

        tensor.set(value, position)
    }

    return tensor
}

fun Tensor<Double>.toDoubleTensor(): DoubleTensor {
    val dimension = dimension()
    val default = default()
        ?: throw IllegalStateException("default of Tensor<Double> cannot be null if it must convert to IntTensor")
    val tensor = DoubleTensor.create {
        this dimension dimension
        this default default
    }

    forEachIndexed { position, v ->
        val value = v
            ?: throw IllegalStateException("value cannot be null, but on position ${position.contentToString()} it is")

        tensor.set(value, position)
    }

    return tensor
}

fun Tensor<Float>.toFloatTensor(): FloatTensor {
    val dimension = dimension()
    val default = default()
        ?: throw IllegalStateException("default of Tensor<Float> cannot be null if it must convert to IntTensor")
    val tensor = FloatTensor.create {
        this dimension dimension
        this default default
    }

    forEachIndexed { position, v ->
        val value = v
            ?: throw IllegalStateException("value cannot be null, but on position ${position.contentToString()} it is")

        tensor.set(value, position)
    }

    return tensor
}

fun Tensor<Long>.toLongTensor(): LongTensor {
    val dimension = dimension()
    val default = default()
        ?: throw IllegalStateException("default of Tensor<Long> cannot be null if it must convert to IntTensor")
    val tensor = LongTensor.create {
        this dimension dimension
        this default default
    }

    forEachIndexed { position, v ->
        val value = v
            ?: throw IllegalStateException("value cannot be null, but on position ${position.contentToString()} it is")

        tensor.set(value, position)
    }

    return tensor
}