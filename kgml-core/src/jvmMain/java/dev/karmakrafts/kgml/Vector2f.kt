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

@file:JvmName("Vector2f$")

package dev.karmakrafts.kgml

import kotlin.math.sqrt

@Suppress("NOTHING_TO_INLINE")
@JvmInline
actual value class Vector2f actual constructor(actual val data: FloatArray) {
    init {
        require(data.size == 2) { "Vector2f can only accept array with 2 values" }
    }

    actual inline operator fun plus(other: Float): Vector2f = Vector2f(x + other, y + other)
    actual inline operator fun minus(other: Float): Vector2f = Vector2f(x - other, y - other)
    actual inline operator fun times(other: Float): Vector2f = Vector2f(x * other, y * other)
    actual inline operator fun div(other: Float): Vector2f = Vector2f(x / other, y / other)
    actual inline operator fun rem(other: Float): Vector2f = Vector2f(x % other, y % other)

    actual inline operator fun plusAssign(other: Float) {
        x += other
        y += other
    }

    actual inline operator fun minusAssign(other: Float) {
        x -= other
        y -= other
    }

    actual inline operator fun timesAssign(other: Float) {
        x *= other
        y *= other
    }

    actual inline operator fun divAssign(other: Float) {
        x /= other
        y /= other
    }

    actual inline operator fun remAssign(other: Float) {
        x %= other
        y %= other
    }

    actual inline operator fun plus(other: Vector2f): Vector2f = Vector2f(x + other.x, y + other.y)
    actual inline operator fun minus(other: Vector2f): Vector2f = Vector2f(x - other.x, y - other.y)
    actual inline operator fun times(other: Vector2f): Vector2f = Vector2f(x * other.x, y * other.y)
    actual inline operator fun div(other: Vector2f): Vector2f = Vector2f(x / other.x, y / other.y)
    actual inline operator fun rem(other: Vector2f): Vector2f = Vector2f(x % other.x, y % other.y)

    actual inline operator fun plusAssign(other: Vector2f) {
        x += other.x
        y += other.y
    }

    actual inline operator fun minusAssign(other: Vector2f) {
        x -= other.x
        y -= other.y
    }

    actual inline operator fun timesAssign(other: Vector2f) {
        x *= other.x
        y *= other.y
    }

    actual inline operator fun divAssign(other: Vector2f) {
        x /= other.x
        y /= other.y
    }

    actual inline operator fun remAssign(other: Vector2f) {
        x %= other.x
        y %= other.y
    }

    actual inline operator fun unaryMinus(): Vector2f = Vector2f(-x, -y)
    actual inline operator fun unaryPlus(): Vector2f = Vector2f(+x, +y)

    actual inline fun lengthSq(): Float = x * x + y * y
    actual inline fun length(): Float = sqrt(lengthSq())

    actual inline fun normalize() {
        this /= length()
    }

    actual inline fun normalized(): Vector2f {
        val result = copy()
        result.normalize()
        return result
    }

    actual inline fun copy(): Vector2f = Vector2f(x, y)
}