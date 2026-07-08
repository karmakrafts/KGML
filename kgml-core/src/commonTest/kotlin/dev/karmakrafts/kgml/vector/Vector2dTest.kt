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

import dev.karmakrafts.kgml.matrix.Matrix2x2d
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class Vector2dTest {
    @Test
    fun `constructor should initialize components to zero`() {
        assertEquals(Vector2d(0.0, 0.0), Vector2d())
    }

    @Test
    fun `constructor should initialize all components to given value`() {
        assertEquals(Vector2d(2.0, 2.0), Vector2d(2.0))
    }

    @Test
    fun `componentType should return Double`() {
        assertEquals(Double::class, Vector2d.componentType)
    }

    @Test
    fun `componentSize should return size of Double in bytes`() {
        assertEquals(Double.SIZE_BYTES, Vector2d.componentSize)
    }

    @Test
    fun `dimensions should return 2`() {
        assertEquals(2, Vector2d.dimensions)
    }

    @Test
    fun `components should return X and Y`() {
        assertContentEquals(arrayOf(VectorComponent.X, VectorComponent.Y), Vector2d.components)
    }

    @Test
    fun `zero should return vector with all zeros`() {
        assertEquals(Vector2d.ZERO, Vector2d(0.0, 0.0))
    }

    @Test
    fun `one should return vector with all ones`() {
        assertEquals(Vector2d.ONE, Vector2d(1.0, 1.0))
    }

    @Test
    fun `fromArray should create vector from array at offset`() {
        assertEquals(Vector2d(2.0, 3.0), Vector2d.fromArray(doubleArrayOf(0.0, 2.0, 3.0), 1))
    }

    @Test
    fun `type should return Vector2d`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d, vector.type)
    }

    @Test
    fun `plus operator with scalar should add value to all components`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(5.0, 6.0), vector + 2.0)
    }

    @Test
    fun `plus operator with vector should add components`() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(8.0, 10.0), vector + other)
    }

    @Test
    fun `minus operator with scalar should subtract value from all components`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(1.0, 2.0), vector - 2.0)
    }

    @Test
    fun `minus operator with vector should subtract components`() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(-2.0, -2.0), vector - other)
    }

    @Test
    fun `times operator with scalar should multiply all components`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(6.0, 8.0), vector * 2.0)
    }

    @Test
    fun `times operator with vector should multiply components`() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(15.0, 24.0), vector * other)
    }

    @Test
    fun `div operator with scalar should divide all components`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(1.5, 2.0), vector / 2.0)
    }

    @Test
    fun `div operator with vector should divide components`() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(0.6, 2.0 / 3.0), vector / other)
    }

    @Test
    fun `rem operator with scalar should calculate remainder of all components`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(1.0, 0.0), vector % 2.0)
    }

    @Test
    fun `rem operator with vector should calculate remainder of components`() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(3.0, 4.0), vector % other)
    }

    @Test
    fun `fma should calculate fused multiply add`() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(17.0, 26.0), vector.fma(other, Vector2d(2.0, 2.0)))
    }

    @Test
    fun `lerp should linearly interpolate between vectors`() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(Vector2d(4.0, 5.0), vector.lerp(other, 0.5))
    }

    @Test
    fun `min should return the smaller vector`() {
        val v1 = Vector2d(1.0, 2.0)
        val v2 = Vector2d(3.0, 4.0)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun `minComponents should return the component-wise minimum`() {
        val v1 = Vector2d(1.0, 4.0)
        val v2 = Vector2d(3.0, 2.0)
        assertEquals(Vector2d(1.0, 2.0), v1 minComponents v2)
    }

    @Test
    fun `max should return the larger vector`() {
        val v1 = Vector2d(1.0, 2.0)
        val v2 = Vector2d(3.0, 4.0)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun `maxComponents should return the component-wise maximum`() {
        val v1 = Vector2d(1.0, 4.0)
        val v2 = Vector2d(3.0, 2.0)
        assertEquals(Vector2d(3.0, 4.0), v1 maxComponents v2)
    }

    @Test
    fun `lengthSq should return squared length of vector`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(25.0, vector.lengthSq())
    }

    @Test
    fun `length should return length of vector`() {
        val vector = Vector2d(3.0, 4.0)
        assertFloatEquals(5.0, vector.length())
    }

    @Test
    fun `normalized should return normalized vector`() {
        val vector = Vector2d(3.0, 4.0)
        assertVectorEquals(Vector2d(0.6, 0.8), vector.normalized())
    }

    @Test
    fun `distanceSq should return squared distance to another vector`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(8.0, vector distanceSq Vector2d(1.0, 2.0))
    }

    @Test
    fun `distance should return distance to another vector`() {
        val vector = Vector2d(3.0, 4.0)
        assertFloatEquals(5.0, vector distance Vector2d(0.0, 0.0))
    }

    @Test
    fun `dot should return dot product`() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(39.0, vector dot other)
    }

    @Test
    fun `cross should return cross product`() {
        val vector = Vector2d(3.0, 4.0)
        val other = Vector2d(5.0, 6.0)
        assertEquals(-2.0, vector cross other)
    }

    @Test
    fun `angleRad should return angle in radians`() {
        val v1 = Vector2d(1.0, 0.0)
        val v2 = Vector2d(0.0, 1.0)
        assertFloatEquals(1.5707964, v1 angleRad v2)
    }

    @Test
    fun `angle should return angle in degrees`() {
        val v1 = Vector2d(1.0, 0.0)
        val v2 = Vector2d(0.0, 1.0)
        assertFloatEquals(90.0, v1 angle v2)
    }

    @Test
    fun `toVector2i should convert to Vector2i`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2i(3, 4), vector.toVector2i())
    }

    @Test
    fun `times operator with matrix should multiply vector by matrix`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(11.0, 25.0), vector * Matrix2x2d(1.0, 2.0, 3.0, 4.0))
    }

    @Test
    fun `get should return component by index`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(3.0, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[2] }
    }

    @Test
    fun `get should return component by VectorComponent`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(4.0, vector[VectorComponent.Y])
        assertFailsWith<IllegalArgumentException> { vector[VectorComponent.Z] }
    }

    @Test
    fun `toDoubleArray should return components as float array`() {
        val vector = Vector2d(3.0, 4.0)
        assertContentEquals(doubleArrayOf(3.0, 4.0), vector.toDoubleArray())
    }

    @Test
    fun `swizzle should return swizzled vector`() {
        val vector = Vector2d(3.0, 4.0)
        assertEquals(Vector2d(4.0, 3.0), vector.swizzle(VectorComponent.Y, VectorComponent.X))
    }

    @Test
    fun `compareTo should return magnitude comparison`() {
        val v1 = Vector2d(1.0, 2.0)
        val v2 = Vector2d(1.0, 2.0)
        val v3 = Vector2d(2.0, 1.0)
        val v4 = Vector2d(1.0, 3.0)

        assertEquals(0, v1.compareTo(v2))
        assertEquals(0, v1.compareTo(v3))
        assertTrue(v1 < v4)
        assertTrue(v4 > v1)
    }

    private fun assertVectorEquals(expected: Vector2d, actual: Vector2d) {
        assertFloatEquals(expected.x, actual.x)
        assertFloatEquals(expected.y, actual.y)
    }

    private fun assertFloatEquals(expected: Double, actual: Double) {
        assertEquals(expected, actual, 1E-6)
    }
}
