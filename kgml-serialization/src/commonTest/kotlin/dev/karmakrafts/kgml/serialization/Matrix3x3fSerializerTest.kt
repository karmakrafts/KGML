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

import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.matrix.MatrixProperties
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix3x3fSerializerTest {
    private val value = Matrix3x3f(
        1.25F, -2.5F, 3.75F, -4.5F, 5.25F, -6.5F, 7.75F, -8.5F, 9.25F
    )

    @Test
    fun descriptorShouldDescribeFloatComponentsInRowMajorOrder() = assertDescriptor(
        Matrix3x3fSerializer, "kotlin.FloatArray", PrimitiveKind.FLOAT, 9
    )

    @Test
    fun serializeShouldWriteAllComponentsInRowMajorOrder() = assertSerializes(
        Matrix3x3fSerializer, value, 1.25F, -2.5F, 3.75F, -4.5F, 5.25F, -6.5F, 7.75F, -8.5F, 9.25F
    )

    @Test
    fun deserializeShouldReadComponentsInAnyOrder() = assertDeserializes(
        Matrix3x3fSerializer, value, 1.25F, -2.5F, 3.75F, -4.5F, 5.25F, -6.5F, 7.75F, -8.5F, 9.25F
    )

    @Test
    fun deserializeShouldDefaultMissingComponentsToZero() {
        assertEquals(Matrix3x3f(0F), decode(Matrix3x3fSerializer))
        assertEquals(
            Matrix3x3f(0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 9.25F), decode(Matrix3x3fSerializer, 8 to 9.25F)
        )
    }

    @Test
    fun deserializeShouldRejectUnexpectedIndices() = assertRejectsUnexpectedIndices(Matrix3x3fSerializer, "Matrix3x3f", 9)

    @Test
    fun componentsShouldRoundTrip() = assertRoundTrips(Matrix3x3fSerializer, value)

    @Test
    fun extremeComponentsShouldRoundTrip() = assertRoundTrips(
        Matrix3x3fSerializer, Matrix3x3f(
            Float.MAX_VALUE, -Float.MIN_VALUE, -Float.MAX_VALUE, 0F, 1F, -1F, Float.MIN_VALUE, 2.5F, -3.75F
        )
    )

    @Test
    fun matrixPropertiesShouldNotBeSerialized() {
        assertSerializes(Matrix3x3fSerializer, Matrix3x3f(), 1F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 1F)
        val decoded = decode(Matrix3x3fSerializer, 0 to 1F, 4 to 1F, 8 to 1F)
        assertEquals(MatrixProperties.NONE, decoded.properties)
    }
}