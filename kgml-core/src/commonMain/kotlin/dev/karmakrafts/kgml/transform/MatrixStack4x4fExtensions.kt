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

import dev.karmakrafts.kgml.matrix.Matrix4x4f
import dev.karmakrafts.kgml.matrix.MatrixStack

/**
 * Rotates the current matrix in the stack by the given Euler angles in radians.
 *
 * @param angleX The angle around the X axis in radians.
 * @param angleY The angle around the Y axis in radians.
 * @param angleZ The angle around the Z axis in radians.
 */
fun MatrixStack<Matrix4x4f>.rotateRad( // @formatter:off
    angleX: Float = 0F,
    angleY: Float = 0F,
    angleZ: Float = 0F
) { // @formatter:on
    swap(current() * Matrix4x4f.rotationRad(angleX, angleY, angleZ))
}

/**
 * Rotates the current matrix in the stack by the given Euler angles in degrees.
 *
 * @param angleX The angle around the X axis in degrees.
 * @param angleY The angle around the Y axis in degrees.
 * @param angleZ The angle around the Z axis in degrees.
 */
fun MatrixStack<Matrix4x4f>.rotate( // @formatter:off
    angleX: Float = 0F,
    angleY: Float = 0F,
    angleZ: Float = 0F
) { // @formatter:on
    swap(current() * Matrix4x4f.rotation(angleX, angleY, angleZ))
}

/**
 * Rotates the current matrix in the stack by the given quaternion.
 *
 * @param quat The quaternion to rotate by.
 */
fun MatrixStack<Matrix4x4f>.rotate(quat: Quaternion) {
    swap(current() * quat)
}

/**
 * Scales the current matrix in the stack by the given scale factors.
 *
 * @param scaleX The scale factor on the X axis.
 * @param scaleY The scale factor on the Y axis.
 * @param scaleZ The scale factor on the Z axis.
 */
fun MatrixStack<Matrix4x4f>.scale( // @formatter:off
    scaleX: Float = 1F,
    scaleY: Float = 1F,
    scaleZ: Float = 1F
) { // @formatter:on
    swap(current() * Matrix4x4f.scale(scaleX, scaleY, scaleZ))
}

/**
 * Translates the current matrix in the stack by the given offsets.
 *
 * @param x The translation on the X axis.
 * @param y The translation on the Y axis.
 * @param z The translation on the Z axis.
 */
fun MatrixStack<Matrix4x4f>.translate( // @formatter:off
    x: Float = 0F,
    y: Float = 0F,
    z: Float = 0F
) { // @formatter:on
    swap(current() * Matrix4x4f.translation(x, y, z))
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
fun MatrixStack<Matrix4x4f>.skew( // @formatter:off
    xy: Float = 0F,
    xz: Float = 0F,
    yx: Float = 0F,
    yz: Float = 0F,
    zx: Float = 0F,
    zy: Float = 0F
) { // @formatter:on
    swap(current() * Matrix4x4f.skew(xy, xz, yx, yz, zx, zy))
}