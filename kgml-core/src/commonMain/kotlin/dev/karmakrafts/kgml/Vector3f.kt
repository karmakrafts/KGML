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
expect value class Vector3f(val data: FloatArray) {
    inline operator fun plus(other: Float): Vector3f
    inline operator fun minus(other: Float): Vector3f
    inline operator fun times(other: Float): Vector3f
    inline operator fun div(other: Float): Vector3f
    inline operator fun rem(other: Float): Vector3f

    inline operator fun plusAssign(other: Float)
    inline operator fun minusAssign(other: Float)
    inline operator fun timesAssign(other: Float)
    inline operator fun divAssign(other: Float)
    inline operator fun remAssign(other: Float)

    inline operator fun plus(other: Vector3f): Vector3f
    inline operator fun minus(other: Vector3f): Vector3f
    inline operator fun times(other: Vector3f): Vector3f
    inline operator fun div(other: Vector3f): Vector3f
    inline operator fun rem(other: Vector3f): Vector3f

    inline operator fun plusAssign(other: Vector3f)
    inline operator fun minusAssign(other: Vector3f)
    inline operator fun timesAssign(other: Vector3f)
    inline operator fun divAssign(other: Vector3f)
    inline operator fun remAssign(other: Vector3f)

    inline operator fun unaryMinus(): Vector3f
    inline operator fun unaryPlus(): Vector3f

    inline fun lengthSq(): Float
    inline fun length(): Float

    inline fun normalize()
    inline fun normalized(): Vector3f

    inline fun copy(): Vector3f
}

@Suppress("NOTHING_TO_INLINE")
inline fun Vector3f(xyz: Float): Vector3f = Vector3f(FloatArray(3) { xyz })

@Suppress("NOTHING_TO_INLINE")
inline fun Vector3f(x: Float, y: Float, z: Float): Vector3f = Vector3f(floatArrayOf(x, y, z))

inline var Vector3f.x: Float
    get() = data[0]
    set(value) {
        data[0] = value
    }

inline var Vector3f.y: Float
    get() = data[1]
    set(value) {
        data[1] = value
    }

inline var Vector3f.z: Float
    get() = data[2]
    set(value) {
        data[2] = value
    }