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

import dev.karmakrafts.kgml.matrix.Matrix4x4f
import dev.karmakrafts.kgml.matrix.MatrixProperties
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix4x4fSerializerTest {
    private val value = Matrix4x4f(
        1.25F,
        -2.5F,
        3.75F,
        -4.5F,
        5.25F,
        -6.5F,
        7.75F,
        -8.5F,
        9.25F,
        -10.5F,
        11.75F,
        -12.5F,
        13.25F,
        -14.5F,
        15.75F,
        -16.5F
    )

    @Test
    fun descriptorShouldDescribeFloatComponentsInRowMajorOrder() = assertDescriptor(
        Matrix4x4fSerializer, "kotlin.FloatArray", PrimitiveKind.FLOAT, 16
    )

    @Test
    fun serializeShouldWriteAllComponentsInRowMajorOrder() = assertSerializes(
        Matrix4x4fSerializer,
        value,
        1.25F,
        -2.5F,
        3.75F,
        -4.5F,
        5.25F,
        -6.5F,
        7.75F,
        -8.5F,
        9.25F,
        -10.5F,
        11.75F,
        -12.5F,
        13.25F,
        -14.5F,
        15.75F,
        -16.5F
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Matrix4x4fSerializer,
        value,
        1.25F,
        -2.5F,
        3.75F,
        -4.5F,
        5.25F,
        -6.5F,
        7.75F,
        -8.5F,
        9.25F,
        -10.5F,
        11.75F,
        -12.5F,
        13.25F,
        -14.5F,
        15.75F,
        -16.5F
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Matrix4x4f(0F), decode(Matrix4x4fSerializer))
        assertEquals(
            Matrix4x4f(
                0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -16.5F
            ), decode(Matrix4x4fSerializer, 15 to -16.5F)
        )
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Matrix4x4fSerializer, "Matrix4x4f", 16)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Matrix4x4fSerializer, value)

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Matrix4x4fSerializer, Matrix4x4f(
            Float.MAX_VALUE,
            -Float.MIN_VALUE,
            -Float.MAX_VALUE,
            0F,
            1F,
            -1F,
            Float.MIN_VALUE,
            2.5F,
            -3.75F,
            4.5F,
            -5.25F,
            6.5F,
            -7.75F,
            8.5F,
            -9.25F,
            10.5F
        )
    )

    @Test
    fun matrixPropertiesShouldNotBeSerialized() {
        assertSerializes(
            Matrix4x4fSerializer, Matrix4x4f(), 1F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 1F
        )
        val decoded = decode(Matrix4x4fSerializer, 0 to 1F, 5 to 1F, 10 to 1F, 15 to 1F)
        assertEquals(MatrixProperties.NONE, decoded.properties)
    }
}