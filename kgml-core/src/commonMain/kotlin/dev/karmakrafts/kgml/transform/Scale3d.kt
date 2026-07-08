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

import dev.karmakrafts.kgml.matrix.Matrix4x4d
import dev.karmakrafts.kgml.vector.Vector3d
import kotlin.jvm.JvmInline

/**
 * A transformation representing a scale in 3D space.
 *
 * @property scale The scale factors for the X, Y, and Z axes.
 */
@JvmInline
value class Scale3d(val scale: Vector3d) : Transform<Matrix4x4d> {
    /**
     * Creates a scale from the given factors.
     *
     * @param scaleX The scale factor on the X axis.
     * @param scaleY The scale factor on the Y axis.
     * @param scaleZ The scale factor on the Z axis.
     */
    constructor( // @formatter:off
        scaleX: Double = 1.0,
        scaleY: Double = 1.0,
        scaleZ: Double = 1.0
    ) : this(Vector3d(scaleX, scaleY, scaleZ)) // @formatter:on

    /**
     * Applies this scale to the given matrix.
     *
     * @param matrix The matrix to transform.
     * @return The transformed matrix.
     */
    override operator fun invoke(matrix: Matrix4x4d): Matrix4x4d = matrix * Matrix4x4d.scale(scale.x, scale.y, scale.z)
}