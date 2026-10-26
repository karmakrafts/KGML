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
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Vector2dSerializerTest {
    @Test
    fun descriptorShouldDescribeDoubleComponents() = assertDescriptor(
        Vector2dSerializer, "Vector2d", PrimitiveKind.DOUBLE, "x", "y"
    )

    @Test
    fun serializeShouldWriteAllComponentsInOrder() = assertSerializes(
        Vector2dSerializer, Vector2d(1.25, -2.5), 1.25, -2.5
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Vector2dSerializer, Vector2d(1.25, -2.5), 1.25, -2.5
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Vector2d(0.0, 0.0), decode(Vector2dSerializer))
        assertEquals(Vector2d(0.0, -2.5), decode(Vector2dSerializer, 1 to -2.5))
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Vector2dSerializer)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Vector2dSerializer, Vector2d(1.25, -2.5))

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Vector2dSerializer, Vector2d(Double.MAX_VALUE, -Double.MIN_VALUE)
    )
}