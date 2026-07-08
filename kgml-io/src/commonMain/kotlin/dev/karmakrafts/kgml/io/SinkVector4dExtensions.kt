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

import dev.karmakrafts.karbide.writeDoubleLeFast
import dev.karmakrafts.kgml.vector.Vector4d
import kotlinx.io.Sink
import kotlinx.io.writeDouble

/**
 * Writes the given 4D double vector to this sink in component order `x`, `y`, `z`, `w`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector4d(vector: Vector4d) {
    writeDouble(vector.x)
    writeDouble(vector.y)
    writeDouble(vector.z)
    writeDouble(vector.w)
}

/**
 * Writes the given 4D double vector to this sink in little-endian component order `x`, `y`, `z`, `w`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector4dLe(vector: Vector4d) {
    writeDoubleLeFast(vector.x)
    writeDoubleLeFast(vector.y)
    writeDoubleLeFast(vector.z)
    writeDoubleLeFast(vector.w)
}
