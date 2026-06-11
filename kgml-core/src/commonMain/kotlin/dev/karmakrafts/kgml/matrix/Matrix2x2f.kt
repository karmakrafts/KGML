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
import dev.karmakrafts.kgml.vector.Vector2f
import dev.karmakrafts.kgml.vector.VectorN
import kotlin.jvm.JvmField
import kotlin.jvm.JvmRecord
import kotlin.reflect.KClass

/**
 * A 2x2 float matrix.
 *
 * @property m00 Row 0, Column 0
 * @property m01 Row 0, Column 1
 * @property m10 Row 1, Column 0
 * @property m11 Row 1, Column 1
 */
@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Matrix2x2f( // @formatter:off
    @JvmField val m00: Float,
    @JvmField val m01: Float,
    @JvmField val m10: Float,
    @JvmField val m11: Float
) : MatrixNxNf { // @formatter:on
    /**
     * The type of [Matrix2x2f].
     */
    companion object : MatrixType {
        override val componentType: KClass<*> = Float::class
        override val componentSize: Int = Float.SIZE_BYTES
        override val rows: Int = 2
        override val columns: Int = 2

        override val components: Array<MatrixComponent> = arrayOf( // @formatter:off
            MatrixComponent.M00, MatrixComponent.M01,
            MatrixComponent.M10, MatrixComponent.M11
        ) // @formatter:on

        /**
         * The identity matrix for [Matrix2x2f].
         */
        val identity: Matrix2x2f = Matrix2x2f()

        /**
         * Creates a [Matrix2x2f] from the given float array.
         *
         * @param array The array to read from.
         * @param offset The offset in the array.
         * @return The created matrix.
         */
        fun fromArray(array: FloatArray, offset: Int = 0): Matrix2x2f = Matrix2x2f( // @formatter:off
            array[offset],
            array[offset + 1],
            array[offset + 2],
            array[offset + 3]
        ) // @formatter:on
    }

    /**
     * Creates an identity matrix.
     */
    constructor() : this( // @formatter:off
        1F, 0F,
        0F, 1F
    ) // @formatter:on

    /**
     * Creates a matrix with all components set to the given value.
     *
     * @param value The value to set all components to.
     */
    constructor(value: Float) : this( // @formatter:off
        value, value,
        value, value
    ) // @formatter:on

    /**
     * The type of the matrix.
     */
    override val type: MatrixType get() = Matrix2x2f

    /**
     * Extends this 2x2 matrix to a 3x3 matrix.
     */
    fun extend(): Matrix3x3f = Matrix3x3f( // @formatter:off
        m00, m01, 0F,
        m10, m11, 0F,
        0F,  0F,  1F
    ) // @formatter:on

    /**
     * Transposes this matrix.
     *
     * @return The transposed matrix.
     */
    fun transpose(): Matrix2x2f = Matrix2x2f( // @formatter:off
        m00, m10,
        m01, m11
    ) // @formatter:on

    /**
     * Multiplies this matrix with another 2x2 matrix.
     *
     * @param other The matrix to multiply with.
     * @return The result of the multiplication.
     */
    operator fun times(other: Matrix2x2f): Matrix2x2f = Matrix2x2f(
        fma(m00, other.m00, m01 * other.m10),
        fma(m00, other.m01, m01 * other.m11),
        fma(m10, other.m00, m11 * other.m10),
        fma(m10, other.m01, m11 * other.m11)
    )

    /**
     * Multiplies this matrix with a 2D vector.
     *
     * @param other The vector to multiply with.
     * @return The result of the multiplication.
     */
    operator fun times(other: Vector2f): Vector2f = Vector2f( // @formatter:off
        fma(m00, other.x, m01 * other.y),
        fma(m10, other.x, m11 * other.y)
    ) // @formatter:on

    /**
     * Multiplies this matrix with another matrix.
     *
     * @param other The matrix to multiply with.
     * @return The result of the multiplication.
     * @throws IllegalArgumentException If the other matrix is not a [Matrix2x2f].
     */
    override fun times(other: MatrixNxN): MatrixNxN = when (other) {
        is Matrix2x2f -> this * other
        else -> throw IllegalArgumentException("Unsupported matrix type for multiplication")
    }

    /**
     * Multiplies this matrix with a vector.
     *
     * @param other The vector to multiply with.
     * @return The result of the multiplication.
     * @throws IllegalArgumentException If the other vector is not a [Vector2f].
     */
    override fun times(other: VectorN): VectorN = when (other) {
        is Vector2f -> this * other
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
        2 -> m10
        3 -> m11
        else -> throw IllegalArgumentException("Invalid matrix component $index for Matrix2x2f")
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
        MatrixComponent.M10 -> m10
        MatrixComponent.M11 -> m11
        else -> throw IllegalArgumentException("Invalid matrix component $component for Matrix2x2f")
    }

    /**
     * Converts this matrix to a float array.
     *
     * @return The matrix as a float array.
     */
    override fun toFloatArray(): FloatArray = floatArrayOf( // @formatter:off
        m00, m01,
        m10, m11
    ) // @formatter:on
}
