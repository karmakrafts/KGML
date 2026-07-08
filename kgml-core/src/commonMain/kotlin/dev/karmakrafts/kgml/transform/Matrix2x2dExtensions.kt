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
import dev.karmakrafts.kgml.matrix.MatrixProperties
import dev.karmakrafts.kgml.util.toRadians
import kotlin.math.cos
import kotlin.math.sin

/**
 * Creates a rotation matrix from the given angle in radians.
 *
 * @param rad The angle in radians.
 * @return A new rotation [Matrix2x2d].
 */
fun Matrix2x2d.Companion.rotationRad(rad: Double): Matrix2x2d {
    val c = cos(rad)
    val s = sin(rad)
    return Matrix2x2d(c, -s, s, c, MatrixProperties.AFFINE or MatrixProperties.LINEAR or MatrixProperties.ROTATION)
}

/**
 * Creates a rotation matrix from the given angle in degrees.
 *
 * @param deg The angle in degrees.
 * @return A new rotation [Matrix2x2d].
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Matrix2x2d.Companion.rotation(deg: Double): Matrix2x2d = rotationRad(toRadians(deg))

/**
 * Creates a scale matrix from the given X and Y scale factors.
 *
 * @param scaleX The scale factor on the X axis.
 * @param scaleY The scale factor on the Y axis.
 * @return A new scale [Matrix2x2d].
 */
fun Matrix2x2d.Companion.scale(scaleX: Double, scaleY: Double): Matrix2x2d = Matrix2x2d( // @formatter:off
    scaleX, 0.0,
    0.0, scaleY,
    MatrixProperties.AFFINE or MatrixProperties.LINEAR or MatrixProperties.DIAGONAL
) // @formatter:on

/**
 * Creates a uniform scale matrix from the given scale factor.
 *
 * @param scale The scale factor on all axes.
 * @return A new scale [Matrix2x2d].
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Matrix2x2d.Companion.scale(scale: Double): Matrix2x2d = scale(scale, scale)