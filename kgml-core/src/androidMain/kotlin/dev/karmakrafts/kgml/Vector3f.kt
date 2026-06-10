@file:JvmName("Vector3f$")

package dev.karmakrafts.kgml

import kotlin.math.sqrt

@JvmInline
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

    actual inline operator fun plusAssign(other: Float) {
        x += other
        y += other
        z += other
    }

    actual inline operator fun minusAssign(other: Float) {
        x -= other
        y -= other
        z -= other
    }

    actual inline operator fun timesAssign(other: Float) {
        x *= other
        y *= other
        z *= other
    }

    actual inline operator fun divAssign(other: Float) {
        x /= other
        y /= other
        z /= other
    }

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

    actual inline operator fun plusAssign(other: Vector3f) {
        x += other.x
        y += other.y
        z += other.z
    }

    actual inline operator fun minusAssign(other: Vector3f) {
        x -= other.x
        y -= other.y
        z -= other.z
    }

    actual inline operator fun timesAssign(other: Vector3f) {
        x *= other.x
        y *= other.y
        z *= other.z
    }

    actual inline operator fun divAssign(other: Vector3f) {
        x /= other.x
        y /= other.y
        z /= other.z
    }

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