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

import dev.karmakrafts.kgml.matrix.Matrix3x3d
import dev.karmakrafts.kgml.matrix.MatrixProperties
import dev.karmakrafts.kgml.util.toRadians

/**
 * Creates a rotation matrix from the given Euler angles in radians.
 *
 * @param angleX The angle around the X axis in radians.
 * @param angleY The angle around the Y axis in radians.
 * @param angleZ The angle around the Z axis in radians.
 * @return A new rotation [Matrix3x3d].
 */
fun Matrix3x3d.Companion.rotationRad( // @formatter:off
    angleX: Double = 0.0,
    angleY: Double = 0.0,
    angleZ: Double = 0.0
): Matrix3x3d { // @formatter:on
    val result = ( // @formatter:off
        Quaternion4d.fromAnglesRad(angleX, 0.0, 0.0) *
        Quaternion4d.fromAnglesRad(0.0, angleY, 0.0) *
        Quaternion4d.fromAnglesRad(0.0, 0.0, angleZ)
    ).toRotationMatrix3x3d() // @formatter:on
    return if (angleX == 0.0 && angleY == 0.0) result.copy(properties = result.properties or MatrixProperties.HOMOGENEOUS) else result
}

/**
 * Creates a rotation matrix from the given Euler angles in degrees.
 *
 * @param angleX The angle around the X axis in degrees.
 * @param angleY The angle around the Y axis in degrees.
 * @param angleZ The angle around the Z axis in degrees.
 * @return A new rotation [Matrix3x3d].
 */
inline fun Matrix3x3d.Companion.rotation( // @formatter:off
    angleX: Double = 0.0,
    angleY: Double = 0.0,
    angleZ: Double = 0.0
): Matrix3x3d = rotationRad(
    toRadians(angleX),
    toRadians(angleY),
    toRadians(angleZ)
) // @formatter:on

/**
 * Multiplies this matrix by the given quaternion.
 *
 * @param quat The quaternion to multiply by.
 * @return The result of the multiplication.
 */
inline operator fun Matrix3x3d.times(quat: Quaternion4d): Matrix3x3d = this * quat.toRotationMatrix3x3d()

/**
 * Creates a translation matrix from the given X and Y offsets.
 *
 * @param x The translation on the X axis.
 * @param y The translation on the Y axis.
 * @return A new translation [Matrix3x3d].
 */
fun Matrix3x3d.Companion.translation(x: Double, y: Double): Matrix3x3d = Matrix3x3d( // @formatter:off
    1.0, 0.0, x,
    0.0, 1.0, y,
    0.0, 0.0, 1.0,
    MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS or MatrixProperties.TRANSLATION
) // @formatter:on

/**
 * Creates a scale matrix from the given X and Y scale factors.
 *
 * @param x The scale factor on the X axis.
 * @param y The scale factor on the Y axis.
 * @return A new scale [Matrix3x3d].
 */
fun Matrix3x3d.Companion.scale(x: Double, y: Double): Matrix3x3d = Matrix3x3d( // @formatter:off
    x,   0.0, 0.0,
    0.0, y,   0.0,
    0.0, 0.0, 1.0,
    MatrixProperties.AFFINE or MatrixProperties.HOMOGENEOUS or MatrixProperties.LINEAR
) // @formatter:on