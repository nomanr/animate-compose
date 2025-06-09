# Contributing Guidelines

Thank you for considering contributing to animate-compose! We are happy to have you here.

## About this repository

This repository holds the animation library components, presets, and sample applications for demonstrating the animations across different platforms.

- `animate-compose` holds the core animation library and presets
- `sample/app/android` Android catalogue app
- `sample/app/common` Compose Multiplatform common code
- `sample/app/desktop` Desktop runner module
- `sample/app/ios` iOS runner module
- `sample/app/web` Web runner module

## Structure

```
├── gradle/
│   ├── libs.versions.toml         # Dependency version management
├── animate-compose/               # Core animation library
│   ├── src/
│   │   ├── commonMain/           # Multiplatform animation code
│   │   │   ├── animated/         # Main animation API
│   │   │   ├── core/             # Core animation engine
│   │   │   ├── presets/          # Animation presets by category
│   │   │   └── tokens/           # Animation tokens
│   │   ├── androidMain/          # Android-specific implementations
│   │   └── skikoMain/            # Skiko-specific implementations
├── sample/                        # Sample applications
│   ├── app/
│   │   ├── android/              # Android sample app
│   │   ├── common/               # Shared sample code
│   │   ├── desktop/              # Desktop sample app
│   │   ├── ios/                  # iOS sample app
│   │   └── web/                  # Web sample app
│   └── ui-components/            # Sample UI components
```

## Development

To contribute to this project, follow these steps:

#### Fork the repository

Click on the 'Fork' button at the top right corner of the repository page to create a copy of the repository.

#### Clone your fork:

```bash
git clone https://github.com/your-username/animate-compose.git
```

#### Create a new branch:

```bash
git checkout -b feature/your-feature-name
```

#### Make your changes:

Make the necessary changes to the codebase. Ensure that your code follows the project's coding standards and guidelines.

## Adding New Animation Presets

To add a new animation preset:

1. Determine the appropriate category for your animation (e.g., attention seekers, entrances, exits)
2. Create a new Kotlin file in the appropriate package under `animate-compose/src/commonMain/kotlin/com/nomanr/animate/compose/presets/`
3. Extend the `AnimationPreset` interface:

```kotlin
class YourAnimation : AnimationPreset {
    override val name = "YourAnimation"
    override val durationMillis = 1000 // Default duration
    
    override fun createKeyframes(containerSize: ContainerSize): List<Keyframe> {
        return listOf(
            // Define your keyframes here
            Keyframe.static(0f, alpha = 0f),
            Keyframe.segment(
                0f to 1f,
                alpha = SegmentFloat(0f, 1f),
                easing = Easing.EaseInOut
            )
        )
    }
}
```

4. Add your animation to the appropriate category list in `sample/app/common/src/commonMain/kotlin/com/nomanr/animate/compose/data/Animations.kt`
5. Test your animation in the playground

## Code Style Guidelines

- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Keep functions focused and concise
- Add KDoc comments for public APIs
- Ensure your code is properly formatted

## Testing

Before submitting your PR:

1. Run the sample apps to verify your changes work correctly
2. Test on multiple platforms if possible (Android, Desktop, Web)
3. Ensure existing animations still work as expected
4. Check that the build passes:

```bash
./gradlew build
```

## Creating a Pull Request

1. Commit your changes with a clear, descriptive message
2. Push your branch to your fork
3. Create a pull request from your fork to the main repository
4. Provide a clear description of your changes
5. Reference any related issues
6. Allow maintainer edits on your PR

## Request for New Animations

If you have ideas for new animations but aren't ready to implement them:

1. Create an issue describing the animation
2. Include reference materials or examples if possible
3. Explain potential use cases

We'd be happy to help implement or guide you through the process!

## Questions?

Feel free to open an issue for any questions about contributing.

**Happy animating!** 🎨