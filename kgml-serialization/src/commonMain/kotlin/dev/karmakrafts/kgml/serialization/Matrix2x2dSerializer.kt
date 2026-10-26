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
import kotlinx.serialization.builtins.DoubleArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A [KSerializer] for [Matrix2x2d].
 *
 * Matrix components are represented as a [DoubleArray] in row-major order.
 * Matrix properties are not serialized.
 */
public object Matrix2x2dSerializer : KSerializer<Matrix2x2d> {
    /**
     * The [DoubleArray] descriptor for components in row-major order.
     */
    override val descriptor: SerialDescriptor = DoubleArraySerializer().descriptor

    /**
     * Encodes the components of [value] using [encoder].
     *
     * @param encoder The encoder to write to.
     * @param value The matrix to serialize.
     */
    override fun serialize(encoder: Encoder, value: Matrix2x2d) {
        encoder.beginCollection(descriptor, 4).apply {
            encodeDoubleElement(descriptor, 0, value.m00)
            encodeDoubleElement(descriptor, 1, value.m01)
            encodeDoubleElement(descriptor, 2, value.m10)
            encodeDoubleElement(descriptor, 3, value.m11)
            endStructure(descriptor)
        }
    }

    /**
     * Decodes a [Matrix2x2d] using [decoder].
     *
     * Components not reported by the decoder default to zero.
     * Matrix properties use the default provided by the matrix constructor.
     *
     * @param decoder The decoder to read from.
     * @return The decoded matrix.
     * @throws SerializationException If the decoder reports an unexpected element index.
     */
    override fun deserialize(decoder: Decoder): Matrix2x2d {
        return decoder.beginStructure(descriptor).let { composite ->
            var m00 = 0.0
            var m01 = 0.0
            var m10 = 0.0
            var m11 = 0.0
            composite.decodeArray(descriptor) { index ->
                when (index) {
                    0 -> m00 = composite.decodeDoubleElement(descriptor, 0)
                    1 -> m01 = composite.decodeDoubleElement(descriptor, 1)
                    2 -> m10 = composite.decodeDoubleElement(descriptor, 2)
                    3 -> m11 = composite.decodeDoubleElement(descriptor, 3)
                    else -> throw SerializationException("Unexpected element index in Matrix2x2d $index")
                }
            }
            composite.endStructure(descriptor)
            Matrix2x2d(m00, m01, m10, m11)
        }
    }
}