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

import dev.karmakrafts.kgml.matrix.Matrix2x2f
import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.matrix.Matrix4x4f
import dev.karmakrafts.kgml.matrix.MatrixStack
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class MatrixStackTransformTest {

    @Test
    fun `MatrixStack translate should work`() {
        val stack = createStack()
        stack.translate(1f, 2f, 3f)
        assertMatrix4x4Equals(Matrix4x4f.translation(1f, 2f, 3f), stack.current())
    }

    @Test
    fun `MatrixStack scale should work`() {
        val stack = createStack()
        stack.scale(2f, 2f, 2f)
        assertMatrix4x4Equals(Matrix4x4f.scale(2f, 2f, 2f), stack.current())
    }

    @Test
    fun `MatrixStack rotate should work`() {
        val stack = createStack()
        stack.rotate(90f, 0f, 0f)
        assertMatrix4x4Equals(Matrix4x4f.rotation(angleX = 90f), stack.current())
    }

    @Test
    fun `MatrixStack rotateRad should work`() {
        val stack = createStack()
        val rad = (PI / 2).toFloat()
        stack.rotateRad(0f, rad, 0f)
        assertMatrix4x4Equals(Matrix4x4f.rotationRad(angleY = rad), stack.current())
    }

    @Test
    fun `MatrixStack rotate quaternion should work`() {
        val stack = createStack()
        val q = Quaternion4f.fromAngles(0f, 0f, 90f)
        stack.rotate(q)
        assertMatrix4x4Equals(Matrix4x4f.rotation(angleZ = 90f), stack.current())
    }

    @Test
    fun `MatrixStack skew should work`() {
        val stack = createStack()
        stack.skew(xy = 1f)
        assertMatrix4x4Equals(Matrix4x4f.skew(xy = 1f), stack.current())
    }

    @Test
    fun `MatrixStack 2x2 rotation should work`() {
        val stack = MatrixStack(identityProvider = { Matrix2x2f.identity }).apply { push() }
        stack.rotate(90f)
        assertMatrix2x2Equals(Matrix2x2f.rotation(90f), stack.current())
    }

    @Test
    fun `MatrixStack 2x2 rotationRad should work`() {
        val stack = MatrixStack(identityProvider = { Matrix2x2f.identity }).apply { push() }
        val rad = (PI / 2).toFloat()
        stack.rotateRad(rad)
        assertMatrix2x2Equals(Matrix2x2f.rotationRad(rad), stack.current())
    }

    @Test
    fun `MatrixStack 2x2 scale should work`() {
        val stack = MatrixStack(identityProvider = { Matrix2x2f.identity }).apply { push() }
        stack.scale(2f, 3f)
        assertMatrix2x2Equals(Matrix2x2f.scale(2f, 3f), stack.current())
    }

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

    private fun createStack() = MatrixStack(identityProvider = Matrix4x4f::identity).apply { push() }

    private fun assertMatrix2x2Equals(expected: Matrix2x2f, actual: Matrix2x2f) {
        for (i in 0 until 4) {
            assertEquals(expected[i], actual[i], 1e-6f, "At index $i")
        }
    }

    private fun assertMatrix3x3Equals(expected: Matrix3x3f, actual: Matrix3x3f) {
        for (i in 0 until 9) {
            assertEquals(expected[i], actual[i], 1e-6f, "At index $i")
        }
    }

    private fun assertMatrix4x4Equals(expected: Matrix4x4f, actual: Matrix4x4f) {
        for (i in 0 until 16) {
            assertEquals(expected[i], actual[i], 1e-6f, "At index $i")
        }
    }
}
