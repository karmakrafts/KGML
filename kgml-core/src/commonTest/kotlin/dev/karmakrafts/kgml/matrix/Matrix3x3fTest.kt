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

import dev.karmakrafts.kgml.transform.rotationXRad
import dev.karmakrafts.kgml.transform.rotationYRad
import dev.karmakrafts.kgml.transform.rotationZRad
import dev.karmakrafts.kgml.transform.translation
import dev.karmakrafts.kgml.vector.Vector3f
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class Matrix3x3fTest {
    @Test
    fun `constructor should initialize to identity`() {
        val matrix = Matrix3x3f()
        assertEquals(1F, matrix.m00)
        assertEquals(0F, matrix.m01)
        assertEquals(0F, matrix.m02)
        assertEquals(0F, matrix.m10)
        assertEquals(1F, matrix.m11)
        assertEquals(0F, matrix.m12)
        assertEquals(0F, matrix.m20)
        assertEquals(0F, matrix.m21)
        assertEquals(1F, matrix.m22)
    }

    @Test
    fun `constructor with value should initialize all components`() {
        val matrix = Matrix3x3f(2F)
        for (i in 0 until 9) {
            assertEquals(2F, matrix[i])
        }
    }

    @Test
    fun `companion object properties should be correct`() {
        assertEquals(Float::class, Matrix3x3f.componentType)
        assertEquals(Float.SIZE_BYTES, Matrix3x3f.componentSize)
        assertEquals(3, Matrix3x3f.rows)
        assertEquals(3, Matrix3x3f.columns)
        assertEquals(Matrix3x3f(), Matrix3x3f.identity)
    }

    @Test
    fun `fromArray should create matrix from array at offset`() {
        val array = FloatArray(10) { it.toFloat() }
        val matrix = Matrix3x3f.fromArray(array, 1)
        assertEquals(Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F), matrix)
    }

    @Test
    fun `type should return Matrix3x3f`() {
        assertEquals(Matrix3x3f, Matrix3x3f().type)
    }

    @Test
    fun `extend should return Matrix4x4f`() {
        val matrix = Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)
        val extended = matrix.extend()
        assertEquals(Matrix4x4f(1F, 2F, 3F, 0F, 4F, 5F, 6F, 0F, 7F, 8F, 9F, 0F, 0F, 0F, 0F, 1F), extended)
    }

    @Test
    fun `transpose should return transposed matrix`() {
        val matrix = Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)
        assertEquals(Matrix3x3f(1F, 4F, 7F, 2F, 5F, 8F, 3F, 6F, 9F), matrix.transpose())
    }

    @Test
    fun `times operator with matrix should multiply matrices`() {
        val m1 = Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)
        val m2 = Matrix3x3f(9F, 8F, 7F, 6F, 5F, 4F, 3F, 2F, 1F)
        // [1 2 3] [9 8 7]   [1*9+2*6+3*3 1*8+2*5+3*2 1*7+2*4+3*1]   [30 24 18]
        // [4 5 6] [6 5 4] = [4*9+5*6+6*3 4*8+5*5+6*2 4*7+5*4+6*1] = [84 69 54]
        // [7 8 9] [3 2 1]   [7*9+8*6+9*3 7*8+8*5+9*2 7*7+8*4+9*1]   [138 114 90]
        assertEquals(Matrix3x3f(30F, 24F, 18F, 84F, 69F, 54F, 138F, 114F, 90F), m1 * m2)

        val identity = Matrix3x3f.identity
        assertEquals(m1, m1 * identity)
        assertEquals(m1, identity * m1)
    }

    @Test
    fun `times operator with identity should return existing matrix`() {
        val matrix = Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, MatrixProperties.AFFINE)
        val identity = Matrix3x3f.identity
        assertSame(matrix, matrix * identity)
        assertSame(matrix, identity * matrix)
        assertEquals(MatrixProperties.AFFINE, (matrix * identity).properties)
        assertEquals(MatrixProperties.AFFINE, (identity * matrix).properties)
    }

    @Test
    fun `times operator with vector should multiply matrix by vector`() {
        val matrix = Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)
        val vector = Vector3f(10F, 11F, 12F)
        // [1 2 3] [10] = [1*10+2*11+3*12] = [10+22+36] = [68]
        // [4 5 6] [11] = [4*10+5*11+6*12] = [40+55+72] = [167]
        // [7 8 9] [12] = [7*10+8*11+9*12] = [70+88+108] = [266]
        assertEquals(Vector3f(68F, 167F, 266F), matrix * vector)
    }

    @Test
    fun `get should return component by index`() {
        val matrix = Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)
        for (i in 0 until 9) {
            assertEquals((i + 1).toFloat(), matrix[i])
        }
        assertFailsWith<IllegalArgumentException> { matrix[9] }
    }

    @Test
    fun `get should return component by MatrixComponent`() {
        val matrix = Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)
        assertEquals(1F, matrix[MatrixComponent.M00])
        assertEquals(2F, matrix[MatrixComponent.M01])
        assertEquals(3F, matrix[MatrixComponent.M02])
        assertEquals(4F, matrix[MatrixComponent.M10])
        assertEquals(5F, matrix[MatrixComponent.M11])
        assertEquals(6F, matrix[MatrixComponent.M12])
        assertEquals(7F, matrix[MatrixComponent.M20])
        assertEquals(8F, matrix[MatrixComponent.M21])
        assertEquals(9F, matrix[MatrixComponent.M22])
        assertFailsWith<IllegalArgumentException> { matrix[MatrixComponent.M33] }
    }

    @Test
    fun `toFloatArray should return components as float array`() {
        val matrix = Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)
        assertContentEquals(floatArrayOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F), matrix.toFloatArray())
    }

    @Test
    fun `rotationZRad should return rotation matrix around Z`() {
        val angle = PI.toFloat() / 2F
        val matrix = Matrix3x3f.rotationZRad(angle)
        // [0 -1 0]
        // [1  0 0]
        // [0  0 1]
        assertMatrixEquals(Matrix3x3f(0F, -1F, 0F, 1F, 0F, 0F, 0F, 0F, 1F), matrix)
    }

    @Test
    fun `rotationXRad should return rotation matrix around X`() {
        val angle = PI.toFloat() / 2F
        val matrix = Matrix3x3f.rotationXRad(angle)
        // [1 0  0]
        // [0 0 -1]
        // [0 1  0]
        assertMatrixEquals(Matrix3x3f(1F, 0F, 0F, 0F, 0F, -1F, 0F, 1F, 0F), matrix)
    }

    @Test
    fun `rotationYRad should return rotation matrix around Y`() {
        val angle = PI.toFloat() / 2F
        val matrix = Matrix3x3f.rotationYRad(angle)
        // [ 0 0 1]
        // [ 0 1 0]
        // [-1 0 0]
        assertMatrixEquals(Matrix3x3f(0F, 0F, 1F, 0F, 1F, 0F, -1F, 0F, 0F), matrix)
    }

    @Test
    fun `translation should return translation matrix`() {
        val matrix = Matrix3x3f.translation(2F, 3F)
        assertEquals(Matrix3x3f(1F, 0F, 2F, 0F, 1F, 3F, 0F, 0F, 1F), matrix)
    }

    @Test
    fun `times operator with incompatible matrix should throw exception`() {
        val m1 = Matrix3x3f()
        val m2 = Matrix2x2f()
        assertFailsWith<IllegalArgumentException> { m1.times(m2) }
    }

    @Test
    fun `times operator with incompatible vector should throw exception`() {
        val matrix = Matrix3x3f()
        val vector = dev.karmakrafts.kgml.vector.Vector2f()
        assertFailsWith<IllegalArgumentException> { matrix.times(vector) }
    }

    private fun assertMatrixEquals(expected: Matrix3x3f, actual: Matrix3x3f) {
        for (i in 0 until 9) {
            assertEquals(expected[i], actual[i], 1E-6F)
        }
    }
}
