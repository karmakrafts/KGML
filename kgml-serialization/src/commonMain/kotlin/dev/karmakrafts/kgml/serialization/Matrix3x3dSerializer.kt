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

import dev.karmakrafts.kgml.matrix.Matrix3x3d
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.DoubleArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A [KSerializer] for [Matrix3x3d].
 *
 * Matrix components are represented as a [DoubleArray] in row-major order.
 * Matrix properties are not serialized.
 */
public object Matrix3x3dSerializer : KSerializer<Matrix3x3d> {
    /**
     * The [DoubleArray] descriptor for components in row-major order.
     */
    override val descriptor: SerialDescriptor = DoubleArraySerializer().descriptor

    /**
     * Encodes the components of [value] in row-major order using [encoder].
     *
     * @param encoder The encoder to write to.
     * @param value The matrix to serialize.
     */
    override fun serialize(encoder: Encoder, value: Matrix3x3d) {
        encoder.beginCollection(descriptor, 9).apply {
            encodeDoubleElement(descriptor, 0, value.m00)
            encodeDoubleElement(descriptor, 1, value.m01)
            encodeDoubleElement(descriptor, 2, value.m02)
            encodeDoubleElement(descriptor, 3, value.m10)
            encodeDoubleElement(descriptor, 4, value.m11)
            encodeDoubleElement(descriptor, 5, value.m12)
            encodeDoubleElement(descriptor, 6, value.m20)
            encodeDoubleElement(descriptor, 7, value.m21)
            encodeDoubleElement(descriptor, 8, value.m22)
            endStructure(descriptor)
        }
    }

    /**
     * Decodes a [Matrix3x3d] using [decoder].
     *
     * Components not reported by the decoder default to zero.
     * Matrix properties use the default provided by the matrix constructor.
     *
     * @param decoder The decoder to read from.
     * @return The decoded matrix.
     * @throws SerializationException If the decoder reports an unexpected element index.
     */
    override fun deserialize(decoder: Decoder): Matrix3x3d {
        return decoder.beginStructure(descriptor).let { composite ->
            var m00 = 0.0
            var m01 = 0.0
            var m02 = 0.0
            var m10 = 0.0
            var m11 = 0.0
            var m12 = 0.0
            var m20 = 0.0
            var m21 = 0.0
            var m22 = 0.0
            composite.decodeArray(descriptor) { index ->
                when (index) {
                    0 -> m00 = composite.decodeDoubleElement(descriptor, 0)
                    1 -> m01 = composite.decodeDoubleElement(descriptor, 1)
                    2 -> m02 = composite.decodeDoubleElement(descriptor, 2)
                    3 -> m10 = composite.decodeDoubleElement(descriptor, 3)
                    4 -> m11 = composite.decodeDoubleElement(descriptor, 4)
                    5 -> m12 = composite.decodeDoubleElement(descriptor, 5)
                    6 -> m20 = composite.decodeDoubleElement(descriptor, 6)
                    7 -> m21 = composite.decodeDoubleElement(descriptor, 7)
                    8 -> m22 = composite.decodeDoubleElement(descriptor, 8)
                    else -> throw SerializationException("Unexpected element index in Matrix3x3d $index")
                }
            }
            composite.endStructure(descriptor)
            Matrix3x3d( // @formatter:off
                m00, m01, m02,
                m10, m11, m12,
                m20, m21, m22
            ) // @formatter:on
        }
    }
}