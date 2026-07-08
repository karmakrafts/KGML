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

import dev.karmakrafts.kgml.matrix.Matrix2x2f
import dev.karmakrafts.kgml.matrix.MatrixStack
import kotlin.jvm.JvmName

/**
 * Rotates the current matrix in the stack by the given angle in radians.
 *
 * @param angle The angle in radians.
 */
fun MatrixStack<Matrix2x2f>.rotateRad(angle: Float) {
    swap(current() * Matrix2x2f.rotationRad(angle))
}

/**
 * Rotates the current matrix in the stack by the given angle in degrees.
 *
 * @param angle The angle in degrees.
 */
fun MatrixStack<Matrix2x2f>.rotate(angle: Float) {
    swap(current() * Matrix2x2f.rotation(angle))
}

/**
 * Scales the current matrix in the stack by the given scale factors.
 *
 * @param scaleX The scale factor on the X axis.
 * @param scaleY The scale factor on the Y axis.
 */
@JvmName("scale22")
fun MatrixStack<Matrix2x2f>.scale( // @formatter:off
    scaleX: Float = 1F,
    scaleY: Float = 1F
) { // @formatter:on
    swap(current() * Matrix2x2f.scale(scaleX, scaleY))
}