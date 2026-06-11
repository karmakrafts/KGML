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
import kotlin.jvm.JvmRecord
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.reflect.KClass

@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Vector2i( // @formatter:off
    @JvmField val x: Int,
    @JvmField val y: Int
) : VectorNi, Comparable<Vector2i> { // @formatter:on
    companion object : VectorType {
        override val componentType: KClass<*> = Int::class
        override val componentSize: Int = Int.SIZE_BYTES
        override val dimensions: Int = 2
        override val components: Array<VectorComponent> = arrayOf(VectorComponent.X, VectorComponent.Y)

        val zero: Vector2i = Vector2i()
        val one: Vector2i = Vector2i(1)

        val lexComparator: Comparator<Vector2i> = { a, b ->
            val (ax, ay) = a
            val (bx, by) = b
            if (ax != bx) ax.compareTo(bx)
            else ay.compareTo(by)
        }

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

    fun fma(b: Vector2i, c: Vector2i): Vector2i = Vector2i( // @formatter:off
        fma(x, b.x, c.x),
        fma(y, b.y, c.y)
    ) // @formatter:on

    fun lerp(other: Vector2i, factor: Float): Vector2i = Vector2i( // @formatter:off
        fma((other.x - x).toFloat(), factor, x.toFloat()).toInt(),
        fma((other.y - y).toFloat(), factor, y.toFloat()).toInt()
    ) // @formatter:on

    infix fun min(other: Vector2i): Vector2i = when {
        this > other -> other
        else -> this
    }

    infix fun minComponents(other: Vector2i): Vector2i = Vector2i( // @formatter:off
        min(x, other.x),
        min(y, other.y)
    ) // @formatter:on

    infix fun max(other: Vector2i): Vector2i = when {
        this < other -> other
        else -> this
    }

    infix fun maxComponents(other: Vector2i): Vector2i = Vector2i( // @formatter:off
        max(x, other.x),
        max(y, other.y)
    ) // @formatter:on

    fun lengthSq(): Int = fma(x, x, y * y)
    inline fun length(): Int = sqrt(lengthSq().toFloat()).toInt()

    inline fun normalized(): Vector2i = this / length()

    infix fun dot(other: Vector2i): Int = fma(x, other.x, y * other.y)
    infix fun cross(other: Vector2i): Int = x * other.y - y * other.x

    inline fun toVector2f(): Vector2f = Vector2f(x.toFloat(), y.toFloat())

    override operator fun compareTo(other: Vector2i): Int {
        return length().compareTo(other.length())
    }

    override operator fun get(index: Int): Int = when (index) {
        0 -> x
        1 -> y
        else -> throw IllegalArgumentException("Invalid vector component $index for Vector2i")
    }

    override operator fun get(component: VectorComponent): Int = when (component) {
        VectorComponent.X -> x
        VectorComponent.Y -> y
        else -> throw IllegalArgumentException("Invalid vector component $component for Vector2i")
    }

    override fun toIntArray(): IntArray = intArrayOf(x, y)

    inline fun swizzle( // @formatter:off
        x: VectorComponent,
        y: VectorComponent
    ): Vector2i = Vector2i(this[x], this[y]) // @formatter:on
}