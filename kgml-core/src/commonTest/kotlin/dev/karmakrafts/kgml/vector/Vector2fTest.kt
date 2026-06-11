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
    fun `constructor should initialize components to zero`() {
        assertEquals(Vector2f(0F, 0F), Vector2f())
    }

    @Test
    fun `constructor should initialize all components to given value`() {
        assertEquals(Vector2f(2F, 2F), Vector2f(2F))
    }

    @Test
    fun `componentType should return Float`() {
        assertEquals(Float::class, Vector2f.componentType)
    }

    @Test
    fun `componentSize should return size of Float in bytes`() {
        assertEquals(Float.SIZE_BYTES, Vector2f.componentSize)
    }

    @Test
    fun `dimensions should return 2`() {
        assertEquals(2, Vector2f.dimensions)
    }

    @Test
    fun `components should return X and Y`() {
        assertContentEquals(arrayOf(VectorComponent.X, VectorComponent.Y), Vector2f.components)
    }

    @Test
    fun `zero should return vector with all zeros`() {
        assertEquals(Vector2f.zero, Vector2f(0F, 0F))
    }

    @Test
    fun `one should return vector with all ones`() {
        assertEquals(Vector2f.one, Vector2f(1F, 1F))
    }

    @Test
    fun `fromArray should create vector from array at offset`() {
        assertEquals(Vector2f(2F, 3F), Vector2f.fromArray(floatArrayOf(0F, 2F, 3F), 1))
    }

    @Test
    fun `type should return Vector2f`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f, vector.type)
    }

    @Test
    fun `plus operator with scalar should add value to all components`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(5F, 6F), vector + 2F)
    }

    @Test
    fun `plus operator with vector should add components`() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(8F, 10F), vector + other)
    }

    @Test
    fun `minus operator with scalar should subtract value from all components`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(1F, 2F), vector - 2F)
    }

    @Test
    fun `minus operator with vector should subtract components`() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(-2F, -2F), vector - other)
    }

    @Test
    fun `times operator with scalar should multiply all components`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(6F, 8F), vector * 2F)
    }

    @Test
    fun `times operator with vector should multiply components`() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(15F, 24F), vector * other)
    }

    @Test
    fun `div operator with scalar should divide all components`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(1.5F, 2F), vector / 2F)
    }

    @Test
    fun `div operator with vector should divide components`() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(0.6F, 2F / 3F), vector / other)
    }

    @Test
    fun `rem operator with scalar should calculate remainder of all components`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(1F, 0F), vector % 2F)
    }

    @Test
    fun `rem operator with vector should calculate remainder of components`() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(3F, 4F), vector % other)
    }

    @Test
    fun `fma should calculate fused multiply add`() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(17F, 26F), vector.fma(other, Vector2f(2F, 2F)))
    }

    @Test
    fun `lerp should linearly interpolate between vectors`() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(Vector2f(4F, 5F), vector.lerp(other, 0.5F))
    }

    @Test
    fun `lengthSq should return squared length of vector`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(25F, vector.lengthSq())
    }

    @Test
    fun `length should return length of vector`() {
        val vector = Vector2f(3F, 4F)
        assertFloatEquals(5F, vector.length())
    }

    @Test
    fun `normalized should return normalized vector`() {
        val vector = Vector2f(3F, 4F)
        assertVectorEquals(Vector2f(0.6F, 0.8F), vector.normalized())
    }

    @Test
    fun `distanceSq should return squared distance to another vector`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(8F, vector.distanceSq(Vector2f(1F, 2F)))
    }

    @Test
    fun `distance should return distance to another vector`() {
        val vector = Vector2f(3F, 4F)
        assertFloatEquals(5F, vector.distance(Vector2f(0F, 0F)))
    }

    @Test
    fun `dot should return dot product`() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(39F, vector dot other)
    }

    @Test
    fun `cross should return cross product`() {
        val vector = Vector2f(3F, 4F)
        val other = Vector2f(5F, 6F)
        assertEquals(-2F, vector cross other)
    }

    @Test
    fun `toVector2i should convert to Vector2i`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2i(3, 4), vector.toVector2i())
    }

    @Test
    fun `times operator with matrix should multiply vector by matrix`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(11F, 25F), vector * Matrix2x2f(1F, 2F, 3F, 4F))
    }

    @Test
    fun `get should return component by index`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(3F, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[2] }
    }

    @Test
    fun `get should return component by VectorComponent`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(4F, vector[VectorComponent.Y])
        assertFailsWith<IllegalArgumentException> { vector[VectorComponent.Z] }
    }

    @Test
    fun `toFloatArray should return components as float array`() {
        val vector = Vector2f(3F, 4F)
        assertContentEquals(floatArrayOf(3F, 4F), vector.toFloatArray())
    }

    @Test
    fun `swizzle should return swizzled vector`() {
        val vector = Vector2f(3F, 4F)
        assertEquals(Vector2f(4F, 3F), vector.swizzle(VectorComponent.Y, VectorComponent.X))
    }

    @Test
    fun `compareTo should return magnitude comparison`() {
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
