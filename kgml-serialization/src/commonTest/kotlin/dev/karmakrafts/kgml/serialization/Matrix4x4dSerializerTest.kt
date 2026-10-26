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

import dev.karmakrafts.kgml.matrix.Matrix4x4d
import dev.karmakrafts.kgml.matrix.MatrixProperties
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix4x4dSerializerTest {
    private val value = Matrix4x4d(
        1.25, -2.5, 3.75, -4.5, 5.25, -6.5, 7.75, -8.5, 9.25, -10.5, 11.75, -12.5, 13.25, -14.5, 15.75, -16.5
    )

    @Test
    fun descriptorShouldDescribeDoubleComponentsInRowMajorOrder() = assertDescriptor(
        Matrix4x4dSerializer,
        "Matrix4x4d",
        PrimitiveKind.DOUBLE,
        "m00",
        "m01",
        "m02",
        "m03",
        "m10",
        "m11",
        "m12",
        "m13",
        "m20",
        "m21",
        "m22",
        "m23",
        "m30",
        "m31",
        "m32",
        "m33"
    )

    @Test
    fun serializeShouldWriteAllComponentsInRowMajorOrder() = assertSerializes(
        Matrix4x4dSerializer,
        value,
        1.25,
        -2.5,
        3.75,
        -4.5,
        5.25,
        -6.5,
        7.75,
        -8.5,
        9.25,
        -10.5,
        11.75,
        -12.5,
        13.25,
        -14.5,
        15.75,
        -16.5
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Matrix4x4dSerializer,
        value,
        1.25,
        -2.5,
        3.75,
        -4.5,
        5.25,
        -6.5,
        7.75,
        -8.5,
        9.25,
        -10.5,
        11.75,
        -12.5,
        13.25,
        -14.5,
        15.75,
        -16.5
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Matrix4x4d(0.0), decode(Matrix4x4dSerializer))
        assertEquals(
            Matrix4x4d(
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -16.5
            ), decode(Matrix4x4dSerializer, 15 to -16.5)
        )
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Matrix4x4dSerializer)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Matrix4x4dSerializer, value)

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Matrix4x4dSerializer, Matrix4x4d(
            Double.MAX_VALUE,
            -Double.MIN_VALUE,
            -Double.MAX_VALUE,
            0.0,
            1.0,
            -1.0,
            Double.MIN_VALUE,
            2.5,
            -3.75,
            4.5,
            -5.25,
            6.5,
            -7.75,
            8.5,
            -9.25,
            10.5
        )
    )

    @Test
    fun matrixPropertiesShouldNotBeSerialized() {
        assertSerializes(
            Matrix4x4dSerializer,
            Matrix4x4d(),
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            1.0
        )
        val decoded = decode(Matrix4x4dSerializer, 0 to 1.0, 5 to 1.0, 10 to 1.0, 15 to 1.0)
        assertEquals(MatrixProperties.NONE, decoded.properties)
    }
}