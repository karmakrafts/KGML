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

import dev.karmakrafts.kgml.matrix.Matrix4x4f
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class Vector4fTest {
    @Test
    fun constructorShouldInitializeComponentsToZero() {
        assertEquals(Vector4f(0F, 0F, 0F, 0F), Vector4f())
    }

    @Test
    fun constructorShouldInitializeAllComponentsToGivenValue() {
        assertEquals(Vector4f(2F, 2F, 2F, 2F), Vector4f(2F))
    }

    @Test
    fun componentTypeShouldReturnFloat() {
        assertEquals(Float::class, Vector4f.componentType)
    }

    @Test
    fun componentSizeShouldReturnSizeOfFloatInBytes() {
        assertEquals(Float.SIZE_BYTES, Vector4f.componentSize)
    }

    @Test
    fun dimensionsShouldReturn4() {
        assertEquals(4, Vector4f.dimensions)
    }

    @Test
    fun componentsShouldReturnXYZAndW() {
        assertContentEquals(VectorComponent.entries.toTypedArray(), Vector4f.components)
    }

    @Test
    fun zeroShouldReturnVectorWithAllZeros() {
        assertEquals(Vector4f.ZERO, Vector4f(0F, 0F, 0F, 0F))
    }

    @Test
    fun oneShouldReturnVectorWithAllOnes() {
        assertEquals(Vector4f.ONE, Vector4f(1F, 1F, 1F, 1F))
    }

    @Test
    fun fromArrayShouldCreateVectorFromArrayAtOffset() {
        assertEquals(Vector4f(2F, 3F, 4F, 5F), Vector4f.fromArray(floatArrayOf(0F, 2F, 3F, 4F, 5F), 1))
    }

    @Test
    fun typeShouldReturnVector4f() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f, vector.type)
    }

    @Test
    fun plusOperatorWithScalarShouldAddValueToAllComponents() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f(4F, 5F, 8F, 11F), vector + 2F)
    }

    @Test
    fun plusOperatorWithVectorShouldAddComponents() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(7F, 10F, 17F, 22F), vector + other)
    }

    @Test
    fun minusOperatorWithScalarShouldSubtractValueFromAllComponents() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f(0F, 1F, 4F, 7F), vector - 2F)
    }

    @Test
    fun minusOperatorWithVectorShouldSubtractComponents() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(-3F, -4F, -5F, -4F), vector - other)
    }

    @Test
    fun timesOperatorWithScalarShouldMultiplyAllComponents() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f(4F, 6F, 12F, 18F), vector * 2F)
    }

    @Test
    fun timesOperatorWithVectorShouldMultiplyComponents() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(10F, 21F, 66F, 117F), vector * other)
    }

    @Test
    fun divOperatorWithScalarShouldDivideAllComponents() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f(1F, 1.5F, 3F, 4.5F), vector / 2F)
    }

    @Test
    fun divOperatorWithVectorShouldDivideComponents() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(0.4F, 3F / 7F, 6F / 11F, 9F / 13F), vector / other)
    }

    @Test
    fun remOperatorWithScalarShouldCalculateRemainderOfAllComponents() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f(0F, 1F, 0F, 1F), vector % 2F)
    }

    @Test
    fun remOperatorWithVectorShouldCalculateRemainderOfComponents() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(2F, 3F, 6F, 9F), vector % other)
    }

    @Test
    fun fmaShouldCalculateFusedMultiplyAdd() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(12F, 23F, 68F, 119F), vector.fma(other, Vector4f(2F, 2F, 2F, 2F)))
    }

    @Test
    fun lerpShouldLinearlyInterpolateBetweenVectors() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(3.5F, 5F, 8.5F, 11F), vector.lerp(other, 0.5F))
    }

    @Test
    fun minShouldReturnTheSmallerVector() {
        val v1 = Vector4f(1F, 2F, 3F, 4F)
        val v2 = Vector4f(5F, 6F, 7F, 8F)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun minComponentsShouldReturnTheComponentWiseMinimum() {
        val v1 = Vector4f(1F, 7F, 3F, 9F)
        val v2 = Vector4f(5F, 2F, 8F, 4F)
        assertEquals(Vector4f(1F, 2F, 3F, 4F), v1 minComponents v2)
    }

    @Test
    fun maxShouldReturnTheLargerVector() {
        val v1 = Vector4f(1F, 2F, 3F, 4F)
        val v2 = Vector4f(5F, 6F, 7F, 8F)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun maxComponentsShouldReturnTheComponentWiseMaximum() {
        val v1 = Vector4f(1F, 7F, 3F, 9F)
        val v2 = Vector4f(5F, 2F, 8F, 4F)
        assertEquals(Vector4f(5F, 7F, 8F, 9F), v1 maxComponents v2)
    }

    @Test
    fun lengthSqShouldReturnSquaredLengthOfVector() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(130F, vector.lengthSq())
    }

    @Test
    fun lengthShouldReturnLengthOfVector() {
        assertFloatEquals(13F, Vector4f(3F, 4F, 12F, 0F).length())
    }

    @Test
    fun normalizedShouldReturnNormalizedVector() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val length = sqrt(130F)
        assertVectorEquals(Vector4f(2F / length, 3F / length, 6F / length, 9F / length), vector.normalized())
    }

    @Test
    fun distanceSqShouldReturnSquaredDistanceToAnotherVector() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(94F, vector distanceSq Vector4f(1F, 1F, 1F, 1F))
    }

    @Test
    fun distanceShouldReturnDistanceToAnotherVector() {
        assertFloatEquals(13F, Vector4f(3F, 4F, 12F, 0F) distance Vector4f.ZERO)
    }

    @Test
    fun dotShouldReturnDotProduct() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(214F, vector dot other)
    }

    @Test
    fun toVector4iShouldConvertToVector4i() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4i(2, 3, 6, 9), vector.toVector4i())
    }

    @Test
    fun timesOperatorWithMatrixShouldMultiplyVectorByMatrix() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(
            Vector4f(38F, 96F, 212F, 332F), vector * Matrix4x4f(
                1F, 2F, 2F, 2F, 3F, 5F, 5F, 5F, 7F, 11F, 11F, 11F, 13F, 17F, 17F, 17F
            )
        )
    }

    @Test
    fun getShouldReturnComponentByIndex() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(2F, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[4] }
    }

    @Test
    fun getShouldReturnComponentByVectorComponent() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(9F, vector[VectorComponent.W])
    }

    @Test
    fun toFloatArrayShouldReturnComponentsAsFloatArray() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertContentEquals(floatArrayOf(2F, 3F, 6F, 9F), vector.toFloatArray())
    }

    @Test
    fun swizzleShouldReturnSwizzledVector() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(
            Vector4f(9F, 2F, 3F, 6F),
            vector.swizzle(VectorComponent.W, VectorComponent.X, VectorComponent.Y, VectorComponent.Z)
        )
    }

    @Test
    fun swizzle3ShouldReturnSwizzledVector() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector3f(9F, 2F, 3F), vector.swizzle3(VectorComponent.W, VectorComponent.X, VectorComponent.Y))
    }

    @Test
    fun swizzle2ShouldReturnSwizzledVector() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector2f(9F, 2F), vector.swizzle2(VectorComponent.W, VectorComponent.X))
    }

    @Test
    fun compareToShouldReturnMagnitudeComparison() {
        val v1 = Vector4f(1F, 2F, 3F, 4F)
        val v2 = Vector4f(1F, 2F, 3F, 4F)
        val v3 = Vector4f(2F, 1F, 1F, 1F)
        val v4 = Vector4f(1F, 3F, 2F, 2F)
        val v5 = Vector4f(1F, 2F, 4F, 3F)
        val v6 = Vector4f(1F, 2F, 3F, 5F)

        assertEquals(0, v1.compareTo(v2))
        assertTrue(v1 > v3)
        assertTrue(v3 < v1)
        assertTrue(v1 > v4)
        assertTrue(v4 < v1)
        assertEquals(0, v1.compareTo(v5))
        assertTrue(v1 < v6)
        assertTrue(v6 > v1)
    }

    private fun assertVectorEquals(expected: Vector4f, actual: Vector4f) {
        assertFloatEquals(expected.x, actual.x)
        assertFloatEquals(expected.y, actual.y)
        assertFloatEquals(expected.z, actual.z)
        assertFloatEquals(expected.w, actual.w)
    }

    private fun assertFloatEquals(expected: Float, actual: Float) {
        assertEquals(expected, actual, 1E-6F)
    }
}
