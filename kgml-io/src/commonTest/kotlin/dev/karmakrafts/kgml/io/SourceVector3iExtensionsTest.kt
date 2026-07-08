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

import dev.karmakrafts.kgml.vector.Vector3i
import kotlin.test.Test

class SourceVector3iExtensionsTest {
    @Test
    fun `readVector3i should read big endian vector`() = assertReads(
        bytesOf(1, -2, 3), Vector3i(1, -2, 3)
    ) { readVector3i() }

    @Test
    fun `readVector3iLe should read little endian vector`() = assertReads(
        bytesOf(1, -2, 3, littleEndian = true), Vector3i(1, -2, 3)
    ) { readVector3iLe() }
}