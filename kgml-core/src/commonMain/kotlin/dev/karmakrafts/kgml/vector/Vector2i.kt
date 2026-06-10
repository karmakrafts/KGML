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

import dev.karmakrafts.kgml.util.fma
import kotlin.jvm.JvmField
import kotlin.math.sqrt
import kotlin.reflect.KClass

@Suppress("NOTHING_TO_INLINE")
data class Vector2i( // @formatter:off
    @JvmField val x: Int,
    @JvmField val y: Int
) : VectorNi { // @formatter:on
    companion object : VectorType {
        override val componentType: KClass<*> = Int::class
        override val componentSize: Int = Int.SIZE_BYTES
        override val dimensions: Int = 2
        override val components: Array<VectorComponent> = arrayOf(VectorComponent.X, VectorComponent.Y)

        val zero: Vector2i = Vector2i()
        val one: Vector2i = Vector2i(1)

        inline fun fromArray(array: IntArray, offset: Int = 0): Vector2i = Vector2i( // @formatter:off
            array[offset],
            array[offset + 1]
        ) // @formatter:on
    }

    constructor(xy: Int) : this(xy, xy)
    constructor() : this(0)

    override val type: VectorType get() = Vector2i

    inline operator fun plus(xy: Int): Vector2i = Vector2i(x + xy, y + xy)
    inline operator fun plus(other: Vector2i): Vector2i = Vector2i(x + other.x, y + other.y)

    inline operator fun minus(xy: Int): Vector2i = Vector2i(x - xy, y - xy)
    inline operator fun minus(other: Vector2i): Vector2i = Vector2i(x - other.x, y - other.y)

    inline operator fun times(xy: Int): Vector2i = Vector2i(x * xy, y * xy)
    inline operator fun times(other: Vector2i): Vector2i = Vector2i(x * other.x, y * other.y)

    inline operator fun div(xy: Int): Vector2i = Vector2i(x / xy, y / xy)
    inline operator fun div(other: Vector2i): Vector2i = Vector2i(x / other.x, y / other.y)

    inline operator fun rem(xy: Int): Vector2i = Vector2i(x % xy, y % xy)
    inline operator fun rem(other: Vector2i): Vector2i = Vector2i(x % other.x, y % other.y)

    inline fun lengthSq(): Int = fma(x, x, y) * y
    inline fun length(): Int = sqrt(lengthSq().toFloat()).toInt()

    inline infix fun dot(other: Vector2i): Int = fma(x, other.x, y) * other.y
    inline infix fun cross(other: Vector2i): Int = x * other.y - y * other.x

    inline fun toVector2f(): Vector2f = Vector2f(x.toFloat(), y.toFloat())

    override operator fun get(index: Int): Int = when (index) {
        0 -> x
        1 -> y
        else -> throw IllegalArgumentException("Invalid vector component $index for Vector2f")
    }

    override operator fun get(component: VectorComponent): Int = when (component) {
        VectorComponent.X -> x
        VectorComponent.Y -> y
        else -> throw IllegalArgumentException("Invalid vector component $component for Vector2f")
    }

    override fun toIntArray(): IntArray = intArrayOf(x, y)

    inline fun swizzle( // @formatter:off
        x: VectorComponent,
        y: VectorComponent
    ): Vector2i = Vector2i(this[x], this[y]) // @formatter:on
}