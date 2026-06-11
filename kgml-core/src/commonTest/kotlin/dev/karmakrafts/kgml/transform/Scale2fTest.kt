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
import kotlin.test.Test
import kotlin.test.assertEquals

class Scale2fTest {
    @Test
    fun `Scale2f invoke should apply scale`() {
        val scale = Scale2f(2f, 3f)
        val matrix = Matrix3x3f.identity
        val result = scale(matrix)

        val expected = Matrix3x3f.scale(2f, 3f)
        assertMatrix3x3Equals(expected, result)
    }

    private fun assertMatrix3x3Equals(expected: Matrix3x3f, actual: Matrix3x3f) {
        for (i in 0 until 9) {
            assertEquals(expected[i], actual[i], 1e-6f, "At index $i")
        }
    }
}
