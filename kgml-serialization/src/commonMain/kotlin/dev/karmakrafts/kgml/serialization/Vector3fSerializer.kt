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

import dev.karmakrafts.kgml.vector.Vector3f
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A [KSerializer] for [Vector3f].
 *
 * Vector components are represented as named [Float] fields `x`, `y`, and `z`.
 */
public object Vector3fSerializer : KSerializer<Vector3f> {
    /**
     * The `Vector3f` class descriptor with [Float] elements `x`, `y`, and `z`, in that order.
     */
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Vector3f") {
        element<Float>("x")
        element<Float>("y")
        element<Float>("z")
    }

    /**
     * Encodes the components of [value] using [encoder].
     *
     * @param encoder The encoder to write to.
     * @param value The vector to serialize.
     */
    override fun serialize(encoder: Encoder, value: Vector3f) {
        encoder.beginStructure(descriptor).apply {
            encodeFloatElement(descriptor, 0, value.x)
            encodeFloatElement(descriptor, 1, value.y)
            encodeFloatElement(descriptor, 2, value.z)
            endStructure(descriptor)
        }
    }

    /**
     * Decodes a [Vector3f] using [decoder].
     *
     * Components not reported by the decoder default to zero.
     *
     * @param decoder The decoder to read from.
     * @return The decoded vector.
     * @throws SerializationException If the decoder reports an unexpected element index.
     */
    override fun deserialize(decoder: Decoder): Vector3f {
        return decoder.beginStructure(descriptor).let { composite ->
            var x = 0F
            var y = 0F
            var z = 0F
            while (true) {
                when (val index = composite.decodeElementIndex(descriptor)) {
                    CompositeDecoder.DECODE_DONE -> break
                    0 -> x = composite.decodeFloatElement(descriptor, 0)
                    1 -> y = composite.decodeFloatElement(descriptor, 1)
                    2 -> z = composite.decodeFloatElement(descriptor, 2)
                    else -> throw SerializationException("Unexpected element index in Vector3f $index")
                }
            }
            composite.endStructure(descriptor)
            Vector3f(x, y, z)
        }
    }
}