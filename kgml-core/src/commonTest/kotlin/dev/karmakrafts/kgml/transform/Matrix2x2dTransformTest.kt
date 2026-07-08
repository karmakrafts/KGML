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
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix2x2dTransformTest {
    @Test
    fun `Matrix2x2 rotation should work`() {
        val rad = (PI / 2)
        val m = Matrix2x2d.rotationRad(rad)
        assertEquals(0.0, m.m00, 1e-6)
        assertEquals(-1.0, m.m01, 1e-6)
        assertEquals(1.0, m.m10, 1e-6)
        assertEquals(0.0, m.m11, 1e-6)

        val m2 = Matrix2x2d.rotation(90.0)
        assertEquals(m.m00, m2.m00, 1e-6)
        assertEquals(m.m01, m2.m01, 1e-6)
        assertEquals(m.m10, m2.m10, 1e-6)
        assertEquals(m.m11, m2.m11, 1e-6)
    }

    @Test
    fun `Matrix2x2 scale should work`() {
        val m = Matrix2x2d.scale(2.0, 3.0)
        assertEquals(2.0, m.m00, 1e-6)
        assertEquals(0.0, m.m01, 1e-6)
        assertEquals(0.0, m.m10, 1e-6)
        assertEquals(3.0, m.m11, 1e-6)

        val m2 = Matrix2x2d.scale(4.0)
        assertEquals(4.0, m2.m00, 1e-6)
        assertEquals(4.0, m2.m11, 1e-6)
    }
}
