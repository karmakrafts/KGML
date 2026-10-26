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

import dev.karmakrafts.kgml.matrix.Matrix2x2f
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A [KSerializer] for [Matrix2x2f].
 *
 * Matrix components are represented as named fields in row-major order.
 * Matrix properties are not serialized.
 */
public object Matrix2x2fSerializer : KSerializer<Matrix2x2f> {
    /**
     * The `Matrix2x2f` class descriptor with four [Float] elements named
     * `m00`, `m01`, `m10`, and `m11` in row-major order.
     */
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Matrix2x2f") {
        element<Float>("m00")
        element<Float>("m01")
        element<Float>("m10")
        element<Float>("m11")
    }

    /**
     * Encodes the components of [value] using [encoder].
     *
     * @param encoder The encoder to write to.
     * @param value The matrix to serialize.
     */
    override fun serialize(encoder: Encoder, value: Matrix2x2f) {
        encoder.beginStructure(descriptor).apply {
            encodeFloatElement(descriptor, 0, value.m00)
            encodeFloatElement(descriptor, 1, value.m01)
            encodeFloatElement(descriptor, 2, value.m10)
            encodeFloatElement(descriptor, 3, value.m11)
            endStructure(descriptor)
        }
    }

    /**
     * Decodes a [Matrix2x2f] using [decoder].
     *
     * Components not reported by the decoder default to zero.
     * Matrix properties use the default provided by the matrix constructor.
     *
     * @param decoder The decoder to read from.
     * @return The decoded matrix.
     * @throws SerializationException If the decoder reports an unexpected element index.
     */
    override fun deserialize(decoder: Decoder): Matrix2x2f {
        return decoder.beginStructure(descriptor).let { composite ->
            var m00 = 0F
            var m01 = 0F
            var m10 = 0F
            var m11 = 0F
            while (true) {
                when (val index = composite.decodeElementIndex(descriptor)) {
                    CompositeDecoder.DECODE_DONE -> break
                    0 -> m00 = composite.decodeFloatElement(descriptor, 0)
                    1 -> m01 = composite.decodeFloatElement(descriptor, 1)
                    2 -> m10 = composite.decodeFloatElement(descriptor, 2)
                    3 -> m11 = composite.decodeFloatElement(descriptor, 3)
                    else -> throw SerializationException("Unexpected element index in Matrix2x2f $index")
                }
            }
            composite.endStructure(descriptor)
            Matrix2x2f(m00, m01, m10, m11)
        }
    }
}