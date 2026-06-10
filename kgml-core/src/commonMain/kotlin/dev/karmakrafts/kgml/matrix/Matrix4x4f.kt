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
    companion object : MatrixType {
        override val componentType: KClass<*> = Float::class
        override val componentSize: Int = Float.SIZE_BYTES
        override val rows: Int = 4
        override val columns: Int = 4
        override val components: Array<MatrixComponent> = MatrixComponent.entries.toTypedArray()

        val identity: Matrix4x4f = Matrix4x4f()
    }

    constructor() : this( // @formatter:off
        1F, 0F, 0F, 0F,
        0F, 1F, 0F, 0F,
        0F, 0F, 1F, 0F,
        0F, 0F, 0F, 1F
    ) // @formatter:on

    constructor(value: Float) : this( // @formatter:off
        value, value, value, value,
        value, value, value, value,
        value, value, value, value,
        value, value, value, value
    ) // @formatter:on

    override val type: MatrixType get() = Matrix4x4f

    operator fun times(other: Matrix4x4f): Matrix4x4f = Matrix4x4f(
        fma(fma(fma(m00, other.m00, m10), other.m01, m20), other.m02, m30) * other.m03,
        fma(fma(fma(m01, other.m00, m11), other.m01, m21), other.m02, m31) * other.m03,
        fma(fma(fma(m02, other.m00, m12), other.m01, m22), other.m02, m32) * other.m03,
        fma(fma(fma(m03, other.m00, m13), other.m01, m23), other.m02, m33) * other.m03,
        fma(fma(fma(m00, other.m10, m10), other.m11, m20), other.m12, m30) * other.m13,
        fma(fma(fma(m01, other.m10, m11), other.m11, m21), other.m12, m31) * other.m13,
        fma(fma(fma(m02, other.m10, m12), other.m11, m22), other.m12, m32) * other.m13,
        fma(fma(fma(m03, other.m10, m13), other.m11, m23), other.m12, m33) * other.m13,
        fma(fma(fma(m00, other.m20, m10), other.m21, m20), other.m22, m30) * other.m23,
        fma(fma(fma(m01, other.m20, m11), other.m21, m21), other.m22, m31) * other.m23,
        fma(fma(fma(m02, other.m20, m12), other.m21, m22), other.m22, m32) * other.m23,
        fma(fma(fma(m03, other.m20, m13), other.m21, m23), other.m22, m33) * other.m23,
        fma(fma(fma(m00, other.m30, m10), other.m31, m20), other.m32, m30) * other.m33,
        fma(fma(fma(m01, other.m30, m11), other.m31, m31), other.m32, m31) * other.m33,
        fma(fma(fma(m02, other.m30, m12), other.m31, m32), other.m32, m32) * other.m33,
        fma(fma(fma(m03, other.m30, m13), other.m31, m33), other.m32, m33) * other.m33
    )

    override fun get(index: Int): Float = when (index) {
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

    override fun get(component: MatrixComponent): Float = when (component) {
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

    override fun toFloatArray(): FloatArray = floatArrayOf( // @formatter:off
        m00, m01, m02, m03,
        m10, m11, m12, m13,
        m20, m21, m22, m23,
        m30, m31, m32, m33
    ) // @formatter:on
}
