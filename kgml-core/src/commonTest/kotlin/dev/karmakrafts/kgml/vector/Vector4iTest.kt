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

class Vector4iTest {
    @Test
    fun distanceSqShouldReturnSquaredDistanceToAnotherVector() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(94, vector distanceSq Vector4i(1, 1, 1, 1))
    }

    @Test
    fun distanceShouldReturnDistanceToAnotherVector() {
        assertEquals(13, Vector4i(3, 4, 12, 0) distance Vector4i.ZERO)
    }

    @Test
    fun constructorShouldInitializeComponentsToZero() {
        assertEquals(Vector4i(0, 0, 0, 0), Vector4i())
    }

    @Test
    fun constructorShouldInitializeAllComponentsToGivenValue() {
        assertEquals(Vector4i(2, 2, 2, 2), Vector4i(2))
    }

    @Test
    fun componentTypeShouldReturnInt() {
        assertEquals(Int::class, Vector4i.componentType)
    }

    @Test
    fun componentSizeShouldReturnSizeOfIntInBytes() {
        assertEquals(Int.SIZE_BYTES, Vector4i.componentSize)
    }

    @Test
    fun dimensionsShouldReturn4() {
        assertEquals(4, Vector4i.dimensions)
    }

    @Test
    fun componentsShouldReturnXYZAndW() {
        assertContentEquals(VectorComponent.entries.toTypedArray(), Vector4i.components)
    }

    @Test
    fun zeroShouldReturnVectorWithAllZeros() {
        assertEquals(Vector4i.ZERO, Vector4i(0, 0, 0, 0))
    }

    @Test
    fun oneShouldReturnVectorWithAllOnes() {
        assertEquals(Vector4i.ONE, Vector4i(1, 1, 1, 1))
    }

    @Test
    fun fromArrayShouldCreateVectorFromArrayAtOffset() {
        assertEquals(Vector4i(2, 3, 4, 5), Vector4i.fromArray(intArrayOf(0, 2, 3, 4, 5), 1))
    }

    @Test
    fun typeShouldReturnVector4i() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i, vector.type)
    }

    @Test
    fun plusOperatorWithScalarShouldAddValueToAllComponents() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(4, 5, 8, 11), vector + 2)
    }

    @Test
    fun plusOperatorWithVectorShouldAddComponents() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(7, 10, 17, 22), vector + other)
    }

    @Test
    fun minusOperatorWithScalarShouldSubtractValueFromAllComponents() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(0, 1, 4, 7), vector - 2)
    }

    @Test
    fun minusOperatorWithVectorShouldSubtractComponents() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(-3, -4, -5, -4), vector - other)
    }

    @Test
    fun timesOperatorWithScalarShouldMultiplyAllComponents() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(4, 6, 12, 18), vector * 2)
    }

    @Test
    fun timesOperatorWithVectorShouldMultiplyComponents() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(10, 21, 66, 117), vector * other)
    }

    @Test
    fun divOperatorWithScalarShouldDivideAllComponents() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(1, 1, 3, 4), vector / 2)
    }

    @Test
    fun divOperatorWithVectorShouldDivideComponents() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(0, 0, 0, 0), vector / other)
    }

    @Test
    fun remOperatorWithScalarShouldCalculateRemainderOfAllComponents() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(0, 1, 0, 1), vector % 2)
    }

    @Test
    fun remOperatorWithVectorShouldCalculateRemainderOfComponents() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(2, 3, 6, 9), vector % other)
    }

    @Test
    fun fmaShouldCalculateFusedMultiplyAdd() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(12, 23, 68, 119), vector.fma(other, Vector4i(2, 2, 2, 2)))
    }

    @Test
    fun lerpShouldLinearlyInterpolateBetweenVectors() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(3, 5, 8, 11), vector.lerp(other, 0.5F))
    }

    @Test
    fun minShouldReturnTheSmallerVector() {
        val v1 = Vector4i(1, 2, 3, 4)
        val v2 = Vector4i(5, 6, 7, 8)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun minComponentsShouldReturnTheComponentWiseMinimum() {
        val v1 = Vector4i(1, 7, 3, 9)
        val v2 = Vector4i(5, 2, 8, 4)
        assertEquals(Vector4i(1, 2, 3, 4), v1 minComponents v2)
    }

    @Test
    fun maxShouldReturnTheLargerVector() {
        val v1 = Vector4i(1, 2, 3, 4)
        val v2 = Vector4i(5, 6, 7, 8)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun maxComponentsShouldReturnTheComponentWiseMaximum() {
        val v1 = Vector4i(1, 7, 3, 9)
        val v2 = Vector4i(5, 2, 8, 4)
        assertEquals(Vector4i(5, 7, 8, 9), v1 maxComponents v2)
    }

    @Test
    fun lengthSqShouldReturnSquaredLengthOfVector() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(130, vector.lengthSq())
    }

    @Test
    fun lengthShouldReturnLengthOfVector() {
        assertEquals(13, Vector4i(3, 4, 12, 0).length())
    }

    @Test
    fun normalizedShouldReturnNormalizedVector() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(0, 0, 0, 0), vector.normalized())
    }

    @Test
    fun dotShouldReturnDotProduct() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(214, vector dot other)
    }

    @Test
    fun toVector4fShouldConvertToVector4f() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4f(2F, 3F, 6F, 9F), vector.toVector4f())
    }

    @Test
    fun getShouldReturnComponentByIndex() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(2, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[4] }
    }

    @Test
    fun getShouldReturnComponentByVectorComponent() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(9, vector[VectorComponent.W])
    }

    @Test
    fun toIntArrayShouldReturnComponentsAsIntArray() {
        val vector = Vector4i(2, 3, 6, 9)
        assertContentEquals(intArrayOf(2, 3, 6, 9), vector.toIntArray())
    }

    @Test
    fun swizzleShouldReturnSwizzledVector() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(
            Vector4i(9, 2, 3, 6),
            vector.swizzle(VectorComponent.W, VectorComponent.X, VectorComponent.Y, VectorComponent.Z)
        )
    }

    @Test
    fun swizzle3ShouldReturnSwizzledVector() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector3i(9, 2, 3), vector.swizzle3(VectorComponent.W, VectorComponent.X, VectorComponent.Y))
    }

    @Test
    fun swizzle2ShouldReturnSwizzledVector() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector2i(9, 2), vector.swizzle2(VectorComponent.W, VectorComponent.X))
    }

    @Test
    fun compareToShouldReturnMagnitudeComparison() {
        val v1 = Vector4i(1, 2, 3, 4)
        val v2 = Vector4i(1, 2, 3, 4)
        val v3 = Vector4i(2, 1, 1, 1)
        val v4 = Vector4i(1, 3, 2, 2)
        val v5 = Vector4i(1, 2, 4, 3)
        val v6 = Vector4i(1, 2, 3, 5)

        assertEquals(0, v1.compareTo(v2))
        assertTrue(v1 > v3)
        assertTrue(v3 < v1)
        assertTrue(v1 > v4)
        assertTrue(v4 < v1)
        assertEquals(0, v1.compareTo(v5))
        assertTrue(v1 < v6)
        assertTrue(v6 > v1)
    }
}
