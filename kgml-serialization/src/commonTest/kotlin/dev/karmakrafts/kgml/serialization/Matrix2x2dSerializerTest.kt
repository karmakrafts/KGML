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

import dev.karmakrafts.kgml.matrix.Matrix2x2d
import dev.karmakrafts.kgml.matrix.MatrixProperties
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix2x2dSerializerTest {
    private val value = Matrix2x2d(1.25, -2.5, 3.75, -4.5)

    @Test
    fun descriptorShouldDescribeDoubleComponentsInRowMajorOrder() = assertDescriptor(
        Matrix2x2dSerializer, "kotlin.DoubleArray", PrimitiveKind.DOUBLE, 4
    )

    @Test
    fun serializeShouldWriteAllComponentsInRowMajorOrder() = assertSerializes(
        Matrix2x2dSerializer, value, 1.25, -2.5, 3.75, -4.5
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Matrix2x2dSerializer, value, 1.25, -2.5, 3.75, -4.5
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Matrix2x2d(0.0), decode(Matrix2x2dSerializer))
        assertEquals(Matrix2x2d(0.0, 0.0, 0.0, -4.5), decode(Matrix2x2dSerializer, 3 to -4.5))
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Matrix2x2dSerializer, "Matrix2x2d", 4)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Matrix2x2dSerializer, value)

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Matrix2x2dSerializer, Matrix2x2d(Double.MAX_VALUE, -Double.MIN_VALUE, -Double.MAX_VALUE, 0.0)
    )

    @Test
    fun matrixPropertiesShouldNotBeSerialized() {
        assertSerializes(Matrix2x2dSerializer, Matrix2x2d(), 1.0, 0.0, 0.0, 1.0)
        val decoded = decode(Matrix2x2dSerializer, 0 to 1.0, 3 to 1.0)
        assertEquals(MatrixProperties.NONE, decoded.properties)
    }
}