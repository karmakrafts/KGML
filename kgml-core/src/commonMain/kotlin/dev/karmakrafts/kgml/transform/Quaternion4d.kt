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

import dev.karmakrafts.kgml.matrix.Matrix3x3d
import dev.karmakrafts.kgml.matrix.Matrix4x4d
import dev.karmakrafts.kgml.matrix.MatrixProperties
import dev.karmakrafts.kgml.util.fma
import dev.karmakrafts.kgml.util.toDegrees
import dev.karmakrafts.kgml.util.toRadians
import dev.karmakrafts.kgml.vector.Vector4d
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
 * @property value The underlying [Vector4d] storing the x, y, z, and w components.
 */
@Suppress("NOTHING_TO_INLINE")
@JvmInline
value class Quaternion4d(@PublishedApi internal val value: Vector4d) : Transform<Matrix4x4d> {
    companion object {
        /**
         * The identity quaternion.
         */
        val identity: Quaternion4d = Quaternion4d()

        /**
         * Creates a quaternion from the given Euler angles in radians.
         *
         * @param angleX The angle around the X axis in radians.
         * @param angleY The angle around the Y axis in radians.
         * @param angleZ The angle around the Z axis in radians.
         * @return A new [Quaternion4d].
         */
        fun fromAnglesRad( // @formatter:off
            angleX: Double = 0.0,
            angleY: Double = 0.0,
            angleZ: Double = 0.0
        ): Quaternion4d { // @formatter:on
            val hx = angleX * 0.5
            val hy = angleY * 0.5
            val hz = angleZ * 0.5
            val cx = cos(hx)
            val sx = sin(hx)
            val cy = cos(hy)
            val sy = sin(hy)
            val cz = cos(hz)
            val sz = sin(hz)
            return Quaternion4d(
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
         * @return A new [Quaternion4d].
         */
        inline fun fromAngles( // @formatter:off
            angleX: Double = 0.0,
            angleY: Double = 0.0,
            angleZ: Double = 0.0
        ): Quaternion4d = fromAnglesRad(
            toRadians(angleX),
            toRadians(angleY),
            toRadians(angleZ)
        ) // @formatter:on
    }

    /**
     * The X component of the quaternion.
     */
    inline val x: Double get() = value.x

    /**
     * The Y component of the quaternion.
     */
    inline val y: Double get() = value.y

    /**
     * The Z component of the quaternion.
     */
    inline val z: Double get() = value.z

    /**
     * The W component of the quaternion.
     */
    inline val w: Double get() = value.w

    /**
     * Creates a quaternion from the given components.
     *
     * @param x The X component.
     * @param y The Y component.
     * @param z The Z component.
     * @param w The W component.
     */
    constructor(x: Double, y: Double, z: Double, w: Double) : this(Vector4d(x, y, z, w))

    /**
     * Creates an identity quaternion.
     */
    constructor() : this(0.0, 0.0, 0.0, 1.0)

    /**
     * Returns the rotation around the X axis in radians.
     *
     * @return The rotation around the X axis in radians.
     */
    fun getAngleXRad(): Double = atan2(2F * fma(w, x, y * z), 1F - 2F * fma(x, x, y * y))

    /**
     * Returns the rotation around the X axis in degrees.
     *
     * @return The rotation around the X axis in degrees.
     */
    inline fun getAngleX(): Double = toDegrees(getAngleXRad())

    /**
     * Returns the rotation around the Y axis in radians.
     *
     * @return The rotation around the Y axis in radians.
     */
    fun getAngleYRad(): Double {
        val sinp = 2F * (w * y - z * x)
        return if (abs(sinp) >= 1F) (PI * 0.5).withSign(sinp)
        else asin(sinp)
    }

    /**
     * Returns the rotation around the Y axis in degrees.
     *
     * @return The rotation around the Y axis in degrees.
     */
    inline fun getAngleY(): Double = toDegrees(getAngleYRad())

    /**
     * Returns the rotation around the Z axis in radians.
     *
     * @return The rotation around the Z axis in radians.
     */
    fun getAngleZRad(): Double = atan2(2F * fma(w, z, x * y), 1F - 2F * fma(y, y, z * z))

    /**
     * Returns the rotation around the Z axis in degrees.
     *
     * @return The rotation around the Z axis in degrees.
     */
    inline fun getAngleZ(): Double = toDegrees(getAngleZRad())

    /**
     * Multiplies this quaternion by the given quaternion.
     *
     * @param other The quaternion to multiply by.
     * @return The result of the multiplication.
     */
    operator fun times(other: Quaternion4d): Quaternion4d {
        val ax = x
        val ay = y
        val az = z
        val aw = w
        val bx = other.x
        val by = other.y
        val bz = other.z
        val bw = other.w
        return Quaternion4d(
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
    inline operator fun times(other: Double): Quaternion4d = Quaternion4d(value * other)

    /**
     * Performs a spherical linear interpolation between this quaternion and the given quaternion.
     *
     * @param other The other quaternion.
     * @param factor The interpolation factor.
     * @return The interpolated quaternion.
     */
    fun slerp(other: Quaternion4d, factor: Double): Quaternion4d {
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
            return Quaternion4d(rx * invLength, ry * invLength, rz * invLength, rw * invLength)
        }
        // Otherwise we use spherical lerping
        val theta = acos(dot)
        val sTheta = sin(theta)
        val w0 = sin((1F - factor) * theta) / sTheta
        val w1 = sin(factor * theta) / sTheta
        return Quaternion4d( // @formatter:off
            fma(ax, w0, bx * w1),
            fma(ay, w0, by * w1),
            fma(az, w0, bz * w1),
            fma(aw, w0, bw * w1)
        ) // @formatter:on
    }

    /**
     * Converts this quaternion to a 3x3 rotation matrix.
     *
     * @return A new [Matrix3x3d] representing the rotation.
     */
    fun toRotationMatrix3x3d(): Matrix3x3d {
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
        return Matrix3x3d(
            1.0 - 2.0 * (yy + zz),
            2.0 * (xy - zw),
            2.0 * (xz + yw),
            2.0 * (xy + zw),
            1.0 - 2.0 * (xx + zz),
            2.0 * (yz - xw),
            2.0 * (xz - yw),
            2.0 * (yz + xw),
            1.0 - 2.0 * (xx + yy),
            MatrixProperties.AFFINE or MatrixProperties.LINEAR
        )
    }

    /**
     * Converts this quaternion to a 4x4 rotation matrix.
     *
     * @return A new [Matrix4x4d] representing the rotation.
     */
    fun toRotationMatrix4x4d(): Matrix4x4d {
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
        return Matrix4x4d(
            1.0 - 2.0 * (yy + zz),
            2.0 * (xy - zw),
            2.0 * (xz + yw),
            0.0,
            2.0 * (xy + zw),
            1.0 - 2.0 * (xx + zz),
            2.0 * (yz - xw),
            0.0,
            2.0 * (xz - yw),
            2.0 * (yz + xw),
            1.0 - 2.0 * (xx + yy),
            0.0,
            0.0,
            0.0,
            0.0,
            1.0,
            MatrixProperties.AFFINE or MatrixProperties.LINEAR
        )
    }

    /**
     * Returns the underlying vector representation of this quaternion.
     *
     * @return The underlying [Vector4d].
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun asVector4d(): Vector4d = value

    /**
     * Creates a copy of this quaternion with the given components.
     *
     * @param x The X component.
     * @param y The Y component.
     * @param z The Z component.
     * @param w The W component.
     * @return A new [Quaternion4d] with the given components.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun copy( // @formatter:off
        x: Double = this.x,
        y: Double = this.y,
        z: Double = this.z,
        w: Double = this.w
    ): Quaternion4d = Quaternion4d(Vector4d(x, y, z, w)) // @formatter:on

    /**
     * Applies this rotation to the given matrix.
     *
     * @param matrix The matrix to transform.
     * @return The transformed matrix.
     */
    override operator fun invoke(matrix: Matrix4x4d): Matrix4x4d = matrix * this

    inline operator fun component1(): Double = value.x

    inline operator fun component2(): Double = value.y

    inline operator fun component3(): Double = value.z

    inline operator fun component4(): Double = value.w
}
