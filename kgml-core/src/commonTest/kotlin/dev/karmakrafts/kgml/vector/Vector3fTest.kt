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
    fun `constructor should initialize components to zero`() {
        assertEquals(Vector3f(0F, 0F, 0F), Vector3f())
    }

    @Test
    fun `constructor should initialize all components to given value`() {
        assertEquals(Vector3f(2F, 2F, 2F), Vector3f(2F))
    }

    @Test
    fun `componentType should return Float`() {
        assertEquals(Float::class, Vector3f.componentType)
    }

    @Test
    fun `componentSize should return size of Float in bytes`() {
        assertEquals(Float.SIZE_BYTES, Vector3f.componentSize)
    }

    @Test
    fun `dimensions should return 3`() {
        assertEquals(3, Vector3f.dimensions)
    }

    @Test
    fun `components should return X Y and Z`() {
        assertContentEquals(arrayOf(VectorComponent.X, VectorComponent.Y, VectorComponent.Z), Vector3f.components)
    }

    @Test
    fun `zero should return vector with all zeros`() {
        assertEquals(Vector3f.ZERO, Vector3f(0F, 0F, 0F))
    }

    @Test
    fun `one should return vector with all ones`() {
        assertEquals(Vector3f.ONE, Vector3f(1F, 1F, 1F))
    }

    @Test
    fun `fromArray should create vector from array at offset`() {
        assertEquals(Vector3f(2F, 3F, 4F), Vector3f.fromArray(floatArrayOf(0F, 2F, 3F, 4F), 1))
    }

    @Test
    fun `type should return Vector3f`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f, vector.type)
    }

    @Test
    fun `plus operator with scalar should add value to all components`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(4F, 5F, 8F), vector + 2F)
    }

    @Test
    fun `plus operator with vector should add components`() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(7F, 10F, 17F), vector + other)
    }

    @Test
    fun `minus operator with scalar should subtract value from all components`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(0F, 1F, 4F), vector - 2F)
    }

    @Test
    fun `minus operator with vector should subtract components`() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(-3F, -4F, -5F), vector - other)
    }

    @Test
    fun `times operator with scalar should multiply all components`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(4F, 6F, 12F), vector * 2F)
    }

    @Test
    fun `times operator with vector should multiply components`() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(10F, 21F, 66F), vector * other)
    }

    @Test
    fun `div operator with scalar should divide all components`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(1F, 1.5F, 3F), vector / 2F)
    }

    @Test
    fun `div operator with vector should divide components`() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(0.4F, 3F / 7F, 6F / 11F), vector / other)
    }

    @Test
    fun `rem operator with scalar should calculate remainder of all components`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(0F, 1F, 0F), vector % 2F)
    }

    @Test
    fun `rem operator with vector should calculate remainder of components`() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(2F, 3F, 6F), vector % other)
    }

    @Test
    fun `fma should calculate fused multiply add`() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(12F, 23F, 68F), vector.fma(other, Vector3f(2F, 2F, 2F)))
    }

    @Test
    fun `lerp should linearly interpolate between vectors`() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(3.5F, 5F, 8.5F), vector.lerp(other, 0.5F))
    }

    @Test
    fun `min should return the smaller vector`() {
        val v1 = Vector3f(1F, 2F, 3F)
        val v2 = Vector3f(4F, 5F, 6F)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun `minComponents should return the component-wise minimum`() {
        val v1 = Vector3f(1F, 5F, 3F)
        val v2 = Vector3f(4F, 2F, 6F)
        assertEquals(Vector3f(1F, 2F, 3F), v1 minComponents v2)
    }

    @Test
    fun `max should return the larger vector`() {
        val v1 = Vector3f(1F, 2F, 3F)
        val v2 = Vector3f(4F, 5F, 6F)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun `maxComponents should return the component-wise maximum`() {
        val v1 = Vector3f(1F, 5F, 3F)
        val v2 = Vector3f(4F, 2F, 6F)
        assertEquals(Vector3f(4F, 5F, 6F), v1 maxComponents v2)
    }

    @Test
    fun `lengthSq should return squared length of vector`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(49F, vector.lengthSq())
    }

    @Test
    fun `length should return length of vector`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertFloatEquals(7F, vector.length())
    }

    @Test
    fun `normalized should return normalized vector`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertVectorEquals(Vector3f(2F / 7F, 3F / 7F, 6F / 7F), vector.normalized())
    }

    @Test
    fun `distanceSq should return squared distance to another vector`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(30F, vector distanceSq Vector3f(1F, 1F, 1F))
    }

    @Test
    fun `distance should return distance to another vector`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertFloatEquals(7F, vector distance Vector3f(0F, 0F, 0F))
    }

    @Test
    fun `dot should return dot product`() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(97F, vector dot other)
    }

    @Test
    fun `cross should return cross product`() {
        val vector = Vector3f(2F, 3F, 6F)
        val other = Vector3f(5F, 7F, 11F)
        assertEquals(Vector3f(-9F, 8F, -1F), vector cross other)
    }

    @Test
    fun `toVector3i should convert to Vector3i`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3i(2, 3, 6), vector.toVector3i())
    }

    @Test
    fun `times operator with matrix should multiply vector by matrix`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(
            Vector3f(20F, 51F, 113F), vector * Matrix3x3f(
                1F, 2F, 2F, 3F, 5F, 5F, 7F, 11F, 11F
            )
        )
    }

    @Test
    fun `get should return component by index`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(2F, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[3] }
    }

    @Test
    fun `get should return component by VectorComponent`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(6F, vector[VectorComponent.Z])
        assertFailsWith<IllegalArgumentException> { vector[VectorComponent.W] }
    }

    @Test
    fun `toFloatArray should return components as float array`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertContentEquals(floatArrayOf(2F, 3F, 6F), vector.toFloatArray())
    }

    @Test
    fun `swizzle should return swizzled vector`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector3f(6F, 2F, 3F), vector.swizzle(VectorComponent.Z, VectorComponent.X, VectorComponent.Y))
    }

    @Test
    fun `swizzle2 should return swizzled vector`() {
        val vector = Vector3f(2F, 3F, 6F)
        assertEquals(Vector2f(6F, 2F), vector.swizzle2(VectorComponent.Z, VectorComponent.X))
    }

    @Test
    fun `compareTo should return magnitude comparison`() {
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
