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

import dev.karmakrafts.kgml.matrix.Matrix3x3d
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class Vector3dTest {
    @Test
    fun constructorShouldInitializeComponentsToZero() {
        assertEquals(Vector3d(0.0, 0.0, 0.0), Vector3d())
    }

    @Test
    fun constructorShouldInitializeAllComponentsToGivenValue() {
        assertEquals(Vector3d(2.0, 2.0, 2.0), Vector3d(2.0))
    }

    @Test
    fun componentTypeShouldReturnDouble() {
        assertEquals(Double::class, Vector3d.componentType)
    }

    @Test
    fun componentSizeShouldReturnSizeOfDoubleInBytes() {
        assertEquals(Double.SIZE_BYTES, Vector3d.componentSize)
    }

    @Test
    fun dimensionsShouldReturn3() {
        assertEquals(3, Vector3d.dimensions)
    }

    @Test
    fun componentsShouldReturnXYAndZ() {
        assertContentEquals(arrayOf(VectorComponent.X, VectorComponent.Y, VectorComponent.Z), Vector3d.components)
    }

    @Test
    fun zeroShouldReturnVectorWithAllZeros() {
        assertEquals(Vector3d.ZERO, Vector3d(0.0, 0.0, 0.0))
    }

    @Test
    fun oneShouldReturnVectorWithAllOnes() {
        assertEquals(Vector3d.ONE, Vector3d(1.0, 1.0, 1.0))
    }

    @Test
    fun fromArrayShouldCreateVectorFromArrayAtOffset() {
        assertEquals(Vector3d(2.0, 3.0, 4.0), Vector3d.fromArray(doubleArrayOf(0.0, 2.0, 3.0, 4.0), 1))
    }

    @Test
    fun typeShouldReturnVector3d() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(Vector3d, vector.type)
    }

    @Test
    fun plusOperatorWithScalarShouldAddValueToAllComponents() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(Vector3d(4.0, 5.0, 8.0), vector + 2.0)
    }

    @Test
    fun plusOperatorWithVectorShouldAddComponents() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        val other = Vector3d(5.0, 7.0, 11.0)
        assertEquals(Vector3d(7.0, 10.0, 17.0), vector + other)
    }

    @Test
    fun minusOperatorWithScalarShouldSubtractValueFromAllComponents() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(Vector3d(0.0, 1.0, 4.0), vector - 2.0)
    }

    @Test
    fun minusOperatorWithVectorShouldSubtractComponents() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        val other = Vector3d(5.0, 7.0, 11.0)
        assertEquals(Vector3d(-3.0, -4.0, -5.0), vector - other)
    }

    @Test
    fun timesOperatorWithScalarShouldMultiplyAllComponents() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(Vector3d(4.0, 6.0, 12.0), vector * 2.0)
    }

    @Test
    fun timesOperatorWithVectorShouldMultiplyComponents() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        val other = Vector3d(5.0, 7.0, 11.0)
        assertEquals(Vector3d(10.0, 21.0, 66.0), vector * other)
    }

    @Test
    fun divOperatorWithScalarShouldDivideAllComponents() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(Vector3d(1.0, 1.5, 3.0), vector / 2.0)
    }

    @Test
    fun divOperatorWithVectorShouldDivideComponents() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        val other = Vector3d(5.0, 7.0, 11.0)
        assertEquals(Vector3d(0.4, 3.0 / 7.0, 6.0 / 11.0), vector / other)
    }

    @Test
    fun remOperatorWithScalarShouldCalculateRemainderOfAllComponents() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(Vector3d(0.0, 1.0, 0.0), vector % 2.0)
    }

    @Test
    fun remOperatorWithVectorShouldCalculateRemainderOfComponents() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        val other = Vector3d(5.0, 7.0, 11.0)
        assertEquals(Vector3d(2.0, 3.0, 6.0), vector % other)
    }

    @Test
    fun fmaShouldCalculateFusedMultiplyAdd() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        val other = Vector3d(5.0, 7.0, 11.0)
        assertEquals(Vector3d(12.0, 23.0, 68.0), vector.fma(other, Vector3d(2.0, 2.0, 2.0)))
    }

    @Test
    fun lerpShouldLinearlyInterpolateBetweenVectors() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        val other = Vector3d(5.0, 7.0, 11.0)
        assertEquals(Vector3d(3.5, 5.0, 8.5), vector.lerp(other, 0.5))
    }

    @Test
    fun minShouldReturnTheSmallerVector() {
        val v1 = Vector3d(1.0, 2.0, 3.0)
        val v2 = Vector3d(4.0, 5.0, 6.0)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun minComponentsShouldReturnTheComponentWiseMinimum() {
        val v1 = Vector3d(1.0, 5.0, 3.0)
        val v2 = Vector3d(4.0, 2.0, 6.0)
        assertEquals(Vector3d(1.0, 2.0, 3.0), v1 minComponents v2)
    }

    @Test
    fun maxShouldReturnTheLargerVector() {
        val v1 = Vector3d(1.0, 2.0, 3.0)
        val v2 = Vector3d(4.0, 5.0, 6.0)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun maxComponentsShouldReturnTheComponentWiseMaximum() {
        val v1 = Vector3d(1.0, 5.0, 3.0)
        val v2 = Vector3d(4.0, 2.0, 6.0)
        assertEquals(Vector3d(4.0, 5.0, 6.0), v1 maxComponents v2)
    }

    @Test
    fun lengthSqShouldReturnSquaredLengthOfVector() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(49.0, vector.lengthSq())
    }

    @Test
    fun lengthShouldReturnLengthOfVector() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertFloatEquals(7.0, vector.length())
    }

    @Test
    fun normalizedShouldReturnNormalizedVector() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertVectorEquals(Vector3d(2.0 / 7.0, 3.0 / 7.0, 6.0 / 7.0), vector.normalized())
    }

    @Test
    fun distanceSqShouldReturnSquaredDistanceToAnotherVector() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(30.0, vector distanceSq Vector3d(1.0, 1.0, 1.0))
    }

    @Test
    fun distanceShouldReturnDistanceToAnotherVector() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertFloatEquals(7.0, vector distance Vector3d(0.0, 0.0, 0.0))
    }

    @Test
    fun dotShouldReturnDotProduct() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        val other = Vector3d(5.0, 7.0, 11.0)
        assertEquals(97.0, vector dot other)
    }

    @Test
    fun crossShouldReturnCrossProduct() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        val other = Vector3d(5.0, 7.0, 11.0)
        assertEquals(Vector3d(-9.0, 8.0, -1.0), vector cross other)
    }

    @Test
    fun angleRadShouldReturnAngleInRadians() {
        val v1 = Vector3d(1.0, 0.0, 0.0)
        val v2 = Vector3d(0.0, 1.0, 0.0)
        assertFloatEquals(1.5707964, v1 angleRad v2)
    }

    @Test
    fun angleShouldReturnAngleInDegrees() {
        val v1 = Vector3d(1.0, 0.0, 0.0)
        val v2 = Vector3d(0.0, 1.0, 0.0)
        assertFloatEquals(90.0, v1 angle v2)
    }

    @Test
    fun signedAngleRadShouldReturnSignedAngleInRadians() {
        val v1 = Vector3d(1.0, 0.0, 0.0)
        val v2 = Vector3d(0.0, 1.0, 0.0)
        val axis = Vector3d(0.0, 0.0, 1.0)
        assertFloatEquals(1.5707964, v1.signedAngleRad(v2, axis))
        assertFloatEquals(-1.5707964, v1.signedAngleRad(v2, Vector3d(0.0, 0.0, -1.0)))
    }

    @Test
    fun signedAngleShouldReturnSignedAngleInDegrees() {
        val v1 = Vector3d(1.0, 0.0, 0.0)
        val v2 = Vector3d(0.0, 1.0, 0.0)
        val axis = Vector3d(0.0, 0.0, 1.0)
        assertFloatEquals(90.0, v1.signedAngle(v2, axis))
        assertFloatEquals(-90.0, v1.signedAngle(v2, Vector3d(0.0, 0.0, -1.0)))
    }

    @Test
    fun toVector3iShouldConvertToVector3i() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(Vector3i(2, 3, 6), vector.toVector3i())
    }

    @Test
    fun timesOperatorWithMatrixShouldMultiplyVectorByMatrix() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(
            Vector3d(20.0, 51.0, 113.0), vector * Matrix3x3d(
                1.0, 2.0, 2.0, 3.0, 5.0, 5.0, 7.0, 11.0, 11.0
            )
        )
    }

    @Test
    fun getShouldReturnComponentByIndex() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(2.0, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[3] }
    }

    @Test
    fun getShouldReturnComponentByVectorComponent() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(6.0, vector[VectorComponent.Z])
        assertFailsWith<IllegalArgumentException> { vector[VectorComponent.W] }
    }

    @Test
    fun toDoubleArrayShouldReturnComponentsAsFloatArray() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertContentEquals(doubleArrayOf(2.0, 3.0, 6.0), vector.toDoubleArray())
    }

    @Test
    fun swizzleShouldReturnSwizzledVector() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(Vector3d(6.0, 2.0, 3.0), vector.swizzle(VectorComponent.Z, VectorComponent.X, VectorComponent.Y))
    }

    @Test
    fun swizzle2ShouldReturnSwizzledVector() {
        val vector = Vector3d(2.0, 3.0, 6.0)
        assertEquals(Vector2d(6.0, 2.0), vector.swizzle2(VectorComponent.Z, VectorComponent.X))
    }

    @Test
    fun compareToShouldReturnMagnitudeComparison() {
        val v1 = Vector3d(1.0, 2.0, 3.0)
        val v2 = Vector3d(1.0, 2.0, 3.0)
        val v3 = Vector3d(2.0, 1.0, 1.0)
        val v4 = Vector3d(1.0, 3.0, 2.0)
        val v5 = Vector3d(1.0, 2.0, 4.0)

        assertEquals(0, v1.compareTo(v2))
        assertTrue(v1 > v3)
        assertTrue(v3 < v1)
        assertEquals(0, v1.compareTo(v4))
        assertTrue(v1 < v5)
        assertTrue(v5 > v1)
    }

    private fun assertVectorEquals(expected: Vector3d, actual: Vector3d) {
        assertFloatEquals(expected.x, actual.x)
        assertFloatEquals(expected.y, actual.y)
        assertFloatEquals(expected.z, actual.z)
    }

    private fun assertFloatEquals(expected: Double, actual: Double) {
        assertEquals(expected, actual, 1E-6)
    }
}
