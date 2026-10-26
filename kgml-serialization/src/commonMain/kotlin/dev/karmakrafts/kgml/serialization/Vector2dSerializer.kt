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

import dev.karmakrafts.kgml.vector.Vector2d
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.DoubleArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A [KSerializer] for [Vector2d].
 *
 * Vector components are represented as a [DoubleArray] in `x`, `y` order.
 */
public object Vector2dSerializer : KSerializer<Vector2d> {
    /**
     * The [DoubleArray] descriptor for components in `x`, `y` order.
     */
    override val descriptor: SerialDescriptor = DoubleArraySerializer().descriptor

    /**
     * Encodes the components of [value] using [encoder].
     *
     * @param encoder The encoder to write to.
     * @param value The vector to serialize.
     */
    override fun serialize(encoder: Encoder, value: Vector2d) {
        encoder.beginCollection(descriptor, 2).apply {
            encodeDoubleElement(descriptor, 0, value.x)
            encodeDoubleElement(descriptor, 1, value.y)
            endStructure(descriptor)
        }
    }

    /**
     * Decodes a [Vector2d] using [decoder].
     *
     * Components not reported by the decoder default to zero.
     *
     * @param decoder The decoder to read from.
     * @return The decoded vector.
     * @throws SerializationException If the decoder reports an unexpected element index.
     */
    override fun deserialize(decoder: Decoder): Vector2d {
        return decoder.beginStructure(descriptor).let { composite ->
            var x = 0.0
            var y = 0.0
            composite.decodeArray(descriptor) { index ->
                when (index) {
                    0 -> x = composite.decodeDoubleElement(descriptor, 0)
                    1 -> y = composite.decodeDoubleElement(descriptor, 1)
                    else -> throw SerializationException("Unexpected element index in Vector2d $index")
                }
            }
            composite.endStructure(descriptor)
            Vector2d(x, y)
        }
    }
}