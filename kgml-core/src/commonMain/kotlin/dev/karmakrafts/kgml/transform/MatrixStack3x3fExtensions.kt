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
import dev.karmakrafts.kgml.matrix.MatrixStack
import kotlin.jvm.JvmName

/**
 * Rotates the current matrix in the stack by the given Euler angles in radians.
 *
 * @param angleX The angle around the X axis in radians.
 * @param angleY The angle around the Y axis in radians.
 * @param angleZ The angle around the Z axis in radians.
 */
@JvmName("rotateRad33")
fun MatrixStack<Matrix3x3f>.rotateRad( // @formatter:off
    angleX: Float = 0F,
    angleY: Float = 0F,
    angleZ: Float = 0F
) { // @formatter:on
    swap(current() * Matrix3x3f.rotationRad(angleX, angleY, angleZ))
}

/**
 * Rotates the current matrix in the stack by the given Euler angles in degrees.
 *
 * @param angleX The angle around the X axis in degrees.
 * @param angleY The angle around the Y axis in degrees.
 * @param angleZ The angle around the Z axis in degrees.
 */
@JvmName("rotate33")
fun MatrixStack<Matrix3x3f>.rotate( // @formatter:off
    angleX: Float = 0F,
    angleY: Float = 0F,
    angleZ: Float = 0F
) { // @formatter:on
    swap(current() * Matrix3x3f.rotation(angleX, angleY, angleZ))
}

/**
 * Scales the current matrix in the stack by the given scale factors.
 *
 * @param scaleX The scale factor on the X axis.
 * @param scaleY The scale factor on the Y axis.
 */
@JvmName("scale33")
fun MatrixStack<Matrix3x3f>.scale( // @formatter:off
    scaleX: Float = 1F,
    scaleY: Float = 1F
) { // @formatter:on
    swap(current() * Matrix3x3f.scale(scaleX, scaleY))
}

/**
 * Translates the current matrix in the stack by the given offsets.
 *
 * @param x The translation on the X axis.
 * @param y The translation on the Y axis.
 */
fun MatrixStack<Matrix3x3f>.translate( // @formatter:off
    x: Float = 0F,
    y: Float = 0F
) { // @formatter:on
    swap(current() * Matrix3x3f.translation(x, y))
}