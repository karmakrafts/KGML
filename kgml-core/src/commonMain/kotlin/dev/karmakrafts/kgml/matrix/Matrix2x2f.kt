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

@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Matrix2x2f( // @formatter:off
    @JvmField val m00: Float,
    @JvmField val m01: Float,
    @JvmField val m10: Float,
    @JvmField val m11: Float
) : MatrixNxNf { // @formatter:on
    companion object : MatrixType {
        override val componentType: KClass<*> = Float::class
        override val componentSize: Int = Float.SIZE_BYTES
        override val rows: Int = 2
        override val columns: Int = 2

        override val components: Array<MatrixComponent> = arrayOf( // @formatter:off
            MatrixComponent.M00, MatrixComponent.M01,
            MatrixComponent.M10, MatrixComponent.M11
        ) // @formatter:on

        val identity: Matrix2x2f = Matrix2x2f()

        fun fromArray(array: FloatArray, offset: Int = 0): Matrix2x2f = Matrix2x2f( // @formatter:off
            array[offset],
            array[offset + 1],
            array[offset + 2],
            array[offset + 3]
        ) // @formatter:on
    }

    constructor() : this( // @formatter:off
        1F, 0F,
        0F, 1F
    ) // @formatter:on

    constructor(value: Float) : this( // @formatter:off
        value, value,
        value, value
    ) // @formatter:on

    override val type: MatrixType get() = Matrix2x2f

    fun extend(): Matrix3x3f = Matrix3x3f( // @formatter:off
        m00, m01, 0F,
        m10, m11, 0F,
        0F,  0F,  1F
    ) // @formatter:on

    fun transpose(): Matrix2x2f = Matrix2x2f( // @formatter:off
        m00, m10,
        m01, m11
    ) // @formatter:on

    operator fun times(other: Matrix2x2f): Matrix2x2f = Matrix2x2f(
        fma(m00, other.m00, m01) * other.m10,
        fma(m00, other.m01, m01) * other.m11,
        fma(m10, other.m00, m11) * other.m10,
        fma(m10, other.m01, m11) * other.m11
    )

    operator fun times(other: Vector2f): Vector2f = Vector2f( // @formatter:off
        fma(m00, other.x, m01) * other.y,
        fma(m10, other.x, m11) * other.y
    ) // @formatter:on

    override fun times(other: MatrixNxN): MatrixNxN = when (other) {
        is Matrix2x2f -> this * other
        else -> throw IllegalArgumentException("Unsupported matrix type for multiplication")
    }

    override fun times(other: VectorN): VectorN = when (other) {
        is Vector2f -> this * other
        else -> throw IllegalArgumentException("Unsupported vector type for multiplication")
    }

    override operator fun get(index: Int): Float = when (index) {
        0 -> m00
        1 -> m01
        2 -> m10
        3 -> m11
        else -> throw IllegalArgumentException("Invalid matrix component $index for Matrix2x2f")
    }

    override operator fun get(component: MatrixComponent): Float = when (component) {
        MatrixComponent.M00 -> m00
        MatrixComponent.M01 -> m01
        MatrixComponent.M10 -> m10
        MatrixComponent.M11 -> m11
        else -> throw IllegalArgumentException("Invalid matrix component $component for Matrix2x2f")
    }

    override fun toFloatArray(): FloatArray = floatArrayOf( // @formatter:off
        m00, m01,
        m10, m11
    ) // @formatter:on
}
