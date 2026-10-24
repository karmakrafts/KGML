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

package dev.karmakrafts.kgml.vector

import dev.karmakrafts.kgml.matrix.Matrix4x4d
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class Vector4dTest {
    @Test
    fun constructorShouldInitializeComponentsToZero() {
        assertEquals(Vector4d(0.0, 0.0, 0.0, 0.0), Vector4d())
    }

    @Test
    fun constructorShouldInitializeAllComponentsToGivenValue() {
        assertEquals(Vector4d(2.0, 2.0, 2.0, 2.0), Vector4d(2.0))
    }

    @Test
    fun componentTypeShouldReturnDouble() {
        assertEquals(Double::class, Vector4d.componentType)
    }

    @Test
    fun componentSizeShouldReturnSizeOfDoubleInBytes() {
        assertEquals(Double.SIZE_BYTES, Vector4d.componentSize)
    }

    @Test
    fun dimensionsShouldReturn4() {
        assertEquals(4, Vector4d.dimensions)
    }

    @Test
    fun componentsShouldReturnXYZAndW() {
        assertContentEquals(VectorComponent.entries.toTypedArray(), Vector4d.components)
    }

    @Test
    fun zeroShouldReturnVectorWithAllZeros() {
        assertEquals(Vector4d.ZERO, Vector4d(0.0, 0.0, 0.0, 0.0))
    }

    @Test
    fun oneShouldReturnVectorWithAllOnes() {
        assertEquals(Vector4d.ONE, Vector4d(1.0, 1.0, 1.0, 1.0))
    }

    @Test
    fun fromArrayShouldCreateVectorFromArrayAtOffset() {
        assertEquals(Vector4d(2.0, 3.0, 4.0, 5.0), Vector4d.fromArray(doubleArrayOf(0.0, 2.0, 3.0, 4.0, 5.0), 1))
    }

    @Test
    fun typeShouldReturnVector4d() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(Vector4d, vector.type)
    }

    @Test
    fun plusOperatorWithScalarShouldAddValueToAllComponents() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(Vector4d(4.0, 5.0, 8.0, 11.0), vector + 2.0)
    }

    @Test
    fun plusOperatorWithVectorShouldAddComponents() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        val other = Vector4d(5.0, 7.0, 11.0, 13.0)
        assertEquals(Vector4d(7.0, 10.0, 17.0, 22.0), vector + other)
    }

    @Test
    fun minusOperatorWithScalarShouldSubtractValueFromAllComponents() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(Vector4d(0.0, 1.0, 4.0, 7.0), vector - 2.0)
    }

    @Test
    fun minusOperatorWithVectorShouldSubtractComponents() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        val other = Vector4d(5.0, 7.0, 11.0, 13.0)
        assertEquals(Vector4d(-3.0, -4.0, -5.0, -4.0), vector - other)
    }

    @Test
    fun timesOperatorWithScalarShouldMultiplyAllComponents() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(Vector4d(4.0, 6.0, 12.0, 18.0), vector * 2.0)
    }

    @Test
    fun timesOperatorWithVectorShouldMultiplyComponents() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        val other = Vector4d(5.0, 7.0, 11.0, 13.0)
        assertEquals(Vector4d(10.0, 21.0, 66.0, 117.0), vector * other)
    }

    @Test
    fun divOperatorWithScalarShouldDivideAllComponents() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(Vector4d(1.0, 1.5, 3.0, 4.5), vector / 2.0)
    }

    @Test
    fun divOperatorWithVectorShouldDivideComponents() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        val other = Vector4d(5.0, 7.0, 11.0, 13.0)
        assertEquals(Vector4d(0.4, 3.0 / 7.0, 6.0 / 11.0, 9.0 / 13.0), vector / other)
    }

    @Test
    fun remOperatorWithScalarShouldCalculateRemainderOfAllComponents() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(Vector4d(0.0, 1.0, 0.0, 1.0), vector % 2.0)
    }

    @Test
    fun remOperatorWithVectorShouldCalculateRemainderOfComponents() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        val other = Vector4d(5.0, 7.0, 11.0, 13.0)
        assertEquals(Vector4d(2.0, 3.0, 6.0, 9.0), vector % other)
    }

    @Test
    fun fmaShouldCalculateFusedMultiplyAdd() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        val other = Vector4d(5.0, 7.0, 11.0, 13.0)
        assertEquals(Vector4d(12.0, 23.0, 68.0, 119.0), vector.fma(other, Vector4d(2.0, 2.0, 2.0, 2.0)))
    }

    @Test
    fun lerpShouldLinearlyInterpolateBetweenVectors() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        val other = Vector4d(5.0, 7.0, 11.0, 13.0)
        assertEquals(Vector4d(3.5, 5.0, 8.5, 11.0), vector.lerp(other, 0.5))
    }

    @Test
    fun minShouldReturnTheSmallerVector() {
        val v1 = Vector4d(1.0, 2.0, 3.0, 4.0)
        val v2 = Vector4d(5.0, 6.0, 7.0, 8.0)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun minComponentsShouldReturnTheComponentWiseMinimum() {
        val v1 = Vector4d(1.0, 7.0, 3.0, 9.0)
        val v2 = Vector4d(5.0, 2.0, 8.0, 4.0)
        assertEquals(Vector4d(1.0, 2.0, 3.0, 4.0), v1 minComponents v2)
    }

    @Test
    fun maxShouldReturnTheLargerVector() {
        val v1 = Vector4d(1.0, 2.0, 3.0, 4.0)
        val v2 = Vector4d(5.0, 6.0, 7.0, 8.0)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun maxComponentsShouldReturnTheComponentWiseMaximum() {
        val v1 = Vector4d(1.0, 7.0, 3.0, 9.0)
        val v2 = Vector4d(5.0, 2.0, 8.0, 4.0)
        assertEquals(Vector4d(5.0, 7.0, 8.0, 9.0), v1 maxComponents v2)
    }

    @Test
    fun lengthSqShouldReturnSquaredLengthOfVector() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(130.0, vector.lengthSq())
    }

    @Test
    fun lengthShouldReturnLengthOfVector() {
        assertFloatEquals(13.0, Vector4d(3.0, 4.0, 12.0, 0.0).length())
    }

    @Test
    fun normalizedShouldReturnNormalizedVector() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        val length = sqrt(130.0)
        assertVectorEquals(Vector4d(2.0 / length, 3.0 / length, 6.0 / length, 9.0 / length), vector.normalized())
    }

    @Test
    fun distanceSqShouldReturnSquaredDistanceToAnotherVector() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(94.0, vector distanceSq Vector4d(1.0, 1.0, 1.0, 1.0))
    }

    @Test
    fun distanceShouldReturnDistanceToAnotherVector() {
        assertFloatEquals(13.0, Vector4d(3.0, 4.0, 12.0, 0.0) distance Vector4d.ZERO)
    }

    @Test
    fun dotShouldReturnDotProduct() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        val other = Vector4d(5.0, 7.0, 11.0, 13.0)
        assertEquals(214.0, vector dot other)
    }

    @Test
    fun toVector4iShouldConvertToVector4i() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(Vector4i(2, 3, 6, 9), vector.toVector4i())
    }

    @Test
    fun timesOperatorWithMatrixShouldMultiplyVectorByMatrix() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(
            Vector4d(38.0, 96.0, 212.0, 332.0), vector * Matrix4x4d(
                1.0, 2.0, 2.0, 2.0, 3.0, 5.0, 5.0, 5.0, 7.0, 11.0, 11.0, 11.0, 13.0, 17.0, 17.0, 17.0
            )
        )
    }

    @Test
    fun getShouldReturnComponentByIndex() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(2.0, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[4] }
    }

    @Test
    fun getShouldReturnComponentByVectorComponent() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(9.0, vector[VectorComponent.W])
    }

    @Test
    fun toDoubleArrayShouldReturnComponentsAsFloatArray() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertContentEquals(doubleArrayOf(2.0, 3.0, 6.0, 9.0), vector.toDoubleArray())
    }

    @Test
    fun swizzleShouldReturnSwizzledVector() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(
            Vector4d(9.0, 2.0, 3.0, 6.0),
            vector.swizzle(VectorComponent.W, VectorComponent.X, VectorComponent.Y, VectorComponent.Z)
        )
    }

    @Test
    fun swizzle3ShouldReturnSwizzledVector() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(Vector3d(9.0, 2.0, 3.0), vector.swizzle3(VectorComponent.W, VectorComponent.X, VectorComponent.Y))
    }

    @Test
    fun swizzle2ShouldReturnSwizzledVector() {
        val vector = Vector4d(2.0, 3.0, 6.0, 9.0)
        assertEquals(Vector2d(9.0, 2.0), vector.swizzle2(VectorComponent.W, VectorComponent.X))
    }

    @Test
    fun compareToShouldReturnMagnitudeComparison() {
        val v1 = Vector4d(1.0, 2.0, 3.0, 4.0)
        val v2 = Vector4d(1.0, 2.0, 3.0, 4.0)
        val v3 = Vector4d(2.0, 1.0, 1.0, 1.0)
        val v4 = Vector4d(1.0, 3.0, 2.0, 2.0)
        val v5 = Vector4d(1.0, 2.0, 4.0, 3.0)
        val v6 = Vector4d(1.0, 2.0, 3.0, 5.0)

        assertEquals(0, v1.compareTo(v2))
        assertTrue(v1 > v3)
        assertTrue(v3 < v1)
        assertTrue(v1 > v4)
        assertTrue(v4 < v1)
        assertEquals(0, v1.compareTo(v5))
        assertTrue(v1 < v6)
        assertTrue(v6 > v1)
    }

    private fun assertVectorEquals(expected: Vector4d, actual: Vector4d) {
        assertFloatEquals(expected.x, actual.x)
        assertFloatEquals(expected.y, actual.y)
        assertFloatEquals(expected.z, actual.z)
        assertFloatEquals(expected.w, actual.w)
    }

    private fun assertFloatEquals(expected: Double, actual: Double) {
        assertEquals(expected, actual, 1E-6)
    }
}
