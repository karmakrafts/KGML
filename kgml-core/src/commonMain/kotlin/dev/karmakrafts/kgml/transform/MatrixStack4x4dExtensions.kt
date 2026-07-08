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

import dev.karmakrafts.kgml.matrix.Matrix4x4d
import dev.karmakrafts.kgml.matrix.MatrixStack

/**
 * Rotates the current matrix in the stack by the given Euler angles in radians.
 *
 * @param angleX The angle around the X axis in radians.
 * @param angleY The angle around the Y axis in radians.
 * @param angleZ The angle around the Z axis in radians.
 */
fun MatrixStack<Matrix4x4d>.rotateRad( // @formatter:off
    angleX: Double = 0.0,
    angleY: Double = 0.0,
    angleZ: Double = 0.0
) { // @formatter:on
    swap(current() * Matrix4x4d.rotationRad(angleX, angleY, angleZ))
}

/**
 * Rotates the current matrix in the stack by the given Euler angles in degrees.
 *
 * @param angleX The angle around the X axis in degrees.
 * @param angleY The angle around the Y axis in degrees.
 * @param angleZ The angle around the Z axis in degrees.
 */
fun MatrixStack<Matrix4x4d>.rotate( // @formatter:off
    angleX: Double = 0.0,
    angleY: Double = 0.0,
    angleZ: Double = 0.0
) { // @formatter:on
    swap(current() * Matrix4x4d.rotation(angleX, angleY, angleZ))
}

/**
 * Rotates the current matrix in the stack by the given quaternion.
 *
 * @param quat The quaternion to rotate by.
 */
fun MatrixStack<Matrix4x4d>.rotate(quat: Quaternion4d) {
    swap(current() * quat)
}

/**
 * Scales the current matrix in the stack by the given scale factors.
 *
 * @param scaleX The scale factor on the X axis.
 * @param scaleY The scale factor on the Y axis.
 * @param scaleZ The scale factor on the Z axis.
 */
fun MatrixStack<Matrix4x4d>.scale( // @formatter:off
    scaleX: Double = 1.0,
    scaleY: Double = 1.0,
    scaleZ: Double = 1.0
) { // @formatter:on
    swap(current() * Matrix4x4d.scale(scaleX, scaleY, scaleZ))
}

/**
 * Translates the current matrix in the stack by the given offsets.
 *
 * @param x The translation on the X axis.
 * @param y The translation on the Y axis.
 * @param z The translation on the Z axis.
 */
fun MatrixStack<Matrix4x4d>.translate( // @formatter:off
    x: Double = 0.0,
    y: Double = 0.0,
    z: Double = 0.0
) { // @formatter:on
    swap(current() * Matrix4x4d.translation(x, y, z))
}

/**
 * Skews the current matrix in the stack by the given skew factors.
 *
 * @param xy The skew factor of the X axis towards the Y axis.
 * @param xz The skew factor of the X axis towards the Z axis.
 * @param yx The skew factor of the Y axis towards the X axis.
 * @param yz The skew factor of the Y axis towards the Z axis.
 * @param zx The skew factor of the Z axis towards the X axis.
 * @param zy The skew factor of the Z axis towards the Y axis.
 */
fun MatrixStack<Matrix4x4d>.skew( // @formatter:off
    xy: Double = 0.0,
    xz: Double = 0.0,
    yx: Double = 0.0,
    yz: Double = 0.0,
    zx: Double = 0.0,
    zy: Double = 0.0
) { // @formatter:on
    swap(current() * Matrix4x4d.skew(xy, xz, yx, yz, zx, zy))
}