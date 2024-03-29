package pinger.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import pinger.challenge.composable.MainActivityScreen
import pinger.challenge.theme.PingerAndroidCodingChallengeTheme
import pinger.challenge.viewmodel.PageSequenceViewModel

@AndroidEntryPoint
class PageSequenceActivity : ComponentActivity() {
    private val viewModel : PageSequenceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivity(viewModel)
        }
    }

}

@Composable
fun MainActivity(viewModel: PageSequenceViewModel) {
    PingerAndroidCodingChallengeTheme {
        MainActivityScreen(viewModel)
    }
}
