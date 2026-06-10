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

@Suppress("NOTHING_TO_INLINE")
expect value class Vector2f(val data: FloatArray) {
    inline operator fun plus(other: Float): Vector2f
    inline operator fun minus(other: Float): Vector2f
    inline operator fun times(other: Float): Vector2f
    inline operator fun div(other: Float): Vector2f
    inline operator fun rem(other: Float): Vector2f

    inline operator fun plusAssign(other: Float)
    inline operator fun minusAssign(other: Float)
    inline operator fun timesAssign(other: Float)
    inline operator fun divAssign(other: Float)
    inline operator fun remAssign(other: Float)

    inline operator fun plus(other: Vector2f): Vector2f
    inline operator fun minus(other: Vector2f): Vector2f
    inline operator fun times(other: Vector2f): Vector2f
    inline operator fun div(other: Vector2f): Vector2f
    inline operator fun rem(other: Vector2f): Vector2f

    inline operator fun plusAssign(other: Vector2f)
    inline operator fun minusAssign(other: Vector2f)
    inline operator fun timesAssign(other: Vector2f)
    inline operator fun divAssign(other: Vector2f)
    inline operator fun remAssign(other: Vector2f)

    inline operator fun unaryMinus(): Vector2f
    inline operator fun unaryPlus(): Vector2f

    inline fun lengthSq(): Float
    inline fun length(): Float

    inline fun normalize()
    inline fun normalized(): Vector2f

    inline fun copy(): Vector2f
}

@Suppress("NOTHING_TO_INLINE")
inline fun Vector2f(xy: Float): Vector2f = Vector2f(FloatArray(2) { xy })

@Suppress("NOTHING_TO_INLINE")
inline fun Vector2f(x: Float, y: Float): Vector2f = Vector2f(floatArrayOf(x, y))

inline var Vector2f.x: Float
    get() = data[0]
    set(value) {
        data[0] = value
    }

inline var Vector2f.y: Float
    get() = data[1]
    set(value) {
        data[1] = value
    }