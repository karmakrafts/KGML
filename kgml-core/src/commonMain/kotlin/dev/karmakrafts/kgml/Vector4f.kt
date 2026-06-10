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

package dev.karmakrafts.kgml

import kotlin.jvm.JvmField
import kotlin.reflect.KClass

@Suppress("NOTHING_TO_INLINE")
data class Vector4f( // @formatter:off
    @JvmField val x: Float,
    @JvmField val y: Float,
    @JvmField val z: Float,
    @JvmField val w: Float
) : VectorNf { // @formatter:on
    companion object : VectorType {
        override val componentType: KClass<*> = Float::class
        override val dimensions: Int = 4
    }

    constructor(xyzw: Float) : this(xyzw, xyzw, xyzw, xyzw)

    override val type: VectorType get() = Vector4f

    inline operator fun plus(xyzw: Float): Vector4f = Vector4f(x + xyzw, y + xyzw, z + xyzw, w + xyzw)
    inline operator fun plus(other: Vector4f): Vector4f = Vector4f(x + other.x, y + other.y, z + other.z, w + other.w)

    inline operator fun minus(xyzw: Float): Vector4f = Vector4f(x - xyzw, y - xyzw, z - xyzw, w - xyzw)
    inline operator fun minus(other: Vector4f): Vector4f = Vector4f(x - other.x, y - other.y, z - other.z, w - other.w)

    inline operator fun times(xyzw: Float): Vector4f = Vector4f(x * xyzw, y * xyzw, z * xyzw, w * xyzw)
    inline operator fun times(other: Vector4f): Vector4f = Vector4f(x * other.x, y * other.y, z * other.z, w * other.w)

    inline operator fun div(xyzw: Float): Vector4f = Vector4f(x / xyzw, y / xyzw, z / xyzw, w / xyzw)
    inline operator fun div(other: Vector4f): Vector4f = Vector4f(x / other.x, y / other.y, z / other.z, w / other.w)

    inline operator fun rem(xyzw: Float): Vector4f = Vector4f(x % xyzw, y % xyzw, z % xyzw, w % xyzw)
    inline operator fun rem(other: Vector4f): Vector4f = Vector4f(x % other.x, y % other.y, z % other.z, w % other.w)
}