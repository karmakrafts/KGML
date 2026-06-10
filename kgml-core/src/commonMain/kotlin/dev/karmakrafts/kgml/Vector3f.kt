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
        override val dimensions: Int = 3
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

    inline fun lengthSq(): Float = x * x + y * y + z * z
    inline fun length(): Float = sqrt(lengthSq())
}