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

import dev.karmakrafts.kgml.matrix.Matrix2x2f
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class Vector2fTest {
    @Test
    fun constructorShouldInitializeComponentsToZero() {
        assertEquals(Vector2f(0F, 0F), Vector2f())
    }

    @Test
    fun constructorShouldInitializeAllComponentsToGivenValue() {
        assertEquals(Vector2f(2F, 2F), Vector2f(2F))
    }

    @Test
    fun componentTypeShouldReturnFloat() {
        assertEquals(Float::class, Vector2f.componentType)
    }

    @Test
    fun componentSizeShouldReturnSizeOfFloatInBytes() {
        assertEquals(Float.SIZE_BYTES, Vector2f.componentSize)
    }

    @Test
    fun dimensionsShouldReturn2() {
        assertEquals(2, Vector2f.dimensions)
    }

    @Test
    fun componentsShouldReturnXAndY() {
        assertContentEquals(arrayOf(VectorComponent.X, VectorComponent.Y), Vector2f.components)
    }

    @Test
    fun zeroShouldReturnVectorWithAllZeros() {
        assertEquals(Vector2f.ZERO, Vector2f(0F, 0F))
    }

    @Test
    fun oneShouldReturnVectorWithAllOnes() {
        assertEquals(Vector2f.ONE, Vector2f(1F, 1F))
    }

    @Test
    fun fromArrayShouldCreateVectorFromArrayAtOffset() {
        assertEquals(Vector2f(2F, 3F), Vector2f.fromArray(floatArrayOf(0F, 2F, 3F), 1))
    }

    @Test
    fun typeShouldReturnVector2f() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f, vector.type)
    }

    @Test
    fun plusOperatorWithScalarShouldAddValueToAllComponents() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(5F, 6F), vector + 2F)
    }

    @Test
    fun plusOperatorWithVectorShouldAddComponents() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(8F, 10F), vector + other)
    }

    @Test
    fun minusOperatorWithScalarShouldSubtractValueFromAllComponents() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(1F, 2F), vector - 2F)
    }

    @Test
    fun minusOperatorWithVectorShouldSubtractComponents() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(-2F, -2F), vector - other)
    }

    @Test
    fun timesOperatorWithScalarShouldMultiplyAllComponents() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(6F, 8F), vector * 2F)
    }

    @Test
    fun timesOperatorWithVectorShouldMultiplyComponents() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(15F, 24F), vector * other)
    }

    @Test
    fun divOperatorWithScalarShouldDivideAllComponents() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(1.5F, 2F), vector / 2F)
    }

    @Test
    fun divOperatorWithVectorShouldDivideComponents() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(0.6F, 2F / 3F), vector / other)
    }

    @Test
    fun remOperatorWithScalarShouldCalculateRemainderOfAllComponents() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(1F, 0F), vector % 2F)
    }

    @Test
    fun remOperatorWithVectorShouldCalculateRemainderOfComponents() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(3F, 4F), vector % other)
    }

    @Test
    fun fmaShouldCalculateFusedMultiplyAdd() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(17F, 26F), vector.fma(other, Vector2f(2F, 2F)))
    }

    @Test
    fun lerpShouldLinearlyInterpolateBetweenVectors() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(4F, 5F), vector.lerp(other, 0.5F))
    }

    @Test
    fun minShouldReturnTheSmallerVector() {
        val v1 = Vector2f(1F, 2F)
        val v2 = Vector2f(3F, 4F)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun minComponentsShouldReturnTheComponentWiseMinimum() {
        val v1 = Vector2f(1F, 4F)
        val v2 = Vector2f(3F, 2F)
        assertEquals(Vector2f(1F, 2F), v1 minComponents v2)
    }

    @Test
    fun maxShouldReturnTheLargerVector() {
        val v1 = Vector2f(1F, 2F)
        val v2 = Vector2f(3F, 4F)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun maxComponentsShouldReturnTheComponentWiseMaximum() {
        val v1 = Vector2f(1F, 4F)
        val v2 = Vector2f(3F, 2F)
        assertEquals(Vector2f(3F, 4F), v1 maxComponents v2)
    }

    @Test
    fun lengthSqShouldReturnSquaredLengthOfVector() {
        val vector = Vector2f(3F, 4F)
        assertEquals(25F, vector.lengthSq())
    }

    @Test
    fun lengthShouldReturnLengthOfVector() {
        val vector = Vector2f(3F, 4F)
        assertFloatEquals(5F, vector.length())
    }

    @Test
    fun normalizedShouldReturnNormalizedVector() {
        val vector = Vector2f(3F, 4F)
        assertVectorEquals(Vector2f(0.6F, 0.8F), vector.normalized())
    }

    @Test
    fun distanceSqShouldReturnSquaredDistanceToAnotherVector() {
        val vector = Vector2f(3F, 4F)
        assertEquals(8F, vector distanceSq Vector2f(1F, 2F))
    }

    @Test
    fun distanceShouldReturnDistanceToAnotherVector() {
        val vector = Vector2f(3F, 4F)
        assertFloatEquals(5F, vector distance Vector2f(0F, 0F))
    }

    @Test
    fun dotShouldReturnDotProduct() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(39F, vector dot other)
    }

    @Test
    fun crossShouldReturnCrossProduct() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(-2F, vector cross other)
    }

    @Test
    fun angleRadShouldReturnAngleInRadians() {
        val v1 = Vector2f(1F, 0F)
        val v2 = Vector2f(0F, 1F)
        assertFloatEquals(1.5707964F, v1 angleRad v2)
    }

    @Test
    fun angleShouldReturnAngleInDegrees() {
        val v1 = Vector2f(1F, 0F)
        val v2 = Vector2f(0F, 1F)
        assertFloatEquals(90F, v1 angle v2)
    }

    @Test
    fun toVector2iShouldConvertToVector2i() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2i(3, 4), vector.toVector2i())
    }

    @Test
    fun timesOperatorWithMatrixShouldMultiplyVectorByMatrix() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(11F, 25F), vector * Matrix2x2f(1F, 2F, 3F, 4F))
    }

    @Test
    fun getShouldReturnComponentByIndex() {
        val vector = Vector2f(3F, 4F)
        assertEquals(3F, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[2] }
    }

    @Test
    fun getShouldReturnComponentByVectorComponent() {
        val vector = Vector2f(3F, 4F)
        assertEquals(4F, vector[VectorComponent.Y])
        assertFailsWith<IllegalArgumentException> { vector[VectorComponent.Z] }
    }

    @Test
    fun toFloatArrayShouldReturnComponentsAsFloatArray() {
        val vector = Vector2f(3F, 4F)
        assertContentEquals(floatArrayOf(3F, 4F), vector.toFloatArray())
    }

    @Test
    fun swizzleShouldReturnSwizzledVector() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(4F, 3F), vector.swizzle(VectorComponent.Y, VectorComponent.X))
    }

    @Test
    fun compareToShouldReturnMagnitudeComparison() {
        val v1 = Vector2f(1F, 2F)
        val v2 = Vector2f(1F, 2F)
        val v3 = Vector2f(2F, 1F)
        val v4 = Vector2f(1F, 3F)

        assertEquals(0, v1.compareTo(v2))
        assertEquals(0, v1.compareTo(v3))
        assertTrue(v1 < v4)
        assertTrue(v4 > v1)
    }

    private fun assertVectorEquals(expected: Vector2f, actual: Vector2f) {
        assertFloatEquals(expected.x, actual.x)
        assertFloatEquals(expected.y, actual.y)
    }

    private fun assertFloatEquals(expected: Float, actual: Float) {
        assertEquals(expected, actual, 1E-6F)
    }
}
