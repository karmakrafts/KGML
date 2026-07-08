## [Unreleased]

### Added

- Positional destructuring operators for `Quaternion4f`
- Benchmarks for `Quaternion4f`
- **More granular tags for `MatrixProperties`**
- `kgml-io` module for `kotlinx.io` extensions to read and write KGML types
- **Dedicated `toRadians` functions for `Float` and `Double`**
- **Dedicated `toDegrees` functions for `Float` and `Double`**
- 64-bit IEEE-754 vector types `Vector2d`, `Vector3d` and `Vector4d`
- 64-bit IEEE-754 matrix types `Matrix2x2d`, `Matrix3x3d` and `Matrix4x4d`
- 64-bit IEEE-754 transform types `Quaternion4d`, `Rotation3d`, `Scale2d`, `Scale3d`, `Translation2d` and `Translation3d`

### Changed

- Updated to Gradle 9.6.1
- Updated to Karma Conventions 1.18.3
- Updated to Kotlin Wrappers 2026.7.1
- Updated to NMCP 1.6.1
- **Renamed `Quaternion` to `Quaternion4f`**
- Performance improvements for all types of matrix multiplications
- Performance improvements for `Quaternion4f` multiplications
- Extended benchmarks and tests for all matrix types

### Removed

- `rotationX`, `rotationY` and `rotationZ` extensions for `Matrix3x3f`
- `rotationX`, `rotationY` and `rotationZ` extensions for `Matrix4x4f`
- `TO_DEG` constant
- `TO_RAD` constant

## [1.2.1]

### Fixed

- Possible high precision loss in `Matrix3x3f.rotationRad` and `Matrix3x3f.rotation`
- Possible high precision loss in `Matrix4x4f.rotationRad` and `Matrix4x4f.rotation`

### Added

- Default parameters for `Quaternion.fromAnglesRad`
- Default parameters for `Quaternion.fromAngles`

### Changed

- Updated to Karma Conventions 1.18.1
- Updated to Gradle 9.6.0
- Updated to Kotlin Wrappers 2026.6.9
- Migrated to NMCP based Maven Central publishing
- Deprecated `Matrix3x3f.rotationX(Rad)`, `Matrix3x3f.rotationY(Rad)` and `Matrix3x3f.rotationZ(Rad)`
- Deprecated `Matrix4x4f.rotationX(Rad)`, `Matrix4x4f.rotationY(Rad)` and `Matrix4x4f.rotationZ(Rad)`

## [1.2.0]

### Added

- WASM WASI support

### Changed

- Updated to Karma Conventions 1.18.0

### Removed

- Redundant link to `kotlinx.io` in generated Dokka output

## [1.1.0]

### Added

- Missing `distanceSq` and `distance` functions in `Vector2i`
- Missing `distanceSq` and `distance` functions in `Vector3i`
- Missing `distanceSq` and `distance` functions in `Vector4i`
- `MatrixProperties` to tag matrices as translations, affine, perspective and identity
- `rowN` row accessors for all matrix types
- `columnN` column accessors for all matrix types
- `fromRows` factory function for all matrix types
- `fromColumns` factory function for all matrix types
- `angleRad` and `angle` functions for all 2-dimensional vector types
- `angleRad` and `angle` functions for all 3-dimensional vector types
- `signedAngleRad` and `signedAngle` functions for all 3-dimensional vector types
- Axis constants for all vector types

### Changed

- Mark `distanceSq` functions on vector types as `infix`
- Mark `distance` functions on vector types as `infix`
- `Matrix4x4f.times(Matrix4x4f)` takes into account matrix properties

## [1.0.0]

### Added

- Initial release