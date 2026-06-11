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
import dev.karmakrafts.kgml.util.TO_RAD
import dev.karmakrafts.kgml.vector.Vector3f
import kotlin.jvm.JvmInline

@JvmInline
value class Rotation3f(val rotation: Vector3f) : Transform<Matrix3x3f> {
    companion object {
        fun fromDegrees(rotation: Vector3f): Rotation3f = Rotation3f( // @formatter:off
            (rotation.x * TO_RAD).toFloat(),
            (rotation.y * TO_RAD).toFloat(),
            (rotation.z * TO_RAD).toFloat()
        ) // @formatter:on
    }

    constructor( // @formatter:off
        angleX: Float = 0F,
        angleY: Float = 0F,
        angleZ: Float = 0F
    ) : this(Vector3f(angleX, angleY, angleZ)) // @formatter:on

    override fun transform(matrix: Matrix3x3f): Matrix3x3f = matrix * Matrix3x3f.rotationRad( // @formatter:off
        rotation.x,
        rotation.y,
        rotation.z
    ) // @formatter:on
}