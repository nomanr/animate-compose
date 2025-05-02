import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.components.AnimatedDemo
import com.nomanr.animate.compose.components.AnimationList
import com.nomanr.animate.compose.data.animationSets
import com.nomanr.animate.compose.ui.AppTheme

@Composable
fun SampleScreen() {
    var currentAnimation by remember { mutableStateOf(animationSets.first().animations.first()) }

    Row(
        modifier = Modifier.background(AppTheme.colors.background).fillMaxSize(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AnimatedDemo(animation = currentAnimation)
        }

        Box(
            modifier = Modifier.wrapContentWidth().background(AppTheme.colors.tertiary).padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.wrapContentWidth().fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                AnimationList { animation ->
                    currentAnimation = animation.copy()
                }
            }
        }
    }
}