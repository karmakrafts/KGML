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

import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.util.fma
import kotlin.jvm.JvmField
import kotlin.math.sqrt
import kotlin.reflect.KClass

@Suppress("NOTHING_TO_INLINE")
data class Vector3f( // @formatter:off
    @JvmField val x: Float,
    @JvmField val y: Float,
    @JvmField val z: Float
) : VectorNf { // @formatter:on
    companion object : VectorType {
        override val componentType: KClass<*> = Float::class
        override val componentSize: Int = Float.SIZE_BYTES
        override val dimensions: Int = 3
        override val components: Array<VectorComponent> =
            arrayOf(VectorComponent.X, VectorComponent.Y, VectorComponent.Z)
    }

    constructor(xyz: Float) : this(xyz, xyz, xyz)

    override val type: VectorType get() = Vector3f

    inline operator fun plus(xyz: Float): Vector3f = Vector3f(x + xyz, y + xyz, z + xyz)
    inline operator fun plus(other: Vector3f): Vector3f = Vector3f(x + other.x, y + other.y, z + other.z)

    inline operator fun minus(xyz: Float): Vector3f = Vector3f(x - xyz, y - xyz, z - xyz)
    inline operator fun minus(other: Vector3f): Vector3f = Vector3f(x - other.x, y - other.y, z - other.z)

    inline operator fun times(xyz: Float): Vector3f = Vector3f(x * xyz, y * xyz, z * xyz)
    inline operator fun times(other: Vector3f): Vector3f = Vector3f(x * other.x, y * other.y, z * other.z)

    inline operator fun div(xyz: Float): Vector3f = Vector3f(x / xyz, y / xyz, z / xyz)
    inline operator fun div(other: Vector3f): Vector3f = Vector3f(x / other.x, y / other.y, z / other.z)

    inline operator fun rem(xyz: Float): Vector3f = Vector3f(x % xyz, y % xyz, z % xyz)
    inline operator fun rem(other: Vector3f): Vector3f = Vector3f(x % other.x, y % other.y, z % other.z)

    inline fun lengthSq(): Float = fma(fma(x, x, y), y, z) * z
    inline fun length(): Float = sqrt(lengthSq())

    operator fun times(other: Matrix3x3f): Vector3f = Vector3f(
        fma(fma(other.m00, x, other.m01), y, other.m02) * z,
        fma(fma(other.m10, x, other.m11), y, other.m12) * z,
        fma(fma(other.m20, x, other.m21), y, other.m22) * z
    )

    override fun get(index: Int): Float = when (index) {
        0 -> x
        1 -> y
        2 -> z
        else -> throw IllegalArgumentException("Invalid vector component $index for Vector3f")
    }

    override fun get(component: VectorComponent): Float = when (component) {
        VectorComponent.X -> x
        VectorComponent.Y -> y
        VectorComponent.Z -> z
        else -> throw IllegalArgumentException("Invalid vector component $component for Vector3f")
    }

    override fun toFloatArray(): FloatArray = floatArrayOf(x, y, z)

    inline fun swizzle( // @formatter:off
        x: VectorComponent,
        y: VectorComponent,
        z: VectorComponent
    ): Vector3f = Vector3f(this[x], this[y], this[z]) // @formatter:on

    inline fun swizzle2( // @formatter:off
        x: VectorComponent,
        y: VectorComponent
    ): Vector2f = Vector2f(this[x], this[y]) // @formatter:on
}