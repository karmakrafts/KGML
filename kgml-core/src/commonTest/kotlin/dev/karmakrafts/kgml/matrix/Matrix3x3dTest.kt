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

import dev.karmakrafts.kgml.transform.rotationRad
import dev.karmakrafts.kgml.transform.scale
import dev.karmakrafts.kgml.transform.translation
import dev.karmakrafts.kgml.vector.Vector3d
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class Matrix3x3dTest {
    @Test
    fun `constructor should initialize to identity`() {
        val matrix = Matrix3x3d()
        assertEquals(1.0, matrix.m00)
        assertEquals(0.0, matrix.m01)
        assertEquals(0.0, matrix.m02)
        assertEquals(0.0, matrix.m10)
        assertEquals(1.0, matrix.m11)
        assertEquals(0.0, matrix.m12)
        assertEquals(0.0, matrix.m20)
        assertEquals(0.0, matrix.m21)
        assertEquals(1.0, matrix.m22)
    }

    @Test
    fun `constructor with value should initialize all components`() {
        val matrix = Matrix3x3d(2.0)
        for (i in 0 until 9) {
            assertEquals(2.0, matrix[i])
        }
    }

    @Test
    fun `companion object properties should be correct`() {
        assertEquals(Double::class, Matrix3x3d.componentType)
        assertEquals(Double.SIZE_BYTES, Matrix3x3d.componentSize)
        assertEquals(3, Matrix3x3d.rows)
        assertEquals(3, Matrix3x3d.columns)
        assertEquals(Matrix3x3d(), Matrix3x3d.identity)
    }

    @Test
    fun `fromArray should create matrix from array at offset`() {
        val array = DoubleArray(10) { it.toDouble() }
        val matrix = Matrix3x3d.fromArray(array, 1)
        assertEquals(Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), matrix)
    }

    @Test
    fun `fromArray should create matrix from array at offset with properties`() {
        val array = DoubleArray(10) { it.toDouble() }
        val matrix = Matrix3x3d.fromArray(array, 1, MatrixProperties.AFFINE)
        assertEquals(Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, MatrixProperties.AFFINE), matrix)
        assertEquals(MatrixProperties.AFFINE, matrix.properties)
    }

    @Test
    fun `fromRows should create matrix from vectors`() {
        val row0 = Vector3d(1.0, 2.0, 3.0)
        val row1 = Vector3d(4.0, 5.0, 6.0)
        val row2 = Vector3d(7.0, 8.0, 9.0)
        val matrix = Matrix3x3d.fromRows(row0, row1, row2, MatrixProperties.AFFINE)
        assertEquals(Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, MatrixProperties.AFFINE), matrix)
    }

    @Test
    fun `fromColumns should create matrix from vectors`() {
        val column0 = Vector3d(1.0, 4.0, 7.0)
        val column1 = Vector3d(2.0, 5.0, 8.0)
        val column2 = Vector3d(3.0, 6.0, 9.0)
        val matrix = Matrix3x3d.fromColumns(column0, column1, column2, MatrixProperties.AFFINE)
        assertEquals(Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, MatrixProperties.AFFINE), matrix)
    }

    @Test
    fun `row accessors should return correct vectors`() {
        val matrix = Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        assertEquals(Vector3d(1.0, 2.0, 3.0), matrix.row0)
        assertEquals(Vector3d(4.0, 5.0, 6.0), matrix.row1)
        assertEquals(Vector3d(7.0, 8.0, 9.0), matrix.row2)
    }

    @Test
    fun `column accessors should return correct vectors`() {
        val matrix = Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        assertEquals(Vector3d(1.0, 4.0, 7.0), matrix.column0)
        assertEquals(Vector3d(2.0, 5.0, 8.0), matrix.column1)
        assertEquals(Vector3d(3.0, 6.0, 9.0), matrix.column2)
    }

    @Test
    fun `type should return Matrix3x3d`() {
        assertEquals(Matrix3x3d, Matrix3x3d().type)
    }

    @Test
    fun `extend should return Matrix4x4d`() {
        val matrix = Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val extended = matrix.extend()
        assertEquals(
            Matrix4x4d(1.0, 2.0, 3.0, 0.0, 4.0, 5.0, 6.0, 0.0, 7.0, 8.0, 9.0, 0.0, 0.0, 0.0, 0.0, 1.0),
            extended
        )
    }

    @Test
    fun `transpose should return transposed matrix`() {
        val matrix = Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        assertEquals(Matrix3x3d(1.0, 4.0, 7.0, 2.0, 5.0, 8.0, 3.0, 6.0, 9.0), matrix.transpose())
    }

    @Test
    fun `times operator with matrix should multiply matrices`() {
        val m1 = Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val m2 = Matrix3x3d(9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0)
        // [1 2 3] [9 8 7]   [1*9+2*6+3*3 1*8+2*5+3*2 1*7+2*4+3*1]   [30 24 18]
        // [4 5 6] [6 5 4] = [4*9+5*6+6*3 4*8+5*5+6*2 4*7+5*4+6*1] = [84 69 54]
        // [7 8 9] [3 2 1]   [7*9+8*6+9*3 7*8+8*5+9*2 7*7+8*4+9*1]   [138 114 90]
        assertEquals(Matrix3x3d(30.0, 24.0, 18.0, 84.0, 69.0, 54.0, 138.0, 114.0, 90.0), m1 * m2)

        val identity = Matrix3x3d.identity
        assertEquals(m1, m1 * identity)
        assertEquals(m1, identity * m1)
    }

    @Test
    fun `times operator with identity should return existing matrix`() {
        val matrix = Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, MatrixProperties.AFFINE)
        val identity = Matrix3x3d.identity
        assertSame(matrix, matrix * identity)
        assertSame(matrix, identity * matrix)
        assertEquals(MatrixProperties.AFFINE, (matrix * identity).properties)
        assertEquals(MatrixProperties.AFFINE, (identity * matrix).properties)
    }

    @Test
    fun `times operator with affine translations should preserve translation property`() {
        val left = Matrix3x3d.translation(2.0, 3.0)
        val right = Matrix3x3d.translation(5.0, 7.0)

        val result = left * right

        assertEquals(Matrix3x3d.translation(7.0, 10.0), result)
        assertEquals(
            MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS or MatrixProperties.TRANSLATION, result.properties
        )
    }

    @Test
    fun `times operator with affine translation and linear matrices should match generic multiplication`() {
        val translation = Matrix3x3d.translation(2.0, 3.0)
        val scale = Matrix3x3d.scale(4.0, 5.0)

        val translationScale = translation * scale
        assertMatrixEquals(Matrix3x3d(4.0, 0.0, 2.0, 0.0, 5.0, 3.0, 0.0, 0.0, 1.0), translationScale)
        assertEquals(MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS, translationScale.properties)

        val scaleTranslation = scale * translation
        assertMatrixEquals(Matrix3x3d(4.0, 0.0, 8.0, 0.0, 5.0, 15.0, 0.0, 0.0, 1.0), scaleTranslation)
        assertEquals(MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS, scaleTranslation.properties)
    }

    @Test
    fun `times operator with linear matrices should preserve linear property`() {
        val left = Matrix3x3d.scale(2.0, 3.0)
        val right = Matrix3x3d.rotationRad(angleZ = PI / 2.0)

        val result = left * right

        assertMatrixEquals(Matrix3x3d(0.0, -2.0, 0.0, 3.0, 0.0, 0.0, 0.0, 0.0, 1.0), result)
        assertEquals(
            MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS or MatrixProperties.LINEAR, result.properties
        )
    }

    @Test
    fun `times operator with vector should multiply matrix by vector`() {
        val matrix = Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val vector = Vector3d(10.0, 11.0, 12.0)
        // [1 2 3] [10] = [1*10+2*11+3*12] = [10+22+36] = [68]
        // [4 5 6] [11] = [4*10+5*11+6*12] = [40+55+72] = [167]
        // [7 8 9] [12] = [7*10+8*11+9*12] = [70+88+108] = [266]
        assertEquals(Vector3d(68.0, 167.0, 266.0), matrix * vector)
    }

    @Test
    fun `get should return component by index`() {
        val matrix = Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        for (i in 0 until 9) {
            assertEquals(i + 1.0, matrix[i])
        }
        assertFailsWith<IllegalArgumentException> { matrix[9] }
    }

    @Test
    fun `get should return component by MatrixComponent`() {
        val matrix = Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        assertEquals(1.0, matrix[MatrixComponent.M00])
        assertEquals(2.0, matrix[MatrixComponent.M01])
        assertEquals(3.0, matrix[MatrixComponent.M02])
        assertEquals(4.0, matrix[MatrixComponent.M10])
        assertEquals(5.0, matrix[MatrixComponent.M11])
        assertEquals(6.0, matrix[MatrixComponent.M12])
        assertEquals(7.0, matrix[MatrixComponent.M20])
        assertEquals(8.0, matrix[MatrixComponent.M21])
        assertEquals(9.0, matrix[MatrixComponent.M22])
        assertFailsWith<IllegalArgumentException> { matrix[MatrixComponent.M33] }
    }

    @Test
    fun `toDoubleArray should return components as float array`() {
        val matrix = Matrix3x3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        assertContentEquals(doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), matrix.toDoubleArray())
    }

    @Test
    fun `rotationZRad should return rotation matrix around Z`() {
        val angle = PI / 2.0
        val matrix = Matrix3x3d.rotationRad(angleZ = angle)
        // [0 -1 0]
        // [1  0 0]
        // [0  0 1]
        assertMatrixEquals(Matrix3x3d(0.0, -1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0), matrix)
    }

    @Test
    fun `rotationXRad should return rotation matrix around X`() {
        val angle = PI / 2.0
        val matrix = Matrix3x3d.rotationRad(angleX = angle)
        // [1 0  0]
        // [0 0 -1]
        // [0 1  0]
        assertMatrixEquals(Matrix3x3d(1.0, 0.0, 0.0, 0.0, 0.0, -1.0, 0.0, 1.0, 0.0), matrix)
    }

    @Test
    fun `rotationYRad should return rotation matrix around Y`() {
        val angle = PI / 2.0
        val matrix = Matrix3x3d.rotationRad(angleY = angle)
        // [ 0 0 1]
        // [ 0 1 0]
        // [-1 0 0]
        assertMatrixEquals(Matrix3x3d(0.0, 0.0, 1.0, 0.0, 1.0, 0.0, -1.0, 0.0, 0.0), matrix)
    }

    @Test
    fun `translation should return translation matrix`() {
        val matrix = Matrix3x3d.translation(2.0, 3.0)
        assertEquals(Matrix3x3d(1.0, 0.0, 2.0, 0.0, 1.0, 3.0, 0.0, 0.0, 1.0), matrix)
    }

    @Test
    fun `scale should return linear matrix`() {
        val matrix = Matrix3x3d.scale(2.0, 3.0)
        assertEquals(
            Matrix3x3d(
                2.0,
                0.0,
                0.0,
                0.0,
                3.0,
                0.0,
                0.0,
                0.0,
                1.0,
                MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS or MatrixProperties.LINEAR
            ), matrix
        )
    }

    @Test
    fun `times operator with incompatible matrix should throw exception`() {
        val m1 = Matrix3x3d()
        val m2 = Matrix2x2d()
        assertFailsWith<IllegalArgumentException> { m1.times(m2) }
    }

    @Test
    fun `times operator with incompatible vector should throw exception`() {
        val matrix = Matrix3x3d()
        val vector = dev.karmakrafts.kgml.vector.Vector2d()
        assertFailsWith<IllegalArgumentException> { matrix.times(vector) }
    }

    private fun assertMatrixEquals(expected: Matrix3x3d, actual: Matrix3x3d) {
        for (i in 0 until 9) {
            assertEquals(expected[i], actual[i], 1E-6)
        }
    }
}
