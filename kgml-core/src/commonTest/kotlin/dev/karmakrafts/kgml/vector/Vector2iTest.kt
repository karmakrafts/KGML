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

class Vector2iTest {
    @Test
    fun `constructor should initialize components to zero`() {
        assertEquals(Vector2i(0, 0), Vector2i())
    }

    @Test
    fun `constructor should initialize all components to given value`() {
        assertEquals(Vector2i(2, 2), Vector2i(2))
    }

    @Test
    fun `componentType should return Int`() {
        assertEquals(Int::class, Vector2i.componentType)
    }

    @Test
    fun `componentSize should return size of Int in bytes`() {
        assertEquals(Int.SIZE_BYTES, Vector2i.componentSize)
    }

    @Test
    fun `dimensions should return 2`() {
        assertEquals(2, Vector2i.dimensions)
    }

    @Test
    fun `components should return X and Y`() {
        assertContentEquals(arrayOf(VectorComponent.X, VectorComponent.Y), Vector2i.components)
    }

    @Test
    fun `zero should return vector with all zeros`() {
        assertEquals(Vector2i.zero, Vector2i(0, 0))
    }

    @Test
    fun `one should return vector with all ones`() {
        assertEquals(Vector2i.one, Vector2i(1, 1))
    }

    @Test
    fun `fromArray should create vector from array at offset`() {
        assertEquals(Vector2i(2, 3), Vector2i.fromArray(intArrayOf(0, 2, 3), 1))
    }

    @Test
    fun `type should return Vector2i`() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i, vector.type)
    }

    @Test
    fun `plus operator with scalar should add value to all components`() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(5, 6), vector + 2)
    }

    @Test
    fun `plus operator with vector should add components`() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(8, 10), vector + other)
    }

    @Test
    fun `minus operator with scalar should subtract value from all components`() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(1, 2), vector - 2)
    }

    @Test
    fun `minus operator with vector should subtract components`() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(-2, -2), vector - other)
    }

    @Test
    fun `times operator with scalar should multiply all components`() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(6, 8), vector * 2)
    }

    @Test
    fun `times operator with vector should multiply components`() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(15, 24), vector * other)
    }

    @Test
    fun `div operator with scalar should divide all components`() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(1, 2), vector / 2)
    }

    @Test
    fun `div operator with vector should divide components`() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(0, 0), vector / other)
    }

    @Test
    fun `rem operator with scalar should calculate remainder of all components`() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(1, 0), vector % 2)
    }

    @Test
    fun `rem operator with vector should calculate remainder of components`() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(3, 4), vector % other)
    }

    @Test
    fun `fma should calculate fused multiply add`() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(17, 26), vector.fma(other, Vector2i(2, 2)))
    }

    @Test
    fun `lerp should linearly interpolate between vectors`() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(Vector2i(4, 5), vector.lerp(other, 0.5F))
    }

    @Test
    fun `lengthSq should return squared length of vector`() {
        val vector = Vector2i(3, 4)
        assertEquals(25, vector.lengthSq())
    }

    @Test
    fun `length should return length of vector`() {
        val vector = Vector2i(3, 4)
        assertEquals(5, vector.length())
    }

    @Test
    fun `normalized should return normalized vector`() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(0, 0), vector.normalized())
    }

    @Test
    fun `dot should return dot product`() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(39, vector dot other)
    }

    @Test
    fun `cross should return cross product`() {
        val vector = Vector2i(3, 4)
        val other = Vector2i(5, 6)
        assertEquals(-2, vector cross other)
    }

    @Test
    fun `toVector2f should convert to Vector2f`() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2f(3F, 4F), vector.toVector2f())
    }

    @Test
    fun `get should return component by index`() {
        val vector = Vector2i(3, 4)
        assertEquals(3, vector[0])
        assertFailsWith<IllegalArgumentException> { vector[2] }
    }

    @Test
    fun `get should return component by VectorComponent`() {
        val vector = Vector2i(3, 4)
        assertEquals(4, vector[VectorComponent.Y])
        assertFailsWith<IllegalArgumentException> { vector[VectorComponent.Z] }
    }

    @Test
    fun `toIntArray should return components as int array`() {
        val vector = Vector2i(3, 4)
        assertContentEquals(intArrayOf(3, 4), vector.toIntArray())
    }

    @Test
    fun `swizzle should return swizzled vector`() {
        val vector = Vector2i(3, 4)
        assertEquals(Vector2i(4, 3), vector.swizzle(VectorComponent.Y, VectorComponent.X))
    }
}
