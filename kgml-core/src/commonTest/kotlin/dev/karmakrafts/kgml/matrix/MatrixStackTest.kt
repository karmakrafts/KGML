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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MatrixStackTest {

    @Test
    fun `push and current should work`() {
        val stack = MatrixStack(identityProvider = { Matrix2x2f.identity.copy() })
        stack.push()
        assertEquals(Matrix2x2f.identity, stack.current())

        val m1 = Matrix2x2f(1F, 2F, 3F, 4F)
        stack.push(m1)
        assertEquals(m1, stack.current())
    }

    @Test
    fun `swap should replace current matrix`() {
        val stack = MatrixStack(identityProvider = { Matrix2x2f.identity.copy() })
        stack.push()
        val m1 = Matrix2x2f(1F, 2F, 3F, 4F)
        stack.swap(m1)
        assertEquals(m1, stack.current())
    }

    @Test
    fun `pop should remove and return top matrix`() {
        val stack = MatrixStack(identityProvider = { Matrix2x2f.identity.copy() })
        stack.push()
        val m1 = Matrix2x2f(1F, 2F, 3F, 4F)
        stack.push(m1)

        assertEquals(m1, stack.pop())
        assertEquals(Matrix2x2f.identity, stack.current())
        assertEquals(Matrix2x2f.identity, stack.pop())
        assertNull(stack.pop())
    }

    @Test
    fun `reduce should multiply all matrices`() {
        val stack = MatrixStack(identityProvider = { Matrix2x2f.identity.copy() })
        val m1 = Matrix2x2f(1F, 2F, 3F, 4F)
        val m2 = Matrix2x2f(5F, 6F, 7F, 8F)

        stack.push(m1)
        stack.push(m2)

        val expected = m1 * m2
        assertEquals(expected, stack.reduce())
    }

    @Test
    fun `reduce with single element should return that element`() {
        val stack = MatrixStack(identityProvider = { Matrix2x2f.identity.copy() })
        val m1 = Matrix2x2f(1F, 2F, 3F, 4F)
        stack.push(m1)
        assertEquals(m1, stack.reduce())
    }
}
