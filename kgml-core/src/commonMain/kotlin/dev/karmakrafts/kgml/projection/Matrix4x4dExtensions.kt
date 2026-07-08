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

package dev.karmakrafts.kgml.projection

import dev.karmakrafts.kgml.matrix.Matrix4x4d
import dev.karmakrafts.kgml.matrix.Matrix4x4d.Companion
import dev.karmakrafts.kgml.matrix.MatrixProperties
import kotlin.math.tan

/**
 * Creates an orthographic projection matrix.
 *
 * @param left The left coordinate of the clipping volume.
 * @param right The right coordinate of the clipping volume.
 * @param bottom The bottom coordinate of the clipping volume.
 * @param top The top coordinate of the clipping volume.
 * @param near The near coordinate of the clipping volume.
 * @param far The far coordinate of the clipping volume.
 * @return A new orthographic projection [Matrix4x4d].
 */
fun Companion.orthographic( // @formatter:off
    left: Double,
    right: Double,
    bottom: Double,
    top: Double,
    near: Double,
    far: Double
): Matrix4x4d {
    val rml = right - left
    val tmb = top - bottom
    val fmn = far - near
    return Matrix4x4d(
        2.0 / rml, 0.0, 0.0, -(right + left) / rml,
        0.0, 2.0 / tmb, 0.0, -(top + bottom) / tmb,
        0.0, 0.0, -2.0 / fmn, -(far + near) / fmn,
        0.0, 0.0, 0.0, 1.0,
        MatrixProperties.AFFINE
    )
} // @formatter:on

/**
 * Creates a perspective projection matrix.
 *
 * @param fov The field of view in radians.
 * @param aspect The aspect ratio.
 * @param near The near coordinate of the clipping volume.
 * @param far The far coordinate of the clipping volume.
 * @return A new perspective projection [Matrix4x4d].
 */
fun Companion.perspective( // @formatter:off
    fov: Double,
    aspect: Double,
    near: Double,
    far: Double
): Matrix4x4d {
    val f = 1.0 / tan(fov * 0.5)
    val nmf = near - far
    return Matrix4x4d(
        f / aspect, 0.0, 0.0, 0.0,
        0.0, f, 0.0, 0.0,
        0.0, 0.0, (far + near) / nmf, (2.0 * far * near) / nmf,
        0.0, 0.0, -.0, 0.0,
        MatrixProperties.PERSPECTIVE
    )
} // @formatter:on