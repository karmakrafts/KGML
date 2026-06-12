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

package dev.karmakrafts.kgml

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import org.joml.Matrix4f

@State(Scope.Benchmark)
open class JomlMatrix4x4fArithmeticBenchmark {
    val m1 = Matrix4f().perspective(70F, 1920F / 1080F, 0.1F, 100F)
    val m2 = Matrix4f().rotation(Math.toRadians(45.0).toFloat(), 1F, 0F, 0F)

    @JvmName("run")
    @Benchmark
    fun run(): Matrix4f {
        return m1.mul(m2, Matrix4f())
    }
}