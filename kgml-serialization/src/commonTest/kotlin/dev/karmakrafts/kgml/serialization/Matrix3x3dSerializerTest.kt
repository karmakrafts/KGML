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

import dev.karmakrafts.kgml.matrix.Matrix3x3d
import dev.karmakrafts.kgml.matrix.MatrixProperties
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix3x3dSerializerTest {
    private val value = Matrix3x3d(
        1.25, -2.5, 3.75, -4.5, 5.25, -6.5, 7.75, -8.5, 9.25
    )

    @Test
    fun descriptorShouldDescribeDoubleComponentsInRowMajorOrder() = assertDescriptor(
        Matrix3x3dSerializer,
        "Matrix3x3d",
        PrimitiveKind.DOUBLE,
        "m00",
        "m01",
        "m02",
        "m10",
        "m11",
        "m12",
        "m20",
        "m21",
        "m22"
    )

    @Test
    fun serializeShouldWriteAllComponentsInRowMajorOrder() = assertSerializes(
        Matrix3x3dSerializer, value, 1.25, -2.5, 3.75, -4.5, 5.25, -6.5, 7.75, -8.5, 9.25
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Matrix3x3dSerializer, value, 1.25, -2.5, 3.75, -4.5, 5.25, -6.5, 7.75, -8.5, 9.25
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Matrix3x3d(0.0), decode(Matrix3x3dSerializer))
        assertEquals(
            Matrix3x3d(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 9.25), decode(Matrix3x3dSerializer, 8 to 9.25)
        )
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Matrix3x3dSerializer)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Matrix3x3dSerializer, value)

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Matrix3x3dSerializer, Matrix3x3d(
            Double.MAX_VALUE, -Double.MIN_VALUE, -Double.MAX_VALUE, 0.0, 1.0, -1.0, Double.MIN_VALUE, 2.5, -3.75
        )
    )

    @Test
    fun matrixPropertiesShouldNotBeSerialized() {
        assertSerializes(Matrix3x3dSerializer, Matrix3x3d(), 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0)
        val decoded = decode(Matrix3x3dSerializer, 0 to 1.0, 4 to 1.0, 8 to 1.0)
        assertEquals(MatrixProperties.NONE, decoded.properties)
    }
}