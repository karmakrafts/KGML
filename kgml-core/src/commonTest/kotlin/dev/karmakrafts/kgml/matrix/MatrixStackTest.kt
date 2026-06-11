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

package dev.karmakrafts.kgml.matrix

import dev.karmakrafts.kgml.transform.translate
import dev.karmakrafts.kgml.transform.translation
import kotlin.test.Test
import kotlin.test.assertEquals

class MatrixStackTest {
    @Test
    fun `stack should initialize with identity`() {
        val stack = MatrixStack { Matrix4x4f.identity }
        stack.push()
        assertEquals(Matrix4x4f.identity, stack.current())
    }

    @Test
    fun `push and pop should work correctly`() {
        val stack = MatrixStack { Matrix4x4f.identity }
        stack.push(Matrix4x4f.identity)
        val m1 = Matrix4x4f(2F)
        stack.push(m1)
        assertEquals(m1, stack.current())
        assertEquals(m1, stack.pop())
        assertEquals(Matrix4x4f.identity, stack.current())
    }

    @Test
    fun `swap should replace current matrix`() {
        val stack = MatrixStack { Matrix4x4f.identity }
        stack.push(Matrix4x4f.identity)
        val m1 = Matrix4x4f(2F)
        stack.swap(m1)
        assertEquals(m1, stack.current())
    }

    @Test
    fun `reduce should multiply all matrices in stack`() {
        val stack = MatrixStack { Matrix4x4f.identity }
        stack.push(Matrix4x4f.translation(2F, 0F, 0F))
        stack.push(Matrix4x4f.translation(3F, 0F, 0F))
        // T(2) * T(3) = T(5)
        assertEquals(Matrix4x4f.translation(5F, 0F, 0F), stack.reduce())
    }

    @Test
    fun `translate extension should update current matrix`() {
        val stack = MatrixStack { Matrix4x4f.identity }
        stack.push(Matrix4x4f.identity)
        stack.translate(x = 2F)
        assertEquals(Matrix4x4f.translation(2F, 0F, 0F), stack.current())
    }
}
