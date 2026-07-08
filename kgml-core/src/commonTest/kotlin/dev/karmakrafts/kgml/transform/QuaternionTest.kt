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

package dev.karmakrafts.kgml.transform

import dev.karmakrafts.kgml.vector.Vector4f
import kotlin.test.Test
import kotlin.test.assertEquals

class QuaternionTest {
    @Test
    fun `constructor should initialize components`() {
        val q = Quaternion4f(1F, 2F, 3F, 4F)
        assertEquals(1F, q.x)
        assertEquals(2F, q.y)
        assertEquals(3F, q.z)
        assertEquals(4F, q.w)
    }

    @Test
    fun `asVector4f should return underlying vector`() {
        val v = Vector4f(1F, 2F, 3F, 4F)
        val q = Quaternion4f(v)
        assertEquals(v, q.asVector4f())
    }

    @Test
    fun `copy should return copy with modified components`() {
        val q = Quaternion4f(1F, 2F, 3F, 4F)
        assertEquals(Quaternion4f(5F, 2F, 3F, 4F), q.copy(x = 5F))
        assertEquals(Quaternion4f(1F, 5F, 3F, 4F), q.copy(y = 5F))
        assertEquals(Quaternion4f(1F, 2F, 5F, 4F), q.copy(z = 5F))
        assertEquals(Quaternion4f(1F, 2F, 3F, 5F), q.copy(w = 5F))
    }

    @Test
    fun `fromAnglesRad should create identity quaternion from zero angles`() {
        val q = Quaternion4f.fromAnglesRad(0F, 0F, 0F)
        assertQuaternionEquals(Quaternion4f(0F, 0F, 0F, 1F), q)
    }

    @Test
    fun `times operator should multiply quaternions`() {
        val q1 = Quaternion4f(0F, 0F, 0F, 1F) // Identity
        val q2 = Quaternion4f(1F, 2F, 3F, 4F)
        assertQuaternionEquals(q2, q1 * q2)
        assertQuaternionEquals(q2, q2 * q1)

        // Rotation 90 deg around X * Rotation 90 deg around Y
        val qX = Quaternion4f.fromAngles(90F, 0F, 0F)
        val qY = Quaternion4f.fromAngles(0F, 90F, 0F)
        val qYX = qY * qX

        // This should be equivalent to fromAngles(90, 90, 0)
        assertQuaternionEquals(Quaternion4f.fromAngles(90F, 90F, 0F), qYX)
    }

    @Test
    fun `times operator with scalar should multiply components`() {
        val q = Quaternion4f(1F, 2F, 3F, 4F)
        assertQuaternionEquals(Quaternion4f(2F, 4F, 6F, 8F), q * 2F)
    }

    @Test
    fun `getAngle methods should return correct angles`() {
        val q = Quaternion4f.fromAngles(30F, 45F, 60F)
        assertEquals(30F, q.getAngleX(), 1E-5F)
        assertEquals(45F, q.getAngleY(), 1E-5F)
        assertEquals(60F, q.getAngleZ(), 1E-5F)
    }

    @Test
    fun `slerp should interpolate between quaternions`() {
        val q1 = Quaternion4f.fromAngles(0F, 0F, 0F)
        val q2 = Quaternion4f.fromAngles(90F, 0F, 0F)

        assertQuaternionEquals(q1, q1.slerp(q2, 0F))
        assertQuaternionEquals(q2, q1.slerp(q2, 1F))
        assertQuaternionEquals(Quaternion4f.fromAngles(45F, 0F, 0F), q1.slerp(q2, 0.5F))
    }

    @Test
    fun `slerp should use shortest path for negated quaternion`() {
        val q1 = Quaternion4f.fromAngles(0F, 0F, 0F)
        val q2 = Quaternion4f.fromAngles(90F, 0F, 0F)
        val q2Negated = Quaternion4f(-q2.x, -q2.y, -q2.z, -q2.w)

        assertQuaternionEquals(Quaternion4f.fromAngles(45F, 0F, 0F), q1.slerp(q2Negated, 0.5F))
    }

    @Test
    fun `slerp should normalize close quaternion interpolation`() {
        val q1 = Quaternion4f.fromAngles(30F, 45F, 60F)
        val q2 = Quaternion4f.fromAngles(30.01F, 45.01F, 60.01F)
        val result = q1.slerp(q2, 0.5F)
        val lengthSq = result.x * result.x + result.y * result.y + result.z * result.z + result.w * result.w

        assertEquals(1F, lengthSq, 1E-5F)
        assertQuaternionEquals(q1, q1.slerp(q2, 0F))
        assertQuaternionEquals(q2, q1.slerp(q2, 1F))
    }

    @Test
    fun `toRotationMatrix should return correct matrix`() {
        val q = Quaternion4f.fromAngles(90F, 0F, 0F)
        val m3 = q.toRotationMatrix3x3f()
        // Rotation 90 deg around X:
        // 1  0  0
        // 0  0 -1
        // 0  1  0
        assertEquals(1F, m3.m00, 1E-6F)
        assertEquals(0F, m3.m10, 1E-6F)
        assertEquals(0F, m3.m20, 1E-6F)
        assertEquals(0F, m3.m01, 1E-6F)
        assertEquals(0F, m3.m11, 1E-6F)
        assertEquals(1F, m3.m21, 1E-6F)
        assertEquals(0F, m3.m02, 1E-6F)
        assertEquals(-1F, m3.m12, 1E-6F)
        assertEquals(0F, m3.m22, 1E-6F)

        val m4 = q.toRotationMatrix4x4f()
        assertEquals(1F, m4.m00, 1E-6F)
        assertEquals(1F, m4.m33, 1E-6F)
        assertEquals(0F, m4.m03, 1E-6F)
        assertEquals(0F, m4.m30, 1E-6F)
    }

    private fun assertQuaternionEquals(expected: Quaternion4f, actual: Quaternion4f) {
        assertEquals(expected.x, actual.x, 1E-6F)
        assertEquals(expected.y, actual.y, 1E-6F)
        assertEquals(expected.z, actual.z, 1E-6F)
        assertEquals(expected.w, actual.w, 1E-6F)
    }
}
