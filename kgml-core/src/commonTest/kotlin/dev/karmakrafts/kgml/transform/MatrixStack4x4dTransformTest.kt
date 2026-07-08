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
import dev.karmakrafts.kgml.matrix.MatrixStack
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class MatrixStack4x4dTransformTest {
    @Test
    fun `MatrixStack translate should work`() {
        val stack = createStack()
        stack.translate(1.0, 2.0, 3.0)
        assertMatrix4x4Equals(Matrix4x4d.translation(1.0, 2.0, 3.0), stack.current())
    }

    @Test
    fun `MatrixStack scale should work`() {
        val stack = createStack()
        stack.scale(2.0, 2.0, 2.0)
        assertMatrix4x4Equals(Matrix4x4d.scale(2.0, 2.0, 2.0), stack.current())
    }

    @Test
    fun `MatrixStack rotate should work`() {
        val stack = createStack()
        stack.rotate(90.0, 0.0, 0.0)
        assertMatrix4x4Equals(Matrix4x4d.rotation(angleX = 90.0), stack.current())
    }

    @Test
    fun `MatrixStack rotateRad should work`() {
        val stack = createStack()
        val rad = (PI / 2)
        stack.rotateRad(0.0, rad, 0.0)
        assertMatrix4x4Equals(Matrix4x4d.rotationRad(angleY = rad), stack.current())
    }

    @Test
    fun `MatrixStack rotate quaternion should work`() {
        val stack = createStack()
        val q = Quaternion4d.fromAngles(0.0, 0.0, 90.0)
        stack.rotate(q)
        assertMatrix4x4Equals(Matrix4x4d.rotation(angleZ = 90.0), stack.current())
    }

    @Test
    fun `MatrixStack skew should work`() {
        val stack = createStack()
        stack.skew(xy = 1.0)
        assertMatrix4x4Equals(Matrix4x4d.skew(xy = 1.0), stack.current())
    }

    private fun createStack() = MatrixStack(identityProvider = Matrix4x4d::identity).apply { push() }

    private fun assertMatrix4x4Equals(expected: Matrix4x4d, actual: Matrix4x4d) {
        for (i in 0 until 16) {
            assertEquals(expected[i], actual[i], 1e-6, "At index $i")
        }
    }
}
