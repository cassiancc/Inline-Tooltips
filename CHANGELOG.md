# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.6.2]

### Fixed
- Duplicated attributes on NeoForge.

## [1.6.1]

### Fixed
- Mipmapping issues

## [1.6.0]

### Added
- Durability tooltips can now show durability even before the item is damaged, defaults to disabled.
- Spacing between icons can now be adjusted.
- Textures for Explosion Knockback Resistance, Submerged Mining Speed, and Burning Time, contributed by ProbablyEkho.
- Textures for Food, Saturation, Attack Knockback, Attack Reach, Fall Damage Multiplier, Flying Speed, Gravity, Jump Strength, Luck, Mining Efficiency, Movement Efficiency, Safe Fall Damage, Scale, Step Height, Sneaking Speed, Sweeping Damage Ration, Water Movement Efficiency, contributed by dmsw0303.

### Changed
- Durability tooltips now have their own config section.
- Missing textures are now represented by an empty texture instead of a missing texture icon.

### Fixed
- Crash on startup on NeoForge 1.21.10.

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