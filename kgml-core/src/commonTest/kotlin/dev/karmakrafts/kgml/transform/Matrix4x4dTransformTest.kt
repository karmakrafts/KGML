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

import dev.karmakrafts.kgml.matrix.Matrix3x3d
import dev.karmakrafts.kgml.matrix.Matrix4x4d
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix4x4dTransformTest {
    @Test
    fun `Matrix4x4 rotation should work`() {
        val rad = (PI / 2)

        assertMatrix4x4Equals(Matrix3x3d.rotationRad(angleX = rad).extend(), Matrix4x4d.rotationRad(angleX = rad))
        assertMatrix4x4Equals(Matrix3x3d.rotation(angleX = 90.0).extend(), Matrix4x4d.rotation(angleX = 90.0))

        assertMatrix4x4Equals(Matrix3x3d.rotationRad(angleY = rad).extend(), Matrix4x4d.rotationRad(angleY = rad))
        assertMatrix4x4Equals(Matrix3x3d.rotation(angleY = 90.0).extend(), Matrix4x4d.rotation(angleY = 90.0))

        assertMatrix4x4Equals(Matrix3x3d.rotationRad(angleZ = rad).extend(), Matrix4x4d.rotationRad(angleZ = rad))
        assertMatrix4x4Equals(Matrix3x3d.rotation(angleZ = 90.0).extend(), Matrix4x4d.rotation(angleZ = 90.0))

        val mXYZRad = Matrix4x4d.rotationRad(rad, rad, rad)
        val expectedRad =
            Matrix4x4d.rotationRad(angleX = rad) * Matrix4x4d.rotationRad(angleY = rad) * Matrix4x4d.rotationRad(angleZ = rad)
        assertMatrix4x4Equals(expectedRad, mXYZRad)

        val mXYZ = Matrix4x4d.rotation(90.0, 90.0, 90.0)
        val expected =
            Matrix4x4d.rotation(angleX = 90.0) * Matrix4x4d.rotation(angleY = 90.0) * Matrix4x4d.rotation(angleZ = 90.0)
        assertMatrix4x4Equals(expected, mXYZ)
    }

    @Test
    fun `Matrix4x4 translation should work`() {
        val m = Matrix4x4d.translation(1.0, 2.0, 3.0)
        assertEquals(1.0, m.m00, 1e-6)
        assertEquals(1.0, m.m03, 1e-6)
        assertEquals(2.0, m.m13, 1e-6)
        assertEquals(3.0, m.m23, 1e-6)
        assertEquals(1.0, m.m33, 1e-6)
    }

    @Test
    fun `Matrix4x4 scale should work`() {
        val m = Matrix4x4d.scale(2.0, 3.0, 4.0)
        assertEquals(2.0, m.m00, 1e-6)
        assertEquals(3.0, m.m11, 1e-6)
        assertEquals(4.0, m.m22, 1e-6)
        assertEquals(1.0, m.m33, 1e-6)
    }

    @Test
    fun `Matrix4x4 skew should work`() {
        val m = Matrix4x4d.skew(xy = 1.0, xz = 2.0, yx = 3.0, yz = 4.0, zx = 5.0, zy = 6.0)
        assertEquals(1.0, m.m00, 1e-6)
        assertEquals(1.0, m.m01, 1e-6)
        assertEquals(2.0, m.m02, 1e-6)
        assertEquals(3.0, m.m10, 1e-6)
        assertEquals(1.0, m.m11, 1e-6)
        assertEquals(4.0, m.m12, 1e-6)
        assertEquals(5.0, m.m20, 1e-6)
        assertEquals(6.0, m.m21, 1e-6)
        assertEquals(1.0, m.m22, 1e-6)
    }

    @Test
    fun `Matrix4x4 rotation from quaternion should work`() {
        val q = Quaternion4d.fromAngles(0.0, 90.0, 0.0)
        val m = Matrix4x4d.identity * q
        assertMatrix4x4Equals(Matrix4x4d.rotation(angleY = 90.0), m)
    }

    private fun assertMatrix4x4Equals(expected: Matrix4x4d, actual: Matrix4x4d) {
        for (i in 0 until 16) {
            assertEquals(expected[i], actual[i], 1e-6, "At index $i")
        }
    }
}
