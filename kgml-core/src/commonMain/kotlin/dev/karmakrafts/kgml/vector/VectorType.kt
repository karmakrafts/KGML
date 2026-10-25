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

package dev.karmakrafts.kgml.vector

import kotlin.reflect.KClass

/**
 * Represents the type information for a vector.
 */
public sealed interface VectorType {
    /**
     * The type of the components in the vector.
     *
     * @return The component type.
     */
    public val componentType: KClass<*>

    /**
     * The size of a single component in bytes.
     *
     * @return The component size in bytes.
     */
    public val componentSize: Int

    /**
     * The number of dimensions in the vector.
     *
     * @return The number of dimensions.
     */
    public val dimensions: Int

    /**
     * The components of the vector.
     *
     * @return The components.
     */
    public val components: Array<VectorComponent>
}

/**
 * The total size of the vector in bytes.
 *
 * @return The total size in bytes.
 */
public inline val VectorType.size: Int get() = componentSize * dimensions