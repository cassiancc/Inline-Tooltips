# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.5.1]

### Fixed
- Attribute tooltips missing icons.

## [1.5.0]

### Added
- Nutrition and saturation tooltips (disabled when AppleSkin is present)
- Support for Fabric 1.20.1 (and MinecraftForge through Sinytra Connector)

### Fixed
- Text not displaying on 1.21.1 when Shift is held.

## [1.4.0]

### Added
- More information is shown when Shift is held.

### Fixed
- Fuel tooltips now use NeoForge's API and are properly null-checked on 1.21.1, hopefully preventing crashes experienced on 1.3.

## [1.3.0] - 2025-10-19

### Added

- Style options for text tooltips.
- Recovery compass tooltips.
- Clock tooltips.
- `en_ud` translation.
- Tooltips for light level.

### Changed
- Non-attribute icons are now placed in the Inline Tooltips namespace.
- Non-attribute icons are no longer hidden when Shift is held.

### Fixed
- Mipmapping issues.

## [1.2.0] - 2025-10-16

Initial release.