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
import dev.karmakrafts.kgml.matrix.MatrixStack
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class MatrixStack3x3fTransformTest {
    @Test
    fun `MatrixStack 3x3 rotation should work`() {
        val stack = MatrixStack(identityProvider = Matrix3x3f::identity).apply { push() }
        stack.rotate(90f, 0f, 0f)
        assertMatrix3x3Equals(Matrix3x3f.rotation(angleX = 90f), stack.current())
    }

    @Test
    fun `MatrixStack 3x3 rotationRad should work`() {
        val stack = MatrixStack(identityProvider = Matrix3x3f::identity).apply { push() }
        val rad = (PI / 2).toFloat()
        stack.rotateRad(0f, rad, 0f)
        assertMatrix3x3Equals(Matrix3x3f.rotationRad(angleY = rad), stack.current())
    }

    @Test
    fun `MatrixStack 3x3 scale should work`() {
        val stack = MatrixStack(identityProvider = Matrix3x3f::identity).apply { push() }
        stack.scale(2f, 3f)
        assertMatrix3x3Equals(Matrix3x3f.scale(2f, 3f), stack.current())
    }

    @Test
    fun `MatrixStack 3x3 translate should work`() {
        val stack = MatrixStack(identityProvider = Matrix3x3f::identity).apply { push() }
        stack.translate(2f, 3f)
        assertMatrix3x3Equals(Matrix3x3f.translation(2f, 3f), stack.current())
    }

    private fun assertMatrix3x3Equals(expected: Matrix3x3f, actual: Matrix3x3f) {
        for (i in 0 until 9) {
            assertEquals(expected[i], actual[i], 1e-6f, "At index $i")
        }
    }
}