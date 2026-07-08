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
import dev.karmakrafts.kgml.vector.Vector2d
import kotlin.jvm.JvmInline

/**
 * A transformation representing a translation in 2D space.
 *
 * @property translation The translation offsets for the X and Y axes.
 */
@JvmInline
value class Translation2d(val translation: Vector2d) : Transform<Matrix3x3d> {
    /**
     * Creates a translation from the given offsets.
     *
     * @param x The translation on the X axis.
     * @param y The translation on the Y axis.
     */
    constructor( // @formatter:off
        x: Double = 0.0,
        y: Double = 0.0
    ) : this(Vector2d(x, y)) // @formatter:on

    /**
     * Applies this translation to the given matrix.
     *
     * @param matrix The matrix to transform.
     * @return The transformed matrix.
     */
    override operator fun invoke(matrix: Matrix3x3d): Matrix3x3d = matrix * Matrix3x3d.translation( // @formatter:off
        translation.x,
        translation.y
    ) // @formatter:on
}