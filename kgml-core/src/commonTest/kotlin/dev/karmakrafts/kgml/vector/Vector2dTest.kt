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

import dev.karmakrafts.kgml.matrix.Matrix2x2d
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class Vector2dTest {
    @Test
    fun constructorShouldInitializeComponentsToZero() {
        assertEquals(Vector2d(0.0, 0.0), Vector2d())
    }

    @Test
    fun constructorShouldInitializeAllComponentsToGivenValue() {
        assertEquals(Vector2d(2.0, 2.0), Vector2d(2.0))
    }

    @Test
    fun componentTypeShouldReturnDouble() {
        assertEquals(Double::class, Vector2d.componentType)
    }

    @Test
    fun componentSizeShouldReturnSizeOfDoubleInBytes() {
        assertEquals(Double.SIZE_BYTES, Vector2d.componentSize)
    }

    @Test
    fun dimensionsShouldReturn2() {
        assertEquals(2, Vector2d.dimensions)
    }

    @Test
    fun componentsShouldReturnXAndY() {
        assertContentEquals(arrayOf(VectorComponent.X, VectorComponent.Y), Vector2d.components)
    }

    @Test
    fun zeroShouldReturnVectorWithAllZeros() {
        assertEquals(Vector2d.ZERO, Vector2d(0.0, 0.0))
    }

    @Test
    fun oneShouldReturnVectorWithAllOnes() {
        assertEquals(Vector2d.ONE, Vector2d(1.0, 1.0))
    }

    @Test
    fun fromArrayShouldCreateVectorFromArrayAtOffset() {
        assertEquals(Vector2d(2.0, 3.0), Vector2d.fromArray(doubleArrayOf(0.0, 2.0, 3.0), 1))
    }

    @Test
    fun typeShouldReturnVector2d() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d, vector.type)
    }

    @Test
    fun plusOperatorWithScalarShouldAddValueToAllComponents() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(5.0, 6.0), vector + 2.0)
    }

    @Test
    fun plusOperatorWithVectorShouldAddComponents() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(8.0, 10.0), vector + other)
    }

    @Test
    fun minusOperatorWithScalarShouldSubtractValueFromAllComponents() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(1.0, 2.0), vector - 2.0)
    }

    @Test
    fun minusOperatorWithVectorShouldSubtractComponents() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(-2.0, -2.0), vector - other)
    }

    @Test
    fun timesOperatorWithScalarShouldMultiplyAllComponents() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(6.0, 8.0), vector * 2.0)
    }

    @Test
    fun timesOperatorWithVectorShouldMultiplyComponents() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(15.0, 24.0), vector * other)
    }

    @Test
    fun divOperatorWithScalarShouldDivideAllComponents() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(1.5, 2.0), vector / 2.0)
    }

    @Test
    fun divOperatorWithVectorShouldDivideComponents() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(0.6, 2.0 / 3.0), vector / other)
    }

    @Test
    fun remOperatorWithScalarShouldCalculateRemainderOfAllComponents() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(1.0, 0.0), vector % 2.0)
    }

    @Test
    fun remOperatorWithVectorShouldCalculateRemainderOfComponents() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(3.0, 4.0), vector % other)
    }

    @Test
    fun fmaShouldCalculateFusedMultiplyAdd() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(17.0, 26.0), vector.fma(other, Vector2d(2.0, 2.0)))
    }

    @Test
    fun lerpShouldLinearlyInterpolateBetweenVectors() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(4.0, 5.0), vector.lerp(other, 0.5))
    }

    @Test
    fun minShouldReturnTheSmallerVector() {
        val v1 = Vector2d(1.0, 2.0)
        val v2 = Vector2d(3.0, 4.0)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun minComponentsShouldReturnTheComponentWiseMinimum() {
        val v1 = Vector2d(1.0, 4.0)
        val v2 = Vector2d(3.0, 2.0)
        assertEquals(Vector2d(1.0, 2.0), v1 minComponents v2)
    }

    @Test
    fun maxShouldReturnTheLargerVector() {
        val v1 = Vector2d(1.0, 2.0)
        val v2 = Vector2d(3.0, 4.0)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun maxComponentsShouldReturnTheComponentWiseMaximum() {
        val v1 = Vector2d(1.0, 4.0)
        val v2 = Vector2d(3.0, 2.0)
        assertEquals(Vector2d(3.0, 4.0), v1 maxComponents v2)
    }

    @Test
    fun lengthSqShouldReturnSquaredLengthOfVector() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(25.0, vector.lengthSq())
    }

    @Test
    fun lengthShouldReturnLengthOfVector() {
        val vector = Vector2d(3.0, 4.0)
        assertFloatEquals(5.0, vector.length())
    }

    @Test
    fun normalizedShouldReturnNormalizedVector() {
        val vector = Vector2d(3.0, 4.0)
        assertVectorEquals(Vector2d(0.6, 0.8), vector.normalized())
    }

    @Test
    fun distanceSqShouldReturnSquaredDistanceToAnotherVector() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(8.0, vector distanceSq Vector2d(1.0, 2.0))
    }

    @Test
    fun distanceShouldReturnDistanceToAnotherVector() {
        val vector = Vector2d(3.0, 4.0)
        assertFloatEquals(5.0, vector distance Vector2d(0.0, 0.0))
    }

    @Test
    fun dotShouldReturnDotProduct() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(39.0, vector dot other)
    }

    @Test
    fun crossShouldReturnCrossProduct() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(-2.0, vector cross other)
    }

    @Test
    fun angleRadShouldReturnAngleInRadians() {
        val v1 = Vector2d(1.0, 0.0)
        val v2 = Vector2d(0.0, 1.0)
        assertFloatEquals(1.5707964, v1 angleRad v2)
    }

    @Test
    fun angleShouldReturnAngleInDegrees() {
        val v1 = Vector2d(1.0, 0.0)
        val v2 = Vector2d(0.0, 1.0)
        assertFloatEquals(90.0, v1 angle v2)
    }

    @Test
    fun toVector2iShouldConvertToVector2i() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2i(3, 4), vector.toVector2i())
    }

    @Test
    fun timesOperatorWithMatrixShouldMultiplyVectorByMatrix() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(11.0, 25.0), vector * Matrix2x2d(1.0, 2.0, 3.0, 4.0))
    }

    @Test
    fun getShouldReturnComponentByIndex() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(3.0, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[2] }
    }

    @Test
    fun getShouldReturnComponentByVectorComponent() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(4.0, vector[VectorComponent.Y])
        assertFailsWith<IllegalArgumentException> { vector[VectorComponent.Z] }
    }

    @Test
    fun toDoubleArrayShouldReturnComponentsAsFloatArray() {
        val vector = Vector2d(3.0, 4.0)
        assertContentEquals(doubleArrayOf(3.0, 4.0), vector.toDoubleArray())
    }

    @Test
    fun swizzleShouldReturnSwizzledVector() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(4.0, 3.0), vector.swizzle(VectorComponent.Y, VectorComponent.X))
    }

    @Test
    fun compareToShouldReturnMagnitudeComparison() {
        val v1 = Vector2d(1.0, 2.0)
        val v2 = Vector2d(1.0, 2.0)
        val v3 = Vector2d(2.0, 1.0)
        val v4 = Vector2d(1.0, 3.0)

        assertEquals(0, v1.compareTo(v2))
        assertEquals(0, v1.compareTo(v3))
        assertTrue(v1 < v4)
        assertTrue(v4 > v1)
    }

    private fun assertVectorEquals(expected: Vector2d, actual: Vector2d) {
        assertFloatEquals(expected.x, actual.x)
        assertFloatEquals(expected.y, actual.y)
    }

    private fun assertFloatEquals(expected: Double, actual: Double) {
        assertEquals(expected, actual, 1E-6)
    }
}
