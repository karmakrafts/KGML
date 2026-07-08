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
import dev.karmakrafts.kgml.matrix.MatrixProperties
import dev.karmakrafts.kgml.util.TO_DEG
import dev.karmakrafts.kgml.util.TO_RAD
import dev.karmakrafts.kgml.util.fma
import dev.karmakrafts.kgml.vector.Vector4f
import kotlin.jvm.JvmInline
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.withSign

/**
 * A quaternion representing a rotation in 3D space.
 *
 * @property value The underlying [Vector4f] storing the x, y, z, and w components.
 */
@Suppress("NOTHING_TO_INLINE")
@JvmInline
value class Quaternion(@PublishedApi internal val value: Vector4f) : Transform<Matrix4x4f> {
    companion object {
        /**
         * The identity quaternion.
         */
        val identity: Quaternion = Quaternion()

        /**
         * Creates a quaternion from the given Euler angles in radians.
         *
         * @param angleX The angle around the X axis in radians.
         * @param angleY The angle around the Y axis in radians.
         * @param angleZ The angle around the Z axis in radians.
         * @return A new [Quaternion].
         */
        fun fromAnglesRad( // @formatter:off
            angleX: Float = 0F,
            angleY: Float = 0F,
            angleZ: Float = 0F
        ): Quaternion { // @formatter:on
            val hx = angleX * 0.5F
            val hy = angleY * 0.5F
            val hz = angleZ * 0.5F
            val cx = cos(hx)
            val sx = sin(hx)
            val cy = cos(hy)
            val sy = sin(hy)
            val cz = cos(hz)
            val sz = sin(hz)
            return Quaternion(
                cz * cy * sx - sz * sy * cx,
                fma(cz * sy, cx, sz * cy * sx),
                sz * cy * cx - cz * sy * sx,
                fma(cz * cy, cx, sz * sy * sx)
            )
        }

        /**
         * Creates a quaternion from the given Euler angles in degrees.
         *
         * @param angleX The angle around the X axis in degrees.
         * @param angleY The angle around the Y axis in degrees.
         * @param angleZ The angle around the Z axis in degrees.
         * @return A new [Quaternion].
         */
        inline fun fromAngles( // @formatter:off
            angleX: Float = 0F,
            angleY: Float = 0F,
            angleZ: Float = 0F
        ): Quaternion = fromAnglesRad(
            (angleX * TO_RAD).toFloat(),
            (angleY * TO_RAD).toFloat(),
            (angleZ * TO_RAD).toFloat()
        ) // @formatter:on
    }

    /**
     * The X component of the quaternion.
     */
    inline val x: Float get() = value.x

    /**
     * The Y component of the quaternion.
     */
    inline val y: Float get() = value.y

    /**
     * The Z component of the quaternion.
     */
    inline val z: Float get() = value.z

    /**
     * The W component of the quaternion.
     */
    inline val w: Float get() = value.w

    /**
     * Creates a quaternion from the given components.
     *
     * @param x The X component.
     * @param y The Y component.
     * @param z The Z component.
     * @param w The W component.
     */
    constructor(x: Float, y: Float, z: Float, w: Float) : this(Vector4f(x, y, z, w))

    /**
     * Creates an identity quaternion.
     */
    constructor() : this(0F, 0F, 0F, 1F)

    /**
     * Returns the rotation around the X axis in radians.
     *
     * @return The rotation around the X axis in radians.
     */
    fun getAngleXRad(): Float = atan2(2F * fma(w, x, y * z), 1F - 2F * fma(x, x, y * y))

    /**
     * Returns the rotation around the X axis in degrees.
     *
     * @return The rotation around the X axis in degrees.
     */
    inline fun getAngleX(): Float = (getAngleXRad() * TO_DEG).toFloat()

    /**
     * Returns the rotation around the Y axis in radians.
     *
     * @return The rotation around the Y axis in radians.
     */
    fun getAngleYRad(): Float {
        val sinp = 2F * (w * y - z * x)
        return if (abs(sinp) >= 1F) (PI * 0.5).toFloat().withSign(sinp)
        else asin(sinp)
    }

    /**
     * Returns the rotation around the Y axis in degrees.
     *
     * @return The rotation around the Y axis in degrees.
     */
    inline fun getAngleY(): Float = (getAngleYRad() * TO_DEG).toFloat()

    /**
     * Returns the rotation around the Z axis in radians.
     *
     * @return The rotation around the Z axis in radians.
     */
    fun getAngleZRad(): Float = atan2(2F * fma(w, z, x * y), 1F - 2F * fma(y, y, z * z))

    /**
     * Returns the rotation around the Z axis in degrees.
     *
     * @return The rotation around the Z axis in degrees.
     */
    inline fun getAngleZ(): Float = (getAngleZRad() * TO_DEG).toFloat()

    /**
     * Multiplies this quaternion by the given quaternion.
     *
     * @param other The quaternion to multiply by.
     * @return The result of the multiplication.
     */
    operator fun times(other: Quaternion): Quaternion {
        val ax = x
        val ay = y
        val az = z
        val aw = w
        val bx = other.x
        val by = other.y
        val bz = other.z
        val bw = other.w
        return Quaternion(
            fma(aw, bx, fma(ax, bw, ay * bz)) - az * by,
            fma(aw, by, fma(ay, bw, az * bx)) - ax * bz,
            fma(aw, bz, fma(az, bw, ax * by)) - ay * bx,
            aw * bw - ax * bx - ay * by - az * bz
        )
    }

    /**
     * Multiplies this quaternion by the given scalar.
     *
     * @param other The scalar to multiply by.
     * @return The result of the multiplication.
     */
    inline operator fun times(other: Float): Quaternion = Quaternion(value * other)

    /**
     * Performs a spherical linear interpolation between this quaternion and the given quaternion.
     *
     * @param other The other quaternion.
     * @param factor The interpolation factor.
     * @return The interpolated quaternion.
     */
    fun slerp(other: Quaternion, factor: Float): Quaternion {
        val ax = x
        val ay = y
        val az = z
        val aw = w
        var bx = other.x
        var by = other.y
        var bz = other.z
        var bw = other.w
        var dot = fma(ax, bx, fma(ay, by, fma(az, bz, aw * bw)))
        // Ensure we take the shortest path
        if (dot < 0F) {
            bx = -bx
            by = -by
            bz = -bz
            bw = -bw
            dot = -dot
        }
        // If the quaternions are very close already, we use regular lerping
        if (dot > 0.9995F) {
            val invFactor = 1F - factor
            val rx = fma(ax, invFactor, bx * factor)
            val ry = fma(ay, invFactor, by * factor)
            val rz = fma(az, invFactor, bz * factor)
            val rw = fma(aw, invFactor, bw * factor)
            val invLength = 1F / sqrt(fma(rx, rx, fma(ry, ry, fma(rz, rz, rw * rw))))
            return Quaternion(rx * invLength, ry * invLength, rz * invLength, rw * invLength)
        }
        // Otherwise we use spherical lerping
        val theta = acos(dot)
        val sTheta = sin(theta)
        val w0 = sin((1F - factor) * theta) / sTheta
        val w1 = sin(factor * theta) / sTheta
        return Quaternion( // @formatter:off
            fma(ax, w0, bx * w1),
            fma(ay, w0, by * w1),
            fma(az, w0, bz * w1),
            fma(aw, w0, bw * w1)
        ) // @formatter:on
    }

    /**
     * Converts this quaternion to a 3x3 rotation matrix.
     *
     * @return A new [Matrix3x3f] representing the rotation.
     */
    fun toRotationMatrix3x3(): Matrix3x3f {
        val qx = x
        val qy = y
        val qz = z
        val qw = w
        val xx = qx * qx
        val xy = qx * qy
        val yy = qy * qy
        val xz = qx * qz
        val yz = qy * qz
        val xw = qx * qw
        val zz = qz * qz
        val zw = qz * qw
        val yw = qy * qw
        return Matrix3x3f(
            1F - 2F * (yy + zz),
            2F * (xy - zw),
            2F * (xz + yw),
            2F * (xy + zw),
            1F - 2F * (xx + zz),
            2F * (yz - xw),
            2F * (xz - yw),
            2F * (yz + xw),
            1F - 2F * (xx + yy),
            MatrixProperties.AFFINE or MatrixProperties.LINEAR
        )
    }

    /**
     * Converts this quaternion to a 4x4 rotation matrix.
     *
     * @return A new [Matrix4x4f] representing the rotation.
     */
    fun toRotationMatrix4x4(): Matrix4x4f {
        val qx = x
        val qy = y
        val qz = z
        val qw = w
        val xx = qx * qx
        val xy = qx * qy
        val yy = qy * qy
        val xz = qx * qz
        val yz = qy * qz
        val xw = qx * qw
        val zz = qz * qz
        val zw = qz * qw
        val yw = qy * qw
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
            1F,
            MatrixProperties.AFFINE or MatrixProperties.LINEAR
        )
    }

    /**
     * Returns the underlying vector representation of this quaternion.
     *
     * @return The underlying [Vector4f].
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun asVector4f(): Vector4f = value

    /**
     * Creates a copy of this quaternion with the given components.
     *
     * @param x The X component.
     * @param y The Y component.
     * @param z The Z component.
     * @param w The W component.
     * @return A new [Quaternion] with the given components.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun copy( // @formatter:off
        x: Float = this.x,
        y: Float = this.y,
        z: Float = this.z,
        w: Float = this.w
    ): Quaternion = Quaternion(Vector4f(x, y, z, w)) // @formatter:on

    /**
     * Applies this rotation to the given matrix.
     *
     * @param matrix The matrix to transform.
     * @return The transformed matrix.
     */
    override operator fun invoke(matrix: Matrix4x4f): Matrix4x4f = matrix * this

    inline operator fun component1(): Float = value.x

    inline operator fun component2(): Float = value.y

    inline operator fun component3(): Float = value.z

    inline operator fun component4(): Float = value.w
}
