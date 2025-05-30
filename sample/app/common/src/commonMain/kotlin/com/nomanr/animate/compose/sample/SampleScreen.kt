import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import com.nomanr.animate.compose.components.AppTopbar
import com.nomanr.animate.compose.data.animationSets
import com.nomanr.animate.compose.sample.components.AdaptiveSampleLayout
import com.nomanr.animate.compose.ui.AdaptiveProvider

@Composable
fun SampleScreen() {
    var maxSize by remember { mutableStateOf(DpSize.Zero) }
    val animationSetsMap = animationSets(maxSize)

    var currentAnimation by remember { mutableStateOf(animationSetsMap.first().animations.first()) }

    AdaptiveProvider(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppTopbar()
            
            AdaptiveSampleLayout(
                animationSets = animationSetsMap,
                currentAnimation = currentAnimation,
                onAnimationSelected = { animation ->
                    currentAnimation = animation.copy()
                },
                onSizeChanged = { size ->
                    maxSize = size
                }
            )
        }
    }
}