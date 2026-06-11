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
        assertMatrix4x4Equals(Matrix4x4f.rotationX(90f), stack.current())
    }

    @Test
    fun `MatrixStack rotateRad should work`() {
        val stack = createStack()
        val rad = (PI / 2).toFloat()
        stack.rotateRad(0f, rad, 0f)
        assertMatrix4x4Equals(Matrix4x4f.rotationYRad(rad), stack.current())
    }

    @Test
    fun `MatrixStack rotate quaternion should work`() {
        val stack = createStack()
        val q = Quaternion.fromAngles(0f, 0f, 90f)
        stack.rotate(q)
        assertMatrix4x4Equals(Matrix4x4f.rotationZ(90f), stack.current())
    }

    @Test
    fun `MatrixStack skew should work`() {
        val stack = createStack()
        stack.skew(xy = 1f)
        assertMatrix4x4Equals(Matrix4x4f.skew(xy = 1f), stack.current())
    }

    private fun createStack() = MatrixStack(identityProvider = { Matrix4x4f.identity.copy() }).apply { push() }

    private fun assertMatrix4x4Equals(expected: Matrix4x4f, actual: Matrix4x4f) {
        for (i in 0 until 16) {
            assertEquals(expected[i], actual[i], 1e-6f, "At index $i")
        }
    }
}
