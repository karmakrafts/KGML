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
    fun `constructor should initialize components to zero`() {
        assertEquals(Vector4f(0F, 0F, 0F, 0F), Vector4f())
    }

    @Test
    fun `constructor should initialize all components to given value`() {
        assertEquals(Vector4f(2F, 2F, 2F, 2F), Vector4f(2F))
    }

    @Test
    fun `componentType should return Float`() {
        assertEquals(Float::class, Vector4f.componentType)
    }

    @Test
    fun `componentSize should return size of Float in bytes`() {
        assertEquals(Float.SIZE_BYTES, Vector4f.componentSize)
    }

    @Test
    fun `dimensions should return 4`() {
        assertEquals(4, Vector4f.dimensions)
    }

    @Test
    fun `components should return X Y Z and W`() {
        assertContentEquals(VectorComponent.entries.toTypedArray(), Vector4f.components)
    }

    @Test
    fun `zero should return vector with all zeros`() {
        assertEquals(Vector4f.zero, Vector4f(0F, 0F, 0F, 0F))
    }

    @Test
    fun `one should return vector with all ones`() {
        assertEquals(Vector4f.one, Vector4f(1F, 1F, 1F, 1F))
    }

    @Test
    fun `fromArray should create vector from array at offset`() {
        assertEquals(Vector4f(2F, 3F, 4F, 5F), Vector4f.fromArray(floatArrayOf(0F, 2F, 3F, 4F, 5F), 1))
    }

    @Test
    fun `type should return Vector4f`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f, vector.type)
    }

    @Test
    fun `plus operator with scalar should add value to all components`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f(4F, 5F, 8F, 11F), vector + 2F)
    }

    @Test
    fun `plus operator with vector should add components`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(7F, 10F, 17F, 22F), vector + other)
    }

    @Test
    fun `minus operator with scalar should subtract value from all components`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f(0F, 1F, 4F, 7F), vector - 2F)
    }

    @Test
    fun `minus operator with vector should subtract components`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(-3F, -4F, -5F, -4F), vector - other)
    }

    @Test
    fun `times operator with scalar should multiply all components`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f(4F, 6F, 12F, 18F), vector * 2F)
    }

    @Test
    fun `times operator with vector should multiply components`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(10F, 21F, 66F, 117F), vector * other)
    }

    @Test
    fun `div operator with scalar should divide all components`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f(1F, 1.5F, 3F, 4.5F), vector / 2F)
    }

    @Test
    fun `div operator with vector should divide components`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(0.4F, 3F / 7F, 6F / 11F, 9F / 13F), vector / other)
    }

    @Test
    fun `rem operator with scalar should calculate remainder of all components`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4f(0F, 1F, 0F, 1F), vector % 2F)
    }

    @Test
    fun `rem operator with vector should calculate remainder of components`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(2F, 3F, 6F, 9F), vector % other)
    }

    @Test
    fun `fma should calculate fused multiply add`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(12F, 23F, 68F, 119F), vector.fma(other, Vector4f(2F, 2F, 2F, 2F)))
    }

    @Test
    fun `lerp should linearly interpolate between vectors`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(Vector4f(3.5F, 5F, 8.5F, 11F), vector.lerp(other, 0.5F))
    }

    @Test
    fun `lengthSq should return squared length of vector`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(130F, vector.lengthSq())
    }

    @Test
    fun `length should return length of vector`() {
        assertFloatEquals(13F, Vector4f(3F, 4F, 12F, 0F).length())
    }

    @Test
    fun `normalized should return normalized vector`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val length = sqrt(130F)
        assertVectorEquals(Vector4f(2F / length, 3F / length, 6F / length, 9F / length), vector.normalized())
    }

    @Test
    fun `distanceSq should return squared distance to another vector`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(94F, vector.distanceSq(Vector4f(1F, 1F, 1F, 1F)))
    }

    @Test
    fun `distance should return distance to another vector`() {
        assertFloatEquals(13F, Vector4f(3F, 4F, 12F, 0F).distance(Vector4f.zero))
    }

    @Test
    fun `dot should return dot product`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        val other = Vector4f(5F, 7F, 11F, 13F)
        assertEquals(214F, vector dot other)
    }

    @Test
    fun `toVector4i should convert to Vector4i`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector4i(2, 3, 6, 9), vector.toVector4i())
    }

    @Test
    fun `times operator with matrix should multiply vector by matrix`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(
            Vector4f(38F, 96F, 212F, 332F), vector * Matrix4x4f(
                1F, 2F, 2F, 2F, 3F, 5F, 5F, 5F, 7F, 11F, 11F, 11F, 13F, 17F, 17F, 17F
            )
        )
    }

    @Test
    fun `get should return component by index`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(2F, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[4] }
    }

    @Test
    fun `get should return component by VectorComponent`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(9F, vector[VectorComponent.W])
    }

    @Test
    fun `toFloatArray should return components as float array`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertContentEquals(floatArrayOf(2F, 3F, 6F, 9F), vector.toFloatArray())
    }

    @Test
    fun `swizzle should return swizzled vector`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(
            Vector4f(9F, 2F, 3F, 6F),
            vector.swizzle(VectorComponent.W, VectorComponent.X, VectorComponent.Y, VectorComponent.Z)
        )
    }

    @Test
    fun `swizzle3 should return swizzled vector`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector3f(9F, 2F, 3F), vector.swizzle3(VectorComponent.W, VectorComponent.X, VectorComponent.Y))
    }

    @Test
    fun `swizzle2 should return swizzled vector`() {
        val vector = Vector4f(2F, 3F, 6F, 9F)
        assertEquals(Vector2f(9F, 2F), vector.swizzle2(VectorComponent.W, VectorComponent.X))
    }

    @Test
    fun `compareTo should return magnitude comparison`() {
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
