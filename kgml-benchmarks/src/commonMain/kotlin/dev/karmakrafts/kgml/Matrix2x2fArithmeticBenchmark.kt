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

import dev.karmakrafts.kgml.matrix.Matrix2x2f
import dev.karmakrafts.kgml.transform.rotation
import dev.karmakrafts.kgml.transform.scale
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName

@State(Scope.Benchmark)
open class Matrix2x2fArithmeticBenchmark {
    val genericLeft = Matrix2x2f(1.25F, 2.5F, 3.75F, 4.25F)
    val genericRight = Matrix2x2f(4.25F, 3.75F, 2.5F, 1.25F)
    val genericScale = Matrix2x2f(4F, 0F, 0F, 5F)
    val genericScaleRight = Matrix2x2f(6F, 0F, 0F, 7F)
    val scale = Matrix2x2f.scale(4F, 5F)
    val scaleRight = Matrix2x2f.scale(6F, 7F)
    val rotation = Matrix2x2f.rotation(45F)
    val rotationRight = Matrix2x2f.rotation(30F)
    val genericRotation = Matrix2x2f(rotation.m00, rotation.m01, rotation.m10, rotation.m11)
    val genericRotationRight = Matrix2x2f(rotationRight.m00, rotationRight.m01, rotationRight.m10, rotationRight.m11)

    @JvmName("generic")
    @Benchmark
    fun generic(): Matrix2x2f {
        return genericLeft * genericRight
    }

    @JvmName("genericDiagonalDiagonal")
    @Benchmark
    fun genericDiagonalDiagonal(): Matrix2x2f {
        return genericScale * genericScaleRight
    }

    @JvmName("genericRotationRotation")
    @Benchmark
    fun genericRotationRotation(): Matrix2x2f {
        return genericRotation * genericRotationRight
    }

    @JvmName("linearDiagonal")
    @Benchmark
    fun linearDiagonal(): Matrix2x2f {
        return rotation * scale
    }

    @JvmName("diagonalLinear")
    @Benchmark
    fun diagonalLinear(): Matrix2x2f {
        return scale * rotation
    }

    @JvmName("diagonalDiagonal")
    @Benchmark
    fun diagonalDiagonal(): Matrix2x2f {
        return scale * scaleRight
    }

    @JvmName("rotationRotation")
    @Benchmark
    fun rotationRotation(): Matrix2x2f {
        return rotation * rotationRight
    }
}