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

import dev.karmakrafts.kgml.matrix.Matrix4x4d
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A [KSerializer] for [Matrix4x4d].
 *
 * The descriptor lists matrix components as named fields in row-major order.
 * Matrix properties are not serialized.
 */
public object Matrix4x4dSerializer : KSerializer<Matrix4x4d> {
    /**
     * The `Matrix4x4d` class descriptor with sixteen [Double] elements named `m00` through `m33`
     * in row-major order.
     */
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Matrix4x4d") {
        element<Double>("m00")
        element<Double>("m01")
        element<Double>("m02")
        element<Double>("m03")

        element<Double>("m10")
        element<Double>("m11")
        element<Double>("m12")
        element<Double>("m13")

        element<Double>("m20")
        element<Double>("m21")
        element<Double>("m22")
        element<Double>("m23")

        element<Double>("m30")
        element<Double>("m31")
        element<Double>("m32")
        element<Double>("m33")
    }

    /**
     * Encodes the components of [value] in row-major order using [encoder].
     *
     * @param encoder The encoder to write to.
     * @param value The matrix to serialize.
     */
    override fun serialize(encoder: Encoder, value: Matrix4x4d) {
        encoder.beginStructure(descriptor).apply {
            encodeDoubleElement(descriptor, 0, value.m00)
            encodeDoubleElement(descriptor, 1, value.m01)
            encodeDoubleElement(descriptor, 2, value.m02)
            encodeDoubleElement(descriptor, 3, value.m03)
            encodeDoubleElement(descriptor, 4, value.m10)
            encodeDoubleElement(descriptor, 5, value.m11)
            encodeDoubleElement(descriptor, 6, value.m12)
            encodeDoubleElement(descriptor, 7, value.m13)
            encodeDoubleElement(descriptor, 8, value.m20)
            encodeDoubleElement(descriptor, 9, value.m21)
            encodeDoubleElement(descriptor, 10, value.m22)
            encodeDoubleElement(descriptor, 11, value.m23)
            encodeDoubleElement(descriptor, 12, value.m30)
            encodeDoubleElement(descriptor, 13, value.m31)
            encodeDoubleElement(descriptor, 14, value.m32)
            encodeDoubleElement(descriptor, 15, value.m33)
            endStructure(descriptor)
        }
    }

    /**
     * Decodes a [Matrix4x4d] using [decoder].
     *
     * Components not reported by the decoder default to zero.
     * Matrix properties use the default provided by the matrix constructor.
     *
     * @param decoder The decoder to read from.
     * @return The decoded matrix.
     * @throws SerializationException If the decoder reports an unexpected element index.
     */
    override fun deserialize(decoder: Decoder): Matrix4x4d {
        return decoder.beginStructure(descriptor).let { composite ->
            var m00 = 0.0
            var m01 = 0.0
            var m02 = 0.0
            var m03 = 0.0
            var m10 = 0.0
            var m11 = 0.0
            var m12 = 0.0
            var m13 = 0.0
            var m20 = 0.0
            var m21 = 0.0
            var m22 = 0.0
            var m23 = 0.0
            var m30 = 0.0
            var m31 = 0.0
            var m32 = 0.0
            var m33 = 0.0
            while (true) {
                when (val index = composite.decodeElementIndex(descriptor)) {
                    CompositeDecoder.DECODE_DONE -> break
                    0 -> m00 = composite.decodeDoubleElement(descriptor, 0)
                    1 -> m01 = composite.decodeDoubleElement(descriptor, 1)
                    2 -> m02 = composite.decodeDoubleElement(descriptor, 2)
                    3 -> m03 = composite.decodeDoubleElement(descriptor, 3)
                    4 -> m10 = composite.decodeDoubleElement(descriptor, 4)
                    5 -> m11 = composite.decodeDoubleElement(descriptor, 5)
                    6 -> m12 = composite.decodeDoubleElement(descriptor, 6)
                    7 -> m13 = composite.decodeDoubleElement(descriptor, 7)
                    8 -> m20 = composite.decodeDoubleElement(descriptor, 8)
                    9 -> m21 = composite.decodeDoubleElement(descriptor, 9)
                    10 -> m22 = composite.decodeDoubleElement(descriptor, 10)
                    11 -> m23 = composite.decodeDoubleElement(descriptor, 11)
                    12 -> m30 = composite.decodeDoubleElement(descriptor, 12)
                    13 -> m31 = composite.decodeDoubleElement(descriptor, 13)
                    14 -> m32 = composite.decodeDoubleElement(descriptor, 14)
                    15 -> m33 = composite.decodeDoubleElement(descriptor, 15)
                    else -> throw SerializationException("Unexpected element index in Matrix4x4d $index")
                }
            }
            composite.endStructure(descriptor)
            Matrix4x4d( // @formatter:off
                m00, m01, m02, m03,
                m10, m11, m12, m13,
                m20, m21, m22, m23,
                m30, m31, m32, m33
            ) // @formatter:on
        }
    }
}