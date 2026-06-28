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
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix3x3TransformTest {
    @Test
    fun `Matrix3x3 rotation should work`() {
        val rad = (PI / 2).toFloat()

        // Z-axis rotation
        assertMatrix3x3Equals(Matrix3x3f.rotationRad(angleZ = rad), Matrix3x3f.rotation(angleZ = 90f))
        val mZ = Matrix3x3f.rotation(angleZ = 90f)
        assertEquals(0f, mZ.m00, 1e-6f)
        assertEquals(-1f, mZ.m01, 1e-6f)
        assertEquals(0f, mZ.m02, 1e-6f)
        assertEquals(1f, mZ.m10, 1e-6f)
        assertEquals(0f, mZ.m11, 1e-6f)
        assertEquals(0f, mZ.m12, 1e-6f)
        assertEquals(0f, mZ.m20, 1e-6f)
        assertEquals(0f, mZ.m21, 1e-6f)
        assertEquals(1f, mZ.m22, 1e-6f)

        // X-axis rotation
        assertMatrix3x3Equals(Matrix3x3f.rotationRad(angleX = rad), Matrix3x3f.rotation(angleX = 90f))
        val mX = Matrix3x3f.rotation(angleX = 90f)
        assertEquals(1f, mX.m00, 1e-6f)
        assertEquals(0f, mX.m11, 1e-6f)
        assertEquals(-1f, mX.m12, 1e-6f)
        assertEquals(1f, mX.m21, 1e-6f)
        assertEquals(0f, mX.m22, 1e-6f)

        // Y-axis rotation
        assertMatrix3x3Equals(Matrix3x3f.rotationRad(angleY = rad), Matrix3x3f.rotation(angleY = 90f))
        val mY = Matrix3x3f.rotation(angleY = 90f)
        assertEquals(0f, mY.m00, 1e-6f)
        assertEquals(1f, mY.m02, 1e-6f)
        assertEquals(-1f, mY.m20, 1e-6f)
        assertEquals(0f, mY.m22, 1e-6f)

        // Combined rotation
        val mXYZRad = Matrix3x3f.rotationRad(rad, rad, rad)
        val expectedRad =
            Matrix3x3f.rotationRad(angleX = rad) * Matrix3x3f.rotationRad(angleY = rad) * Matrix3x3f.rotationRad(angleZ = rad)
        assertMatrix3x3Equals(expectedRad, mXYZRad)

        val mXYZ = Matrix3x3f.rotation(90f, 90f, 90f)
        val expected =
            Matrix3x3f.rotation(angleX = 90f) * Matrix3x3f.rotation(angleY = 90f) * Matrix3x3f.rotation(angleZ = 90f)
        assertMatrix3x3Equals(expected, mXYZ)
    }

    @Test
    fun `Matrix3x3 translation should work`() {
        val m = Matrix3x3f.translation(2f, 3f)
        assertEquals(1f, m.m00, 1e-6f)
        assertEquals(0f, m.m01, 1e-6f)
        assertEquals(2f, m.m02, 1e-6f)
        assertEquals(0f, m.m10, 1e-6f)
        assertEquals(1f, m.m11, 1e-6f)
        assertEquals(3f, m.m12, 1e-6f)
        assertEquals(0f, m.m20, 1e-6f)
        assertEquals(0f, m.m21, 1e-6f)
        assertEquals(1f, m.m22, 1e-6f)
    }

    @Test
    fun `Matrix3x3 rotation from quaternion should work`() {
        val q = Quaternion.fromAngles(90f, 0f, 0f)
        val m = Matrix3x3f.identity * q
        assertMatrix3x3Equals(Matrix3x3f.rotation(angleX = 90f), m)
    }

    private fun assertMatrix3x3Equals(expected: Matrix3x3f, actual: Matrix3x3f) {
        for (i in 0 until 9) {
            assertEquals(expected[i], actual[i], 1e-6f, "At index $i")
        }
    }
}
