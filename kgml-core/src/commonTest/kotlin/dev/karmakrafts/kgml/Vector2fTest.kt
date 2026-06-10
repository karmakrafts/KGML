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

package dev.karmakrafts.kgml

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

class Vector2fTest {

    private fun assertVector2f(expectedX: Float, expectedY: Float, actual: Vector2f, tolerance: Float = 1e-6f) {
        assertEquals(expectedX, actual.x, tolerance, "X component mismatch")
        assertEquals(expectedY, actual.y, tolerance, "Y component mismatch")
    }

    @Test
    fun testConstructors() {
        assertVector2f(1.0f, 2.0f, Vector2f(1.0f, 2.0f))
        assertVector2f(3.0f, 3.0f, Vector2f(3.0f))
        assertVector2f(4.0f, 5.0f, Vector2f(floatArrayOf(4.0f, 5.0f)))
    }

    @Test
    fun testProperties() {
        val v = Vector2f(0.0f, 0.0f)
        v.x = 1.0f
        v.y = 2.0f
        assertEquals(1.0f, v.x)
        assertEquals(2.0f, v.y)
    }

    @Test
    fun testScalarOperators() {
        val v = Vector2f(10.0f, 20.0f)
        assertVector2f(15.0f, 25.0f, v + 5.0f)
        assertVector2f(5.0f, 15.0f, v - 5.0f)
        assertVector2f(20.0f, 40.0f, v * 2.0f)
        assertVector2f(5.0f, 10.0f, v / 2.0f)
        assertVector2f(1.0f, 2.0f, v % 3.0f)
    }

    @Test
    fun testScalarAssignmentOperators() {
        val v = Vector2f(10.0f, 20.0f)
        v += 5.0f
        assertVector2f(15.0f, 25.0f, v)
        v -= 5.0f
        assertVector2f(10.0f, 20.0f, v)
        v *= 2.0f
        assertVector2f(20.0f, 40.0f, v)
        v /= 2.0f
        assertVector2f(10.0f, 20.0f, v)
        v %= 3.0f
        assertVector2f(1.0f, 2.0f, v)
    }

    @Test
    fun testVectorOperators() {
        val v1 = Vector2f(10.0f, 20.0f)
        val v2 = Vector2f(2.0f, 3.0f)
        assertVector2f(12.0f, 23.0f, v1 + v2)
        assertVector2f(8.0f, 17.0f, v1 - v2)
        assertVector2f(20.0f, 60.0f, v1 * v2)
        assertVector2f(5.0f, 6.6666666f, v1 / v2)
        assertVector2f(0.0f, 2.0f, v1 % v2)
    }

    @Test
    fun testVectorAssignmentOperators() {
        val v1 = Vector2f(10.0f, 20.0f)
        val v2 = Vector2f(2.0f, 3.0f)
        v1 += v2
        assertVector2f(12.0f, 23.0f, v1)
        v1 -= v2
        assertVector2f(10.0f, 20.0f, v1)
        v1 *= v2
        assertVector2f(20.0f, 60.0f, v1)
        v1 /= v2
        assertVector2f(10.0f, 20.0f, v1)
        v1 %= v2
        assertVector2f(0.0f, 2.0f, v1)
    }

    @Test
    fun testUnaryOperators() {
        val v = Vector2f(1.0f, -2.0f)
        assertVector2f(-1.0f, 2.0f, -v)
        assertVector2f(1.0f, -2.0f, +v)
    }

    @Test
    fun testLength() {
        val v = Vector2f(3.0f, 4.0f)
        assertEquals(25.0f, v.lengthSq())
        assertEquals(5.0f, v.length())
    }

    @Test
    fun testNormalize() {
        val v = Vector2f(3.0f, 4.0f)
        val n = v.normalized()
        assertVector2f(0.6f, 0.8f, n)
        assertEquals(1.0f, n.length(), 1e-6f)

        v.normalize()
        assertVector2f(0.6f, 0.8f, v)
    }

    @Test
    fun testCopy() {
        val v1 = Vector2f(1.0f, 2.0f)
        val v2 = v1.copy()
        assertVector2f(1.0f, 2.0f, v2)
        assertNotSame(v1.data, v2.data)
    }
}
