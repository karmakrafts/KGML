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
import dev.karmakrafts.kgml.vector.Vector2f
import kotlin.jvm.JvmInline

/**
 * A transformation representing a scale in 2D space.
 *
 * @property value The scale factors for the X and Y axes.
 */
@JvmInline
value class Scale2f(val value: Vector2f) : Transform<Matrix3x3f> {
    /**
     * Creates a scale from the given factors.
     *
     * @param scaleX The scale factor on the X axis.
     * @param scaleY The scale factor on the Y axis.
     */
    constructor( // @formatter:off
        scaleX: Float = 1F,
        scaleY: Float = 1F
    ) : this(Vector2f(scaleX, scaleY)) // @formatter:on

    /**
     * Applies this scale to the given matrix.
     *
     * @param matrix The matrix to transform.
     * @return The transformed matrix.
     */
    override operator fun invoke(matrix: Matrix3x3f): Matrix3x3f = matrix * Matrix3x3f.scale(value.x, value.y)
}