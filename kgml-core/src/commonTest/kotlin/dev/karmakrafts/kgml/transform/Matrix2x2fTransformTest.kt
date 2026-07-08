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
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix2x2fTransformTest {
    @Test
    fun `Matrix2x2 rotation should work`() {
        val rad = (PI / 2).toFloat()
        val m = Matrix2x2f.rotationRad(rad)
        assertEquals(0f, m.m00, 1e-6f)
        assertEquals(-1f, m.m01, 1e-6f)
        assertEquals(1f, m.m10, 1e-6f)
        assertEquals(0f, m.m11, 1e-6f)

        val m2 = Matrix2x2f.rotation(90f)
        assertEquals(m.m00, m2.m00, 1e-6f)
        assertEquals(m.m01, m2.m01, 1e-6f)
        assertEquals(m.m10, m2.m10, 1e-6f)
        assertEquals(m.m11, m2.m11, 1e-6f)
    }

    @Test
    fun `Matrix2x2 scale should work`() {
        val m = Matrix2x2f.scale(2f, 3f)
        assertEquals(2f, m.m00, 1e-6f)
        assertEquals(0f, m.m01, 1e-6f)
        assertEquals(0f, m.m10, 1e-6f)
        assertEquals(3f, m.m11, 1e-6f)

        val m2 = Matrix2x2f.scale(4f)
        assertEquals(4f, m2.m00, 1e-6f)
        assertEquals(4f, m2.m11, 1e-6f)
    }
}
