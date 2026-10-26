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

import dev.karmakrafts.kgml.vector.Vector4d
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Vector4dSerializerTest {
    @Test
    fun descriptorShouldDescribeDoubleComponents() = assertDescriptor(
        Vector4dSerializer, "Vector4d", PrimitiveKind.DOUBLE, "x", "y", "z", "w"
    )

    @Test
    fun serializeShouldWriteAllComponentsInOrder() = assertSerializes(
        Vector4dSerializer, Vector4d(1.25, -2.5, 3.75, -4.5), 1.25, -2.5, 3.75, -4.5
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Vector4dSerializer, Vector4d(1.25, -2.5, 3.75, -4.5), 1.25, -2.5, 3.75, -4.5
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Vector4d(0.0, 0.0, 0.0, 0.0), decode(Vector4dSerializer))
        assertEquals(Vector4d(0.0, 0.0, 0.0, -4.5), decode(Vector4dSerializer, 3 to -4.5))
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Vector4dSerializer)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Vector4dSerializer, Vector4d(1.25, -2.5, 3.75, -4.5))

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Vector4dSerializer, Vector4d(Double.MAX_VALUE, -Double.MIN_VALUE, -Double.MAX_VALUE, 0.0)
    )
}