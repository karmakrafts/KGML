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

import dev.karmakrafts.kgml.matrix.Matrix4x4d
import kotlin.test.Test
import kotlin.test.assertEquals

class Scale3dTest {
    @Test
    fun `Scale3d invoke should apply scale`() {
        val scale = Scale3d(2.0, 3.0, 4.0)
        val matrix = Matrix4x4d.identity
        val result = scale(matrix)

        val expected = Matrix4x4d.scale(2.0, 3.0, 4.0)
        assertMatrix4x4Equals(expected, result)
    }

    private fun assertMatrix4x4Equals(expected: Matrix4x4d, actual: Matrix4x4d) {
        for (i in 0 until 16) {
            assertEquals(expected[i], actual[i], 1e-6, "At index $i")
        }
    }
}
