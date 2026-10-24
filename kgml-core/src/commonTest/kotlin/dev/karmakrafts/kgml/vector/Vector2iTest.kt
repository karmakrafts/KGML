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

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class Vector2iTest {
    @Test
    fun distanceSqShouldReturnSquaredDistanceToAnotherVector() {
        val vector = Vector2i(3, 4)
        assertEquals(8, vector distanceSq Vector2i(1, 2))
    }

    @Test
    fun distanceShouldReturnDistanceToAnotherVector() {
        val vector = Vector2i(3, 4)
        assertEquals(5, vector distance Vector2i(0, 0))
    }

    @Test
    fun constructorShouldInitializeComponentsToZero() {
        assertEquals(Vector2i(0, 0), Vector2i())
    }

    @Test
    fun constructorShouldInitializeAllComponentsToGivenValue() {
        assertEquals(Vector2i(2, 2), Vector2i(2))
    }

    @Test
    fun componentTypeShouldReturnInt() {
        assertEquals(Int::class, Vector2i.componentType)
    }

    @Test
    fun componentSizeShouldReturnSizeOfIntInBytes() {
        assertEquals(Int.SIZE_BYTES, Vector2i.componentSize)
    }

    @Test
    fun dimensionsShouldReturn2() {
        assertEquals(2, Vector2i.dimensions)
    }

    @Test
    fun componentsShouldReturnXAndY() {
        assertContentEquals(arrayOf(VectorComponent.X, VectorComponent.Y), Vector2i.components)
    }

    @Test
    fun zeroShouldReturnVectorWithAllZeros() {
        assertEquals(Vector2i.ZERO, Vector2i(0, 0))
    }

    @Test
    fun oneShouldReturnVectorWithAllOnes() {
        assertEquals(Vector2i.ONE, Vector2i(1, 1))
    }

    @Test
    fun fromArrayShouldCreateVectorFromArrayAtOffset() {
        assertEquals(Vector2i(2, 3), Vector2i.fromArray(intArrayOf(0, 2, 3), 1))
    }

    @Test
    fun typeShouldReturnVector2i() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i, vector.type)
    }

    @Test
    fun plusOperatorWithScalarShouldAddValueToAllComponents() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(5, 6), vector + 2)
    }

    @Test
    fun plusOperatorWithVectorShouldAddComponents() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(8, 10), vector + other)
    }

    @Test
    fun minusOperatorWithScalarShouldSubtractValueFromAllComponents() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(1, 2), vector - 2)
    }

    @Test
    fun minusOperatorWithVectorShouldSubtractComponents() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(-2, -2), vector - other)
    }

    @Test
    fun timesOperatorWithScalarShouldMultiplyAllComponents() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(6, 8), vector * 2)
    }

    @Test
    fun timesOperatorWithVectorShouldMultiplyComponents() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(15, 24), vector * other)
    }

    @Test
    fun divOperatorWithScalarShouldDivideAllComponents() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(1, 2), vector / 2)
    }

    @Test
    fun divOperatorWithVectorShouldDivideComponents() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(0, 0), vector / other)
    }

    @Test
    fun remOperatorWithScalarShouldCalculateRemainderOfAllComponents() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(1, 0), vector % 2)
    }

    @Test
    fun remOperatorWithVectorShouldCalculateRemainderOfComponents() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(3, 4), vector % other)
    }

    @Test
    fun fmaShouldCalculateFusedMultiplyAdd() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(17, 26), vector.fma(other, Vector2i(2, 2)))
    }

    @Test
    fun lerpShouldLinearlyInterpolateBetweenVectors() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(4, 5), vector.lerp(other, 0.5F))
    }

    @Test
    fun minShouldReturnTheSmallerVector() {
        val v1 = Vector2i(1, 2)
        val v2 = Vector2i(3, 4)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun minComponentsShouldReturnTheComponentWiseMinimum() {
        val v1 = Vector2i(1, 4)
        val v2 = Vector2i(3, 2)
        assertEquals(Vector2i(1, 2), v1 minComponents v2)
    }

    @Test
    fun maxShouldReturnTheLargerVector() {
        val v1 = Vector2i(1, 2)
        val v2 = Vector2i(3, 4)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun maxComponentsShouldReturnTheComponentWiseMaximum() {
        val v1 = Vector2i(1, 4)
        val v2 = Vector2i(3, 2)
        assertEquals(Vector2i(3, 4), v1 maxComponents v2)
    }

    @Test
    fun lengthSqShouldReturnSquaredLengthOfVector() {
        val vector = Vector2i(3, 4)
        assertEquals(25, vector.lengthSq())
    }

    @Test
    fun lengthShouldReturnLengthOfVector() {
        val vector = Vector2i(3, 4)
        assertEquals(5, vector.length())
    }

    @Test
    fun normalizedShouldReturnNormalizedVector() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(0, 0), vector.normalized())
    }

    @Test
    fun dotShouldReturnDotProduct() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(39, vector dot other)
    }

    @Test
    fun crossShouldReturnCrossProduct() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(-2, vector cross other)
    }

    @Test
    fun angleRadShouldReturnAngleInRadians() {
        val v1 = Vector2i(1, 0)
        val v2 = Vector2i(0, 1)
        assertEquals(1, v1 angleRad v2)
    }

    @Test
    fun angleShouldReturnAngleInDegrees() {
        val v1 = Vector2i(1, 0)
        val v2 = Vector2i(0, 1)
        assertEquals(57, v1 angle v2)
    }

    @Test
    fun toVector2fShouldConvertToVector2f() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2f(3F, 4F), vector.toVector2f())
    }

    @Test
    fun getShouldReturnComponentByIndex() {
        val vector = Vector2i(3, 4)
        assertEquals(3, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[2] }
    }

    @Test
    fun getShouldReturnComponentByVectorComponent() {
        val vector = Vector2i(3, 4)
        assertEquals(4, vector[VectorComponent.Y])
        assertFailsWith<IllegalArgumentException> { vector[VectorComponent.Z] }
    }

    @Test
    fun toIntArrayShouldReturnComponentsAsIntArray() {
        val vector = Vector2i(3, 4)
        assertContentEquals(intArrayOf(3, 4), vector.toIntArray())
    }

    @Test
    fun swizzleShouldReturnSwizzledVector() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(4, 3), vector.swizzle(VectorComponent.Y, VectorComponent.X))
    }

    @Test
    fun compareToShouldReturnMagnitudeComparison() {
        val v1 = Vector2i(1, 2)
        val v2 = Vector2i(1, 2)
        val v3 = Vector2i(2, 1)
        val v4 = Vector2i(1, 3)

        assertEquals(0, v1.compareTo(v2))
        assertEquals(0, v1.compareTo(v3))
        assertTrue(v1 < v4)
        assertTrue(v4 > v1)
    }
}
