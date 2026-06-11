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

@file:Suppress("NOTHING_TO_INLINE")

package dev.karmakrafts.kgml.transform

import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.matrix.Matrix4x4f
import dev.karmakrafts.kgml.util.TO_RAD

/**
 * Multiplies this matrix by the given quaternion.
 *
 * @param quat The quaternion to multiply by.
 * @return The result of the multiplication.
 */
inline operator fun Matrix4x4f.times(quat: Quaternion): Matrix4x4f = this * quat.toRotationMatrix4x4()

/**
 * Creates a rotation matrix around the X axis from the given angle in radians.
 *
 * @param rad The angle in radians.
 * @return A new rotation [Matrix4x4f].
 */
inline fun Matrix4x4f.Companion.rotationXRad(rad: Float): Matrix4x4f = Matrix3x3f.rotationXRad(rad).extend()

/**
 * Creates a rotation matrix around the X axis from the given angle in degrees.
 *
 * @param deg The angle in degrees.
 * @return A new rotation [Matrix4x4f].
 */
inline fun Matrix4x4f.Companion.rotationX(deg: Float): Matrix4x4f = rotationXRad((deg * TO_RAD).toFloat())

/**
 * Creates a rotation matrix around the Y axis from the given angle in radians.
 *
 * @param rad The angle in radians.
 * @return A new rotation [Matrix4x4f].
 */
inline fun Matrix4x4f.Companion.rotationYRad(rad: Float): Matrix4x4f = Matrix3x3f.rotationYRad(rad).extend()

/**
 * Creates a rotation matrix around the Y axis from the given angle in degrees.
 *
 * @param deg The angle in degrees.
 * @return A new rotation [Matrix4x4f].
 */
inline fun Matrix4x4f.Companion.rotationY(deg: Float): Matrix4x4f = rotationYRad((deg * TO_RAD).toFloat())

/**
 * Creates a rotation matrix around the Z axis from the given angle in radians.
 *
 * @param rad The angle in radians.
 * @return A new rotation [Matrix4x4f].
 */
inline fun Matrix4x4f.Companion.rotationZRad(rad: Float): Matrix4x4f = Matrix3x3f.rotationZRad(rad).extend()

/**
 * Creates a rotation matrix around the Z axis from the given angle in degrees.
 *
 * @param deg The angle in degrees.
 * @return A new rotation [Matrix4x4f].
 */
inline fun Matrix4x4f.Companion.rotationZ(deg: Float): Matrix4x4f = rotationZRad((deg * TO_RAD).toFloat())

/**
 * Creates a rotation matrix from the given Euler angles in radians.
 *
 * @param angleX The angle around the X axis in radians.
 * @param angleY The angle around the Y axis in radians.
 * @param angleZ The angle around the Z axis in radians.
 * @return A new rotation [Matrix4x4f].
 */
fun Matrix4x4f.Companion.rotationRad( // @formatter:off
    angleX: Float = 0F,
    angleY: Float = 0F,
    angleZ: Float = 0F
): Matrix4x4f = rotationXRad(angleX) * rotationYRad(angleY) * rotationZRad(angleZ) // @formatter:on

/**
 * Creates a rotation matrix from the given Euler angles in degrees.
 *
 * @param angleX The angle around the X axis in degrees.
 * @param angleY The angle around the Y axis in degrees.
 * @param angleZ The angle around the Z axis in degrees.
 * @return A new rotation [Matrix4x4f].
 */
fun Matrix4x4f.Companion.rotation( // @formatter:off
    angleX: Float = 0F,
    angleY: Float = 0F,
    angleZ: Float = 0F
): Matrix4x4f = rotationX(angleX) * rotationY(angleY) * rotationZ(angleZ) // @formatter:on

/**
 * Creates a translation matrix from the given X, Y and Z offsets.
 *
 * @param x The translation on the X axis.
 * @param y The translation on the Y axis.
 * @param z The translation on the Z axis.
 * @return A new translation [Matrix4x4f].
 */
fun Matrix4x4f.Companion.translation( // @formatter:off
    x: Float = 0F,
    y: Float = 0F,
    z: Float = 0F
): Matrix4x4f = Matrix4x4f(
    1F, 0F, 0F, x,
    0F, 1F, 0F, y,
    0F, 0F, 1F, z,
    0F, 0F, 0F, 1F
) // @formatter:on

/**
 * Creates a scale matrix from the given X, Y and Z scale factors.
 *
 * @param scaleX The scale factor on the X axis.
 * @param scaleY The scale factor on the Y axis.
 * @param scaleZ The scale factor on the Z axis.
 * @return A new scale [Matrix4x4f].
 */
fun Matrix4x4f.Companion.scale( // @formatter:off
    scaleX: Float = 1F,
    scaleY: Float = 1F,
    scaleZ: Float = 1F
): Matrix4x4f = Matrix4x4f(
    scaleX, 0F,     0F,     0F,
    0F,     scaleY, 0F,     0F,
    0F,     0F,     scaleZ, 0F,
    0F,     0F,     0F,     1F
) // @formatter:on

/**
 * Creates a skew matrix from the given skew factors.
 *
 * @param xy The skew factor of the X axis towards the Y axis.
 * @param xz The skew factor of the X axis towards the Z axis.
 * @param yx The skew factor of the Y axis towards the X axis.
 * @param yz The skew factor of the Y axis towards the Z axis.
 * @param zx The skew factor of the Z axis towards the X axis.
 * @param zy The skew factor of the Z axis towards the Y axis.
 * @return A new skew [Matrix4x4f].
 */
fun Matrix4x4f.Companion.skew( // @formatter:off
    xy: Float = 0F,
    xz: Float = 0F,
    yx: Float = 0F,
    yz: Float = 0F,
    zx: Float = 0F,
    zy: Float = 0F
): Matrix4x4f = Matrix4x4f(
    1F, xy, xz, 0F,
    yx, 1F, yz, 0F,
    zx, zy, 1F, 0F,
    0F, 0F, 0F, 1F
) // @formatter:on