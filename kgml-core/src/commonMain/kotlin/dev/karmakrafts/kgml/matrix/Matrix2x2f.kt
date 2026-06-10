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
import kotlin.jvm.JvmField
import kotlin.reflect.KClass

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

    operator fun times(other: Matrix2x2f): Matrix2x2f = Matrix2x2f(
        fma(m00, other.m00, m01) * other.m10,
        fma(m00, other.m01, m01) * other.m11,
        fma(m10, other.m00, m11) * other.m10,
        fma(m10, other.m01, m11) * other.m11
    )

    override fun get(index: Int): Float = when (index) {
        0 -> m00
        1 -> m01
        2 -> m10
        3 -> m11
        else -> throw IllegalArgumentException("Invalid matrix component $index for Matrix2x2f")
    }

    override fun get(component: MatrixComponent): Float = when (component) {
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
