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
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix3x3dTransformTest {
    @Test
    fun `Matrix3x3 rotation should work`() {
        val rad = (PI / 2)

        // Z-axis rotation
        assertMatrix3x3Equals(Matrix3x3d.rotationRad(angleZ = rad), Matrix3x3d.rotation(angleZ = 90.0))
        val mZ = Matrix3x3d.rotation(angleZ = 90.0)
        assertEquals(0.0, mZ.m00, 1e-6)
        assertEquals(-1.0, mZ.m01, 1e-6)
        assertEquals(0.0, mZ.m02, 1e-6)
        assertEquals(1.0, mZ.m10, 1e-6)
        assertEquals(0.0, mZ.m11, 1e-6)
        assertEquals(0.0, mZ.m12, 1e-6)
        assertEquals(0.0, mZ.m20, 1e-6)
        assertEquals(0.0, mZ.m21, 1e-6)
        assertEquals(1.0, mZ.m22, 1e-6)

        // X-axis rotation
        assertMatrix3x3Equals(Matrix3x3d.rotationRad(angleX = rad), Matrix3x3d.rotation(angleX = 90.0))
        val mX = Matrix3x3d.rotation(angleX = 90.0)
        assertEquals(1.0, mX.m00, 1e-6)
        assertEquals(0.0, mX.m11, 1e-6)
        assertEquals(-1.0, mX.m12, 1e-6)
        assertEquals(1.0, mX.m21, 1e-6)
        assertEquals(0.0, mX.m22, 1e-6)

        // Y-axis rotation
        assertMatrix3x3Equals(Matrix3x3d.rotationRad(angleY = rad), Matrix3x3d.rotation(angleY = 90.0))
        val mY = Matrix3x3d.rotation(angleY = 90.0)
        assertEquals(0.0, mY.m00, 1e-6)
        assertEquals(1.0, mY.m02, 1e-6)
        assertEquals(-1.0, mY.m20, 1e-6)
        assertEquals(0.0, mY.m22, 1e-6)

        // Combined rotation
        val mXYZRad = Matrix3x3d.rotationRad(rad, rad, rad)
        val expectedRad =
            Matrix3x3d.rotationRad(angleX = rad) * Matrix3x3d.rotationRad(angleY = rad) * Matrix3x3d.rotationRad(angleZ = rad)
        assertMatrix3x3Equals(expectedRad, mXYZRad)

        val mXYZ = Matrix3x3d.rotation(90.0, 90.0, 90.0)
        val expected =
            Matrix3x3d.rotation(angleX = 90.0) * Matrix3x3d.rotation(angleY = 90.0) * Matrix3x3d.rotation(angleZ = 90.0)
        assertMatrix3x3Equals(expected, mXYZ)
    }

    @Test
    fun `Matrix3x3 translation should work`() {
        val m = Matrix3x3d.translation(2.0, 3.0)
        assertEquals(1.0, m.m00, 1e-6)
        assertEquals(0.0, m.m01, 1e-6)
        assertEquals(2.0, m.m02, 1e-6)
        assertEquals(0.0, m.m10, 1e-6)
        assertEquals(1.0, m.m11, 1e-6)
        assertEquals(3.0, m.m12, 1e-6)
        assertEquals(0.0, m.m20, 1e-6)
        assertEquals(0.0, m.m21, 1e-6)
        assertEquals(1.0, m.m22, 1e-6)
    }

    @Test
    fun `Matrix3x3 rotation from quaternion should work`() {
        val q = Quaternion4d.fromAngles(90.0, 0.0, 0.0)
        val m = Matrix3x3d.identity * q
        assertMatrix3x3Equals(Matrix3x3d.rotation(angleX = 90.0), m)
    }

    private fun assertMatrix3x3Equals(expected: Matrix3x3d, actual: Matrix3x3d) {
        for (i in 0 until 9) {
            assertEquals(expected[i], actual[i], 1e-6, "At index $i")
        }
    }
}
