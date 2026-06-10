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
actual value class Vector3f actual constructor(actual val data: FloatArray) {
    init {
        require(data.size == 3) { "Vector3f can only accept array with 3 values" }
    }

    actual inline operator fun plus(other: Float): Vector3f = Vector3f(x + other, y + other, z + other)
    actual inline operator fun minus(other: Float): Vector3f = Vector3f(x - other, y - other, z - other)
    actual inline operator fun times(other: Float): Vector3f = Vector3f(x * other, y * other, z * other)
    actual inline operator fun div(other: Float): Vector3f = Vector3f(x / other, y / other, z / other)
    actual inline operator fun rem(other: Float): Vector3f = Vector3f(x % other, y % other, z % other)

    @PublishedApi
    internal inline fun scalarBinaryOpAssign(
        other: Float, op: (CValue<kgml_float4_t>, CValue<kgml_float4_t>) -> CValue<kgml_float4_t>
    ) {
        val a = kgml_float4_create(x, y, z, 0F)
        val b = kgml_float4_create(other, other, other, 0F)
        val r = op(a, b)
        x = kgml_float4_get_x(r)
        y = kgml_float4_get_y(r)
        z = kgml_float4_get_z(r)
    }

    actual inline operator fun plusAssign(other: Float) = scalarBinaryOpAssign(other, ::kgml_float4_add)
    actual inline operator fun minusAssign(other: Float) = scalarBinaryOpAssign(other, ::kgml_float4_sub)
    actual inline operator fun timesAssign(other: Float) = scalarBinaryOpAssign(other, ::kgml_float4_mul)
    actual inline operator fun divAssign(other: Float) = scalarBinaryOpAssign(other, ::kgml_float4_div)

    actual inline operator fun remAssign(other: Float) {
        x %= other
        y %= other
        z %= other
    }

    actual inline operator fun plus(other: Vector3f): Vector3f = Vector3f(x + other.x, y + other.y, z + other.z)
    actual inline operator fun minus(other: Vector3f): Vector3f = Vector3f(x - other.x, y - other.y, z - other.z)
    actual inline operator fun times(other: Vector3f): Vector3f = Vector3f(x * other.x, y * other.y, z * other.z)
    actual inline operator fun div(other: Vector3f): Vector3f = Vector3f(x / other.x, y / other.y, z / other.z)
    actual inline operator fun rem(other: Vector3f): Vector3f = Vector3f(x % other.x, y % other.y, z % other.z)

    @PublishedApi
    internal inline fun binaryOpAssign(
        other: Vector3f, op: (CValue<kgml_float4_t>, CValue<kgml_float4_t>) -> CValue<kgml_float4_t>
    ) {
        val a = kgml_float4_create(x, y, z, 0F)
        val b = kgml_float4_create(other.x, other.y, other.z, 0F)
        val r = op(a, b)
        x = kgml_float4_get_x(r)
        y = kgml_float4_get_y(r)
        z = kgml_float4_get_z(r)
    }

    actual inline operator fun plusAssign(other: Vector3f) = binaryOpAssign(other, ::kgml_float4_add)
    actual inline operator fun minusAssign(other: Vector3f) = binaryOpAssign(other, ::kgml_float4_sub)
    actual inline operator fun timesAssign(other: Vector3f) = binaryOpAssign(other, ::kgml_float4_mul)
    actual inline operator fun divAssign(other: Vector3f) = binaryOpAssign(other, ::kgml_float4_div)

    actual inline operator fun remAssign(other: Vector3f) {
        x %= other.x
        y %= other.y
        z %= other.z
    }

    actual inline operator fun unaryMinus(): Vector3f = Vector3f(-x, -y, -z)
    actual inline operator fun unaryPlus(): Vector3f = Vector3f(+x, +y, +z)

    actual inline fun lengthSq(): Float = x * x + y * y + z * z
    actual inline fun length(): Float = sqrt(lengthSq())

    actual inline fun normalize() {
        this /= length()
    }

    actual inline fun normalized(): Vector3f {
        val result = copy()
        result.normalize()
        return result
    }

    actual inline fun copy(): Vector3f = Vector3f(x, y, z)
}