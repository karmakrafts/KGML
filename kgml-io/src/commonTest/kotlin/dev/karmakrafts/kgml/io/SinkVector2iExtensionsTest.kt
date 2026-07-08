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

import dev.karmakrafts.kgml.vector.Vector2i
import kotlin.test.Test

class SinkVector2iExtensionsTest {
    @Test
    fun `writeVector2i should write big endian vector`() = assertWrites(
        bytesOf(1, -2)
    ) { writeVector2i(Vector2i(1, -2)) }

    @Test
    fun `writeVector2iLe should write little endian vector`() = assertWrites(
        bytesOf(1, -2, littleEndian = true)
    ) { writeVector2iLe(Vector2i(1, -2)) }
}