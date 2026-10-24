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

import dev.karmakrafts.kgml.matrix.Matrix3x3f
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class Vector3fTest {
    @Test
    fun constructorShouldInitializeComponentsToZero() {
        assertEquals(Vector3f(0F, 0F, 0F), Vector3f())
    }

    @Test
    fun constructorShouldInitializeAllComponentsToGivenValue() {
        assertEquals(Vector3f(2F, 2F, 2F), Vector3f(2F))
    }

    @Test
    fun componentTypeShouldReturnFloat() {
        assertEquals(Float::class, Vector3f.componentType)
    }

    @Test
    fun componentSizeShouldReturnSizeOfFloatInBytes() {
        assertEquals(Float.SIZE_BYTES, Vector3f.componentSize)
    }

    @Test
    fun dimensionsShouldReturn3() {
        assertEquals(3, Vector3f.dimensions)
    }

    @Test
    fun componentsShouldReturnXYAndZ() {
        assertContentEquals(arrayOf(VectorComponent.X, VectorComponent.Y, VectorComponent.Z), Vector3f.components)
    }

    @Test
    fun zeroShouldReturnVectorWithAllZeros() {
        assertEquals(Vector3f.ZERO, Vector3f(0F, 0F, 0F))
    }

    @Test
    fun oneShouldReturnVectorWithAllOnes() {
        assertEquals(Vector3f.ONE, Vector3f(1F, 1F, 1F))
    }

    @Test
    fun fromArrayShouldCreateVectorFromArrayAtOffset() {
        assertEquals(Vector3f(2F, 3F, 4F), Vector3f.fromArray(floatArrayOf(0F, 2F, 3F, 4F), 1))
    }

    @Test
    fun typeShouldReturnVector3f() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f, vector.type)
    }

    @Test
    fun plusOperatorWithScalarShouldAddValueToAllComponents() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(4F, 5F, 8F), vector + 2F)
    }

    @Test
    fun plusOperatorWithVectorShouldAddComponents() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(7F, 10F, 17F), vector + other)
    }

    @Test
    fun minusOperatorWithScalarShouldSubtractValueFromAllComponents() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(0F, 1F, 4F), vector - 2F)
    }

    @Test
    fun minusOperatorWithVectorShouldSubtractComponents() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(-3F, -4F, -5F), vector - other)
    }

    @Test
    fun timesOperatorWithScalarShouldMultiplyAllComponents() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(4F, 6F, 12F), vector * 2F)
    }

    @Test
    fun timesOperatorWithVectorShouldMultiplyComponents() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(10F, 21F, 66F), vector * other)
    }

    @Test
    fun divOperatorWithScalarShouldDivideAllComponents() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(1F, 1.5F, 3F), vector / 2F)
    }

    @Test
    fun divOperatorWithVectorShouldDivideComponents() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(0.4F, 3F / 7F, 6F / 11F), vector / other)
    }

    @Test
    fun remOperatorWithScalarShouldCalculateRemainderOfAllComponents() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(0F, 1F, 0F), vector % 2F)
    }

    @Test
    fun remOperatorWithVectorShouldCalculateRemainderOfComponents() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(2F, 3F, 6F), vector % other)
    }

    @Test
    fun fmaShouldCalculateFusedMultiplyAdd() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(12F, 23F, 68F), vector.fma(other, Vector3f(2F, 2F, 2F)))
    }

    @Test
    fun lerpShouldLinearlyInterpolateBetweenVectors() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(3.5F, 5F, 8.5F), vector.lerp(other, 0.5F))
    }

    @Test
    fun minShouldReturnTheSmallerVector() {
        val v1 = Vector3f(1F, 2F, 3F)
        val v2 = Vector3f(4F, 5F, 6F)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun minComponentsShouldReturnTheComponentWiseMinimum() {
        val v1 = Vector3f(1F, 5F, 3F)
        val v2 = Vector3f(4F, 2F, 6F)
        assertEquals(Vector3f(1F, 2F, 3F), v1 minComponents v2)
    }

    @Test
    fun maxShouldReturnTheLargerVector() {
        val v1 = Vector3f(1F, 2F, 3F)
        val v2 = Vector3f(4F, 5F, 6F)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun maxComponentsShouldReturnTheComponentWiseMaximum() {
        val v1 = Vector3f(1F, 5F, 3F)
        val v2 = Vector3f(4F, 2F, 6F)
        assertEquals(Vector3f(4F, 5F, 6F), v1 maxComponents v2)
    }

    @Test
    fun lengthSqShouldReturnSquaredLengthOfVector() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(49F, vector.lengthSq())
    }

    @Test
    fun lengthShouldReturnLengthOfVector() {
        val vector = Vector3f(2F, 3F, 6F)
        assertFloatEquals(7F, vector.length())
    }

    @Test
    fun normalizedShouldReturnNormalizedVector() {
        val vector = Vector3f(2F, 3F, 6F)
        assertVectorEquals(Vector3f(2F / 7F, 3F / 7F, 6F / 7F), vector.normalized())
    }

    @Test
    fun distanceSqShouldReturnSquaredDistanceToAnotherVector() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(30F, vector distanceSq Vector3f(1F, 1F, 1F))
    }

    @Test
    fun distanceShouldReturnDistanceToAnotherVector() {
        val vector = Vector3f(2F, 3F, 6F)
        assertFloatEquals(7F, vector distance Vector3f(0F, 0F, 0F))
    }

    @Test
    fun dotShouldReturnDotProduct() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(97F, vector dot other)
    }

    @Test
    fun crossShouldReturnCrossProduct() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(-9F, 8F, -1F), vector cross other)
    }

    @Test
    fun angleRadShouldReturnAngleInRadians() {
        val v1 = Vector3f(1F, 0F, 0F)
        val v2 = Vector3f(0F, 1F, 0F)
        assertFloatEquals(1.5707964F, v1 angleRad v2)
    }

    @Test
    fun angleShouldReturnAngleInDegrees() {
        val v1 = Vector3f(1F, 0F, 0F)
        val v2 = Vector3f(0F, 1F, 0F)
        assertFloatEquals(90F, v1 angle v2)
    }

    @Test
    fun signedAngleRadShouldReturnSignedAngleInRadians() {
        val v1 = Vector3f(1F, 0F, 0F)
        val v2 = Vector3f(0F, 1F, 0F)
        val axis = Vector3f(0F, 0F, 1F)
        assertFloatEquals(1.5707964F, v1.signedAngleRad(v2, axis))
        assertFloatEquals(-1.5707964F, v1.signedAngleRad(v2, Vector3f(0F, 0F, -1F)))
    }

    @Test
    fun signedAngleShouldReturnSignedAngleInDegrees() {
        val v1 = Vector3f(1F, 0F, 0F)
        val v2 = Vector3f(0F, 1F, 0F)
        val axis = Vector3f(0F, 0F, 1F)
        assertFloatEquals(90F, v1.signedAngle(v2, axis))
        assertFloatEquals(-90F, v1.signedAngle(v2, Vector3f(0F, 0F, -1F)))
    }

    @Test
    fun toVector3iShouldConvertToVector3i() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3i(2, 3, 6), vector.toVector3i())
    }

    @Test
    fun timesOperatorWithMatrixShouldMultiplyVectorByMatrix() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(
            Vector3f(20F, 51F, 113F), vector * Matrix3x3f(
                1F, 2F, 2F, 3F, 5F, 5F, 7F, 11F, 11F
            )
        )
    }

    @Test
    fun getShouldReturnComponentByIndex() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(2F, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[3] }
    }

    @Test
    fun getShouldReturnComponentByVectorComponent() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(6F, vector[VectorComponent.Z])
        assertFailsWith<IllegalArgumentException> { vector[VectorComponent.W] }
    }

    @Test
    fun toFloatArrayShouldReturnComponentsAsFloatArray() {
        val vector = Vector3f(2F, 3F, 6F)
        assertContentEquals(floatArrayOf(2F, 3F, 6F), vector.toFloatArray())
    }

    @Test
    fun swizzleShouldReturnSwizzledVector() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(6F, 2F, 3F), vector.swizzle(VectorComponent.Z, VectorComponent.X, VectorComponent.Y))
    }

    @Test
    fun swizzle2ShouldReturnSwizzledVector() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector2f(6F, 2F), vector.swizzle2(VectorComponent.Z, VectorComponent.X))
    }

    @Test
    fun compareToShouldReturnMagnitudeComparison() {
        val v1 = Vector3f(1F, 2F, 3F)
        val v2 = Vector3f(1F, 2F, 3F)
        val v3 = Vector3f(2F, 1F, 1F)
        val v4 = Vector3f(1F, 3F, 2F)
        val v5 = Vector3f(1F, 2F, 4F)

        assertEquals(0, v1.compareTo(v2))
        assertTrue(v1 > v3)
        assertTrue(v3 < v1)
        assertEquals(0, v1.compareTo(v4))
        assertTrue(v1 < v5)
        assertTrue(v5 > v1)
    }

    private fun assertVectorEquals(expected: Vector3f, actual: Vector3f) {
        assertFloatEquals(expected.x, actual.x)
        assertFloatEquals(expected.y, actual.y)
        assertFloatEquals(expected.z, actual.z)
    }

    private fun assertFloatEquals(expected: Float, actual: Float) {
        assertEquals(expected, actual, 1E-6F)
    }
}
