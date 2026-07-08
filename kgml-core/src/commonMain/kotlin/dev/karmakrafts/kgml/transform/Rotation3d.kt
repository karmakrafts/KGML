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

import dev.karmakrafts.kgml.matrix.Matrix3x3d
import dev.karmakrafts.kgml.util.toRadians
import dev.karmakrafts.kgml.vector.Vector3d
import kotlin.jvm.JvmInline

/**
 * A transformation representing a rotation in 3D space.
 *
 * @property rotation The rotation angles in radians for each axis.
 */
@JvmInline
value class Rotation3d(val rotation: Vector3d) : Transform<Matrix3x3d> {
    companion object {
        /**
         * Creates a rotation from the given angles in degrees.
         *
         * @param rotation The rotation angles in degrees.
         * @return A new [Rotation3d].
         */
        fun fromDegrees(rotation: Vector3d): Rotation3d = Rotation3d( // @formatter:off
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
        angleX: Double = 0.0,
        angleY: Double = 0.0,
        angleZ: Double = 0.0
    ) : this(Vector3d(angleX, angleY, angleZ)) // @formatter:on

    /**
     * Applies this rotation to the given matrix.
     *
     * @param matrix The matrix to transform.
     * @return The transformed matrix.
     */
    override operator fun invoke(matrix: Matrix3x3d): Matrix3x3d = matrix * Matrix3x3d.rotationRad( // @formatter:off
        rotation.x,
        rotation.y,
        rotation.z
    ) // @formatter:on
}