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
import dev.karmakrafts.kgml.transform.skew
import dev.karmakrafts.kgml.transform.translation
import dev.karmakrafts.kgml.vector.Vector4f
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Matrix4x4fTest {
    @Test
    fun `constructor should initialize to identity`() {
        val matrix = Matrix4x4f()
        assertEquals(Matrix4x4f(1F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 1F), matrix)
    }

    @Test
    fun `constructor with value should initialize all components`() {
        val matrix = Matrix4x4f(2F)
        for (i in 0 until 16) {
            assertEquals(2F, matrix[i])
        }
    }

    @Test
    fun `companion object properties should be correct`() {
        assertEquals(Float::class, Matrix4x4f.componentType)
        assertEquals(Float.SIZE_BYTES, Matrix4x4f.componentSize)
        assertEquals(4, Matrix4x4f.rows)
        assertEquals(4, Matrix4x4f.columns)
        assertEquals(Matrix4x4f(), Matrix4x4f.identity)
    }

    @Test
    fun `fromArray should create matrix from array at offset`() {
        val array = FloatArray(17) { it.toFloat() }
        val matrix = Matrix4x4f.fromArray(array, 1)
        assertEquals(Matrix4x4f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F), matrix)
    }

    @Test
    fun `fromArray should create matrix from array at offset with properties`() {
        val array = FloatArray(17) { it.toFloat() }
        val matrix = Matrix4x4f.fromArray(array, 1, MatrixProperties.AFFINE)
        assertEquals(
            Matrix4x4f(
                1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F, MatrixProperties.AFFINE
            ), matrix
        )
        assertEquals(MatrixProperties.AFFINE, matrix.properties)
    }

    @Test
    fun `fromRows should create matrix from vectors`() {
        val row0 = Vector4f(1F, 2F, 3F, 4F)
        val row1 = Vector4f(5F, 6F, 7F, 8F)
        val row2 = Vector4f(9F, 10F, 11F, 12F)
        val row3 = Vector4f(13F, 14F, 15F, 16F)
        val matrix = Matrix4x4f.fromRows(row0, row1, row2, row3, MatrixProperties.AFFINE)
        assertEquals(
            Matrix4x4f(
                1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F, MatrixProperties.AFFINE
            ), matrix
        )
    }

    @Test
    fun `fromColumns should create matrix from vectors`() {
        val column0 = Vector4f(1F, 5F, 9F, 13F)
        val column1 = Vector4f(2F, 6F, 10F, 14F)
        val column2 = Vector4f(3F, 7F, 11F, 15F)
        val column3 = Vector4f(4F, 8F, 12F, 16F)
        val matrix = Matrix4x4f.fromColumns(column0, column1, column2, column3, MatrixProperties.AFFINE)
        assertEquals(
            Matrix4x4f(
                1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F, MatrixProperties.AFFINE
            ), matrix
        )
    }

    @Test
    fun `row accessors should return correct vectors`() {
        val matrix = Matrix4x4f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F)
        assertEquals(Vector4f(1F, 2F, 3F, 4F), matrix.row0)
        assertEquals(Vector4f(5F, 6F, 7F, 8F), matrix.row1)
        assertEquals(Vector4f(9F, 10F, 11F, 12F), matrix.row2)
        assertEquals(Vector4f(13F, 14F, 15F, 16F), matrix.row3)
    }

    @Test
    fun `column accessors should return correct vectors`() {
        val matrix = Matrix4x4f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F)
        assertEquals(Vector4f(1F, 5F, 9F, 13F), matrix.column0)
        assertEquals(Vector4f(2F, 6F, 10F, 14F), matrix.column1)
        assertEquals(Vector4f(3F, 7F, 11F, 15F), matrix.column2)
        assertEquals(Vector4f(4F, 8F, 12F, 16F), matrix.column3)
    }

    @Test
    fun `type should return Matrix4x4f`() {
        assertEquals(Matrix4x4f, Matrix4x4f().type)
    }

    @Test
    fun `transpose should return transposed matrix`() {
        val matrix = Matrix4x4f(
            1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F
        )
        val expected = Matrix4x4f(
            1F, 5F, 9F, 13F, 2F, 6F, 10F, 14F, 3F, 7F, 11F, 15F, 4F, 8F, 12F, 16F
        )
        assertEquals(expected, matrix.transpose())
    }

    @Test
    fun `times operator with matrix should multiply matrices`() {
        val m1 = Matrix4x4f(
            1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F
        )
        val identity = Matrix4x4f.identity
        assertEquals(m1, m1 * identity)
        assertEquals(m1, identity * m1)
    }

    @Test
    fun `times operator with affine matrices should use affine multiplication`() {
        val m1 = Matrix4x4f(
            1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 0F, 0F, 0F, 1F, MatrixProperties.AFFINE
        )
        val m2 = Matrix4x4f(
            1F,
            0F,
            0F,
            2F,
            0F,
            1F,
            0F,
            3F,
            0F,
            0F,
            1F,
            4F,
            0F,
            0F,
            0F,
            1F,
            MatrixProperties.AFFINE or MatrixProperties.TRANSLATION
        )
        val result = m1 * m2
        assertEquals(MatrixProperties.AFFINE, result.properties and MatrixProperties.AFFINE)
        // [1 2 3 4] [1 0 0 2]   [1 2 3 (1*2 + 2*3 + 3*4 + 4)]   [1 2 3 24]
        // [5 6 7 8] [0 1 0 3] = [5 6 7 (5*2 + 6*3 + 7*4 + 8)] = [5 6 7 64]
        // [9 10 11 12] [0 0 1 4]   [9 10 11 (9*2 + 10*3 + 11*4 + 12)]   [9 10 11 104]
        // [0 0 0 1] [0 0 0 1]   [0 0 0 1]   [0 0 0 1]
        assertEquals(
            Matrix4x4f(
                1F, 2F, 3F, 24F, 5F, 6F, 7F, 64F, 9F, 10F, 11F, 104F, 0F, 0F, 0F, 1F, MatrixProperties.AFFINE
            ), result
        )
    }

    @Test
    fun `times operator with vector should multiply matrix by vector`() {
        val matrix = Matrix4x4f(
            1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F
        )
        val vector = Vector4f(1F, 1F, 1F, 1F)
        // [1 2 3 4] [1]   [1+2+3+4]   [10]
        // [5 6 7 8] [1] = [5+6+7+8] = [26]
        // [9 10 11 12] [1]   [9+10+11+12]   [42]
        // [13 14 15 16] [1]   [13+14+15+16]   [58]
        assertEquals(Vector4f(10F, 26F, 42F, 58F), matrix * vector)
    }

    @Test
    fun `get should return component by index`() {
        val matrix = Matrix4x4f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F)
        for (i in 0 until 16) {
            assertEquals((i + 1).toFloat(), matrix[i])
        }
        assertFailsWith<IllegalArgumentException> { matrix[16] }
    }

    @Test
    fun `get should return component by MatrixComponent`() {
        val matrix = Matrix4x4f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F)
        assertEquals(1F, matrix[MatrixComponent.M00])
        assertEquals(16F, matrix[MatrixComponent.M33])
    }

    @Test
    fun `rotationXRad should return rotation matrix around X`() {
        val matrix = Matrix4x4f.rotationRad(angleX = PI.toFloat() / 2F)
        assertMatrixEquals(Matrix4x4f(1F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 0F, 1F), matrix)
    }

    @Test
    fun `translation should return translation matrix`() {
        val matrix = Matrix4x4f.translation(2F, 3F, 4F)
        assertEquals(Matrix4x4f(1F, 0F, 0F, 2F, 0F, 1F, 0F, 3F, 0F, 0F, 1F, 4F, 0F, 0F, 0F, 1F), matrix)
    }

    @Test
    fun `scale should return scale matrix`() {
        val matrix = Matrix4x4f.scale(2F, 3F, 4F)
        assertEquals(Matrix4x4f(2F, 0F, 0F, 0F, 0F, 3F, 0F, 0F, 0F, 0F, 4F, 0F, 0F, 0F, 0F, 1F), matrix)
    }

    @Test
    fun `skew should return skew matrix`() {
        val matrix = Matrix4x4f.skew(xy = 1F)
        assertEquals(Matrix4x4f(1F, 1F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 1F), matrix)
    }

    @Test
    fun `translation times scale should preserve translation`() {
        val t = Matrix4x4f.translation(10F, 20F, 30F)
        val s = Matrix4x4f.scale(2F, 3F, 4F)
        val result = t * s
        assertEquals(
            Matrix4x4f(
                2F,
                0F,
                0F,
                10F,
                0F,
                3F,
                0F,
                20F,
                0F,
                0F,
                4F,
                30F,
                0F,
                0F,
                0F,
                1F,
                MatrixProperties.AFFINE or MatrixProperties.TRANSLATION
            ), result
        )
    }

    @Test
    fun `times operator with incompatible matrix should throw exception`() {
        val m1 = Matrix4x4f()
        val m2 = Matrix2x2f()
        assertFailsWith<IllegalArgumentException> { m1.times(m2) }
    }

    @Test
    fun `times operator with incompatible vector should throw exception`() {
        val matrix = Matrix4x4f()
        val vector = dev.karmakrafts.kgml.vector.Vector2f()
        assertFailsWith<IllegalArgumentException> { matrix.times(vector) }
    }

    private fun assertMatrixEquals(expected: Matrix4x4f, actual: Matrix4x4f) {
        for (i in 0 until 16) {
            assertEquals(expected[i], actual[i], 1E-6F)
        }
    }
}
