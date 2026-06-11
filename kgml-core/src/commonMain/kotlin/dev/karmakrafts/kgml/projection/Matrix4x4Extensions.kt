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
        2F / rml, 0F, 0F, 0F,
        0F, 2F / tmb, 0F, 0F,
        0F, 0F, -2F / fmn, 0F,
        -(right + left) / rml,
        -(top + bottom) / tmb,
        -(far + near) / fmn,
        1F
    )
} // @formatter:on

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
        0F, 0F, (far + near) / nmf, -1F,
        0F, 0F, (2F * far * near) / nmf, 0F
    )
} // @formatter:on