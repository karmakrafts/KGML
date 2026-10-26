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

import dev.karmakrafts.kgml.vector.Vector3i
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Vector3iSerializerTest {
    @Test
    fun descriptorShouldDescribeIntComponents() = assertDescriptor(
        Vector3iSerializer, "kotlin.IntArray", PrimitiveKind.INT, 3
    )

    @Test
    fun serializeShouldWriteAllComponentsInOrder() = assertSerializes(
        Vector3iSerializer, Vector3i(1, -2, 3), 1, -2, 3
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Vector3iSerializer, Vector3i(1, -2, 3), 1, -2, 3
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Vector3i(0, 0, 0), decode(Vector3iSerializer))
        assertEquals(Vector3i(0, 0, 3), decode(Vector3iSerializer, 2 to 3))
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Vector3iSerializer, "Vector3i", 3)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Vector3iSerializer, Vector3i(1, -2, 3))

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Vector3iSerializer, Vector3i(Int.MIN_VALUE, Int.MAX_VALUE, 0)
    )
}