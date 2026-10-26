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

import dev.karmakrafts.kgml.vector.Vector4i
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Vector4iSerializerTest {
    @Test
    fun descriptorShouldDescribeIntComponents() = assertDescriptor(
        Vector4iSerializer, "kotlin.IntArray", PrimitiveKind.INT, 4
    )

    @Test
    fun serializeShouldWriteAllComponentsInOrder() = assertSerializes(
        Vector4iSerializer, Vector4i(1, -2, 3, -4), 1, -2, 3, -4
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Vector4iSerializer, Vector4i(1, -2, 3, -4), 1, -2, 3, -4
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Vector4i(0, 0, 0, 0), decode(Vector4iSerializer))
        assertEquals(Vector4i(0, 0, 0, -4), decode(Vector4iSerializer, 3 to -4))
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Vector4iSerializer, "Vector4i", 4)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Vector4iSerializer, Vector4i(1, -2, 3, -4))

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Vector4iSerializer, Vector4i(Int.MIN_VALUE, Int.MAX_VALUE, 0, -1)
    )
}