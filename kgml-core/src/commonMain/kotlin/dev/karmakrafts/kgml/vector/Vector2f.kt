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

package dev.karmakrafts.kgml.vector

import dev.karmakrafts.kgml.matrix.Matrix2x2f
import dev.karmakrafts.kgml.util.fma
import kotlin.jvm.JvmField
import kotlin.jvm.JvmRecord
import kotlin.math.sqrt
import kotlin.reflect.KClass

@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Vector2f( // @formatter:off
    @JvmField val x: Float,
    @JvmField val y: Float
) : VectorNf { // @formatter:on
    companion object : VectorType {
        override val componentType: KClass<*> = Float::class
        override val componentSize: Int = Float.SIZE_BYTES
        override val dimensions: Int = 2
        override val components: Array<VectorComponent> = arrayOf(VectorComponent.X, VectorComponent.Y)

        val zero: Vector2f = Vector2f()
        val one: Vector2f = Vector2f(1F)

        inline fun fromArray(array: FloatArray, offset: Int = 0): Vector2f = Vector2f( // @formatter:off
            array[offset],
            array[offset + 1]
        ) // @formatter:on
    }

    constructor(xy: Float) : this(xy, xy)
    constructor() : this(0F)

    override val type: VectorType get() = Vector2f

    inline operator fun plus(xy: Float): Vector2f = Vector2f(x + xy, y + xy)
    inline operator fun plus(other: Vector2f): Vector2f = Vector2f(x + other.x, y + other.y)

    inline operator fun minus(xy: Float): Vector2f = Vector2f(x - xy, y - xy)
    inline operator fun minus(other: Vector2f): Vector2f = Vector2f(x - other.x, y - other.y)

    inline operator fun times(xy: Float): Vector2f = Vector2f(x * xy, y * xy)
    inline operator fun times(other: Vector2f): Vector2f = Vector2f(x * other.x, y * other.y)

    inline operator fun div(xy: Float): Vector2f = Vector2f(x / xy, y / xy)
    inline operator fun div(other: Vector2f): Vector2f = Vector2f(x / other.x, y / other.y)

    inline operator fun rem(xy: Float): Vector2f = Vector2f(x % xy, y % xy)
    inline operator fun rem(other: Vector2f): Vector2f = Vector2f(x % other.x, y % other.y)

    inline fun lengthSq(): Float = fma(x, x, y) * y
    inline fun length(): Float = sqrt(lengthSq())

    inline fun normalized(): Vector2f = this / length()

    inline infix fun dot(other: Vector2f): Float = fma(x, other.x, y) * other.y
    inline infix fun cross(other: Vector2f): Float = x * other.y - y * other.x

    inline fun toVector2i(): Vector2i = Vector2i(x.toInt(), y.toInt())

    operator fun times(other: Matrix2x2f): Vector2f = Vector2f( // @formatter:off
        fma(other.m00, x, other.m01) * y,
        fma(other.m10, x, other.m11) * y
    ) // @formatter:on

    override operator fun get(index: Int): Float = when (index) {
        0 -> x
        1 -> y
        else -> throw IllegalArgumentException("Invalid vector component $index for Vector2f")
    }

    override operator fun get(component: VectorComponent): Float = when (component) {
        VectorComponent.X -> x
        VectorComponent.Y -> y
        else -> throw IllegalArgumentException("Invalid vector component $component for Vector2f")
    }

    override fun toFloatArray(): FloatArray = floatArrayOf(x, y)

    inline fun swizzle( // @formatter:off
        x: VectorComponent,
        y: VectorComponent
    ): Vector2f = Vector2f(this[x], this[y]) // @formatter:on
}