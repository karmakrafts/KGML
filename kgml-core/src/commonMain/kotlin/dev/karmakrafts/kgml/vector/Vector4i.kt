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
import kotlin.math.sqrt
import kotlin.reflect.KClass

@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Vector4i( // @formatter:off
    @JvmField val x: Int,
    @JvmField val y: Int,
    @JvmField val z: Int,
    @JvmField val w: Int
) : VectorNi { // @formatter:on
    companion object : VectorType {
        override val componentType: KClass<*> = Int::class
        override val componentSize: Int = Int.SIZE_BYTES
        override val dimensions: Int = 4
        override val components: Array<VectorComponent> = VectorComponent.entries.toTypedArray()

        val zero: Vector4i = Vector4i()
        val one: Vector4i = Vector4i(1)

        inline fun fromArray(array: IntArray, offset: Int = 0): Vector4i = Vector4i( // @formatter:off
            array[offset],
            array[offset + 1],
            array[offset + 2],
            array[offset + 3]
        ) // @formatter:on
    }

    constructor(xyzw: Int) : this(xyzw, xyzw, xyzw, xyzw)
    constructor() : this(0)

    override val type: VectorType get() = Vector4i

    inline operator fun plus(xyzw: Int): Vector4i = Vector4i(x + xyzw, y + xyzw, z + xyzw, w + xyzw)
    inline operator fun plus(other: Vector4i): Vector4i = Vector4i(x + other.x, y + other.y, z + other.z, w + other.w)

    inline operator fun minus(xyzw: Int): Vector4i = Vector4i(x - xyzw, y - xyzw, z - xyzw, w - xyzw)
    inline operator fun minus(other: Vector4i): Vector4i = Vector4i(x - other.x, y - other.y, z - other.z, w - other.w)

    inline operator fun times(xyzw: Int): Vector4i = Vector4i(x * xyzw, y * xyzw, z * xyzw, w * xyzw)
    inline operator fun times(other: Vector4i): Vector4i = Vector4i(x * other.x, y * other.y, z * other.z, w * other.w)

    inline operator fun div(xyzw: Int): Vector4i = Vector4i(x / xyzw, y / xyzw, z / xyzw, w / xyzw)
    inline operator fun div(other: Vector4i): Vector4i = Vector4i(x / other.x, y / other.y, z / other.z, w / other.w)

    inline operator fun rem(xyzw: Int): Vector4i = Vector4i(x % xyzw, y % xyzw, z % xyzw, w % xyzw)
    inline operator fun rem(other: Vector4i): Vector4i = Vector4i(x % other.x, y % other.y, z % other.z, w % other.w)

    fun fma(b: Vector4i, c: Vector4i): Vector4i = Vector4i( // @formatter:off
        fma(x, b.x, c.x),
        fma(y, b.y, c.y),
        fma(z, b.z, c.z),
        fma(w, b.w, c.w)
    ) // @formatter:on

    inline fun lengthSq(): Int = fma(fma(fma(x, x, y), y, z), z, w) * w
    inline fun length(): Int = sqrt(lengthSq().toFloat()).toInt()

    inline fun normalized(): Vector4i = this / length()

    inline infix fun dot(other: Vector4i): Int = fma(fma(fma(x, other.x, y), other.y, z), other.z, w) * other.w

    inline fun toVector4f(): Vector4f = Vector4f(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())

    override operator fun get(index: Int): Int = when (index) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> throw IllegalArgumentException("Invalid vector component $index for Vector4f")
    }

    override operator fun get(component: VectorComponent): Int = when (component) {
        VectorComponent.X -> x
        VectorComponent.Y -> y
        VectorComponent.Z -> z
        VectorComponent.W -> w
    }

    override fun toIntArray(): IntArray = intArrayOf(x, y, z, w)

    inline fun swizzle( // @formatter:off
        x: VectorComponent,
        y: VectorComponent,
        z: VectorComponent,
        w: VectorComponent
    ): Vector4i = Vector4i(this[x], this[y], this[z], this[w]) // @formatter:on

    inline fun swizzle3( // @formatter:off
        x: VectorComponent,
        y: VectorComponent,
        z: VectorComponent
    ): Vector3i = Vector3i(this[x], this[y], this[z]) // @formatter:on

    inline fun swizzle2( // @formatter:off
        x: VectorComponent,
        y: VectorComponent
    ): Vector2i = Vector2i(this[x], this[y]) // @formatter:on
}