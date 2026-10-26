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
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Vector2iSerializerTest {
    @Test
    fun descriptorShouldDescribeIntComponents() = assertDescriptor(
        Vector2iSerializer, "Vector2i", PrimitiveKind.INT, "x", "y"
    )

    @Test
    fun serializeShouldWriteAllComponentsInOrder() = assertSerializes(
        Vector2iSerializer, Vector2i(1, -2), 1, -2
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Vector2iSerializer, Vector2i(1, -2), 1, -2
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Vector2i(0, 0), decode(Vector2iSerializer))
        assertEquals(Vector2i(0, -2), decode(Vector2iSerializer, 1 to -2))
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Vector2iSerializer)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Vector2iSerializer, Vector2i(1, -2))

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Vector2iSerializer, Vector2i(Int.MIN_VALUE, Int.MAX_VALUE)
    )
}