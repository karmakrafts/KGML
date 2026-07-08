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

import dev.karmakrafts.kgml.util.fma
import dev.karmakrafts.kgml.vector.Vector3d
import dev.karmakrafts.kgml.vector.Vector3f
import dev.karmakrafts.kgml.vector.VectorN
import kotlin.jvm.JvmField
import kotlin.jvm.JvmRecord
import kotlin.reflect.KClass

/**
 * A 3x3 double matrix.
 *
 * @property m00 Row 0, Column 0
 * @property m01 Row 0, Column 1
 * @property m02 Row 0, Column 2
 * @property m10 Row 1, Column 0
 * @property m11 Row 1, Column 1
 * @property m12 Row 1, Column 2
 * @property m20 Row 2, Column 0
 * @property m21 Row 2, Column 1
 * @property m22 Row 2, Column 2
 * @property properties The properties of the matrix.
 */
@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Matrix3x3d(
    @JvmField val m00: Double,
    @JvmField val m01: Double,
    @JvmField val m02: Double,
    @JvmField val m10: Double,
    @JvmField val m11: Double,
    @JvmField val m12: Double,
    @JvmField val m20: Double,
    @JvmField val m21: Double,
    @JvmField val m22: Double,
    override val properties: MatrixProperties = MatrixProperties.NONE
) : MatrixNxNd {
    /**
     * The type of [Matrix3x3d].
     */
    companion object : MatrixType {
        override val componentType: KClass<*> = Double::class
        override val componentSize: Int = Double.SIZE_BYTES
        override val rows: Int = 3
        override val columns: Int = 3

        override val components: Array<MatrixComponent> = arrayOf( // @formatter:off
            MatrixComponent.M00, MatrixComponent.M01, MatrixComponent.M02,
            MatrixComponent.M10, MatrixComponent.M11, MatrixComponent.M12,
            MatrixComponent.M20, MatrixComponent.M21, MatrixComponent.M22
        ) // @formatter:on

        /**
         * The identity matrix for [Matrix3x3d].
         */
        val identity: Matrix3x3d = Matrix3x3d()

        /**
         * Creates a [Matrix3x3d] from the given double array.
         *
         * @param array The array to read from.
         * @param offset The offset in the array.
         * @return The created matrix.
         */
        inline fun fromArray( // @formatter:off
            array: DoubleArray,
            offset: Int = 0,
            properties: MatrixProperties = MatrixProperties.NONE
        ): Matrix3x3d = Matrix3x3d(
            array[offset],
            array[offset + 1],
            array[offset + 2],
            array[offset + 3],
            array[offset + 4],
            array[offset + 5],
            array[offset + 6],
            array[offset + 7],
            array[offset + 8],
            properties
        ) // @formatter:on

        /**
         * Creates a [Matrix3x3d] from the given rows.
         *
         * @param row0 The first row.
         * @param row1 The second row.
         * @param row2 The third row.
         * @param properties The properties of the matrix.
         * @return The created matrix.
         */
        inline fun fromRows( // @formatter:off
            row0: Vector3d,
            row1: Vector3d,
            row2: Vector3d,
            properties: MatrixProperties = MatrixProperties.NONE
        ): Matrix3x3d = Matrix3x3d(
            row0.x, row0.y, row0.z,
            row1.x, row1.y, row1.z,
            row2.x, row2.y, row2.z,
            properties
        ) // @formatter:on

        /**
         * Creates a [Matrix3x3d] from the given columns.
         *
         * @param column0 The first column.
         * @param column1 The second column.
         * @param column2 The third column.
         * @param properties The properties of the matrix.
         * @return The created matrix.
         */
        inline fun fromColumns( // @formatter:off
            column0: Vector3d,
            column1: Vector3d,
            column2: Vector3d,
            properties: MatrixProperties = MatrixProperties.NONE
        ): Matrix3x3d = Matrix3x3d(
            column0.x, column1.x, column2.x,
            column0.y, column1.y, column2.y,
            column0.z, column1.z, column2.z,
            properties
        ) // @formatter:on
    }

    /**
     * Creates an identity matrix.
     */
    constructor() : this( // @formatter:off
        1.0, 0.0, 0.0,
        0.0, 1.0, 0.0,
        0.0, 0.0, 1.0,
        MatrixProperties.IDENTITY
    ) // @formatter:on

    /**
     * Creates a matrix with all components set to the given value.
     *
     * @param value The value to set all components to.
     */
    constructor(value: Double) : this( // @formatter:off
        value, value, value,
        value, value, value,
        value, value, value
    ) // @formatter:on

    /**
     * The first row of the matrix.
     *
     * @return The first row.
     */
    inline val row0: Vector3d get() = Vector3d(m00, m01, m02)

    /**
     * The second row of the matrix.
     *
     * @return The second row.
     */
    inline val row1: Vector3d get() = Vector3d(m10, m11, m12)

    /**
     * The third row of the matrix.
     *
     * @return The third row.
     */
    inline val row2: Vector3d get() = Vector3d(m20, m21, m22)

    /**
     * The first column of the matrix.
     *
     * @return The first column.
     */
    inline val column0: Vector3d get() = Vector3d(m00, m10, m20)

    /**
     * The second column of the matrix.
     *
     * @return The second column.
     */
    inline val column1: Vector3d get() = Vector3d(m01, m11, m21)

    /**
     * The third column of the matrix.
     *
     * @return The third column.
     */
    inline val column2: Vector3d get() = Vector3d(m02, m12, m22)

    /**
     * The type of the matrix.
     */
    override val type: MatrixType get() = Matrix3x3d

    /**
     * Converts this matrix to a [Matrix3x3f].
     *
     * @return The converted matrix.
     */
    inline fun toMatrix3x3f(): Matrix3x3f = Matrix3x3f( // @formatter:off
        m00.toFloat(), m01.toFloat(), m02.toFloat(),
        m10.toFloat(), m11.toFloat(), m12.toFloat(),
        m20.toFloat(), m21.toFloat(), m22.toFloat()
    ) // @formatter:on

    /**
     * Extends this 3x3 matrix to a 4x4 matrix.
     *
     * @return The extended 4x4 matrix.
     */
    inline fun extend(): Matrix4x4d = Matrix4x4d( // @formatter:off
        m00, m01, m02, 0.0,
        m10, m11, m12, 0.0,
        m20, m21, m22, 0.0,
        0.0, 0.0, 0.0, 1.0,
        properties
    ) // @formatter:on

    /**
     * Transposes this matrix.
     *
     * @return The transposed matrix.
     */
    inline fun transpose(): Matrix3x3d = Matrix3x3d( // @formatter:off
        m00, m10, m20,
        m01, m11, m21,
        m02, m12, m22,
        properties
    ) // @formatter:on

    /**
     * Multiplies this matrix with another 3x3 matrix.
     *
     * @param other The matrix to multiply with.
     * @return The result of the multiplication.
     */
    operator fun times(other: Matrix3x3d): Matrix3x3d {
        val otherProps = other.properties
        return when {
            properties.isIdentity -> other
            otherProps.isIdentity -> this

            properties.isAffine && properties.isHomogeneous -> when {
                otherProps.isAffine && otherProps.isHomogeneous && otherProps.isTranslation -> multiplyAffineTranslationR(
                    other
                )

                otherProps.isAffine && otherProps.isHomogeneous && otherProps.isLinear -> multiplyAffineLinearR(other)
                otherProps.isAffine && otherProps.isHomogeneous -> multiplyAffineR(other)
                else -> multiplyAffineL(other)
            }

            otherProps.isAffine && otherProps.isHomogeneous && otherProps.isLinear -> multiplyGenericAffineLinearR(other)
            otherProps.isAffine && otherProps.isHomogeneous -> multiplyGenericAffineR(other)
            else -> multiplyGeneric(other)
        }
    }

    private inline fun multiplyAffineProperties(otherProperties: MatrixProperties): MatrixProperties {
        var result = MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS
        if (properties.isLinear && otherProperties.isLinear) result = result or MatrixProperties.LINEAR
        if (properties.isTranslation && otherProperties.isTranslation) result = result or MatrixProperties.TRANSLATION
        return result
    }

    private fun multiplyAffineTranslationR(other: Matrix3x3d): Matrix3x3d {
        return Matrix3x3d(
            m00,
            m01,
            fma(m00, other.m02, fma(m01, other.m12, m02)),
            m10,
            m11,
            fma(m10, other.m02, fma(m11, other.m12, m12)),
            0.0,
            0.0,
            1.0,
            if (properties.isTranslation) {
                MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS or MatrixProperties.TRANSLATION
            }
            else MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS
        )
    }

    private fun multiplyAffineLinearR(other: Matrix3x3d): Matrix3x3d {
        return Matrix3x3d(
            fma(m00, other.m00, m01 * other.m10),
            fma(m00, other.m01, m01 * other.m11),
            m02,
            fma(m10, other.m00, m11 * other.m10),
            fma(m10, other.m01, m11 * other.m11),
            m12,
            0.0,
            0.0,
            1.0,
            if (properties.isLinear) {
                MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS or MatrixProperties.LINEAR
            }
            else MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS
        )
    }

    private fun multiplyAffineR(other: Matrix3x3d): Matrix3x3d {
        return Matrix3x3d(
            fma(m00, other.m00, m01 * other.m10),
            fma(m00, other.m01, m01 * other.m11),
            fma(m00, other.m02, fma(m01, other.m12, m02)),
            fma(m10, other.m00, m11 * other.m10),
            fma(m10, other.m01, m11 * other.m11),
            fma(m10, other.m02, fma(m11, other.m12, m12)),
            0.0,
            0.0,
            1.0,
            multiplyAffineProperties(other.properties)
        )
    }

    private fun multiplyAffineL(other: Matrix3x3d): Matrix3x3d {
        return Matrix3x3d(
            fma(m00, other.m00, fma(m01, other.m10, m02 * other.m20)),
            fma(m00, other.m01, fma(m01, other.m11, m02 * other.m21)),
            fma(m00, other.m02, fma(m01, other.m12, m02 * other.m22)),
            fma(m10, other.m00, fma(m11, other.m10, m12 * other.m20)),
            fma(m10, other.m01, fma(m11, other.m11, m12 * other.m21)),
            fma(m10, other.m02, fma(m11, other.m12, m12 * other.m22)),
            other.m20,
            other.m21,
            other.m22,
            other.properties
        )
    }

    private fun multiplyGenericAffineLinearR(other: Matrix3x3d): Matrix3x3d {
        return Matrix3x3d(
            fma(m00, other.m00, m01 * other.m10),
            fma(m00, other.m01, m01 * other.m11),
            m02,
            fma(m10, other.m00, m11 * other.m10),
            fma(m10, other.m01, m11 * other.m11),
            m12,
            fma(m20, other.m00, m21 * other.m10),
            fma(m20, other.m01, m21 * other.m11),
            m22,
            properties
        )
    }

    private fun multiplyGenericAffineR(other: Matrix3x3d): Matrix3x3d {
        return Matrix3x3d(
            fma(m00, other.m00, m01 * other.m10),
            fma(m00, other.m01, m01 * other.m11),
            fma(m00, other.m02, fma(m01, other.m12, m02)),
            fma(m10, other.m00, m11 * other.m10),
            fma(m10, other.m01, m11 * other.m11),
            fma(m10, other.m02, fma(m11, other.m12, m12)),
            fma(m20, other.m00, m21 * other.m10),
            fma(m20, other.m01, m21 * other.m11),
            fma(m20, other.m02, fma(m21, other.m12, m22)),
            properties
        )
    }

    private fun multiplyGeneric(other: Matrix3x3d): Matrix3x3d {
        return Matrix3x3d(
            fma(m00, other.m00, fma(m01, other.m10, m02 * other.m20)),
            fma(m00, other.m01, fma(m01, other.m11, m02 * other.m21)),
            fma(m00, other.m02, fma(m01, other.m12, m02 * other.m22)),
            fma(m10, other.m00, fma(m11, other.m10, m12 * other.m20)),
            fma(m10, other.m01, fma(m11, other.m11, m12 * other.m21)),
            fma(m10, other.m02, fma(m11, other.m12, m12 * other.m22)),
            fma(m20, other.m00, fma(m21, other.m10, m22 * other.m20)),
            fma(m20, other.m01, fma(m21, other.m11, m22 * other.m21)),
            fma(m20, other.m02, fma(m21, other.m12, m22 * other.m22)),
            properties or other.properties
        )
    }

    /**
     * Multiplies this matrix with a 3D vector.
     *
     * @param other The vector to multiply with.
     * @return The result of the multiplication.
     */
    operator fun times(other: Vector3d): Vector3d = Vector3d(
        fma(m00, other.x, fma(m01, other.y, m02 * other.z)),
        fma(m10, other.x, fma(m11, other.y, m12 * other.z)),
        fma(m20, other.x, fma(m21, other.y, m22 * other.z))
    )

    /**
     * Multiplies this matrix with another matrix.
     *
     * @param other The matrix to multiply with.
     * @return The result of the multiplication.
     * @throws IllegalArgumentException If the other matrix is not a [Matrix3x3d].
     */
    override fun times(other: MatrixNxN): MatrixNxN = when (other) {
        is Matrix3x3d -> this * other
        else -> throw IllegalArgumentException("Unsupported matrix type for multiplication")
    }

    /**
     * Multiplies this matrix with a vector.
     *
     * @param other The vector to multiply with.
     * @return The result of the multiplication.
     * @throws IllegalArgumentException If the other vector is not a [Vector3f].
     */
    override fun times(other: VectorN): VectorN = when (other) {
        is Vector3f -> this * other
        else -> throw IllegalArgumentException("Unsupported vector type for multiplication")
    }

    override operator fun get(index: Int): Double = when (index) {
        0 -> m00
        1 -> m01
        2 -> m02
        3 -> m10
        4 -> m11
        5 -> m12
        6 -> m20
        7 -> m21
        8 -> m22
        else -> throw IllegalArgumentException("Invalid matrix component $index for Matrix3x3f")
    }

    override operator fun get(component: MatrixComponent): Double = when (component) {
        MatrixComponent.M00 -> m00
        MatrixComponent.M01 -> m01
        MatrixComponent.M02 -> m02
        MatrixComponent.M10 -> m10
        MatrixComponent.M11 -> m11
        MatrixComponent.M12 -> m12
        MatrixComponent.M20 -> m20
        MatrixComponent.M21 -> m21
        MatrixComponent.M22 -> m22
        else -> throw IllegalArgumentException("Invalid matrix component $component for Matrix3x3f")
    }

    override fun toDoubleArray(): DoubleArray = doubleArrayOf( // @formatter:off
        m00, m01, m02,
        m10, m11, m12,
        m20, m21, m22
    ) // @formatter:on

    override fun toString(): String {
        var result = "Matrix3x3f[\n"
        result += "\t$m00, $m01, $m02\n"
        result += "\t$m10, $m11, $m12\n"
        result += "\t$m20, $m21, $m22\n"
        result += ']'
        return result
    }

    override fun equals(other: Any?): Boolean = when(other) { // @formatter:off
        is Matrix3x3d -> m00 == other.m00 &&
            m01 == other.m01 &&
            m02 == other.m02 &&
            m10 == other.m10 &&
            m11 == other.m11 &&
            m12 == other.m12 &&
            m20 == other.m20 &&
            m21 == other.m21 &&
            m22 == other.m22
        else -> false
    } // @formatter:on

    override fun hashCode(): Int {
        var result = m00.hashCode()
        result = 31 * result + m01.hashCode()
        result = 31 * result + m02.hashCode()
        result = 31 * result + m10.hashCode()
        result = 31 * result + m11.hashCode()
        result = 31 * result + m12.hashCode()
        result = 31 * result + m20.hashCode()
        result = 31 * result + m21.hashCode()
        result = 31 * result + m22.hashCode()
        return result
    }
}
