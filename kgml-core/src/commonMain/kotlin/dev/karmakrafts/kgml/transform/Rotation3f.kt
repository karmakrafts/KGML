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
import dev.karmakrafts.kgml.util.toRadians
import dev.karmakrafts.kgml.vector.Vector3f
import kotlin.jvm.JvmInline

/**
 * A transformation representing a rotation in 3D space.
 *
 * @property rotation The rotation angles in radians for each axis.
 */
@JvmInline
value class Rotation3f(val rotation: Vector3f) : Transform<Matrix3x3f> {
    companion object {
        /**
         * Creates a rotation from the given angles in degrees.
         *
         * @param rotation The rotation angles in degrees.
         * @return A new [Rotation3f].
         */
        fun fromDegrees(rotation: Vector3f): Rotation3f = Rotation3f( // @formatter:off
            toRadians(rotation.x),
            toRadians(rotation.y),
            toRadians(rotation.z)
        ) // @formatter:on
    }

    /**
     * Creates a rotation from the given angles in radians.
     *
     * @param angleX The angle around the X axis in radians.
     * @param angleY The angle around the Y axis in radians.
     * @param angleZ The angle around the Z axis in radians.
     */
    constructor( // @formatter:off
        angleX: Float = 0F,
        angleY: Float = 0F,
        angleZ: Float = 0F
    ) : this(Vector3f(angleX, angleY, angleZ)) // @formatter:on

    /**
     * Applies this rotation to the given matrix.
     *
     * @param matrix The matrix to transform.
     * @return The transformed matrix.
     */
    override operator fun invoke(matrix: Matrix3x3f): Matrix3x3f = matrix * Matrix3x3f.rotationRad( // @formatter:off
        rotation.x,
        rotation.y,
        rotation.z
    ) // @formatter:on
}