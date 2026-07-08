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

import dev.karmakrafts.kgml.matrix.Matrix2x2d
import dev.karmakrafts.kgml.matrix.MatrixStack
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class MatrixStack2x2dTransformTest {
    @Test
    fun `MatrixStack 2x2 rotation should work`() {
        val stack = MatrixStack(identityProvider = { Matrix2x2d.identity }).apply { push() }
        stack.rotate(90.0)
        assertMatrix2x2Equals(Matrix2x2d.rotation(90.0), stack.current())
    }

    @Test
    fun `MatrixStack 2x2 rotationRad should work`() {
        val stack = MatrixStack(identityProvider = { Matrix2x2d.identity }).apply { push() }
        val rad = (PI / 2)
        stack.rotateRad(rad)
        assertMatrix2x2Equals(Matrix2x2d.rotationRad(rad), stack.current())
    }

    @Test
    fun `MatrixStack 2x2 scale should work`() {
        val stack = MatrixStack(identityProvider = { Matrix2x2d.identity }).apply { push() }
        stack.scale(2.0, 3.0)
        assertMatrix2x2Equals(Matrix2x2d.scale(2.0, 3.0), stack.current())
    }

    private fun assertMatrix2x2Equals(expected: Matrix2x2d, actual: Matrix2x2d) {
        for (i in 0 until 4) {
            assertEquals(expected[i], actual[i], 1e-6, "At index $i")
        }
    }
}