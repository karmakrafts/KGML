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
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Vector4fSerializerTest {
    @Test
    fun descriptorShouldDescribeFloatComponents() = assertDescriptor(
        Vector4fSerializer, "Vector4f", PrimitiveKind.FLOAT, "x", "y", "z", "w"
    )

    @Test
    fun serializeShouldWriteAllComponentsInOrder() = assertSerializes(
        Vector4fSerializer, Vector4f(1.25F, -2.5F, 3.75F, -4.5F), 1.25F, -2.5F, 3.75F, -4.5F
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Vector4fSerializer, Vector4f(1.25F, -2.5F, 3.75F, -4.5F), 1.25F, -2.5F, 3.75F, -4.5F
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Vector4f(0F, 0F, 0F, 0F), decode(Vector4fSerializer))
        assertEquals(Vector4f(0F, 0F, 0F, -4.5F), decode(Vector4fSerializer, 3 to -4.5F))
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Vector4fSerializer)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Vector4fSerializer, Vector4f(1.25F, -2.5F, 3.75F, -4.5F))

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Vector4fSerializer, Vector4f(Float.MAX_VALUE, -Float.MIN_VALUE, -Float.MAX_VALUE, 0F)
    )
}