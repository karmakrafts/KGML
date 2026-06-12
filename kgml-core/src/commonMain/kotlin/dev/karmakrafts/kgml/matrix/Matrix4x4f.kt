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
import dev.karmakrafts.kgml.vector.Vector4f
import dev.karmakrafts.kgml.vector.VectorN
import kotlin.jvm.JvmField
import kotlin.jvm.JvmRecord
import kotlin.reflect.KClass

/**
 * A 4x4 float matrix.
 *
 * @property m00 Row 0, Column 0
 * @property m01 Row 0, Column 1
 * @property m02 Row 0, Column 2
 * @property m03 Row 0, Column 3
 * @property m10 Row 1, Column 0
 * @property m11 Row 1, Column 1
 * @property m12 Row 1, Column 2
 * @property m13 Row 1, Column 3
 * @property m20 Row 2, Column 0
 * @property m21 Row 2, Column 1
 * @property m22 Row 2, Column 2
 * @property m23 Row 2, Column 3
 * @property m30 Row 3, Column 0
 * @property m31 Row 3, Column 1
 * @property m32 Row 3, Column 2
 * @property m33 Row 3, Column 3
 * @property properties The properties of the matrix.
 */
@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Matrix4x4f(
    @JvmField val m00: Float,
    @JvmField val m01: Float,
    @JvmField val m02: Float,
    @JvmField val m03: Float,
    @JvmField val m10: Float,
    @JvmField val m11: Float,
    @JvmField val m12: Float,
    @JvmField val m13: Float,
    @JvmField val m20: Float,
    @JvmField val m21: Float,
    @JvmField val m22: Float,
    @JvmField val m23: Float,
    @JvmField val m30: Float,
    @JvmField val m31: Float,
    @JvmField val m32: Float,
    @JvmField val m33: Float,
    override val properties: MatrixProperties = MatrixProperties.NONE
) : MatrixNxNf {
    /**
     * The type of [Matrix4x4f].
     */
    companion object : MatrixType {
        override val componentType: KClass<*> = Float::class
        override val componentSize: Int = Float.SIZE_BYTES
        override val rows: Int = 4
        override val columns: Int = 4
        override val components: Array<MatrixComponent> = MatrixComponent.entries.toTypedArray()

        /**
         * The identity matrix for [Matrix4x4f].
         */
        val identity: Matrix4x4f = Matrix4x4f()

        /**
         * Creates a [Matrix4x4f] from the given float array.
         *
         * @param array The array to read from.
         * @param offset The offset in the array.
         * @return The created matrix.
         */
        inline fun fromArray( // @formatter:off
            array: FloatArray,
            offset: Int = 0,
            properties: MatrixProperties = MatrixProperties.NONE
        ): Matrix4x4f = Matrix4x4f(
            array[offset],
            array[offset + 1],
            array[offset + 2],
            array[offset + 3],
            array[offset + 4],
            array[offset + 5],
            array[offset + 6],
            array[offset + 7],
            array[offset + 8],
            array[offset + 9],
            array[offset + 10],
            array[offset + 11],
            array[offset + 12],
            array[offset + 13],
            array[offset + 14],
            array[offset + 15],
            properties
        ) // @formatter:on

        /**
         * Creates a [Matrix4x4f] from the given rows.
         *
         * @param row0 The first row.
         * @param row1 The second row.
         * @param row2 The third row.
         * @param row3 The fourth row.
         * @param properties The properties of the matrix.
         * @return The created matrix.
         */
        inline fun fromRows( // @formatter:off
            row0: Vector4f,
            row1: Vector4f,
            row2: Vector4f,
            row3: Vector4f,
            properties: MatrixProperties = MatrixProperties.NONE
        ): Matrix4x4f = Matrix4x4f(
            row0.x, row0.y, row0.z, row0.w,
            row1.x, row1.y, row1.z, row1.w,
            row2.x, row2.y, row2.z, row2.w,
            row3.x, row3.y, row3.z, row3.w,
            properties
        ) // @formatter:on

        /**
         * Creates a [Matrix4x4f] from the given columns.
         *
         * @param column0 The first column.
         * @param column1 The second column.
         * @param column2 The third column.
         * @param column3 The fourth column.
         * @param properties The properties of the matrix.
         * @return The created matrix.
         */
        inline fun fromColumns( // @formatter:off
            column0: Vector4f,
            column1: Vector4f,
            column2: Vector4f,
            column3: Vector4f,
            properties: MatrixProperties = MatrixProperties.NONE
        ): Matrix4x4f = Matrix4x4f(
            column0.x, column1.x, column2.x, column3.x,
            column0.y, column1.y, column2.y, column3.y,
            column0.z, column1.z, column2.z, column3.z,
            column0.w, column1.w, column2.w, column3.w,
            properties
        ) // @formatter:on
    }

    /**
     * Creates an identity matrix.
     */
    constructor() : this( // @formatter:off
        1F, 0F, 0F, 0F,
        0F, 1F, 0F, 0F,
        0F, 0F, 1F, 0F,
        0F, 0F, 0F, 1F,
        MatrixProperties.IDENTITY
    ) // @formatter:on

    /**
     * Creates a matrix with all components set to the given value.
     *
     * @param value The value to set all components to.
     */
    constructor(value: Float) : this( // @formatter:off
        value, value, value, value,
        value, value, value, value,
        value, value, value, value,
        value, value, value, value
    ) // @formatter:on

    /**
     * The first row of the matrix.
     *
     * @return The first row.
     */
    inline val row0: Vector4f get() = Vector4f(m00, m01, m02, m03)

    /**
     * The second row of the matrix.
     *
     * @return The second row.
     */
    inline val row1: Vector4f get() = Vector4f(m10, m11, m12, m13)

    /**
     * The third row of the matrix.
     *
     * @return The third row.
     */
    inline val row2: Vector4f get() = Vector4f(m20, m21, m22, m23)

    /**
     * The fourth row of the matrix.
     *
     * @return The fourth row.
     */
    inline val row3: Vector4f get() = Vector4f(m30, m31, m32, m33)

    /**
     * The first column of the matrix.
     *
     * @return The first column.
     */
    inline val column0: Vector4f get() = Vector4f(m00, m10, m20, m30)

    /**
     * The second column of the matrix.
     *
     * @return The second column.
     */
    inline val column1: Vector4f get() = Vector4f(m01, m11, m21, m31)

    /**
     * The third column of the matrix.
     *
     * @return The third column.
     */
    inline val column2: Vector4f get() = Vector4f(m02, m12, m22, m32)

    /**
     * The fourth column of the matrix.
     *
     * @return The fourth column.
     */
    inline val column3: Vector4f get() = Vector4f(m03, m13, m23, m33)

    /**
     * The type of the matrix.
     */
    override val type: MatrixType get() = Matrix4x4f

    /**
     * Transposes this matrix.
     *
     * @return The transposed matrix.
     */
    inline fun transpose(): Matrix4x4f = Matrix4x4f( // @formatter:off
        m00, m10, m20, m30,
        m01, m11, m21, m31,
        m02, m12, m22, m32,
        m03, m13, m23, m33,
        properties
    ) // @formatter:on

    /**
     * Multiplies this matrix with another 4x4 matrix.
     *
     * @param other The matrix to multiply with.
     * @return The result of the multiplication.
     */
    operator fun times(other: Matrix4x4f): Matrix4x4f = when {
        other.properties.isPerspective -> multiplyGeneric(other) // Perspective is expensive!
        other.properties.isTranslation -> multiplyAffineTranslationR(other)
        other.properties.isAffine -> multiplyAffineR(other)
        properties.isIdentity -> other
        other.properties.isIdentity -> this
        else -> multiplyGeneric(other)
    }

    private fun multiplyAffineTranslationR(other: Matrix4x4f): Matrix4x4f {
        val ( // @formatter:off
            o00, o01, o02, o03,
            o10, o11, o12, o13,
            o20, o21, o22, o23,
            _,   _,   _,   _
        ) = other // @formatter:on
        return Matrix4x4f(
            fma(m00, o00, fma(m01, o10, m02 * o20)),
            fma(m00, o01, fma(m01, o11, m02 * o21)),
            fma(m00, o02, fma(m01, o12, m02 * o22)),
            fma(m00, o03, fma(m01, o13, fma(m02, o23, m03))),
            fma(m10, o00, fma(m11, o10, m12 * o20)),
            fma(m10, o01, fma(m11, o11, m12 * o21)),
            fma(m10, o02, fma(m11, o12, m12 * o22)),
            fma(m10, o03, fma(m11, o13, fma(m12, o23, m13))),
            fma(m20, o00, fma(m21, o10, m22 * o20)),
            fma(m20, o01, fma(m21, o11, m22 * o21)),
            fma(m20, o02, fma(m21, o12, m22 * o22)),
            fma(m20, o03, fma(m21, o13, fma(m22, o23, m23))),
            m30,
            m31,
            m32,
            m33,
            properties or other.properties
        )
    }

    private fun multiplyAffineR(other: Matrix4x4f): Matrix4x4f {
        val ( // @formatter:off
            o00, o01, o02, _,
            o10, o11, o12, _,
            o20, o21, o22, _,
            _,   _,   _,   _
        ) = other // @formatter:on
        return Matrix4x4f(
            fma(m00, o00, fma(m01, o10, m02 * o20)),
            fma(m00, o01, fma(m01, o11, m02 * o21)),
            fma(m00, o02, fma(m01, o12, m02 * o22)),
            other.m03,
            fma(m10, o00, fma(m11, o10, m12 * o20)),
            fma(m10, o01, fma(m11, o11, m12 * o21)),
            fma(m10, o02, fma(m11, o12, m12 * o22)),
            other.m13,
            fma(m20, o00, fma(m21, o10, m22 * o20)),
            fma(m20, o01, fma(m21, o11, m22 * o21)),
            fma(m20, o02, fma(m21, o12, m22 * o22)),
            m23,
            m30,
            m31,
            m32,
            m33,
            properties or other.properties
        )
    }

    private fun multiplyGeneric(other: Matrix4x4f): Matrix4x4f {
        val ( // @formatter:off
            o00, o01, o02, o03,
            o10, o11, o12, o13,
            o20, o21, o22, o23,
            o30, o31, o32, o33
        ) = other // @formatter:on
        return Matrix4x4f(
            fma(m00, o00, fma(m01, o10, fma(m02, o20, m03 * o30))),
            fma(m00, o01, fma(m01, o11, fma(m02, o21, m03 * o31))),
            fma(m00, o02, fma(m01, o12, fma(m02, o22, m03 * o32))),
            fma(m00, o03, fma(m01, o13, fma(m02, o23, m03 * o33))),
            fma(m10, o00, fma(m11, o10, fma(m12, o20, m13 * o30))),
            fma(m10, o01, fma(m11, o11, fma(m12, o21, m13 * o31))),
            fma(m10, o02, fma(m11, o12, fma(m12, o22, m13 * o32))),
            fma(m10, o03, fma(m11, o13, fma(m12, o23, m13 * o33))),
            fma(m20, o00, fma(m21, o10, fma(m22, o20, m23 * o30))),
            fma(m20, o01, fma(m21, o11, fma(m22, o21, m23 * o31))),
            fma(m20, o02, fma(m21, o12, fma(m22, o22, m23 * o32))),
            fma(m20, o03, fma(m21, o13, fma(m22, o23, m23 * o33))),
            fma(m30, o00, fma(m31, o10, fma(m32, o20, m33 * o30))),
            fma(m30, o01, fma(m31, o11, fma(m32, o21, m33 * o31))),
            fma(m30, o02, fma(m31, o12, fma(m32, o22, m33 * o32))),
            fma(m30, o03, fma(m31, o13, fma(m32, o23, m33 * o33))),
            properties or other.properties
        )
    }

    /**
     * Multiplies this matrix with a 4D vector.
     *
     * @param other The vector to multiply with.
     * @return The result of the multiplication.
     */
    operator fun times(other: Vector4f): Vector4f {
        val (ox, oy, oz, ow) = other
        return Vector4f(
            fma(m00, ox, fma(m01, oy, fma(m02, oz, m03 * ow))),
            fma(m10, ox, fma(m11, oy, fma(m12, oz, m13 * ow))),
            fma(m20, ox, fma(m21, oy, fma(m22, oz, m23 * ow))),
            fma(m30, ox, fma(m31, oy, fma(m32, oz, m33 * ow)))
        )
    }

    /**
     * Multiplies this matrix with another matrix.
     *
     * @param other The matrix to multiply with.
     * @return The result of the multiplication.
     * @throws IllegalArgumentException If the other matrix is not a [Matrix4x4f].
     */
    override fun times(other: MatrixNxN): MatrixNxN = when (other) {
        is Matrix4x4f -> this * other
        else -> throw IllegalArgumentException("Unsupported matrix type for multiplication")
    }

    /**
     * Multiplies this matrix with a vector.
     *
     * @param other The vector to multiply with.
     * @return The result of the multiplication.
     * @throws IllegalArgumentException If the other vector is not a [Vector4f].
     */
    override fun times(other: VectorN): VectorN = when (other) {
        is Vector4f -> this * other
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
        3 -> m03
        4 -> m10
        5 -> m11
        6 -> m12
        7 -> m13
        8 -> m20
        9 -> m21
        10 -> m22
        11 -> m23
        12 -> m30
        13 -> m31
        14 -> m32
        15 -> m33
        else -> throw IllegalArgumentException("Invalid matrix component $index for Matrix4x4f")
    }

    /**
     * Gets the component for the given [MatrixComponent].
     *
     * @param component The component to get.
     * @return The component for the given [MatrixComponent].
     */
    override operator fun get(component: MatrixComponent): Float = when (component) {
        MatrixComponent.M00 -> m00
        MatrixComponent.M01 -> m01
        MatrixComponent.M02 -> m02
        MatrixComponent.M03 -> m03
        MatrixComponent.M10 -> m10
        MatrixComponent.M11 -> m11
        MatrixComponent.M12 -> m12
        MatrixComponent.M13 -> m13
        MatrixComponent.M20 -> m20
        MatrixComponent.M21 -> m21
        MatrixComponent.M22 -> m22
        MatrixComponent.M23 -> m23
        MatrixComponent.M30 -> m30
        MatrixComponent.M31 -> m31
        MatrixComponent.M32 -> m32
        MatrixComponent.M33 -> m33
    }

    /**
     * Converts this matrix to a float array.
     *
     * @return The matrix as a float array.
     */
    override fun toFloatArray(): FloatArray = floatArrayOf( // @formatter:off
        m00, m01, m02, m03,
        m10, m11, m12, m13,
        m20, m21, m22, m23,
        m30, m31, m32, m33
    ) // @formatter:on

    override fun toString(): String {
        var result = "Matrix4x4f[\n"
        result += "\t$m00, $m01, $m02, $m03\n"
        result += "\t$m10, $m11, $m12, $m13\n"
        result += "\t$m20, $m21, $m22, $m23\n"
        result += "\t$m30, $m31, $m32, $m33\n"
        result += ']'
        return result
    }

    override fun equals(other: Any?): Boolean = when(other) { // @formatter:off
        is Matrix4x4f -> m00 == other.m00 &&
            m01 == other.m01 &&
            m02 == other.m02 &&
            m03 == other.m03 &&
            m10 == other.m10 &&
            m11 == other.m11 &&
            m12 == other.m12 &&
            m13 == other.m13 &&
            m20 == other.m20 &&
            m21 == other.m21 &&
            m22 == other.m22 &&
            m23 == other.m23 &&
            m30 == other.m30 &&
            m31 == other.m31 &&
            m32 == other.m32 &&
            m33 == other.m33
        else -> false
    } // @formatter:on

    override fun hashCode(): Int {
        var result = m00.hashCode()
        result = 31 * result + m01.hashCode()
        result = 31 * result + m02.hashCode()
        result = 31 * result + m03.hashCode()
        result = 31 * result + m10.hashCode()
        result = 31 * result + m11.hashCode()
        result = 31 * result + m12.hashCode()
        result = 31 * result + m13.hashCode()
        result = 31 * result + m20.hashCode()
        result = 31 * result + m21.hashCode()
        result = 31 * result + m22.hashCode()
        result = 31 * result + m23.hashCode()
        result = 31 * result + m30.hashCode()
        result = 31 * result + m31.hashCode()
        result = 31 * result + m32.hashCode()
        result = 31 * result + m33.hashCode()
        return result
    }
}
