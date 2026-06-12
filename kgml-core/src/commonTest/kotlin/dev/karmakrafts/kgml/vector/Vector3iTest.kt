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
    fun `distanceSq should return squared distance to another vector`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(30, vector distanceSq Vector3i(1, 1, 1))
    }

    @Test
    fun `distance should return distance to another vector`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(7, vector distance Vector3i(0, 0, 0))
    }

    @Test
    fun `constructor should initialize components to zero`() {
        assertEquals(Vector3i(0, 0, 0), Vector3i())
    }

    @Test
    fun `constructor should initialize all components to given value`() {
        assertEquals(Vector3i(2, 2, 2), Vector3i(2))
    }

    @Test
    fun `componentType should return Int`() {
        assertEquals(Int::class, Vector3i.componentType)
    }

    @Test
    fun `componentSize should return size of Int in bytes`() {
        assertEquals(Int.SIZE_BYTES, Vector3i.componentSize)
    }

    @Test
    fun `dimensions should return 3`() {
        assertEquals(3, Vector3i.dimensions)
    }

    @Test
    fun `components should return X Y and Z`() {
        assertContentEquals(arrayOf(VectorComponent.X, VectorComponent.Y, VectorComponent.Z), Vector3i.components)
    }

    @Test
    fun `zero should return vector with all zeros`() {
        assertEquals(Vector3i.ZERO, Vector3i(0, 0, 0))
    }

    @Test
    fun `one should return vector with all ones`() {
        assertEquals(Vector3i.ONE, Vector3i(1, 1, 1))
    }

    @Test
    fun `fromArray should create vector from array at offset`() {
        assertEquals(Vector3i(2, 3, 4), Vector3i.fromArray(intArrayOf(0, 2, 3, 4), 1))
    }

    @Test
    fun `type should return Vector3i`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i, vector.type)
    }

    @Test
    fun `plus operator with scalar should add value to all components`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(4, 5, 8), vector + 2)
    }

    @Test
    fun `plus operator with vector should add components`() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(7, 10, 17), vector + other)
    }

    @Test
    fun `minus operator with scalar should subtract value from all components`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(0, 1, 4), vector - 2)
    }

    @Test
    fun `minus operator with vector should subtract components`() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(-3, -4, -5), vector - other)
    }

    @Test
    fun `times operator with scalar should multiply all components`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(4, 6, 12), vector * 2)
    }

    @Test
    fun `times operator with vector should multiply components`() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(10, 21, 66), vector * other)
    }

    @Test
    fun `div operator with scalar should divide all components`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(1, 1, 3), vector / 2)
    }

    @Test
    fun `div operator with vector should divide components`() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(0, 0, 0), vector / other)
    }

    @Test
    fun `rem operator with scalar should calculate remainder of all components`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(0, 1, 0), vector % 2)
    }

    @Test
    fun `rem operator with vector should calculate remainder of components`() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(2, 3, 6), vector % other)
    }

    @Test
    fun `fma should calculate fused multiply add`() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(12, 23, 68), vector.fma(other, Vector3i(2, 2, 2)))
    }

    @Test
    fun `lerp should linearly interpolate between vectors`() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(3, 5, 8), vector.lerp(other, 0.5F))
    }

    @Test
    fun `min should return the smaller vector`() {
        val v1 = Vector3i(1, 2, 3)
        val v2 = Vector3i(4, 5, 6)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun `minComponents should return the component-wise minimum`() {
        val v1 = Vector3i(1, 5, 3)
        val v2 = Vector3i(4, 2, 6)
        assertEquals(Vector3i(1, 2, 3), v1 minComponents v2)
    }

    @Test
    fun `max should return the larger vector`() {
        val v1 = Vector3i(1, 2, 3)
        val v2 = Vector3i(4, 5, 6)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun `maxComponents should return the component-wise maximum`() {
        val v1 = Vector3i(1, 5, 3)
        val v2 = Vector3i(4, 2, 6)
        assertEquals(Vector3i(4, 5, 6), v1 maxComponents v2)
    }

    @Test
    fun `lengthSq should return squared length of vector`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(49, vector.lengthSq())
    }

    @Test
    fun `length should return length of vector`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(7, vector.length())
    }

    @Test
    fun `normalized should return normalized vector`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(0, 0, 0), vector.normalized())
    }

    @Test
    fun `dot should return dot product`() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(97, vector dot other)
    }

    @Test
    fun `cross should return cross product`() {
        val vector = Vector3i(2, 3, 6)
        val other = Vector3i(5, 7, 11)
        assertEquals(Vector3i(-9, 8, -1), vector cross other)
    }

    @Test
    fun `toVector3f should convert to Vector3f`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3f(2F, 3F, 6F), vector.toVector3f())
    }

    @Test
    fun `get should return component by index`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(2, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[3] }
    }

    @Test
    fun `get should return component by VectorComponent`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(6, vector[VectorComponent.Z])
        assertFailsWith<IllegalArgumentException> { vector[VectorComponent.W] }
    }

    @Test
    fun `toIntArray should return components as int array`() {
        val vector = Vector3i(2, 3, 6)
        assertContentEquals(intArrayOf(2, 3, 6), vector.toIntArray())
    }

    @Test
    fun `swizzle should return swizzled vector`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector3i(6, 2, 3), vector.swizzle(VectorComponent.Z, VectorComponent.X, VectorComponent.Y))
    }

    @Test
    fun `swizzle2 should return swizzled vector`() {
        val vector = Vector3i(2, 3, 6)
        assertEquals(Vector2i(6, 2), vector.swizzle2(VectorComponent.Z, VectorComponent.X))
    }

    @Test
    fun `compareTo should return magnitude comparison`() {
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
