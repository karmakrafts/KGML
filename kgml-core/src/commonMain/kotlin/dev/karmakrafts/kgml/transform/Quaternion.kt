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
import dev.karmakrafts.kgml.vector.Vector4f
import kotlin.jvm.JvmInline

@JvmInline
value class Quaternion(val value: Vector4f) {
    inline val x: Float get() = value.x
    inline val y: Float get() = value.y
    inline val z: Float get() = value.z
    inline val w: Float get() = value.w

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
    inline fun copy( // @formatter:off
        x: Float = this.x,
        y: Float = this.y,
        z: Float = this.z,
        w: Float = this.w
    ): Quaternion = Quaternion(Vector4f(x, y, z, w)) // @formatter:on
}
