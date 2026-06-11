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

import dev.karmakrafts.kgml.matrix.Matrix4x4f
import kotlin.test.Test
import kotlin.test.assertEquals

class Translation3fTest {
    @Test
    fun `Translation3f invoke should apply translation`() {
        val translation = Translation3f(2f, 3f, 4f)
        val matrix = Matrix4x4f.identity
        val result = translation(matrix)

        val expected = Matrix4x4f.translation(2f, 3f, 4f)
        assertMatrix4x4Equals(expected, result)
    }

    private fun assertMatrix4x4Equals(expected: Matrix4x4f, actual: Matrix4x4f) {
        for (i in 0 until 16) {
            assertEquals(expected[i], actual[i], 1e-6f, "At index $i")
        }
    }
}
