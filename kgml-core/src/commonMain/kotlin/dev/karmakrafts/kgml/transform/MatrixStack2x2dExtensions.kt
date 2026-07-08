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

import dev.karmakrafts.kgml.matrix.Matrix2x2d
import dev.karmakrafts.kgml.matrix.MatrixStack
import kotlin.jvm.JvmName

/**
 * Rotates the current matrix in the stack by the given angle in radians.
 *
 * @param angle The angle in radians.
 */
fun MatrixStack<Matrix2x2d>.rotateRad(angle: Double) {
    swap(current() * Matrix2x2d.rotationRad(angle))
}

/**
 * Rotates the current matrix in the stack by the given angle in degrees.
 *
 * @param angle The angle in degrees.
 */
fun MatrixStack<Matrix2x2d>.rotate(angle: Double) {
    swap(current() * Matrix2x2d.rotation(angle))
}

/**
 * Scales the current matrix in the stack by the given scale factors.
 *
 * @param scaleX The scale factor on the X axis.
 * @param scaleY The scale factor on the Y axis.
 */
@JvmName("scale22")
fun MatrixStack<Matrix2x2d>.scale( // @formatter:off
    scaleX: Double = 1.0,
    scaleY: Double = 1.0
) { // @formatter:on
    swap(current() * Matrix2x2d.scale(scaleX, scaleY))
}