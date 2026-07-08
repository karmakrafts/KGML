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

import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.matrix.Matrix4x4f
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix4x4TransformTest {
    @Test
    fun `Matrix4x4 rotation should work`() {
        val rad = (PI / 2).toFloat()

        assertMatrix4x4Equals(Matrix3x3f.rotationRad(angleX = rad).extend(), Matrix4x4f.rotationRad(angleX = rad))
        assertMatrix4x4Equals(Matrix3x3f.rotation(angleX = 90f).extend(), Matrix4x4f.rotation(angleX = 90f))

        assertMatrix4x4Equals(Matrix3x3f.rotationRad(angleY = rad).extend(), Matrix4x4f.rotationRad(angleY = rad))
        assertMatrix4x4Equals(Matrix3x3f.rotation(angleY = 90f).extend(), Matrix4x4f.rotation(angleY = 90f))

        assertMatrix4x4Equals(Matrix3x3f.rotationRad(angleZ = rad).extend(), Matrix4x4f.rotationRad(angleZ = rad))
        assertMatrix4x4Equals(Matrix3x3f.rotation(angleZ = 90f).extend(), Matrix4x4f.rotation(angleZ = 90f))

        val mXYZRad = Matrix4x4f.rotationRad(rad, rad, rad)
        val expectedRad =
            Matrix4x4f.rotationRad(angleX = rad) * Matrix4x4f.rotationRad(angleY = rad) * Matrix4x4f.rotationRad(angleZ = rad)
        assertMatrix4x4Equals(expectedRad, mXYZRad)

        val mXYZ = Matrix4x4f.rotation(90f, 90f, 90f)
        val expected =
            Matrix4x4f.rotation(angleX = 90f) * Matrix4x4f.rotation(angleY = 90f) * Matrix4x4f.rotation(angleZ = 90f)
        assertMatrix4x4Equals(expected, mXYZ)
    }

    @Test
    fun `Matrix4x4 translation should work`() {
        val m = Matrix4x4f.translation(1f, 2f, 3f)
        assertEquals(1f, m.m00, 1e-6f)
        assertEquals(1f, m.m03, 1e-6f)
        assertEquals(2f, m.m13, 1e-6f)
        assertEquals(3f, m.m23, 1e-6f)
        assertEquals(1f, m.m33, 1e-6f)
    }

    @Test
    fun `Matrix4x4 scale should work`() {
        val m = Matrix4x4f.scale(2f, 3f, 4f)
        assertEquals(2f, m.m00, 1e-6f)
        assertEquals(3f, m.m11, 1e-6f)
        assertEquals(4f, m.m22, 1e-6f)
        assertEquals(1f, m.m33, 1e-6f)
    }

    @Test
    fun `Matrix4x4 skew should work`() {
        val m = Matrix4x4f.skew(xy = 1f, xz = 2f, yx = 3f, yz = 4f, zx = 5f, zy = 6f)
        assertEquals(1f, m.m00, 1e-6f)
        assertEquals(1f, m.m01, 1e-6f)
        assertEquals(2f, m.m02, 1e-6f)
        assertEquals(3f, m.m10, 1e-6f)
        assertEquals(1f, m.m11, 1e-6f)
        assertEquals(4f, m.m12, 1e-6f)
        assertEquals(5f, m.m20, 1e-6f)
        assertEquals(6f, m.m21, 1e-6f)
        assertEquals(1f, m.m22, 1e-6f)
    }

    @Test
    fun `Matrix4x4 rotation from quaternion should work`() {
        val q = Quaternion4f.fromAngles(0f, 90f, 0f)
        val m = Matrix4x4f.identity * q
        assertMatrix4x4Equals(Matrix4x4f.rotation(angleY = 90f), m)
    }

    private fun assertMatrix4x4Equals(expected: Matrix4x4f, actual: Matrix4x4f) {
        for (i in 0 until 16) {
            assertEquals(expected[i], actual[i], 1e-6f, "At index $i")
        }
    }
}
