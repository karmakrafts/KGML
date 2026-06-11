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
import dev.karmakrafts.kgml.vector.Vector3f
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class Rotation3fTest {
    @Test
    fun `Rotation3f invoke should apply rotation`() {
        val rad = (PI / 2).toFloat()
        val rotation = Rotation3f(rad, rad, rad)
        val matrix = Matrix3x3f.identity
        val result = rotation(matrix)

        val expected = Matrix3x3f.rotationRad(rad, rad, rad)
        assertMatrix3x3Equals(expected, result)
    }

    @Test
    fun `Rotation3f fromDegrees should create correct rotation`() {
        val rotation = Rotation3f.fromDegrees(Vector3f(90f, 90f, 90f))
        val rad = (PI / 2).toFloat()
        assertEquals(rad, rotation.rotation.x, 1e-6f)
        assertEquals(rad, rotation.rotation.y, 1e-6f)
        assertEquals(rad, rotation.rotation.z, 1e-6f)
    }

    private fun assertMatrix3x3Equals(expected: Matrix3x3f, actual: Matrix3x3f) {
        for (i in 0 until 9) {
            assertEquals(expected[i], actual[i], 1e-6f, "At index $i")
        }
    }
}
