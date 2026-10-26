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

import dev.karmakrafts.kgml.vector.Vector2i
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public object Vector2iSerializer : KSerializer<Vector2i> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Vector2i") {
        element<Int>("x")
        element<Int>("y")
    }

    override fun serialize(encoder: Encoder, value: Vector2i) {
        encoder.beginStructure(descriptor).apply {
            encodeIntElement(descriptor, 0, value.x)
            encodeIntElement(descriptor, 1, value.y)
        }
    }

    override fun deserialize(decoder: Decoder): Vector2i {
        return decoder.beginStructure(descriptor).let { composite ->
            var x = 0
            var y = 0
            while (true) {
                when (val index = composite.decodeElementIndex(descriptor)) {
                    CompositeDecoder.DECODE_DONE -> break
                    0 -> x = composite.decodeIntElement(descriptor, 0)
                    1 -> y = composite.decodeIntElement(descriptor, 1)
                    else -> throw SerializationException("Unexpected element index in Vector2i $index")
                }
            }
            Vector2i(x, y)
        }
    }
}