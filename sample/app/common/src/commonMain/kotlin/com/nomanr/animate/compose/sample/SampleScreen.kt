import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.components.AppTopbar
import com.nomanr.animate.compose.data.animationSets
import com.nomanr.animate.compose.sample.components.AnimatedDemo
import com.nomanr.animate.compose.sample.components.AnimationList
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.VerticalDivider

@Composable
fun SampleScreen() {
    var currentAnimation by remember { mutableStateOf(animationSets.first().animations.first()) }

    Column(modifier = Modifier.fillMaxSize()) {

        AppTopbar()

        Row(
            modifier = Modifier.background(AppTheme.colors.background).fillMaxSize(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Row(
                modifier = Modifier.wrapContentWidth().fillMaxHeight().background(AppTheme.colors.tertiary),
                horizontalArrangement = Arrangement.Start
            ) {
                AnimationList { animation ->
                    currentAnimation = animation.copy()
                }
                VerticalDivider()
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                AnimatedDemo(animation = currentAnimation)
            }
        }
    }
}