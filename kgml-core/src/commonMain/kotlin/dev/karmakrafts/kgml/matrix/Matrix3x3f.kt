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
        fun fromArray(array: FloatArray, offset: Int = 0): Matrix3x3f = Matrix3x3f( // @formatter:off
            array[offset],
            array[offset + 1],
            array[offset + 2],
            array[offset + 3],
            array[offset + 4],
            array[offset + 5],
            array[offset + 6],
            array[offset + 7],
            array[offset + 8]
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
     * The type of the matrix.
     */
    override val type: MatrixType get() = Matrix3x3f

    /**
     * Extends this 3x3 matrix to a 4x4 matrix.
     *
     * @return The extended 4x4 matrix.
     */
    fun extend(): Matrix4x4f = Matrix4x4f( // @formatter:off
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
    fun transpose(): Matrix3x3f = Matrix3x3f( // @formatter:off
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
    operator fun times(other: Matrix3x3f): Matrix3x3f = Matrix3x3f(
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
}
