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

class Vector3iTest {
    @Test
    fun distanceSqShouldReturnSquaredDistanceToAnotherVector() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(30, vector distanceSq Vector3i(1, 1, 1))
    }

    @Test
    fun distanceShouldReturnDistanceToAnotherVector() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(7, vector distance Vector3i(0, 0, 0))
    }

    @Test
    fun constructorShouldInitializeComponentsToZero() {
        assertEquals(Vector3i(0, 0, 0), Vector3i())
    }

    @Test
    fun constructorShouldInitializeAllComponentsToGivenValue() {
        assertEquals(Vector3i(2, 2, 2), Vector3i(2))
    }

    @Test
    fun componentTypeShouldReturnInt() {
        assertEquals(Int::class, Vector3i.componentType)
    }

    @Test
    fun componentSizeShouldReturnSizeOfIntInBytes() {
        assertEquals(Int.SIZE_BYTES, Vector3i.componentSize)
    }

    @Test
    fun dimensionsShouldReturn3() {
        assertEquals(3, Vector3i.dimensions)
    }

    @Test
    fun componentsShouldReturnXYAndZ() {
        assertContentEquals(arrayOf(VectorComponent.X, VectorComponent.Y, VectorComponent.Z), Vector3i.components)
    }

    @Test
    fun zeroShouldReturnVectorWithAllZeros() {
        assertEquals(Vector3i.ZERO, Vector3i(0, 0, 0))
    }

    @Test
    fun oneShouldReturnVectorWithAllOnes() {
        assertEquals(Vector3i.ONE, Vector3i(1, 1, 1))
    }

    @Test
    fun fromArrayShouldCreateVectorFromArrayAtOffset() {
        assertEquals(Vector3i(2, 3, 4), Vector3i.fromArray(intArrayOf(0, 2, 3, 4), 1))
    }

    @Test
    fun typeShouldReturnVector3i() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i, vector.type)
    }

    @Test
    fun plusOperatorWithScalarShouldAddValueToAllComponents() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(4, 5, 8), vector + 2)
    }

    @Test
    fun plusOperatorWithVectorShouldAddComponents() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(7, 10, 17), vector + other)
    }

    @Test
    fun minusOperatorWithScalarShouldSubtractValueFromAllComponents() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(0, 1, 4), vector - 2)
    }

    @Test
    fun minusOperatorWithVectorShouldSubtractComponents() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(-3, -4, -5), vector - other)
    }

    @Test
    fun timesOperatorWithScalarShouldMultiplyAllComponents() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(4, 6, 12), vector * 2)
    }

    @Test
    fun timesOperatorWithVectorShouldMultiplyComponents() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(10, 21, 66), vector * other)
    }

    @Test
    fun divOperatorWithScalarShouldDivideAllComponents() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(1, 1, 3), vector / 2)
    }

    @Test
    fun divOperatorWithVectorShouldDivideComponents() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(0, 0, 0), vector / other)
    }

    @Test
    fun remOperatorWithScalarShouldCalculateRemainderOfAllComponents() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(0, 1, 0), vector % 2)
    }

    @Test
    fun remOperatorWithVectorShouldCalculateRemainderOfComponents() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(2, 3, 6), vector % other)
    }

    @Test
    fun fmaShouldCalculateFusedMultiplyAdd() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(12, 23, 68), vector.fma(other, Vector3i(2, 2, 2)))
    }

    @Test
    fun lerpShouldLinearlyInterpolateBetweenVectors() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(3, 5, 8), vector.lerp(other, 0.5F))
    }

    @Test
    fun minShouldReturnTheSmallerVector() {
        val v1 = Vector3i(1, 2, 3)
        val v2 = Vector3i(4, 5, 6)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun minComponentsShouldReturnTheComponentWiseMinimum() {
        val v1 = Vector3i(1, 5, 3)
        val v2 = Vector3i(4, 2, 6)
        assertEquals(Vector3i(1, 2, 3), v1 minComponents v2)
    }

    @Test
    fun maxShouldReturnTheLargerVector() {
        val v1 = Vector3i(1, 2, 3)
        val v2 = Vector3i(4, 5, 6)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun maxComponentsShouldReturnTheComponentWiseMaximum() {
        val v1 = Vector3i(1, 5, 3)
        val v2 = Vector3i(4, 2, 6)
        assertEquals(Vector3i(4, 5, 6), v1 maxComponents v2)
    }

    @Test
    fun lengthSqShouldReturnSquaredLengthOfVector() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(49, vector.lengthSq())
    }

    @Test
    fun lengthShouldReturnLengthOfVector() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(7, vector.length())
    }

    @Test
    fun normalizedShouldReturnNormalizedVector() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(0, 0, 0), vector.normalized())
    }

    @Test
    fun dotShouldReturnDotProduct() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(97, vector dot other)
    }

    @Test
    fun crossShouldReturnCrossProduct() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(-9, 8, -1), vector cross other)
    }

    @Test
    fun angleRadShouldReturnAngleInRadians() {
        val v1 = Vector3i(1, 0, 0)
        val v2 = Vector3i(0, 1, 0)
        assertEquals(1, v1 angleRad v2)
    }

    @Test
    fun angleShouldReturnAngleInDegrees() {
        val v1 = Vector3i(1, 0, 0)
        val v2 = Vector3i(0, 1, 0)
        assertEquals(57, v1 angle v2)
    }

    @Test
    fun signedAngleRadShouldReturnSignedAngleInRadians() {
        val v1 = Vector3i(1, 0, 0)
        val v2 = Vector3i(0, 1, 0)
        val axis = Vector3i(0, 0, 1)
        assertEquals(1, v1.signedAngleRad(v2, axis))
        assertEquals(-1, v1.signedAngleRad(v2, Vector3i(0, 0, -1)))
    }

    @Test
    fun signedAngleShouldReturnSignedAngleInDegrees() {
        val v1 = Vector3i(1, 0, 0)
        val v2 = Vector3i(0, 1, 0)
        val axis = Vector3i(0, 0, 1)
        assertEquals(57, v1.signedAngle(v2, axis))
        assertEquals(-57, v1.signedAngle(v2, Vector3i(0, 0, -1)))
    }

    @Test
    fun toVector3fShouldConvertToVector3f() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3f(2F, 3F, 6F), vector.toVector3f())
    }

    @Test
    fun getShouldReturnComponentByIndex() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(2, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[3] }
    }

    @Test
    fun getShouldReturnComponentByVectorComponent() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(6, vector[VectorComponent.Z])
        assertFailsWith<IllegalArgumentException> { vector[VectorComponent.W] }
    }

    @Test
    fun toIntArrayShouldReturnComponentsAsIntArray() {
        val vector = Vector3i(2, 3, 6)
        assertContentEquals(intArrayOf(2, 3, 6), vector.toIntArray())
    }

    @Test
    fun swizzleShouldReturnSwizzledVector() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(6, 2, 3), vector.swizzle(VectorComponent.Z, VectorComponent.X, VectorComponent.Y))
    }

    @Test
    fun swizzle2ShouldReturnSwizzledVector() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector2i(6, 2), vector.swizzle2(VectorComponent.Z, VectorComponent.X))
    }

    @Test
    fun compareToShouldReturnMagnitudeComparison() {
        val v1 = Vector3i(1, 2, 3)
        val v2 = Vector3i(1, 2, 3)
        val v3 = Vector3i(2, 1, 1)
        val v4 = Vector3i(1, 3, 2)
        val v5 = Vector3i(1, 2, 4)

        assertEquals(0, v1.compareTo(v2))
        assertTrue(v1 > v3)
        assertTrue(v3 < v1)
        assertEquals(0, v1.compareTo(v4))
        assertTrue(v1 < v5)
        assertTrue(v5 > v1)
    }
}
