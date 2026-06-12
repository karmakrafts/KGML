## [Unreleased]

### Added

- Missing `distanceSq` and `distance` functions in `Vector2i`
- Missing `distanceSq` and `distance` functions in `Vector3i`
- Missing `distanceSq` and `distance` functions in `Vector4i`
- `MatrixProperties` to tag matrices as translations, affine, perspective and identity
- `rowN` row accessors for all matrix types
- `columnN` column accessors for all matrix types
- `fromRows` factory function for all matrix types
- `fromColumns` factory function for all matrix types

### Changed

- Mark `distanceSq` functions on vector types as `infix`
- Mark `distance` functions on vector types as `infix`
- `Matrix4x4f.times(Matrix4x4f)` takes into account matrix properties

## [1.0.0]

### Added

- Initial release