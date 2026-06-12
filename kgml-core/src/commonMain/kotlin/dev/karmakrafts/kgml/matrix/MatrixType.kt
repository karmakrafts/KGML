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

package dev.karmakrafts.kgml.matrix

import kotlin.reflect.KClass

/**
 * Represents the type of a matrix, including its component type and dimensions.
 */
sealed interface MatrixType {
    /**
     * The type of the components in the matrix.
     *
     * @return The component type.
     */
    val componentType: KClass<*>

    /**
     * The size of a single component in bytes.
     *
     * @return The component size.
     */
    val componentSize: Int

    /**
     * The number of rows in the matrix.
     *
     * @return The number of rows.
     */
    val rows: Int

    /**
     * The number of columns in the matrix.
     *
     * @return The number of columns.
     */
    val columns: Int

    /**
     * The components present in the matrix.
     *
     * @return The components.
     */
    val components: Array<MatrixComponent>
}

/**
 * The total size of the matrix in bytes.
 *
 * @return The total size of the matrix in bytes.
 */
inline val MatrixType.size: Int get() = componentSize * (rows * columns)