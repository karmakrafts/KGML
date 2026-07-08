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

import dev.karmakrafts.kgml.matrix.Matrix4x4d
import dev.karmakrafts.kgml.matrix.MatrixProperties
import dev.karmakrafts.kgml.util.toRadians

/**
 * Multiplies this matrix by the given quaternion.
 *
 * @param quat The quaternion to multiply by.
 * @return The result of the multiplication.
 */
inline operator fun Matrix4x4d.times(quat: Quaternion4d): Matrix4x4d = this * quat.toRotationMatrix4x4d()

/**
 * Creates a rotation matrix from the given Euler angles in radians.
 *
 * @param angleX The angle around the X axis in radians.
 * @param angleY The angle around the Y axis in radians.
 * @param angleZ The angle around the Z axis in radians.
 * @return A new rotation [Matrix4x4d].
 */
fun Matrix4x4d.Companion.rotationRad( // @formatter:off
    angleX: Double = 0.0,
    angleY: Double = 0.0,
    angleZ: Double = 0.0
): Matrix4x4d = ( // @formatter:off
    Quaternion4d.fromAnglesRad(angleX, 0.0, 0.0) *
    Quaternion4d.fromAnglesRad(0.0, angleY, 0.0) *
    Quaternion4d.fromAnglesRad(0.0, 0.0, angleZ)
).toRotationMatrix4x4d() // @formatter:on

/**
 * Creates a rotation matrix from the given Euler angles in degrees.
 *
 * @param angleX The angle around the X axis in degrees.
 * @param angleY The angle around the Y axis in degrees.
 * @param angleZ The angle around the Z axis in degrees.
 * @return A new rotation [Matrix4x4d].
 */
fun Matrix4x4d.Companion.rotation( // @formatter:off
    angleX: Double = 0.0,
    angleY: Double = 0.0,
    angleZ: Double = 0.0
): Matrix4x4d = rotationRad(
    angleX = toRadians(angleX),
    angleY = toRadians(angleY),
    angleZ = toRadians(angleZ)
) // @formatter:on

/**
 * Creates a translation matrix from the given X, Y and Z offsets.
 *
 * @param x The translation on the X axis.
 * @param y The translation on the Y axis.
 * @param z The translation on the Z axis.
 * @return A new translation [Matrix4x4d].
 */
fun Matrix4x4d.Companion.translation( // @formatter:off
    x: Double = 0.0,
    y: Double = 0.0,
    z: Double = 0.0
): Matrix4x4d = Matrix4x4d(
    1.0, 0.0, 0.0, x,
    0.0, 1.0, 0.0, y,
    0.0, 0.0, 1.0, z,
    0.0, 0.0, 0.0, 1.0,
    MatrixProperties.AFFINE or MatrixProperties.TRANSLATION
) // @formatter:on

/**
 * Creates a scale matrix from the given X, Y and Z scale factors.
 *
 * @param scaleX The scale factor on the X axis.
 * @param scaleY The scale factor on the Y axis.
 * @param scaleZ The scale factor on the Z axis.
 * @return A new scale [Matrix4x4d].
 */
fun Matrix4x4d.Companion.scale( // @formatter:off
    scaleX: Double = 1.0,
    scaleY: Double = 1.0,
    scaleZ: Double = 1.0
): Matrix4x4d = Matrix4x4d(
    scaleX, 0.0,    0.0,    0.0,
    0.0,    scaleY, 0.0,    0.0,
    0.0,    0.0,    scaleZ, 0.0,
    0.0,    0.0,    0.0,    1.0,
    MatrixProperties.AFFINE or MatrixProperties.LINEAR
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
 * @return A new skew [Matrix4x4d].
 */
fun Matrix4x4d.Companion.skew( // @formatter:off
    xy: Double = 0.0,
    xz: Double = 0.0,
    yx: Double = 0.0,
    yz: Double = 0.0,
    zx: Double = 0.0,
    zy: Double = 0.0
): Matrix4x4d = Matrix4x4d(
    1.0, xy,  xz,  0.0,
    yx,  1.0, yz,  0.0,
    zx,  zy,  1.0, 0.0,
    0.0, 0.0, 0.0, 1.0,
    MatrixProperties.AFFINE or MatrixProperties.LINEAR
) // @formatter:on