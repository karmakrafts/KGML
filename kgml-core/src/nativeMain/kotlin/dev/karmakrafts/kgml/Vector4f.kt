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

import dev.karmakrafts.kgml.builtins.kgml_float4_add
import dev.karmakrafts.kgml.builtins.kgml_float4_create
import dev.karmakrafts.kgml.builtins.kgml_float4_div
import dev.karmakrafts.kgml.builtins.kgml_float4_get_w
import dev.karmakrafts.kgml.builtins.kgml_float4_get_x
import dev.karmakrafts.kgml.builtins.kgml_float4_get_y
import dev.karmakrafts.kgml.builtins.kgml_float4_get_z
import dev.karmakrafts.kgml.builtins.kgml_float4_mul
import dev.karmakrafts.kgml.builtins.kgml_float4_sub
import dev.karmakrafts.kgml.builtins.kgml_float4_t
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.math.sqrt

@OptIn(ExperimentalForeignApi::class)
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

    @PublishedApi
    internal inline fun scalarBinaryOpAssign(
        other: Float, op: (CValue<kgml_float4_t>, CValue<kgml_float4_t>) -> CValue<kgml_float4_t>
    ) {
        val a = kgml_float4_create(x, y, z, w)
        val b = kgml_float4_create(other, other, other, other)
        val r = op(a, b)
        x = kgml_float4_get_x(r)
        y = kgml_float4_get_y(r)
        z = kgml_float4_get_z(r)
        w = kgml_float4_get_w(r)
    }

    actual inline operator fun plusAssign(other: Float) = scalarBinaryOpAssign(other, ::kgml_float4_add)
    actual inline operator fun minusAssign(other: Float) = scalarBinaryOpAssign(other, ::kgml_float4_sub)
    actual inline operator fun timesAssign(other: Float) = scalarBinaryOpAssign(other, ::kgml_float4_mul)
    actual inline operator fun divAssign(other: Float) = scalarBinaryOpAssign(other, ::kgml_float4_div)

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

    @PublishedApi
    internal inline fun binaryOpAssign(
        other: Vector4f, op: (CValue<kgml_float4_t>, CValue<kgml_float4_t>) -> CValue<kgml_float4_t>
    ) {
        val a = kgml_float4_create(x, y, z, w)
        val b = kgml_float4_create(other.x, other.y, other.z, other.w)
        val r = op(a, b)
        x = kgml_float4_get_x(r)
        y = kgml_float4_get_y(r)
        z = kgml_float4_get_z(r)
        w = kgml_float4_get_w(r)
    }

    actual inline operator fun plusAssign(other: Vector4f) = binaryOpAssign(other, ::kgml_float4_add)
    actual inline operator fun minusAssign(other: Vector4f) = binaryOpAssign(other, ::kgml_float4_sub)
    actual inline operator fun timesAssign(other: Vector4f) = binaryOpAssign(other, ::kgml_float4_mul)
    actual inline operator fun divAssign(other: Vector4f) = binaryOpAssign(other, ::kgml_float4_div)

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