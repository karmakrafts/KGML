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

import dev.karmakrafts.kgml.matrix.Matrix4x4f
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A [KSerializer] for [Matrix4x4f].
 *
 * The descriptor lists matrix components as named fields in row-major order.
 * Matrix properties are not serialized.
 */
public object Matrix4x4fSerializer : KSerializer<Matrix4x4f> {
    /**
     * The `Matrix4x4f` class descriptor with sixteen [Float] elements named `m00` through `m33`
     * in row-major order.
     */
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Matrix4x4f") {
        element<Float>("m00")
        element<Float>("m01")
        element<Float>("m02")
        element<Float>("m03")

        element<Float>("m10")
        element<Float>("m11")
        element<Float>("m12")
        element<Float>("m13")

        element<Float>("m20")
        element<Float>("m21")
        element<Float>("m22")
        element<Float>("m23")

        element<Float>("m30")
        element<Float>("m31")
        element<Float>("m32")
        element<Float>("m33")
    }

    /**
     * Encodes [value] using [encoder].
     *
     * The current implementation writes only `m00`, `m01`, `m10`, and `m11`
     * at element indices 0 through 3, respectively.
     *
     * @param encoder The encoder to write to.
     * @param value The matrix to serialize.
     */
    override fun serialize(encoder: Encoder, value: Matrix4x4f) {
        encoder.beginStructure(descriptor).apply {
            encodeFloatElement(descriptor, 0, value.m00)
            encodeFloatElement(descriptor, 1, value.m01)
            encodeFloatElement(descriptor, 2, value.m10)
            encodeFloatElement(descriptor, 3, value.m11)
        }
    }

    /**
     * Decodes a [Matrix4x4f] using [decoder].
     *
     * Components not reported by the decoder default to zero.
     * Matrix properties use the default provided by the matrix constructor.
     *
     * @param decoder The decoder to read from.
     * @return The decoded matrix.
     * @throws SerializationException If the decoder reports an unexpected element index.
     */
    override fun deserialize(decoder: Decoder): Matrix4x4f {
        return decoder.beginStructure(descriptor).let { composite ->
            var m00 = 0F
            var m01 = 0F
            var m02 = 0F
            var m03 = 0F
            var m10 = 0F
            var m11 = 0F
            var m12 = 0F
            var m13 = 0F
            var m20 = 0F
            var m21 = 0F
            var m22 = 0F
            var m23 = 0F
            var m30 = 0F
            var m31 = 0F
            var m32 = 0F
            var m33 = 0F
            while (true) {
                when (val index = composite.decodeElementIndex(descriptor)) {
                    CompositeDecoder.DECODE_DONE -> break
                    0 -> m00 = composite.decodeFloatElement(descriptor, 0)
                    1 -> m01 = composite.decodeFloatElement(descriptor, 1)
                    2 -> m02 = composite.decodeFloatElement(descriptor, 2)
                    3 -> m03 = composite.decodeFloatElement(descriptor, 3)
                    4 -> m10 = composite.decodeFloatElement(descriptor, 4)
                    5 -> m11 = composite.decodeFloatElement(descriptor, 5)
                    6 -> m12 = composite.decodeFloatElement(descriptor, 6)
                    7 -> m13 = composite.decodeFloatElement(descriptor, 7)
                    8 -> m20 = composite.decodeFloatElement(descriptor, 8)
                    9 -> m21 = composite.decodeFloatElement(descriptor, 9)
                    10 -> m22 = composite.decodeFloatElement(descriptor, 10)
                    11 -> m23 = composite.decodeFloatElement(descriptor, 11)
                    12 -> m30 = composite.decodeFloatElement(descriptor, 12)
                    13 -> m31 = composite.decodeFloatElement(descriptor, 13)
                    14 -> m32 = composite.decodeFloatElement(descriptor, 14)
                    15 -> m33 = composite.decodeFloatElement(descriptor, 15)
                    else -> throw SerializationException("Unexpected element index in Matrix4x4f $index")
                }
            }
            Matrix4x4f( // @formatter:off
                m00, m01, m02, m03,
                m10, m11, m12, m13,
                m20, m21, m22, m23,
                m30, m31, m32, m33
            ) // @formatter:on
        }
    }
}