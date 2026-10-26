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

import dev.karmakrafts.kgml.matrix.Matrix2x2f
import dev.karmakrafts.kgml.matrix.MatrixProperties
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix2x2fSerializerTest {
    private val value = Matrix2x2f(1.25F, -2.5F, 3.75F, -4.5F)

    @Test
    fun descriptorShouldDescribeFloatComponentsInRowMajorOrder() = assertDescriptor(
        Matrix2x2fSerializer, "kotlin.FloatArray", PrimitiveKind.FLOAT, 4
    )

    @Test
    fun serializeShouldWriteAllComponentsInRowMajorOrder() = assertSerializes(
        Matrix2x2fSerializer, value, 1.25F, -2.5F, 3.75F, -4.5F
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Matrix2x2fSerializer, value, 1.25F, -2.5F, 3.75F, -4.5F
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Matrix2x2f(0F), decode(Matrix2x2fSerializer))
        assertEquals(Matrix2x2f(0F, 0F, 0F, -4.5F), decode(Matrix2x2fSerializer, 3 to -4.5F))
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Matrix2x2fSerializer, "Matrix2x2f", 4)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Matrix2x2fSerializer, value)

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Matrix2x2fSerializer, Matrix2x2f(Float.MAX_VALUE, -Float.MIN_VALUE, -Float.MAX_VALUE, 0F)
    )

    @Test
    fun matrixPropertiesShouldNotBeSerialized() {
        assertSerializes(Matrix2x2fSerializer, Matrix2x2f(), 1F, 0F, 0F, 1F)
        val decoded = decode(Matrix2x2fSerializer, 0 to 1F, 3 to 1F)
        assertEquals(MatrixProperties.NONE, decoded.properties)
    }
}