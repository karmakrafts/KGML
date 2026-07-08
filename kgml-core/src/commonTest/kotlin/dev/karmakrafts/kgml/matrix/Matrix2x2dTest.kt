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
import dev.karmakrafts.kgml.vector.Vector2d
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

class Matrix2x2dTest {
    @Test
    fun `constructor should initialize to identity`() {
        val matrix = Matrix2x2d()
        assertEquals(1.0, matrix.m00)
        assertEquals(0.0, matrix.m01)
        assertEquals(0.0, matrix.m10)
        assertEquals(1.0, matrix.m11)
    }

    @Test
    fun `constructor with value should initialize all components`() {
        val matrix = Matrix2x2d(2.0)
        assertEquals(2.0, matrix.m00)
        assertEquals(2.0, matrix.m01)
        assertEquals(2.0, matrix.m10)
        assertEquals(2.0, matrix.m11)
    }

    @Test
    fun `companion object properties should be correct`() {
        assertEquals(Double::class, Matrix2x2d.componentType)
        assertEquals(Double.SIZE_BYTES, Matrix2x2d.componentSize)
        assertEquals(2, Matrix2x2d.rows)
        assertEquals(2, Matrix2x2d.columns)
        assertContentEquals(
            arrayOf(MatrixComponent.M00, MatrixComponent.M01, MatrixComponent.M10, MatrixComponent.M11),
            Matrix2x2d.components
        )
        assertEquals(Matrix2x2d(), Matrix2x2d.identity)
    }

    @Test
    fun `fromArray should create matrix from array at offset`() {
        val array = doubleArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0)
        val matrix = Matrix2x2d.fromArray(array, 1)
        assertEquals(Matrix2x2d(1.0, 2.0, 3.0, 4.0), matrix)
    }

    @Test
    fun `fromArray should create matrix from array at offset with properties`() {
        val array = doubleArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0)
        val matrix = Matrix2x2d.fromArray(array, 1, MatrixProperties.AFFINE)
        assertEquals(Matrix2x2d(1.0, 2.0, 3.0, 4.0, MatrixProperties.AFFINE), matrix)
        assertEquals(MatrixProperties.AFFINE, matrix.properties)
    }

    @Test
    fun `fromRows should create matrix from vectors`() {
        val row0 = Vector2d(1.0, 2.0)
        val row1 = Vector2d(3.0, 4.0)
        val matrix = Matrix2x2d.fromRows(row0, row1, MatrixProperties.AFFINE)
        assertEquals(Matrix2x2d(1.0, 2.0, 3.0, 4.0, MatrixProperties.AFFINE), matrix)
    }

    @Test
    fun `fromColumns should create matrix from vectors`() {
        val column0 = Vector2d(1.0, 3.0)
        val column1 = Vector2d(2.0, 4.0)
        val matrix = Matrix2x2d.fromColumns(column0, column1, MatrixProperties.AFFINE)
        assertEquals(Matrix2x2d(1.0, 2.0, 3.0, 4.0, MatrixProperties.AFFINE), matrix)
    }

    @Test
    fun `row accessors should return correct vectors`() {
        val matrix = Matrix2x2d(1.0, 2.0, 3.0, 4.0)
        assertEquals(Vector2d(1.0, 2.0), matrix.row0)
        assertEquals(Vector2d(3.0, 4.0), matrix.row1)
    }

    @Test
    fun `column accessors should return correct vectors`() {
        val matrix = Matrix2x2d(1.0, 2.0, 3.0, 4.0)
        assertEquals(Vector2d(1.0, 3.0), matrix.column0)
        assertEquals(Vector2d(2.0, 4.0), matrix.column1)
    }

    @Test
    fun `type should return Matrix2x2d`() {
        assertEquals(Matrix2x2d, Matrix2x2d().type)
    }

    @Test
    fun `extend should return Matrix3x3d`() {
        val matrix = Matrix2x2d(1.0, 2.0, 3.0, 4.0)
        val extended = matrix.extend()
        assertEquals(Matrix3x3d(1.0, 2.0, 0.0, 3.0, 4.0, 0.0, 0.0, 0.0, 1.0), extended)
    }

    @Test
    fun `transpose should return transposed matrix`() {
        val matrix = Matrix2x2d(1.0, 2.0, 3.0, 4.0)
        assertEquals(Matrix2x2d(1.0, 3.0, 2.0, 4.0), matrix.transpose())
    }

    @Test
    fun `times operator with matrix should multiply matrices`() {
        val m1 = Matrix2x2d(1.0, 2.0, 3.0, 4.0)
        val m2 = Matrix2x2d(5.0, 6.0, 7.0, 8.0)
        // [1 2] [5 6] = [1*5+2*7 1*6+2*8] = [19 22]
        // [3 4] [7 8]   [3*5+4*7 3*6+4*8]   [43 50]
        assertEquals(Matrix2x2d(19.0, 22.0, 43.0, 50.0), m1 * m2)

        val identity = Matrix2x2d.identity
        assertEquals(m1, m1 * identity, "m1 * identity should be m1")
        assertEquals(m1, identity * m1, "identity * m1 should be m1")
    }

    @Test
    fun `times operator with identity should return existing matrix`() {
        val matrix = Matrix2x2d(1.0, 2.0, 3.0, 4.0, MatrixProperties.AFFINE)
        val identity = Matrix2x2d.identity
        assertSame(matrix, matrix * identity)
        assertSame(matrix, identity * matrix)
        assertEquals(MatrixProperties.AFFINE, (matrix * identity).properties)
        assertEquals(MatrixProperties.AFFINE, (identity * matrix).properties)
    }

    @Test
    fun `times operator with diagonal matrices should multiply scales`() {
        val result = Matrix2x2d.scale(2.0, 3.0) * Matrix2x2d.scale(5.0, 7.0)
        assertEquals(Matrix2x2d(10.0, 0.0, 0.0, 21.0), result)
        assertTrue(result.properties.isAffine)
        assertTrue(result.properties.isLinear)
        assertTrue(result.properties.isDiagonal)
    }

    @Test
    fun `times operator with diagonal operand should preserve linear result only`() {
        val generic = Matrix2x2d(1.0, 2.0, 3.0, 4.0, MatrixProperties.AFFINE or MatrixProperties.LINEAR)
        val result = generic * Matrix2x2d.scale(5.0, 7.0)
        assertEquals(Matrix2x2d(5.0, 14.0, 15.0, 28.0), result)
        assertTrue(result.properties.isAffine)
        assertTrue(result.properties.isLinear)
        assertFalse(result.properties.isDiagonal)
        assertFalse(result.properties.isRotation)
    }

    @Test
    fun `times operator with rotation matrices should preserve rotation`() {
        val result = Matrix2x2d.rotation(30.0) * Matrix2x2d.rotation(60.0)
        assertMatrixEquals(Matrix2x2d.rotation(90.0), result)
        assertTrue(result.properties.isAffine)
        assertTrue(result.properties.isLinear)
        assertTrue(result.properties.isRotation)
    }

    @Test
    fun `times operator with vector should multiply matrix by vector`() {
        val matrix = Matrix2x2d(1.0, 2.0, 3.0, 4.0)
        val vector = Vector2d(5.0, 6.0)
        // [1 2] [5] = [1*5+2*6] = [17]
        // [3 4] [6]   [3*5+4*6]   [39]
        assertEquals(Vector2d(17.0, 39.0), matrix * vector)
    }

    @Test
    fun `get should return component by index`() {
        val matrix = Matrix2x2d(1.0, 2.0, 3.0, 4.0)
        assertEquals(1.0, matrix[0])
        assertEquals(2.0, matrix[1])
        assertEquals(3.0, matrix[2])
        assertEquals(4.0, matrix[3])
        assertFailsWith<IllegalArgumentException> { matrix[4] }
    }

    @Test
    fun `get should return component by MatrixComponent`() {
        val matrix = Matrix2x2d(1.0, 2.0, 3.0, 4.0)
        assertEquals(1.0, matrix[MatrixComponent.M00])
        assertEquals(2.0, matrix[MatrixComponent.M01])
        assertEquals(3.0, matrix[MatrixComponent.M10])
        assertEquals(4.0, matrix[MatrixComponent.M11])
        assertFailsWith<IllegalArgumentException> { matrix[MatrixComponent.M22] }
    }

    @Test
    fun `toDoubleArray should return components as float array`() {
        val matrix = Matrix2x2d(1.0, 2.0, 3.0, 4.0)
        assertContentEquals(doubleArrayOf(1.0, 2.0, 3.0, 4.0), matrix.toDoubleArray())
    }

    @Test
    fun `rotationRad should return rotation matrix`() {
        val angle = PI / 2.0
        val matrix = Matrix2x2d.rotationRad(angle)
        // cos(pi/2) = 0, sin(pi/2) = 1
        // [0 -1]
        // [1  0]
        assertMatrixEquals(Matrix2x2d(0.0, -1.0, 1.0, 0.0), matrix)
        assertTrue(matrix.properties.isRotation)
    }

    @Test
    fun `rotation should return rotation matrix`() {
        val matrix = Matrix2x2d.rotation(90.0)
        assertMatrixEquals(Matrix2x2d(0.0, -1.0, 1.0, 0.0), matrix)
    }

    @Test
    fun `scale with x and y should return scale matrix`() {
        val matrix = Matrix2x2d.scale(2.0, 3.0)
        assertEquals(Matrix2x2d(2.0, 0.0, 0.0, 3.0), matrix)
        assertTrue(matrix.properties.isDiagonal)
    }

    @Test
    fun `scale with uniform value should return scale matrix`() {
        val matrix = Matrix2x2d.scale(2.0)
        assertEquals(Matrix2x2d(2.0, 0.0, 0.0, 2.0), matrix)
    }

    @Test
    fun `times operator with incompatible matrix should throw exception`() {
        val m1 = Matrix2x2d()
        val m2 = Matrix3x3d()
        assertFailsWith<IllegalArgumentException> { m1.times(m2) }
    }

    @Test
    fun `times operator with incompatible vector should throw exception`() {
        val matrix = Matrix2x2d()
        val vector = dev.karmakrafts.kgml.vector.Vector3d()
        assertFailsWith<IllegalArgumentException> { matrix.times(vector) }
    }

    private fun assertMatrixEquals(expected: Matrix2x2d, actual: Matrix2x2d) {
        assertEquals(expected.m00, actual.m00, 1E-6)
        assertEquals(expected.m01, actual.m01, 1E-6)
        assertEquals(expected.m10, actual.m10, 1E-6)
        assertEquals(expected.m11, actual.m11, 1E-6)
    }
}
