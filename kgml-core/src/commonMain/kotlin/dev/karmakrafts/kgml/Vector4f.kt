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
expect value class Vector4f(val data: FloatArray) {
    inline operator fun plus(other: Float): Vector4f
    inline operator fun minus(other: Float): Vector4f
    inline operator fun times(other: Float): Vector4f
    inline operator fun div(other: Float): Vector4f
    inline operator fun rem(other: Float): Vector4f

    inline operator fun plusAssign(other: Float)
    inline operator fun minusAssign(other: Float)
    inline operator fun timesAssign(other: Float)
    inline operator fun divAssign(other: Float)
    inline operator fun remAssign(other: Float)

    inline operator fun plus(other: Vector4f): Vector4f
    inline operator fun minus(other: Vector4f): Vector4f
    inline operator fun times(other: Vector4f): Vector4f
    inline operator fun div(other: Vector4f): Vector4f
    inline operator fun rem(other: Vector4f): Vector4f

    inline operator fun plusAssign(other: Vector4f)
    inline operator fun minusAssign(other: Vector4f)
    inline operator fun timesAssign(other: Vector4f)
    inline operator fun divAssign(other: Vector4f)
    inline operator fun remAssign(other: Vector4f)

    inline operator fun unaryMinus(): Vector4f
    inline operator fun unaryPlus(): Vector4f

    inline fun lengthSq(): Float
    inline fun length(): Float

    inline fun normalize()
    inline fun normalized(): Vector4f

    inline fun copy(): Vector4f
}

@Suppress("NOTHING_TO_INLINE")
inline fun Vector4f(xyzw: Float): Vector4f = Vector4f(FloatArray(4) { xyzw })

@Suppress("NOTHING_TO_INLINE")
inline fun Vector4f(x: Float, y: Float, z: Float, w: Float): Vector4f = Vector4f(floatArrayOf(x, y, z, w))

inline var Vector4f.x: Float
    get() = data[0]
    set(value) {
        data[0] = value
    }

inline var Vector4f.y: Float
    get() = data[1]
    set(value) {
        data[1] = value
    }

inline var Vector4f.z: Float
    get() = data[2]
    set(value) {
        data[2] = value
    }

inline var Vector4f.w: Float
    get() = data[3]
    set(value) {
        data[3] = value
    }