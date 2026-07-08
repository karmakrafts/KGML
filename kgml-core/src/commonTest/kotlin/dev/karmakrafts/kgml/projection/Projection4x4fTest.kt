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

package dev.karmakrafts.kgml.projection

import dev.karmakrafts.kgml.matrix.Matrix4x4f
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class Projection4x4fTest {
    @Test
    fun `orthographic should return orthographic projection matrix`() {
        val matrix = Matrix4x4f.orthographic(-1F, 1F, -1F, 1F, 1F, 10F)
        // rml = 2, tmb = 2, fmn = 9
        // 2/2=1, 2/2=1, -2/9
        // -(1-1)/2 = 0, -(1-1)/2 = 0, -(10+1)/9 = -11/9
        assertMatrixEquals(
            Matrix4x4f(
                1F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, -2F / 9F, -11F / 9F, 0F, 0F, 0F, 1F
            ), matrix
        )
    }

    @Test
    fun `perspective should return perspective projection matrix`() {
        val matrix = Matrix4x4f.perspective(PI.toFloat() / 2F, 1F, 1F, 10F)
        // f = 1/tan(pi/4) = 1
        // nmf = 1-10 = -9
        // (10+1)/-9 = -11/9
        // (2*10*1)/-9 = -20/9
        assertMatrixEquals(
            Matrix4x4f(
                1F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, -11F / 9F, -20F / 9F, 0F, 0F, -1F, 0F
            ), matrix
        )
    }

    @Test
    fun `orthographic with asymmetrical bounds should return correct matrix`() {
        val matrix = Matrix4x4f.orthographic(0F, 2F, 0F, 4F, 1F, 5F)
        // rml = 2, tmb = 4, fmn = 4
        // 2/2=1, 2/4=0.5, -2/4=-0.5
        // -(2+0)/2 = -1, -(4+0)/4 = -1, -(5+1)/4 = -1.5
        assertMatrixEquals(
            Matrix4x4f(
                1F, 0F, 0F, -1F, 0F, 0.5F, 0F, -1F, 0F, 0F, -0.5F, -1.5F, 0F, 0F, 0F, 1F
            ), matrix
        )
    }

    @Test
    fun `perspective with different aspect ratio should return correct matrix`() {
        val matrix = Matrix4x4f.perspective(PI.toFloat() / 2F, 2F, 1F, 11F)
        // f = 1
        // nmf = 1-11 = -10
        // f/aspect = 1/2 = 0.5
        // (11+1)/-10 = -1.2
        // (2*11*1)/-10 = -2.2
        assertMatrixEquals(
            Matrix4x4f(
                0.5F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, -1.2F, -2.2F, 0F, 0F, -1F, 0F
            ), matrix
        )
    }

    private fun assertMatrixEquals(expected: Matrix4x4f, actual: Matrix4x4f) {
        for (i in 0 until 16) {
            assertEquals(expected[i], actual[i], 1E-6F)
        }
    }
}
