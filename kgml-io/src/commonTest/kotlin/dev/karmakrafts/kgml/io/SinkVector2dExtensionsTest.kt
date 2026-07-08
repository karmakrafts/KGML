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

import dev.karmakrafts.kgml.vector.Vector2d
import kotlin.test.Test

class SinkVector2dExtensionsTest {
    @Test
    fun `writeVector2d should write big endian vector`() = assertWrites(
        bytesOf(1.0, -2.0)
    ) { writeVector2d(Vector2d(1.0, -2.0)) }

    @Test
    fun `writeVector2dLe should write little endian vector`() = assertWrites(
        bytesOf(1.0, -2.0, littleEndian = true)
    ) { writeVector2dLe(Vector2d(1.0, -2.0)) }
}
