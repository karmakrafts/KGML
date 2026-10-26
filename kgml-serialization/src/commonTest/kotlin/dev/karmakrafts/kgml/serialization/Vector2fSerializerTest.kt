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

import dev.karmakrafts.kgml.vector.Vector2f
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Vector2fSerializerTest {
    @Test
    fun descriptorShouldDescribeFloatComponents() = assertDescriptor(
        Vector2fSerializer, "kotlin.FloatArray", PrimitiveKind.FLOAT, 2
    )

    @Test
    fun serializeShouldWriteAllComponentsInOrder() = assertSerializes(
        Vector2fSerializer, Vector2f(1.25F, -2.5F), 1.25F, -2.5F
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Vector2fSerializer, Vector2f(1.25F, -2.5F), 1.25F, -2.5F
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Vector2f(0F, 0F), decode(Vector2fSerializer))
        assertEquals(Vector2f(0F, -2.5F), decode(Vector2fSerializer, 1 to -2.5F))
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Vector2fSerializer, "Vector2f", 2)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Vector2fSerializer, Vector2f(1.25F, -2.5F))

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Vector2fSerializer, Vector2f(Float.MAX_VALUE, -Float.MIN_VALUE)
    )
}