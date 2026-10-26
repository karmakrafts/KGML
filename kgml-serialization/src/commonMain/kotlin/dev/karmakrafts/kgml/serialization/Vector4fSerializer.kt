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

import dev.karmakrafts.kgml.vector.Vector4f
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A [KSerializer] for [Vector4f].
 *
 * Vector components are represented as named [Float] fields `x`, `y`, `z`, and `w`.
 */
public object Vector4fSerializer : KSerializer<Vector4f> {
    /**
     * The `Vector4f` class descriptor with [Float] elements `x`, `y`, `z`, and `w`, in that order.
     */
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Vector4f") {
        element<Float>("x")
        element<Float>("y")
        element<Float>("z")
        element<Float>("w")
    }

    /**
     * Encodes the components of [value] using [encoder].
     *
     * @param encoder The encoder to write to.
     * @param value The vector to serialize.
     */
    override fun serialize(encoder: Encoder, value: Vector4f) {
        encoder.beginStructure(descriptor).apply {
            encodeFloatElement(descriptor, 0, value.x)
            encodeFloatElement(descriptor, 1, value.y)
            encodeFloatElement(descriptor, 2, value.z)
            encodeFloatElement(descriptor, 3, value.w)
            endStructure(descriptor)
        }
    }

    /**
     * Decodes a [Vector4f] using [decoder].
     *
     * Components not reported by the decoder default to zero.
     *
     * @param decoder The decoder to read from.
     * @return The decoded vector.
     * @throws SerializationException If the decoder reports an unexpected element index.
     */
    override fun deserialize(decoder: Decoder): Vector4f {
        return decoder.beginStructure(descriptor).let { composite ->
            var x = 0F
            var y = 0F
            var z = 0F
            var w = 0F
            while (true) {
                when (val index = composite.decodeElementIndex(descriptor)) {
                    CompositeDecoder.DECODE_DONE -> break
                    0 -> x = composite.decodeFloatElement(descriptor, 0)
                    1 -> y = composite.decodeFloatElement(descriptor, 1)
                    2 -> z = composite.decodeFloatElement(descriptor, 2)
                    3 -> w = composite.decodeFloatElement(descriptor, 3)
                    else -> throw SerializationException("Unexpected element index in Vector4f $index")
                }
            }
            composite.endStructure(descriptor)
            Vector4f(x, y, z, w)
        }
    }
}