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

@Composable
fun SampleScreen(
    onNavigateToPlayground: (() -> Unit)? = null
) {
    var maxSize by remember { mutableStateOf(DpSize.Zero) }
    val animationSetsMap = animationSets(maxSize)

    var currentAnimation by remember { mutableStateOf(animationSetsMap.first().animations.first()) }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopbar(
            isPlaygroundScreen = false,
            onNavigateToPlayground = onNavigateToPlayground
        )
        

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