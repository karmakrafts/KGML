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

import dev.karmakrafts.kgml.matrix.Matrix3x3f
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public object Matrix3x3fSerializer : KSerializer<Matrix3x3f> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Matrix3x3f") {
        element<Float>("m00")
        element<Float>("m01")
        element<Float>("m02")

        element<Float>("m10")
        element<Float>("m11")
        element<Float>("m12")

        element<Float>("m20")
        element<Float>("m21")
        element<Float>("m22")
    }

    override fun serialize(encoder: Encoder, value: Matrix3x3f) {
        encoder.beginStructure(descriptor).apply {
            encodeFloatElement(descriptor, 0, value.m00)
            encodeFloatElement(descriptor, 1, value.m01)
            encodeFloatElement(descriptor, 2, value.m10)
            encodeFloatElement(descriptor, 3, value.m11)
        }
    }

    override fun deserialize(decoder: Decoder): Matrix3x3f {
        return decoder.beginStructure(descriptor).let { composite ->
            var m00 = 0F
            var m01 = 0F
            var m02 = 0F
            var m10 = 0F
            var m11 = 0F
            var m12 = 0F
            var m20 = 0F
            var m21 = 0F
            var m22 = 0F
            while (true) {
                when (val index = composite.decodeElementIndex(descriptor)) {
                    CompositeDecoder.DECODE_DONE -> break
                    0 -> m00 = composite.decodeFloatElement(descriptor, 0)
                    1 -> m01 = composite.decodeFloatElement(descriptor, 1)
                    2 -> m02 = composite.decodeFloatElement(descriptor, 2)
                    3 -> m10 = composite.decodeFloatElement(descriptor, 3)
                    4 -> m11 = composite.decodeFloatElement(descriptor, 4)
                    5 -> m12 = composite.decodeFloatElement(descriptor, 5)
                    6 -> m20 = composite.decodeFloatElement(descriptor, 6)
                    7 -> m21 = composite.decodeFloatElement(descriptor, 7)
                    8 -> m22 = composite.decodeFloatElement(descriptor, 8)
                    else -> throw SerializationException("Unexpected element index in Matrix3x3f $index")
                }
            }
            Matrix3x3f( // @formatter:off
                m00, m01, m02,
                m10, m11, m12,
                m20, m21, m22
            ) // @formatter:on
        }
    }
}