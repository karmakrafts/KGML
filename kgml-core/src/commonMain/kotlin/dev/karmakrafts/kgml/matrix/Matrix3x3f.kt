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

import kotlin.jvm.JvmField
import kotlin.reflect.KClass

data class Matrix3x3f(
    @JvmField val m00: Float,
    @JvmField val m01: Float,
    @JvmField val m02: Float,
    @JvmField val m10: Float,
    @JvmField val m11: Float,
    @JvmField val m12: Float,
    @JvmField val m20: Float,
    @JvmField val m21: Float,
    @JvmField val m22: Float
) : MatrixNxNf {
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
    }

    override val type: MatrixType get() = Matrix3x3f

    override fun get(index: Int): Float = when (index) {
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

    override fun get(component: MatrixComponent): Float = when (component) {
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

    override fun toFloatArray(): FloatArray = floatArrayOf( // @formatter:off
        m00, m01, m02,
        m10, m11, m12,
        m20, m21, m22
    ) // @formatter:on
}
