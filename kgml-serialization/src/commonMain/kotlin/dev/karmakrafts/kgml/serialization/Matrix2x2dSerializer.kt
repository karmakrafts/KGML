/*
 * Copyright 2026 Karma Krafts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.karmakrafts.kgml.serialization

import dev.karmakrafts.kgml.matrix.Matrix2x2d
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public object Matrix2x2dSerializer : KSerializer<Matrix2x2d> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Matrix2x2d") {
        element<Double>("m00")
        element<Double>("m01")
        element<Double>("m10")
        element<Double>("m11")
    }

    override fun serialize(encoder: Encoder, value: Matrix2x2d) {
        encoder.beginStructure(descriptor).apply {
            encodeDoubleElement(descriptor, 0, value.m00)
            encodeDoubleElement(descriptor, 1, value.m01)
            encodeDoubleElement(descriptor, 2, value.m10)
            encodeDoubleElement(descriptor, 3, value.m11)
        }
    }

    override fun deserialize(decoder: Decoder): Matrix2x2d {
        return decoder.beginStructure(descriptor).let { composite ->
            var m00 = 0.0
            var m01 = 0.0
            var m10 = 0.0
            var m11 = 0.0
            while (true) {
                when (val index = composite.decodeElementIndex(descriptor)) {
                    CompositeDecoder.DECODE_DONE -> break
                    0 -> m00 = composite.decodeDoubleElement(descriptor, 0)
                    1 -> m01 = composite.decodeDoubleElement(descriptor, 1)
                    2 -> m10 = composite.decodeDoubleElement(descriptor, 2)
                    3 -> m11 = composite.decodeDoubleElement(descriptor, 3)
                    else -> throw SerializationException("Unexpected element index in Matrix2x2d $index")
                }
            }
            Matrix2x2d(m00, m01, m10, m11)
        }
    }
}