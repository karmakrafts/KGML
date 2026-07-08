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

import dev.karmakrafts.karbide.writeFloatLeFast
import dev.karmakrafts.kgml.vector.Vector2f
import kotlinx.io.Sink
import kotlinx.io.writeFloat

/**
 * Writes the given 2D float vector to this sink in component order `x`, `y`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector2f(vector: Vector2f) {
    writeFloat(vector.x)
    writeFloat(vector.y)
}

/**
 * Writes the given 2D float vector to this sink in little-endian component order `x`, `y`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector2fLe(vector: Vector2f) {
    writeFloatLeFast(vector.x)
    writeFloatLeFast(vector.y)
}