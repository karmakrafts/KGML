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
    fun `constructor should initialize components to zero`() {
        assertEquals(Vector4i(0, 0, 0, 0), Vector4i())
    }

    @Test
    fun `constructor should initialize all components to given value`() {
        assertEquals(Vector4i(2, 2, 2, 2), Vector4i(2))
    }

    @Test
    fun `componentType should return Int`() {
        assertEquals(Int::class, Vector4i.componentType)
    }

    @Test
    fun `componentSize should return size of Int in bytes`() {
        assertEquals(Int.SIZE_BYTES, Vector4i.componentSize)
    }

    @Test
    fun `dimensions should return 4`() {
        assertEquals(4, Vector4i.dimensions)
    }

    @Test
    fun `components should return X Y Z and W`() {
        assertContentEquals(VectorComponent.entries.toTypedArray(), Vector4i.components)
    }

    @Test
    fun `zero should return vector with all zeros`() {
        assertEquals(Vector4i.zero, Vector4i(0, 0, 0, 0))
    }

    @Test
    fun `one should return vector with all ones`() {
        assertEquals(Vector4i.one, Vector4i(1, 1, 1, 1))
    }

    @Test
    fun `fromArray should create vector from array at offset`() {
        assertEquals(Vector4i(2, 3, 4, 5), Vector4i.fromArray(intArrayOf(0, 2, 3, 4, 5), 1))
    }

    @Test
    fun `type should return Vector4i`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i, vector.type)
    }

    @Test
    fun `plus operator with scalar should add value to all components`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(4, 5, 8, 11), vector + 2)
    }

    @Test
    fun `plus operator with vector should add components`() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(7, 10, 17, 22), vector + other)
    }

    @Test
    fun `minus operator with scalar should subtract value from all components`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(0, 1, 4, 7), vector - 2)
    }

    @Test
    fun `minus operator with vector should subtract components`() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(-3, -4, -5, -4), vector - other)
    }

    @Test
    fun `times operator with scalar should multiply all components`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(4, 6, 12, 18), vector * 2)
    }

    @Test
    fun `times operator with vector should multiply components`() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(10, 21, 66, 117), vector * other)
    }

    @Test
    fun `div operator with scalar should divide all components`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(1, 1, 3, 4), vector / 2)
    }

    @Test
    fun `div operator with vector should divide components`() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(0, 0, 0, 0), vector / other)
    }

    @Test
    fun `rem operator with scalar should calculate remainder of all components`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(0, 1, 0, 1), vector % 2)
    }

    @Test
    fun `rem operator with vector should calculate remainder of components`() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(2, 3, 6, 9), vector % other)
    }

    @Test
    fun `fma should calculate fused multiply add`() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(12, 23, 68, 119), vector.fma(other, Vector4i(2, 2, 2, 2)))
    }

    @Test
    fun `lerp should linearly interpolate between vectors`() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(Vector4i(3, 5, 8, 11), vector.lerp(other, 0.5F))
    }

    @Test
    fun `min should return the smaller vector`() {
        val v1 = Vector4i(1, 2, 3, 4)
        val v2 = Vector4i(5, 6, 7, 8)
        assertEquals(v1, v1 min v2)
        assertEquals(v1, v2 min v1)
    }

    @Test
    fun `minComponents should return the component-wise minimum`() {
        val v1 = Vector4i(1, 7, 3, 9)
        val v2 = Vector4i(5, 2, 8, 4)
        assertEquals(Vector4i(1, 2, 3, 4), v1 minComponents v2)
    }

    @Test
    fun `max should return the larger vector`() {
        val v1 = Vector4i(1, 2, 3, 4)
        val v2 = Vector4i(5, 6, 7, 8)
        assertEquals(v2, v1 max v2)
        assertEquals(v2, v2 max v1)
    }

    @Test
    fun `maxComponents should return the component-wise maximum`() {
        val v1 = Vector4i(1, 7, 3, 9)
        val v2 = Vector4i(5, 2, 8, 4)
        assertEquals(Vector4i(5, 7, 8, 9), v1 maxComponents v2)
    }

    @Test
    fun `lengthSq should return squared length of vector`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(130, vector.lengthSq())
    }

    @Test
    fun `length should return length of vector`() {
        assertEquals(13, Vector4i(3, 4, 12, 0).length())
    }

    @Test
    fun `normalized should return normalized vector`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4i(0, 0, 0, 0), vector.normalized())
    }

    @Test
    fun `dot should return dot product`() {
        val vector = Vector4i(2, 3, 6, 9)
        val other = Vector4i(5, 7, 11, 13)
        assertEquals(214, vector dot other)
    }

    @Test
    fun `toVector4f should convert to Vector4f`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector4f(2F, 3F, 6F, 9F), vector.toVector4f())
    }

    @Test
    fun `get should return component by index`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(2, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[4] }
    }

    @Test
    fun `get should return component by VectorComponent`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(9, vector[VectorComponent.W])
    }

    @Test
    fun `toIntArray should return components as int array`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertContentEquals(intArrayOf(2, 3, 6, 9), vector.toIntArray())
    }

    @Test
    fun `swizzle should return swizzled vector`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(
            Vector4i(9, 2, 3, 6),
            vector.swizzle(VectorComponent.W, VectorComponent.X, VectorComponent.Y, VectorComponent.Z)
        )
    }

    @Test
    fun `swizzle3 should return swizzled vector`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector3i(9, 2, 3), vector.swizzle3(VectorComponent.W, VectorComponent.X, VectorComponent.Y))
    }

    @Test
    fun `swizzle2 should return swizzled vector`() {
        val vector = Vector4i(2, 3, 6, 9)
        assertEquals(Vector2i(9, 2), vector.swizzle2(VectorComponent.W, VectorComponent.X))
    }

    @Test
    fun `compareTo should return magnitude comparison`() {
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
