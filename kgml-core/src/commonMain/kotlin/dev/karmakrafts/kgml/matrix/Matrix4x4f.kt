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
    @JvmField val m33: Float
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
        fun fromArray(array: FloatArray, offset: Int = 0): Matrix4x4f = Matrix4x4f( // @formatter:off
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
            array[offset + 15]
        ) // @formatter:on
    }

    /**
     * Creates an identity matrix.
     */
    constructor() : this( // @formatter:off
        1F, 0F, 0F, 0F,
        0F, 1F, 0F, 0F,
        0F, 0F, 1F, 0F,
        0F, 0F, 0F, 1F
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
     * The type of the matrix.
     */
    override val type: MatrixType get() = Matrix4x4f

    /**
     * Transposes this matrix.
     *
     * @return The transposed matrix.
     */
    fun transpose(): Matrix4x4f = Matrix4x4f( // @formatter:off
        m00, m10, m20, m30,
        m01, m11, m21, m31,
        m02, m12, m22, m32,
        m03, m13, m23, m33
    ) // @formatter:on

    /**
     * Multiplies this matrix with another 4x4 matrix.
     *
     * @param other The matrix to multiply with.
     * @return The result of the multiplication.
     */
    operator fun times(other: Matrix4x4f): Matrix4x4f = Matrix4x4f(
        fma(m00, other.m00, fma(m01, other.m10, fma(m02, other.m20, m03 * other.m30))),
        fma(m00, other.m01, fma(m01, other.m11, fma(m02, other.m21, m03 * other.m31))),
        fma(m00, other.m02, fma(m01, other.m12, fma(m02, other.m22, m03 * other.m32))),
        fma(m00, other.m03, fma(m01, other.m13, fma(m02, other.m23, m03 * other.m33))),
        fma(m10, other.m00, fma(m11, other.m10, fma(m12, other.m20, m13 * other.m30))),
        fma(m10, other.m01, fma(m11, other.m11, fma(m12, other.m21, m13 * other.m31))),
        fma(m10, other.m02, fma(m11, other.m12, fma(m12, other.m22, m13 * other.m32))),
        fma(m10, other.m03, fma(m11, other.m13, fma(m12, other.m23, m13 * other.m33))),
        fma(m20, other.m00, fma(m21, other.m10, fma(m22, other.m20, m23 * other.m30))),
        fma(m20, other.m01, fma(m21, other.m11, fma(m22, other.m21, m23 * other.m31))),
        fma(m20, other.m02, fma(m21, other.m12, fma(m22, other.m22, m23 * other.m32))),
        fma(m20, other.m03, fma(m21, other.m13, fma(m22, other.m23, m23 * other.m33))),
        fma(m30, other.m00, fma(m31, other.m10, fma(m32, other.m20, m33 * other.m30))),
        fma(m30, other.m01, fma(m31, other.m11, fma(m32, other.m21, m33 * other.m31))),
        fma(m30, other.m02, fma(m31, other.m12, fma(m32, other.m22, m33 * other.m32))),
        fma(m30, other.m03, fma(m31, other.m13, fma(m32, other.m23, m33 * other.m33)))
    )

    /**
     * Multiplies this matrix with a 4D vector.
     *
     * @param other The vector to multiply with.
     * @return The result of the multiplication.
     */
    operator fun times(other: Vector4f): Vector4f = Vector4f(
        fma(m00, other.x, fma(m01, other.y, fma(m02, other.z, m03 * other.w))),
        fma(m10, other.x, fma(m11, other.y, fma(m12, other.z, m13 * other.w))),
        fma(m20, other.x, fma(m21, other.y, fma(m22, other.z, m23 * other.w))),
        fma(m30, other.x, fma(m31, other.y, fma(m32, other.z, m33 * other.w)))
    )

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
}
