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

import dev.karmakrafts.kgml.vector.Vector4i
import kotlin.test.Test

class SinkVector4iExtensionsTest {
    @Test
    fun `writeVector4i should write big endian vector`() = assertWrites(
        bytesOf(1, -2, 3, -4)
    ) { writeVector4i(Vector4i(1, -2, 3, -4)) }

    @Test
    fun `writeVector4iLe should write little endian vector`() = assertWrites(
        bytesOf(1, -2, 3, -4, littleEndian = true)
    ) { writeVector4iLe(Vector4i(1, -2, 3, -4)) }
}