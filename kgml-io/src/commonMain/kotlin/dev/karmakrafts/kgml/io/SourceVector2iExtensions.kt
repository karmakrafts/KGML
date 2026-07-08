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

package dev.karmakrafts.kgml.io

import dev.karmakrafts.karbide.readIntLeFast
import dev.karmakrafts.kgml.vector.Vector2i
import kotlinx.io.Source

/**
 * Reads a 2D integer vector from this source in component order `x`, `y`.
 *
 * @return The read vector.
 */
fun Source.readVector2i(): Vector2i = Vector2i( // @formatter:off
    x = readInt(),
    y = readInt()
) // @formatter:on

/**
 * Reads a 2D integer vector from this source in little-endian component order `x`, `y`.
 *
 * @return The read vector.
 */
fun Source.readVector2iLe(): Vector2i = Vector2i( // @formatter:off
    x = readIntLeFast(),
    y = readIntLeFast()
) // @formatter:on