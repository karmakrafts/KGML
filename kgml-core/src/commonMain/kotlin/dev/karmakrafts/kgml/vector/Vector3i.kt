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
data class Vector3i( // @formatter:off
    @JvmField val x: Int,
    @JvmField val y: Int,
    @JvmField val z: Int
) : VectorNi { // @formatter:on
    companion object : VectorType {
        override val componentType: KClass<*> = Int::class
        override val componentSize: Int = Int.SIZE_BYTES
        override val dimensions: Int = 3
        override val components: Array<VectorComponent> =
            arrayOf(VectorComponent.X, VectorComponent.Y, VectorComponent.Z)

        val zero: Vector3i = Vector3i()
        val one: Vector3i = Vector3i(1)

        inline fun fromArray(array: IntArray, offset: Int = 0): Vector3i = Vector3i( // @formatter:off
            array[offset],
            array[offset + 1],
            array[offset + 2]
        ) // @formatter:on
    }

    constructor(xyz: Int) : this(xyz, xyz, xyz)
    constructor() : this(0)

    override val type: VectorType get() = Vector3i

    inline operator fun plus(xyz: Int): Vector3i = Vector3i(x + xyz, y + xyz, z + xyz)
    inline operator fun plus(other: Vector3i): Vector3i = Vector3i(x + other.x, y + other.y, z + other.z)

    inline operator fun minus(xyz: Int): Vector3i = Vector3i(x - xyz, y - xyz, z - xyz)
    inline operator fun minus(other: Vector3i): Vector3i = Vector3i(x - other.x, y - other.y, z - other.z)

    inline operator fun times(xyz: Int): Vector3i = Vector3i(x * xyz, y * xyz, z * xyz)
    inline operator fun times(other: Vector3i): Vector3i = Vector3i(x * other.x, y * other.y, z * other.z)

    inline operator fun div(xyz: Int): Vector3i = Vector3i(x / xyz, y / xyz, z / xyz)
    inline operator fun div(other: Vector3i): Vector3i = Vector3i(x / other.x, y / other.y, z / other.z)

    inline operator fun rem(xyz: Int): Vector3i = Vector3i(x % xyz, y % xyz, z % xyz)
    inline operator fun rem(other: Vector3i): Vector3i = Vector3i(x % other.x, y % other.y, z % other.z)

    inline fun lengthSq(): Int = fma(fma(x, x, y), y, z) * z
    inline fun length(): Int = sqrt(lengthSq().toFloat()).toInt()

    inline infix fun dot(other: Vector3i): Int = fma(fma(x, other.x, y), other.y, z) * other.z

    inline infix fun cross(other: Vector3i): Vector3i = Vector3i( // @formatter:off
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
    ) // @formatter:on

    inline fun toVector3f(): Vector3f = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())

    override operator fun get(index: Int): Int = when (index) {
        0 -> x
        1 -> y
        2 -> z
        else -> throw IllegalArgumentException("Invalid vector component $index for Vector3f")
    }

    override operator fun get(component: VectorComponent): Int = when (component) {
        VectorComponent.X -> x
        VectorComponent.Y -> y
        VectorComponent.Z -> z
        else -> throw IllegalArgumentException("Invalid vector component $component for Vector3f")
    }

    override fun toIntArray(): IntArray = intArrayOf(x, y, z)

    inline fun swizzle( // @formatter:off
        x: VectorComponent,
        y: VectorComponent,
        z: VectorComponent
    ): Vector3i = Vector3i(this[x], this[y], this[z]) // @formatter:on

    inline fun swizzle2( // @formatter:off
        x: VectorComponent,
        y: VectorComponent
    ): Vector2i = Vector2i(this[x], this[y]) // @formatter:on
}