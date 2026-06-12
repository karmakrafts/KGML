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
import dev.karmakrafts.kgml.vector.Vector3f
import dev.karmakrafts.kgml.vector.VectorN
import kotlin.jvm.JvmField
import kotlin.jvm.JvmRecord
import kotlin.reflect.KClass

/**
 * A 3x3 float matrix.
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
data class Matrix3x3f(
    @JvmField val m00: Float,
    @JvmField val m01: Float,
    @JvmField val m02: Float,
    @JvmField val m10: Float,
    @JvmField val m11: Float,
    @JvmField val m12: Float,
    @JvmField val m20: Float,
    @JvmField val m21: Float,
    @JvmField val m22: Float,
    override val properties: MatrixProperties = MatrixProperties.NONE
) : MatrixNxNf {
    /**
     * The type of [Matrix3x3f].
     */
    companion object : MatrixType {
        override val componentType: KClass<*> = Float::class
        override val componentSize: Int = Float.SIZE_BYTES
        override val rows: Int = 3
        override val columns: Int = 3

        override val components: Array<MatrixComponent> = arrayOf( // @formatter:off
            MatrixComponent.M00, MatrixComponent.M01, MatrixComponent.M02,
            MatrixComponent.M10, MatrixComponent.M11, MatrixComponent.M12,
            MatrixComponent.M20, MatrixComponent.M21, MatrixComponent.M22
        ) // @formatter:on

        /**
         * The identity matrix for [Matrix3x3f].
         */
        val identity: Matrix3x3f = Matrix3x3f()

        /**
         * Creates a [Matrix3x3f] from the given float array.
         *
         * @param array The array to read from.
         * @param offset The offset in the array.
         * @return The created matrix.
         */
        inline fun fromArray( // @formatter:off
            array: FloatArray,
            offset: Int = 0,
            properties: MatrixProperties = MatrixProperties.NONE
        ): Matrix3x3f = Matrix3x3f(
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
         * Creates a [Matrix3x3f] from the given rows.
         *
         * @param row0 The first row.
         * @param row1 The second row.
         * @param row2 The third row.
         * @param properties The properties of the matrix.
         * @return The created matrix.
         */
        inline fun fromRows( // @formatter:off
            row0: Vector3f,
            row1: Vector3f,
            row2: Vector3f,
            properties: MatrixProperties = MatrixProperties.NONE
        ): Matrix3x3f = Matrix3x3f(
            row0.x, row0.y, row0.z,
            row1.x, row1.y, row1.z,
            row2.x, row2.y, row2.z,
            properties
        ) // @formatter:on

        /**
         * Creates a [Matrix3x3f] from the given columns.
         *
         * @param column0 The first column.
         * @param column1 The second column.
         * @param column2 The third column.
         * @param properties The properties of the matrix.
         * @return The created matrix.
         */
        inline fun fromColumns( // @formatter:off
            column0: Vector3f,
            column1: Vector3f,
            column2: Vector3f,
            properties: MatrixProperties = MatrixProperties.NONE
        ): Matrix3x3f = Matrix3x3f(
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
        1F, 0F, 0F,
        0F, 1F, 0F,
        0F, 0F, 1F,
        MatrixProperties.IDENTITY
    ) // @formatter:on

    /**
     * Creates a matrix with all components set to the given value.
     *
     * @param value The value to set all components to.
     */
    constructor(value: Float) : this( // @formatter:off
        value, value, value,
        value, value, value,
        value, value, value
    ) // @formatter:on

    /**
     * The first row of the matrix.
     *
     * @return The first row.
     */
    inline val row0: Vector3f get() = Vector3f(m00, m01, m02)

    /**
     * The second row of the matrix.
     *
     * @return The second row.
     */
    inline val row1: Vector3f get() = Vector3f(m10, m11, m12)

    /**
     * The third row of the matrix.
     *
     * @return The third row.
     */
    inline val row2: Vector3f get() = Vector3f(m20, m21, m22)

    /**
     * The first column of the matrix.
     *
     * @return The first column.
     */
    inline val column0: Vector3f get() = Vector3f(m00, m10, m20)

    /**
     * The second column of the matrix.
     *
     * @return The second column.
     */
    inline val column1: Vector3f get() = Vector3f(m01, m11, m21)

    /**
     * The third column of the matrix.
     *
     * @return The third column.
     */
    inline val column2: Vector3f get() = Vector3f(m02, m12, m22)

    /**
     * The type of the matrix.
     */
    override val type: MatrixType get() = Matrix3x3f

    /**
     * Extends this 3x3 matrix to a 4x4 matrix.
     *
     * @return The extended 4x4 matrix.
     */
    inline fun extend(): Matrix4x4f = Matrix4x4f( // @formatter:off
        m00, m01, m02, 0F,
        m10, m11, m12, 0F,
        m20, m21, m22, 0F,
        0F,  0F,  0F,  1F,
        properties
    ) // @formatter:on

    /**
     * Transposes this matrix.
     *
     * @return The transposed matrix.
     */
    inline fun transpose(): Matrix3x3f = Matrix3x3f( // @formatter:off
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
    operator fun times(other: Matrix3x3f): Matrix3x3f = when {
        properties.isIdentity -> other
        other.properties.isIdentity -> this
        else -> multiplyGeneric(other)
    }

    private fun multiplyGeneric(other: Matrix3x3f): Matrix3x3f {
        val ( // @formatter:off
            o00, o01, o02,
            o10, o11, o12,
            o20, o21, o22
        ) = other // @formatter:on
        return Matrix3x3f(
            fma(m00, o00, fma(m01, o10, m02 * o20)),
            fma(m00, o01, fma(m01, o11, m02 * o21)),
            fma(m00, o02, fma(m01, o12, m02 * o22)),
            fma(m10, o00, fma(m11, o10, m12 * o20)),
            fma(m10, o01, fma(m11, o11, m12 * o21)),
            fma(m10, o02, fma(m11, o12, m12 * o22)),
            fma(m20, o00, fma(m21, o10, m22 * o20)),
            fma(m20, o01, fma(m21, o11, m22 * o21)),
            fma(m20, o02, fma(m21, o12, m22 * o22)),
            properties or other.properties
        )
    }

    /**
     * Multiplies this matrix with a 3D vector.
     *
     * @param other The vector to multiply with.
     * @return The result of the multiplication.
     */
    operator fun times(other: Vector3f): Vector3f = Vector3f(
        fma(m00, other.x, fma(m01, other.y, m02 * other.z)),
        fma(m10, other.x, fma(m11, other.y, m12 * other.z)),
        fma(m20, other.x, fma(m21, other.y, m22 * other.z))
    )

    /**
     * Multiplies this matrix with another matrix.
     *
     * @param other The matrix to multiply with.
     * @return The result of the multiplication.
     * @throws IllegalArgumentException If the other matrix is not a [Matrix3x3f].
     */
    override fun times(other: MatrixNxN): MatrixNxN = when (other) {
        is Matrix3x3f -> this * other
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

    /**
     * Gets the component at the given index.
     *
     * @param index The index of the component.
     * @return The component at the given index.
     * @throws IllegalArgumentException If the index is out of bounds.
     */
    override operator fun get(index: Int): Float = when (index) {
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

    /**
     * Gets the component for the given [MatrixComponent].
     *
     * @param component The component to get.
     * @return The component for the given [MatrixComponent].
     * @throws IllegalArgumentException If the component is not valid for this matrix.
     */
    override operator fun get(component: MatrixComponent): Float = when (component) {
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

    /**
     * Converts this matrix to a float array.
     *
     * @return The matrix as a float array.
     */
    override fun toFloatArray(): FloatArray = floatArrayOf( // @formatter:off
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
        is Matrix3x3f -> m00 == other.m00 &&
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
