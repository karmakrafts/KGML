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

/**
 * Enumeration of all possible matrix components.
 */
enum class MatrixComponent { // @formatter:off
    /** Row 0, Column 0 */ M00, /** Row 0, Column 1 */ M01, /** Row 0, Column 2 */ M02, /** Row 0, Column 3 */ M03,
    /** Row 1, Column 0 */ M10, /** Row 1, Column 1 */ M11, /** Row 1, Column 2 */ M12, /** Row 1, Column 3 */ M13,
    /** Row 2, Column 0 */ M20, /** Row 2, Column 1 */ M21, /** Row 2, Column 2 */ M22, /** Row 2, Column 3 */ M23,
    /** Row 3, Column 0 */ M30, /** Row 3, Column 1 */ M31, /** Row 3, Column 2 */ M32, /** Row 3, Column 3 */ M33
} // @formatter:on