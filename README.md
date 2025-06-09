# animate-compose

[![Maven Central](https://img.shields.io/maven-central/v/com.nomanr/animate-compose.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.nomanr%22%20AND%20a:%22animate-compose%22)

A powerful Kotlin Multiplatform animation library for Jetpack Compose with 90+ ready-to-use animations inspired by animate.css.

<div style="background-color: white; padding: 20px;">
  
![Group 38](https://github.com/user-attachments/assets/9a898df6-a086-4bb1-92fa-614b70e3945d)

![FocuSeeProject2025-06-0911-49-31_10-ezgif com-crop (1)](https://github.com/user-attachments/assets/cf701bad-7bd6-444d-91a6-a924a623f527)

</div>

## Features

- 🎨 **90+ Pre-built Animations** - Attention seekers, entrances, exits, and special effects
- 🚀 **Simple API** - Just wrap your composables with `Animated()`
- 🎯 **Keyframe-based** - Smooth, natural animations with custom easing
- 📱 **Multiplatform** - Works on Android, iOS, Desktop, and Web
- ⚡ **Performant** - Optimized for Compose with minimal overhead
- 🛠 **Customizable** - Create your own animation presets
- 🎮 **Interactive Playground** - Test and configure animations in real-time

## Quick Start

### Installation

Add the dependency to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.nomanr:animate-compose:${version}")
}
```

### Basic Usage

```kotlin
import com.nomanr.animate.compose.animated.Animated
import com.nomanr.animate.compose.presets.fadeinentrances.FadeIn

@Composable
fun MyComponent() {
    Animated(
        preset = FadeIn(),
        durationMillis = 1000
    ) {
        Text("Hello, Animated World!")
    }
}
```

## Animation Categories

See the [complete list of animations](docs/ANIMATIONS.md) for detailed descriptions of all 90+ animations.

### Attention Seekers
Perfect for drawing user attention to important elements.

```kotlin
Animated(preset = Bounce()) { Icon(...) }
Animated(preset = Flash()) { Button(...) }
Animated(preset = Pulse()) { Card(...) }
Animated(preset = RubberBand()) { Text(...) }
Animated(preset = ShakeX()) { TextField(...) }
Animated(preset = Swing()) { Image(...) }
Animated(preset = Tada()) { Box(...) }
```

### Entrances
Smooth ways to introduce elements to the screen.

```kotlin
// Fade entrances
Animated(preset = FadeIn()) { ... }
Animated(preset = FadeInDown()) { ... }
Animated(preset = FadeInLeft()) { ... }
Animated(preset = FadeInRight()) { ... }
Animated(preset = FadeInUp()) { ... }

// Bounce entrances
Animated(preset = BounceIn()) { ... }
Animated(preset = BounceInDown()) { ... }
Animated(preset = BounceInLeft()) { ... }

// Slide entrances
Animated(preset = SlideInDown()) { ... }
Animated(preset = SlideInLeft()) { ... }

// Zoom entrances
Animated(preset = ZoomIn()) { ... }
Animated(preset = ZoomInDown()) { ... }

// And many more...
```

### Exits
Graceful ways to remove elements from the screen.

```kotlin
Animated(preset = FadeOut(), animateOnEnter = false) { ... }
Animated(preset = BounceOut(), animateOnEnter = false) { ... }
Animated(preset = SlideOutUp(), animateOnEnter = false) { ... }
Animated(preset = ZoomOut(), animateOnEnter = false) { ... }
```

### Special Effects
Unique animations for memorable interactions.

```kotlin
Animated(preset = Hinge()) { ... }
Animated(preset = JackInTheBox()) { ... }
Animated(preset = RollIn()) { ... }
Animated(preset = RollOut()) { ... }
```

## Advanced Usage

### Controlling Animation State

```kotlin
val animatedState = rememberAnimatedState()

Animated(
    preset = FadeIn(),
    state = animatedState
) {
    Card { ... }
}

// Control the animation
LaunchedEffect(someCondition) {
    animatedState.animate()
}
```

### Custom Animation State

```kotlin
val animatedState = rememberAnimatedState()

// Check animation state
val isAnimating = animatedState.isAnimating.value
val isFinished = animatedState.isAnimationFinished.value

Animated(
    preset = BounceIn(),
    state = animatedState
) {
    Box { ... }
}

// Trigger animation manually
Button(onClick = { animatedState.animate() }) {
    Text("Animate")
}
```

### Repeat Animations

```kotlin
Animated(
    preset = Pulse(),
    repeat = true,
    durationMillis = 1000
) {
    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "Pulsing heart"
    )
}
```

### Custom Animation Presets

Create your own animation presets by extending `AnimationPreset`:

```kotlin
class MyCustomAnimation : AnimationPreset {
    override val name = "MyCustomAnimation"
    override val durationMillis = 800
    
    override fun createKeyframes(containerSize: ContainerSize): List<Keyframe> {
        return listOf(
            Keyframe.static(0f, alpha = 0f, scale = 0.3f),
            Keyframe.segment(
                0f to 0.5f,
                alpha = SegmentFloat(0f, 1f),
                scale = SegmentFloat(0.3f, 1.05f),
                easing = Easing.EaseOutCubic
            ),
            Keyframe.segment(
                0.5f to 1f,
                scale = SegmentFloat(1.05f, 1f),
                easing = Easing.EaseInOutCubic
            )
        )
    }
}
```

## Platform Support

| Platform | Status | Notes |
|----------|--------|-------|
| Android | ✅ Stable | Full support |
| iOS | ✅ Stable | Via Skiko |
| Desktop | ✅ Stable | Via Skiko |
| Web | ✅ Stable | Via Skiko |

## Performance Tips

1. **Reuse Animation Presets**: Create preset instances once and reuse them
2. **Avoid Excessive Layers**: Complex animations on deeply nested composables may impact performance
3. **Use `animateOnEnter`**: Set to `false` for exit animations to avoid unnecessary initial animations
4. **Profile Your Animations**: Use the built-in playground to test performance with your specific use cases

## Sample App

Explore all animations in our interactive playground:

```bash
# Clone the repository
git clone https://github.com/yourusername/animate-compose.git
```

### Running the Sample App

The sample app includes:
- Visual catalog of all animations
- Interactive configuration options
- Code generation for custom presets
- Performance profiling tools

#### Android
```bash
./gradlew :sample:app:android:installDebug
# or
./gradlew :sample:app:android:assembleDebug
```

#### iOS
```bash
# Open the project in Xcode
open sample/app/ios/iosApp.xcodeproj

# Or build from command line
cd sample/app/ios
xcodebuild -scheme iosApp -configuration Debug -destination 'platform=iOS Simulator,name=iPhone 15'
```

#### Desktop
```bash
./gradlew :sample:app:desktop:run
```

#### Web
```bash
# Development server with hot reload
./gradlew :sample:app:web:jsBrowserDevelopmentRun

# Production build
./gradlew :sample:app:web:jsBrowserProductionWebpack
```

## Contributing

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for details.

### Adding New Animations

1. Create a new class extending `AnimationPreset`
2. Define keyframes using the keyframe DSL
3. Place it in the appropriate category package
4. Add tests and documentation

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE.txt](LICENSE.txt) file for details.