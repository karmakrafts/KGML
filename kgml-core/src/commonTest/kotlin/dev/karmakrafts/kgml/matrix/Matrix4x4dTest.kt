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
import dev.karmakrafts.kgml.vector.Vector4d
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Matrix4x4dTest {
    @Test
    fun constructorShouldInitializeToIdentity() {
        val matrix = Matrix4x4d()
        assertEquals(Matrix4x4d(1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0), matrix)
    }

    @Test
    fun constructorWithValueShouldInitializeAllComponents() {
        val matrix = Matrix4x4d(2.0)
        for (i in 0 until 16) {
            assertEquals(2.0, matrix[i])
        }
    }

    @Test
    fun companionObjectPropertiesShouldBeCorrect() {
        assertEquals(Double::class, Matrix4x4d.componentType)
        assertEquals(Double.SIZE_BYTES, Matrix4x4d.componentSize)
        assertEquals(4, Matrix4x4d.rows)
        assertEquals(4, Matrix4x4d.columns)
        assertEquals(Matrix4x4d(), Matrix4x4d.identity)
    }

    @Test
    fun fromArrayShouldCreateMatrixFromArrayAtOffset() {
        val array = DoubleArray(17) { it.toDouble() }
        val matrix = Matrix4x4d.fromArray(array, 1)
        assertEquals(
            Matrix4x4d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0),
            matrix
        )
    }

    @Test
    fun fromArrayShouldCreateMatrixFromArrayAtOffsetWithProperties() {
        val array = DoubleArray(17) { it.toDouble() }
        val matrix = Matrix4x4d.fromArray(array, 1, MatrixProperties.AFFINE)
        assertEquals(
            Matrix4x4d(
                1.0,
                2.0,
                3.0,
                4.0,
                5.0,
                6.0,
                7.0,
                8.0,
                9.0,
                10.0,
                11.0,
                12.0,
                13.0,
                14.0,
                15.0,
                16.0,
                MatrixProperties.AFFINE
            ), matrix
        )
        assertEquals(MatrixProperties.AFFINE, matrix.properties)
    }

    @Test
    fun fromRowsShouldCreateMatrixFromVectors() {
        val row0 = Vector4d(1.0, 2.0, 3.0, 4.0)
        val row1 = Vector4d(5.0, 6.0, 7.0, 8.0)
        val row2 = Vector4d(9.0, 10.0, 11.0, 12.0)
        val row3 = Vector4d(13.0, 14.0, 15.0, 16.0)
        val matrix = Matrix4x4d.fromRows(row0, row1, row2, row3, MatrixProperties.AFFINE)
        assertEquals(
            Matrix4x4d(
                1.0,
                2.0,
                3.0,
                4.0,
                5.0,
                6.0,
                7.0,
                8.0,
                9.0,
                10.0,
                11.0,
                12.0,
                13.0,
                14.0,
                15.0,
                16.0,
                MatrixProperties.AFFINE
            ), matrix
        )
    }

    @Test
    fun fromColumnsShouldCreateMatrixFromVectors() {
        val column0 = Vector4d(1.0, 5.0, 9.0, 13.0)
        val column1 = Vector4d(2.0, 6.0, 10.0, 14.0)
        val column2 = Vector4d(3.0, 7.0, 11.0, 15.0)
        val column3 = Vector4d(4.0, 8.0, 12.0, 16.0)
        val matrix = Matrix4x4d.fromColumns(column0, column1, column2, column3, MatrixProperties.AFFINE)
        assertEquals(
            Matrix4x4d(
                1.0,
                2.0,
                3.0,
                4.0,
                5.0,
                6.0,
                7.0,
                8.0,
                9.0,
                10.0,
                11.0,
                12.0,
                13.0,
                14.0,
                15.0,
                16.0,
                MatrixProperties.AFFINE
            ), matrix
        )
    }

    @Test
    fun rowAccessorsShouldReturnCorrectVectors() {
        val matrix = Matrix4x4d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
        assertEquals(Vector4d(1.0, 2.0, 3.0, 4.0), matrix.row0)
        assertEquals(Vector4d(5.0, 6.0, 7.0, 8.0), matrix.row1)
        assertEquals(Vector4d(9.0, 10.0, 11.0, 12.0), matrix.row2)
        assertEquals(Vector4d(13.0, 14.0, 15.0, 16.0), matrix.row3)
    }

    @Test
    fun columnAccessorsShouldReturnCorrectVectors() {
        val matrix = Matrix4x4d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
        assertEquals(Vector4d(1.0, 5.0, 9.0, 13.0), matrix.column0)
        assertEquals(Vector4d(2.0, 6.0, 10.0, 14.0), matrix.column1)
        assertEquals(Vector4d(3.0, 7.0, 11.0, 15.0), matrix.column2)
        assertEquals(Vector4d(4.0, 8.0, 12.0, 16.0), matrix.column3)
    }

    @Test
    fun typeShouldReturnMatrix4x4d() {
        assertEquals(Matrix4x4d, Matrix4x4d().type)
    }

    @Test
    fun transposeShouldReturnTransposedMatrix() {
        val matrix = Matrix4x4d(
            1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0
        )
        val expected = Matrix4x4d(
            1.0, 5.0, 9.0, 13.0, 2.0, 6.0, 10.0, 14.0, 3.0, 7.0, 11.0, 15.0, 4.0, 8.0, 12.0, 16.0
        )
        assertEquals(expected, matrix.transpose())
    }

    @Test
    fun timesOperatorWithMatrixShouldMultiplyMatrices() {
        val m1 = Matrix4x4d(
            1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0
        )
        val identity = Matrix4x4d.identity
        assertEquals(m1, m1 * identity)
        assertEquals(m1, identity * m1)
    }

    @Test
    fun timesOperatorWithAffineMatricesShouldUseAffineMultiplication() {
        val m1 = Matrix4x4d(
            1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 0.0, 0.0, 0.0, 1.0, MatrixProperties.AFFINE
        )
        val m2 = Matrix4x4d(
            1.0,
            0.0,
            0.0,
            2.0,
            0.0,
            1.0,
            0.0,
            3.0,
            0.0,
            0.0,
            1.0,
            4.0,
            0.0,
            0.0,
            0.0,
            1.0,
            MatrixProperties.AFFINE or MatrixProperties.TRANSLATION
        )
        val result = m1 * m2
        assertEquals(MatrixProperties.AFFINE, result.properties and MatrixProperties.AFFINE)
        // [1 2 3 4] [1 0 0 2]   [1 2 3 (1*2 + 2*3 + 3*4 + 4)]   [1 2 3 24]
        // [5 6 7 8] [0 1 0 3] = [5 6 7 (5*2 + 6*3 + 7*4 + 8)] = [5 6 7 64]
        // [9 10 11 12] [0 0 1 4]   [9 10 11 (9*2 + 10*3 + 11*4 + 12)]   [9 10 11 104]
        // [0 0 0 1] [0 0 0 1]   [0 0 0 1]   [0 0 0 1]
        assertEquals(
            Matrix4x4d(
                1.0,
                2.0,
                3.0,
                24.0,
                5.0,
                6.0,
                7.0,
                64.0,
                9.0,
                10.0,
                11.0,
                104.0,
                0.0,
                0.0,
                0.0,
                1.0,
                MatrixProperties.AFFINE
            ), result
        )
    }

    @Test
    fun timesOperatorWithAffineMatricesShouldTransformRightTranslationColumn() {
        val m1 = Matrix4x4d(
            1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 0.0, 0.0, 0.0, 1.0, MatrixProperties.AFFINE
        )
        val m2 = Matrix4x4d(
            2.0, 0.0, 0.0, 3.0, 0.0, 3.0, 0.0, 4.0, 0.0, 0.0, 4.0, 5.0, 0.0, 0.0, 0.0, 1.0, MatrixProperties.AFFINE
        )
        assertEquals(
            Matrix4x4d(
                2.0,
                6.0,
                12.0,
                30.0,
                10.0,
                18.0,
                28.0,
                82.0,
                18.0,
                30.0,
                44.0,
                134.0,
                0.0,
                0.0,
                0.0,
                1.0,
                MatrixProperties.AFFINE
            ), m1 * m2
        )
    }

    @Test
    fun timesOperatorWithPerspectiveAndAffineMatricesShouldMatchGenericMultiplication() {
        val perspective = Matrix4x4d(
            1.5,
            0.0,
            0.0,
            0.0,
            0.0,
            2.5,
            0.0,
            0.0,
            0.0,
            0.0,
            -1.2,
            -0.2,
            0.0,
            0.0,
            -1.0,
            0.0,
            MatrixProperties.PERSPECTIVE or MatrixProperties.TRANSLATION
        )
        val affine = Matrix4x4d(
            1.0, 0.0, 0.0, 2.0, 0.0, 0.5, -0.75, 3.0, 0.0, 0.75, 0.5, 4.0, 0.0, 0.0, 0.0, 1.0, MatrixProperties.AFFINE
        )
        val genericPerspective = perspective.copy(properties = MatrixProperties.NONE)
        val genericAffine = affine.copy(properties = MatrixProperties.NONE)
        assertMatrixEquals(genericPerspective * genericAffine, perspective * affine)
    }

    @Test
    fun timesOperatorWithVectorShouldMultiplyMatrixByVector() {
        val matrix = Matrix4x4d(
            1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0
        )
        val vector = Vector4d(1.0, 1.0, 1.0, 1.0)
        // [1 2 3 4] [1]   [1+2+3+4]   [10]
        // [5 6 7 8] [1] = [5+6+7+8] = [26]
        // [9 10 11 12] [1]   [9+10+11+12]   [42]
        // [13 14 15 16] [1]   [13+14+15+16]   [58]
        assertEquals(Vector4d(10.0, 26.0, 42.0, 58.0), matrix * vector)
    }

    @Test
    fun getShouldReturnComponentByIndex() {
        val matrix = Matrix4x4d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
        for (i in 0 until 16) {
            assertEquals(i + 1.0, matrix[i])
        }
        assertFailsWith<IllegalArgumentException> { matrix[16] }
    }

    @Test
    fun getShouldReturnComponentByMatrixComponent() {
        val matrix = Matrix4x4d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
        assertEquals(1.0, matrix[MatrixComponent.M00])
        assertEquals(16.0, matrix[MatrixComponent.M33])
    }

    @Test
    fun rotationXRadShouldReturnRotationMatrixAroundX() {
        val matrix = Matrix4x4d.rotationRad(angleX = PI / 2.0)
        assertMatrixEquals(
            Matrix4x4d(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0),
            matrix
        )
    }

    @Test
    fun translationShouldReturnTranslationMatrix() {
        val matrix = Matrix4x4d.translation(2.0, 3.0, 4.0)
        assertEquals(Matrix4x4d(1.0, 0.0, 0.0, 2.0, 0.0, 1.0, 0.0, 3.0, 0.0, 0.0, 1.0, 4.0, 0.0, 0.0, 0.0, 1.0), matrix)
    }

    @Test
    fun scaleShouldReturnScaleMatrix() {
        val matrix = Matrix4x4d.scale(2.0, 3.0, 4.0)
        assertEquals(Matrix4x4d(2.0, 0.0, 0.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 0.0, 4.0, 0.0, 0.0, 0.0, 0.0, 1.0), matrix)
    }

    @Test
    fun skewShouldReturnSkewMatrix() {
        val matrix = Matrix4x4d.skew(xy = 1.0)
        assertEquals(Matrix4x4d(1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0), matrix)
    }

    @Test
    fun translationTimesScaleShouldPreserveTranslation() {
        val t = Matrix4x4d.translation(10.0, 20.0, 30.0)
        val s = Matrix4x4d.scale(2.0, 3.0, 4.0)
        val result = t * s
        assertEquals(
            Matrix4x4d(
                2.0,
                0.0,
                0.0,
                10.0,
                0.0,
                3.0,
                0.0,
                20.0,
                0.0,
                0.0,
                4.0,
                30.0,
                0.0,
                0.0,
                0.0,
                1.0,
                MatrixProperties.AFFINE or MatrixProperties.TRANSLATION
            ), result
        )
    }

    @Test
    fun timesOperatorWithIncompatibleMatrixShouldThrowException() {
        val m1 = Matrix4x4d()
        val m2 = Matrix2x2d()
        assertFailsWith<IllegalArgumentException> { m1.times(m2) }
    }

    @Test
    fun timesOperatorWithIncompatibleVectorShouldThrowException() {
        val matrix = Matrix4x4d()
        val vector = dev.karmakrafts.kgml.vector.Vector2d()
        assertFailsWith<IllegalArgumentException> { matrix.times(vector) }
    }

    private fun assertMatrixEquals(expected: Matrix4x4d, actual: Matrix4x4d) {
        for (i in 0 until 16) {
            assertEquals(expected[i], actual[i], 1E-6)
        }
    }
}
