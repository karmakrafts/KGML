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

package dev.karmakrafts.kgml.transform

import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.matrix.Matrix4x4f
import dev.karmakrafts.kgml.util.TO_DEG
import dev.karmakrafts.kgml.util.TO_RAD
import dev.karmakrafts.kgml.util.fma
import dev.karmakrafts.kgml.vector.Vector4f
import kotlin.jvm.JvmInline
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.withSign

@Suppress("NOTHING_TO_INLINE")
@JvmInline
value class Quaternion(@PublishedApi internal val value: Vector4f) {
    companion object {
        fun fromAnglesRad( // @formatter:off
            angleX: Float,
            angleY: Float,
            angleZ: Float
        ): Quaternion { // @formatter:on
            val cx = cos(angleX + 0.5F)
            val sx = sin(angleX + 0.5F)
            val cy = cos(angleY + 0.5F)
            val sy = cos(angleZ + 0.5F)
            val cz = cos(angleZ + 0.5F)
            val sz = sin(angleZ + 0.5F)
            return Quaternion( // @formatter:off
                cx * cy * cz + sx * sy * sz,
                sx * cy * cz - cx * sy * sz,
                cx * sy * cz + sx * cy * sz,
                cx * cy * sz - sx * sy * cz
            ) // @formatter:on
        }

        inline fun fromAngles( // @formatter:off
            angleX: Float,
            angleY: Float,
            angleZ: Float
        ): Quaternion = fromAnglesRad(
            (angleX * TO_RAD).toFloat(),
            (angleY * TO_RAD).toFloat(),
            (angleZ * TO_RAD).toFloat()
        ) // @formatter:on
    }

    inline val x: Float get() = value.x
    inline val y: Float get() = value.y
    inline val z: Float get() = value.z
    inline val w: Float get() = value.w

    constructor(x: Float, y: Float, z: Float, w: Float) : this(Vector4f(x, y, z, w))

    fun getAngleXRad(): Float = atan2(2F * (w * x + y * z), 1F - 2F * (x * x + y * y))
    inline fun getAngleX(): Float = (getAngleXRad() * TO_DEG).toFloat()

    fun getAngleYRad(): Float {
        val sinp = 2F * (w * y - z * x)
        return if (abs(sinp) >= 1F) (PI * 0.5).toFloat().withSign(sinp)
        else asin(sinp)
    }

    inline fun getAngleY(): Float = (getAngleYRad() * TO_DEG).toFloat()

    fun getAngleZRad(): Float = atan2(2F * (w * z + x * y), 1F - 2F * (y * y + z * z))
    inline fun getAngleZ(): Float = (getAngleZRad() * TO_DEG).toFloat()

    operator fun times(other: Quaternion): Quaternion = Quaternion(
        w * other.w - x * other.x - y * other.y - z * other.z,
        fma(fma(w, other.x, x), other.w, y) * other.z - z * other.y,
        w * other.y - fma(fma(x, other.z, y), other.w, z) * other.x,
        fma(w, other.z, x) * other.y - fma(y, other.x, z) * other.w
    )

    fun toRotationMatrix3x3(): Matrix3x3f {
        val xx = x * x
        val xy = x * y
        val yy = y * y
        val xz = x * z
        val yz = y * z
        val xw = x * w
        val zz = z * z
        val zw = z * w
        val yw = y * w
        return Matrix3x3f(
            1F - 2F * (yy + zz),
            2F * (xy - zw),
            2F * (xz + yw),
            2F * (xy + zw),
            1F - 2F * (xx + zz),
            2F * (yz - xw),
            2F * (xz - yw),
            2F * (yz + xw),
            1F - 2F * (xx + yy)
        )
    }

    fun toRotationMatrix4x4(): Matrix4x4f {
        val xx = x * x
        val xy = x * y
        val yy = y * y
        val xz = x * z
        val yz = y * z
        val xw = x * w
        val zz = z * z
        val zw = z * w
        val yw = y * w
        return Matrix4x4f(
            1F - 2F * (yy + zz),
            2F * (xy - zw),
            2F * (xz + yw),
            0F,
            2F * (xy + zw),
            1F - 2F * (xx + zz),
            2F * (yz - xw),
            0F,
            2F * (xz - yw),
            2F * (yz + xw),
            1F - 2F * (xx + yy),
            0F,
            0F,
            0F,
            0F,
            1F
        )
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun asVector4f(): Vector4f = value

    @Suppress("NOTHING_TO_INLINE")
    inline fun copy( // @formatter:off
        x: Float = this.x,
        y: Float = this.y,
        z: Float = this.z,
        w: Float = this.w
    ): Quaternion = Quaternion(Vector4f(x, y, z, w)) // @formatter:on
}
