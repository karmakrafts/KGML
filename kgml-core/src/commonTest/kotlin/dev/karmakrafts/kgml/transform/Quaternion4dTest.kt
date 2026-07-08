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

import dev.karmakrafts.kgml.vector.Vector4d
import kotlin.test.Test
import kotlin.test.assertEquals

class Quaternion4dTest {
    @Test
    fun `constructor should initialize components`() {
        val q = Quaternion4d(1.0, 2.0, 3.0, 4.0)
        assertEquals(1.0, q.x)
        assertEquals(2.0, q.y)
        assertEquals(3.0, q.z)
        assertEquals(4.0, q.w)
    }

    @Test
    fun `asVector4d should return underlying vector`() {
        val v = Vector4d(1.0, 2.0, 3.0, 4.0)
        val q = Quaternion4d(v)
        assertEquals(v, q.asVector4d())
    }

    @Test
    fun `copy should return copy with modified components`() {
        val q = Quaternion4d(1.0, 2.0, 3.0, 4.0)
        assertEquals(Quaternion4d(5.0, 2.0, 3.0, 4.0), q.copy(x = 5.0))
        assertEquals(Quaternion4d(1.0, 5.0, 3.0, 4.0), q.copy(y = 5.0))
        assertEquals(Quaternion4d(1.0, 2.0, 5.0, 4.0), q.copy(z = 5.0))
        assertEquals(Quaternion4d(1.0, 2.0, 3.0, 5.0), q.copy(w = 5.0))
    }

    @Test
    fun `fromAnglesRad should create identity quaternion from zero angles`() {
        val q = Quaternion4d.fromAnglesRad(0.0, 0.0, 0.0)
        assertQuaternionEquals(Quaternion4d(0.0, 0.0, 0.0, 1.0), q)
    }

    @Test
    fun `times operator should multiply quaternions`() {
        val q1 = Quaternion4d(0.0, 0.0, 0.0, 1.0) // Identity
        val q2 = Quaternion4d(1.0, 2.0, 3.0, 4.0)
        assertQuaternionEquals(q2, q1 * q2)
        assertQuaternionEquals(q2, q2 * q1)

        // Rotation 90 deg around X * Rotation 90 deg around Y
        val qX = Quaternion4d.fromAngles(90.0, 0.0, 0.0)
        val qY = Quaternion4d.fromAngles(0.0, 90.0, 0.0)
        val qYX = qY * qX

        // This should be equivalent to fromAngles(90, 90, 0)
        assertQuaternionEquals(Quaternion4d.fromAngles(90.0, 90.0, 0.0), qYX)
    }

    @Test
    fun `times operator with scalar should multiply components`() {
        val q = Quaternion4d(1.0, 2.0, 3.0, 4.0)
        assertQuaternionEquals(Quaternion4d(2.0, 4.0, 6.0, 8.0), q * 2.0)
    }

    @Test
    fun `getAngle methods should return correct angles`() {
        val q = Quaternion4d.fromAngles(30.0, 45.0, 60.0)
        assertEquals(30.0, q.getAngleX(), 1E-5)
        assertEquals(45.0, q.getAngleY(), 1E-5)
        assertEquals(60.0, q.getAngleZ(), 1E-5)
    }

    @Test
    fun `slerp should interpolate between quaternions`() {
        val q1 = Quaternion4d.fromAngles(0.0, 0.0, 0.0)
        val q2 = Quaternion4d.fromAngles(90.0, 0.0, 0.0)

        assertQuaternionEquals(q1, q1.slerp(q2, 0.0))
        assertQuaternionEquals(q2, q1.slerp(q2, 1.0))
        assertQuaternionEquals(Quaternion4d.fromAngles(45.0, 0.0, 0.0), q1.slerp(q2, 0.5))
    }

    @Test
    fun `slerp should use shortest path for negated quaternion`() {
        val q1 = Quaternion4d.fromAngles(0.0, 0.0, 0.0)
        val q2 = Quaternion4d.fromAngles(90.0, 0.0, 0.0)
        val q2Negated = Quaternion4d(-q2.x, -q2.y, -q2.z, -q2.w)

        assertQuaternionEquals(Quaternion4d.fromAngles(45.0, 0.0, 0.0), q1.slerp(q2Negated, 0.5))
    }

    @Test
    fun `slerp should normalize close quaternion interpolation`() {
        val q1 = Quaternion4d.fromAngles(30.0, 45.0, 60.0)
        val q2 = Quaternion4d.fromAngles(30.01, 45.01, 60.01)
        val result = q1.slerp(q2, 0.5)
        val lengthSq = result.x * result.x + result.y * result.y + result.z * result.z + result.w * result.w

        assertEquals(1.0, lengthSq, 1E-5)
        assertQuaternionEquals(q1, q1.slerp(q2, 0.0))
        assertQuaternionEquals(q2, q1.slerp(q2, 1.0))
    }

    @Test
    fun `toRotationMatrix should return correct matrix`() {
        val q = Quaternion4d.fromAngles(90.0, 0.0, 0.0)
        val m3 = q.toRotationMatrix3x3d()
        // Rotation 90 deg around X:
        // 1  0  0
        // 0  0 -1
        // 0  1  0
        assertEquals(1.0, m3.m00, 1E-6)
        assertEquals(0.0, m3.m10, 1E-6)
        assertEquals(0.0, m3.m20, 1E-6)
        assertEquals(0.0, m3.m01, 1E-6)
        assertEquals(0.0, m3.m11, 1E-6)
        assertEquals(1.0, m3.m21, 1E-6)
        assertEquals(0.0, m3.m02, 1E-6)
        assertEquals(-1.0, m3.m12, 1E-6)
        assertEquals(0.0, m3.m22, 1E-6)

        val m4 = q.toRotationMatrix4x4d()
        assertEquals(1.0, m4.m00, 1E-6)
        assertEquals(1.0, m4.m33, 1E-6)
        assertEquals(0.0, m4.m03, 1E-6)
        assertEquals(0.0, m4.m30, 1E-6)
    }

    private fun assertQuaternionEquals(expected: Quaternion4d, actual: Quaternion4d) {
        assertEquals(expected.x, actual.x, 1E-6)
        assertEquals(expected.y, actual.y, 1E-6)
        assertEquals(expected.z, actual.z, 1E-6)
        assertEquals(expected.w, actual.w, 1E-6)
    }
}
