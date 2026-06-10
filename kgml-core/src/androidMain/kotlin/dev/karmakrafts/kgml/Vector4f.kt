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

@file:JvmName("Vector4f$")

package dev.karmakrafts.kgml

import kotlin.math.sqrt

@JvmInline
@Suppress("NOTHING_TO_INLINE")
actual value class Vector4f actual constructor(actual val data: FloatArray) {
    init {
        require(data.size == 4) { "Vector4f can only accept array with 4 values" }
    }

    actual inline operator fun plus(other: Float): Vector4f = Vector4f(x + other, y + other, z + other, w + other)
    actual inline operator fun minus(other: Float): Vector4f = Vector4f(x - other, y - other, z - other, w - other)
    actual inline operator fun times(other: Float): Vector4f = Vector4f(x * other, y * other, z * other, w * other)
    actual inline operator fun div(other: Float): Vector4f = Vector4f(x / other, y / other, z / other, w / other)
    actual inline operator fun rem(other: Float): Vector4f = Vector4f(x % other, y % other, z % other, w % other)

    actual inline operator fun plusAssign(other: Float) {
        x += other
        y += other
        z += other
        w += other
    }

    actual inline operator fun minusAssign(other: Float) {
        x -= other
        y -= other
        z -= other
        w -= other
    }

    actual inline operator fun timesAssign(other: Float) {
        x *= other
        y *= other
        z *= other
        w *= other
    }

    actual inline operator fun divAssign(other: Float) {
        x /= other
        y /= other
        z /= other
        w /= other
    }

    actual inline operator fun remAssign(other: Float) {
        x %= other
        y %= other
        z %= other
        w %= other
    }

    actual inline operator fun plus(other: Vector4f): Vector4f =
        Vector4f(x + other.x, y + other.y, z + other.z, w + other.w)

    actual inline operator fun minus(other: Vector4f): Vector4f =
        Vector4f(x - other.x, y - other.y, z - other.z, w - other.w)

    actual inline operator fun times(other: Vector4f): Vector4f =
        Vector4f(x * other.x, y * other.y, z * other.z, w * other.w)

    actual inline operator fun div(other: Vector4f): Vector4f =
        Vector4f(x / other.x, y / other.y, z / other.z, w / other.w)

    actual inline operator fun rem(other: Vector4f): Vector4f =
        Vector4f(x % other.x, y % other.y, z % other.z, w % other.w)

    actual inline operator fun plusAssign(other: Vector4f) {
        x += other.x
        y += other.y
        z += other.z
        w += other.w
    }

    actual inline operator fun minusAssign(other: Vector4f) {
        x -= other.x
        y -= other.y
        z -= other.z
        w -= other.w
    }

    actual inline operator fun timesAssign(other: Vector4f) {
        x *= other.x
        y *= other.y
        z *= other.z
        w *= other.w
    }

    actual inline operator fun divAssign(other: Vector4f) {
        x /= other.x
        y /= other.y
        z /= other.z
        w /= other.w
    }

    actual inline operator fun remAssign(other: Vector4f) {
        x %= other.x
        y %= other.y
        z %= other.z
        w %= other.w
    }

    actual inline operator fun unaryMinus(): Vector4f = Vector4f(-x, -y, -z, -w)
    actual inline operator fun unaryPlus(): Vector4f = Vector4f(+x, +y, +z, +w)

    actual inline fun lengthSq(): Float = x * x + y * y + z * z + w * w
    actual inline fun length(): Float = sqrt(lengthSq())

    actual inline fun normalize() {
        this /= length()
    }

    actual inline fun normalized(): Vector4f {
        val result = copy()
        result.normalize()
        return result
    }

    actual inline fun copy(): Vector4f = Vector4f(x, y, z, w)
}