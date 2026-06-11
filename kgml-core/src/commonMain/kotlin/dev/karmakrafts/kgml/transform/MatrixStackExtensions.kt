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

fun MatrixStack<Matrix4x4f>.rotateRad( // @formatter:off
    angleX: Float = 0F,
    angleY: Float = 0F,
    angleZ: Float = 0F
) { // @formatter:on
    swap(current() * Matrix4x4f.rotationRad(angleX, angleY, angleZ))
}

fun MatrixStack<Matrix4x4f>.rotate( // @formatter:off
    angleX: Float = 0F,
    angleY: Float = 0F,
    angleZ: Float = 0F
) { // @formatter:on
    swap(current() * Matrix4x4f.rotation(angleX, angleY, angleZ))
}

fun MatrixStack<Matrix4x4f>.rotate(quat: Quaternion) {
    swap(current() * quat)
}

fun MatrixStack<Matrix4x4f>.scale( // @formatter:off
    scaleX: Float = 1F,
    scaleY: Float = 1F,
    scaleZ: Float = 1F
) { // @formatter:on
    swap(current() * Matrix4x4f.scale(scaleX, scaleY, scaleZ))
}

fun MatrixStack<Matrix4x4f>.translate( // @formatter:off
    x: Float = 0F,
    y: Float = 0F,
    z: Float = 0F
) { // @formatter:on
    swap(current() * Matrix4x4f.translation(x, y, z))
}

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