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

import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.transform.rotation
import dev.karmakrafts.kgml.transform.scale
import dev.karmakrafts.kgml.transform.translation
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName

@State(Scope.Benchmark)
open class Matrix3x3fArithmeticBenchmark {
    val genericLeft = Matrix3x3f(1.25F, 2.5F, 3.75F, 4.25F, 5.5F, 6.75F, 7.25F, 8.5F, 9.75F)
    val genericRight = Matrix3x3f(9.75F, 8.5F, 7.25F, 6.75F, 5.5F, 4.25F, 3.75F, 2.5F, 1.25F)
    val genericScale = Matrix3x3f(4F, 0F, 0F, 0F, 5F, 0F, 0F, 0F, 1F)
    val genericScaleRight = Matrix3x3f(6F, 0F, 0F, 0F, 7F, 0F, 0F, 0F, 1F)
    val translation = Matrix3x3f.translation(2F, 3F)
    val translationRight = Matrix3x3f.translation(5F, 7F)
    val scale = Matrix3x3f.scale(4F, 5F)
    val scaleRight = Matrix3x3f.scale(6F, 7F)
    val rotation = Matrix3x3f.rotation(angleZ = 45F)
    val rotationRight = Matrix3x3f.rotation(angleZ = 30F)
    val genericRotation = Matrix3x3f(
        rotation.m00,
        rotation.m01,
        rotation.m02,
        rotation.m10,
        rotation.m11,
        rotation.m12,
        rotation.m20,
        rotation.m21,
        rotation.m22
    )
    val genericRotationRight = Matrix3x3f(
        rotationRight.m00,
        rotationRight.m01,
        rotationRight.m02,
        rotationRight.m10,
        rotationRight.m11,
        rotationRight.m12,
        rotationRight.m20,
        rotationRight.m21,
        rotationRight.m22
    )

    @JvmName("generic")
    @Benchmark
    fun generic(): Matrix3x3f {
        return genericLeft * genericRight
    }

    @JvmName("genericDiagonalDiagonal")
    @Benchmark
    fun genericDiagonalDiagonal(): Matrix3x3f {
        return genericScale * genericScaleRight
    }

    @JvmName("genericRotationRotation")
    @Benchmark
    fun genericRotationRotation(): Matrix3x3f {
        return genericRotation * genericRotationRight
    }

    @JvmName("linearDiagonal")
    @Benchmark
    fun linearDiagonal(): Matrix3x3f {
        return rotation * scale
    }

    @JvmName("diagonalLinear")
    @Benchmark
    fun diagonalLinear(): Matrix3x3f {
        return scale * rotation
    }

    @JvmName("diagonalDiagonal")
    @Benchmark
    fun diagonalDiagonal(): Matrix3x3f {
        return scale * scaleRight
    }

    @JvmName("rotationRotation")
    @Benchmark
    fun rotationRotation(): Matrix3x3f {
        return rotation * rotationRight
    }

    @JvmName("affineLinear")
    @Benchmark
    fun affineLinear(): Matrix3x3f {
        return translation * scale
    }

    @JvmName("linearTranslation")
    @Benchmark
    fun linearTranslation(): Matrix3x3f {
        return scale * translation
    }

    @JvmName("translationTranslation")
    @Benchmark
    fun translationTranslation(): Matrix3x3f {
        return translation * translationRight
    }
}