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

import dev.karmakrafts.kgml.matrix.Matrix4x4f
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
 * @return A new orthographic projection [Matrix4x4f].
 */
fun Matrix4x4f.Companion.orthographic( // @formatter:off
    left: Float,
    right: Float,
    bottom: Float,
    top: Float,
    near: Float,
    far: Float
): Matrix4x4f {
    val rml = right - left
    val tmb = top - bottom
    val fmn = far - near
    return Matrix4x4f(
        2F / rml, 0F, 0F, -(right + left) / rml,
        0F, 2F / tmb, 0F, -(top + bottom) / tmb,
        0F, 0F, -2F / fmn, -(far + near) / fmn,
        0F, 0F, 0F, 1F
    )
} // @formatter:on

/**
 * Creates a perspective projection matrix.
 *
 * @param fov The field of view in radians.
 * @param aspect The aspect ratio.
 * @param near The near coordinate of the clipping volume.
 * @param far The far coordinate of the clipping volume.
 * @return A new perspective projection [Matrix4x4f].
 */
fun Matrix4x4f.Companion.perspective( // @formatter:off
    fov: Float,
    aspect: Float,
    near: Float,
    far: Float
): Matrix4x4f {
    val f = 1F / tan(fov * 0.5F)
    val nmf = near - far
    return Matrix4x4f(
        f / aspect, 0F, 0F, 0F,
        0F, f, 0F, 0F,
        0F, 0F, (far + near) / nmf, (2F * far * near) / nmf,
        0F, 0F, -1F, 0F
    )
} // @formatter:on