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

import dev.karmakrafts.kgml.transform.rotation
import dev.karmakrafts.kgml.transform.rotationRad
import dev.karmakrafts.kgml.transform.scale
import dev.karmakrafts.kgml.vector.Vector2f
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

class Matrix2x2fTest {
    @Test
    fun `constructor should initialize to identity`() {
        val matrix = Matrix2x2f()
        assertEquals(1F, matrix.m00)
        assertEquals(0F, matrix.m01)
        assertEquals(0F, matrix.m10)
        assertEquals(1F, matrix.m11)
    }

    @Test
    fun `constructor with value should initialize all components`() {
        val matrix = Matrix2x2f(2F)
        assertEquals(2F, matrix.m00)
        assertEquals(2F, matrix.m01)
        assertEquals(2F, matrix.m10)
        assertEquals(2F, matrix.m11)
    }

    @Test
    fun `companion object properties should be correct`() {
        assertEquals(Float::class, Matrix2x2f.componentType)
        assertEquals(Float.SIZE_BYTES, Matrix2x2f.componentSize)
        assertEquals(2, Matrix2x2f.rows)
        assertEquals(2, Matrix2x2f.columns)
        assertContentEquals(
            arrayOf(MatrixComponent.M00, MatrixComponent.M01, MatrixComponent.M10, MatrixComponent.M11),
            Matrix2x2f.components
        )
        assertEquals(Matrix2x2f(), Matrix2x2f.identity)
    }

    @Test
    fun `fromArray should create matrix from array at offset`() {
        val array = floatArrayOf(0F, 1F, 2F, 3F, 4F, 5F)
        val matrix = Matrix2x2f.fromArray(array, 1)
        assertEquals(Matrix2x2f(1F, 2F, 3F, 4F), matrix)
    }

    @Test
    fun `fromArray should create matrix from array at offset with properties`() {
        val array = floatArrayOf(0F, 1F, 2F, 3F, 4F, 5F)
        val matrix = Matrix2x2f.fromArray(array, 1, MatrixProperties.AFFINE)
        assertEquals(Matrix2x2f(1F, 2F, 3F, 4F, MatrixProperties.AFFINE), matrix)
        assertEquals(MatrixProperties.AFFINE, matrix.properties)
    }

    @Test
    fun `fromRows should create matrix from vectors`() {
        val row0 = Vector2f(1F, 2F)
        val row1 = Vector2f(3F, 4F)
        val matrix = Matrix2x2f.fromRows(row0, row1, MatrixProperties.AFFINE)
        assertEquals(Matrix2x2f(1F, 2F, 3F, 4F, MatrixProperties.AFFINE), matrix)
    }

    @Test
    fun `fromColumns should create matrix from vectors`() {
        val column0 = Vector2f(1F, 3F)
        val column1 = Vector2f(2F, 4F)
        val matrix = Matrix2x2f.fromColumns(column0, column1, MatrixProperties.AFFINE)
        assertEquals(Matrix2x2f(1F, 2F, 3F, 4F, MatrixProperties.AFFINE), matrix)
    }

    @Test
    fun `row accessors should return correct vectors`() {
        val matrix = Matrix2x2f(1F, 2F, 3F, 4F)
        assertEquals(Vector2f(1F, 2F), matrix.row0)
        assertEquals(Vector2f(3F, 4F), matrix.row1)
    }

    @Test
    fun `column accessors should return correct vectors`() {
        val matrix = Matrix2x2f(1F, 2F, 3F, 4F)
        assertEquals(Vector2f(1F, 3F), matrix.column0)
        assertEquals(Vector2f(2F, 4F), matrix.column1)
    }

    @Test
    fun `type should return Matrix2x2f`() {
        assertEquals(Matrix2x2f, Matrix2x2f().type)
    }

    @Test
    fun `extend should return Matrix3x3f`() {
        val matrix = Matrix2x2f(1F, 2F, 3F, 4F)
        val extended = matrix.extend()
        assertEquals(Matrix3x3f(1F, 2F, 0F, 3F, 4F, 0F, 0F, 0F, 1F), extended)
    }

    @Test
    fun `transpose should return transposed matrix`() {
        val matrix = Matrix2x2f(1F, 2F, 3F, 4F)
        assertEquals(Matrix2x2f(1F, 3F, 2F, 4F), matrix.transpose())
    }

    @Test
    fun `times operator with matrix should multiply matrices`() {
        val m1 = Matrix2x2f(1F, 2F, 3F, 4F)
        val m2 = Matrix2x2f(5F, 6F, 7F, 8F)
        // [1 2] [5 6] = [1*5+2*7 1*6+2*8] = [19 22]
        // [3 4] [7 8]   [3*5+4*7 3*6+4*8]   [43 50]
        assertEquals(Matrix2x2f(19F, 22F, 43F, 50F), m1 * m2)

        val identity = Matrix2x2f.identity
        assertEquals(m1, m1 * identity, "m1 * identity should be m1")
        assertEquals(m1, identity * m1, "identity * m1 should be m1")
    }

    @Test
    fun `times operator with identity should return existing matrix`() {
        val matrix = Matrix2x2f(1F, 2F, 3F, 4F, MatrixProperties.AFFINE)
        val identity = Matrix2x2f.identity
        assertSame(matrix, matrix * identity)
        assertSame(matrix, identity * matrix)
        assertEquals(MatrixProperties.AFFINE, (matrix * identity).properties)
        assertEquals(MatrixProperties.AFFINE, (identity * matrix).properties)
    }

    @Test
    fun `times operator with diagonal matrices should multiply scales`() {
        val result = Matrix2x2f.scale(2F, 3F) * Matrix2x2f.scale(5F, 7F)
        assertEquals(Matrix2x2f(10F, 0F, 0F, 21F), result)
        assertTrue(result.properties.isAffine)
        assertTrue(result.properties.isLinear)
        assertTrue(result.properties.isDiagonal)
    }

    @Test
    fun `times operator with diagonal operand should preserve linear result only`() {
        val generic = Matrix2x2f(1F, 2F, 3F, 4F, MatrixProperties.AFFINE or MatrixProperties.LINEAR)
        val result = generic * Matrix2x2f.scale(5F, 7F)
        assertEquals(Matrix2x2f(5F, 14F, 15F, 28F), result)
        assertTrue(result.properties.isAffine)
        assertTrue(result.properties.isLinear)
        assertFalse(result.properties.isDiagonal)
        assertFalse(result.properties.isRotation)
    }

    @Test
    fun `times operator with rotation matrices should preserve rotation`() {
        val result = Matrix2x2f.rotation(30F) * Matrix2x2f.rotation(60F)
        assertMatrixEquals(Matrix2x2f.rotation(90F), result)
        assertTrue(result.properties.isAffine)
        assertTrue(result.properties.isLinear)
        assertTrue(result.properties.isRotation)
    }

    @Test
    fun `times operator with vector should multiply matrix by vector`() {
        val matrix = Matrix2x2f(1F, 2F, 3F, 4F)
        val vector = Vector2f(5F, 6F)
        // [1 2] [5] = [1*5+2*6] = [17]
        // [3 4] [6]   [3*5+4*6]   [39]
        assertEquals(Vector2f(17F, 39F), matrix * vector)
    }

    @Test
    fun `get should return component by index`() {
        val matrix = Matrix2x2f(1F, 2F, 3F, 4F)
        assertEquals(1F, matrix[0])
        assertEquals(2F, matrix[1])
        assertEquals(3F, matrix[2])
        assertEquals(4F, matrix[3])
        assertFailsWith<IllegalArgumentException> { matrix[4] }
    }

    @Test
    fun `get should return component by MatrixComponent`() {
        val matrix = Matrix2x2f(1F, 2F, 3F, 4F)
        assertEquals(1F, matrix[MatrixComponent.M00])
        assertEquals(2F, matrix[MatrixComponent.M01])
        assertEquals(3F, matrix[MatrixComponent.M10])
        assertEquals(4F, matrix[MatrixComponent.M11])
        assertFailsWith<IllegalArgumentException> { matrix[MatrixComponent.M22] }
    }

    @Test
    fun `toFloatArray should return components as float array`() {
        val matrix = Matrix2x2f(1F, 2F, 3F, 4F)
        assertContentEquals(floatArrayOf(1F, 2F, 3F, 4F), matrix.toFloatArray())
    }

    @Test
    fun `rotationRad should return rotation matrix`() {
        val angle = PI.toFloat() / 2F
        val matrix = Matrix2x2f.rotationRad(angle)
        // cos(pi/2) = 0, sin(pi/2) = 1
        // [0 -1]
        // [1  0]
        assertMatrixEquals(Matrix2x2f(0F, -1F, 1F, 0F), matrix)
        assertTrue(matrix.properties.isRotation)
    }

    @Test
    fun `rotation should return rotation matrix`() {
        val matrix = Matrix2x2f.rotation(90F)
        assertMatrixEquals(Matrix2x2f(0F, -1F, 1F, 0F), matrix)
    }

    @Test
    fun `scale with x and y should return scale matrix`() {
        val matrix = Matrix2x2f.scale(2F, 3F)
        assertEquals(Matrix2x2f(2F, 0F, 0F, 3F), matrix)
        assertTrue(matrix.properties.isDiagonal)
    }

    @Test
    fun `scale with uniform value should return scale matrix`() {
        val matrix = Matrix2x2f.scale(2F)
        assertEquals(Matrix2x2f(2F, 0F, 0F, 2F), matrix)
    }

    @Test
    fun `times operator with incompatible matrix should throw exception`() {
        val m1 = Matrix2x2f()
        val m2 = Matrix3x3f()
        assertFailsWith<IllegalArgumentException> { m1.times(m2) }
    }

    @Test
    fun `times operator with incompatible vector should throw exception`() {
        val matrix = Matrix2x2f()
        val vector = dev.karmakrafts.kgml.vector.Vector3f()
        assertFailsWith<IllegalArgumentException> { matrix.times(vector) }
    }

    private fun assertMatrixEquals(expected: Matrix2x2f, actual: Matrix2x2f) {
        assertEquals(expected.m00, actual.m00, 1E-6F)
        assertEquals(expected.m01, actual.m01, 1E-6F)
        assertEquals(expected.m10, actual.m10, 1E-6F)
        assertEquals(expected.m11, actual.m11, 1E-6F)
    }
}
