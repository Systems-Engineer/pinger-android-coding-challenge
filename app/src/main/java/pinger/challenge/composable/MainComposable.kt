package pinger.challenge.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import pinger.challenge.R
import pinger.challenge.intent.PageSequenceContract
import pinger.challenge.viewmodel.PageSequenceViewModel

@Composable
fun MainActivityScreen(viewModel: PageSequenceViewModel) {
    val state = viewModel.viewState.collectAsState()
    val effect = viewModel.effect
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberLazyListState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBar() },
        content = { ScreenContent(state.value, effect, scaffoldState, scrollState, it) },
        floatingActionButton = {
            FloatingButton {
                viewModel.setEvent(PageSequenceContract.Event.FetchMostPopularPathSequences)
            }
        }
    )
}

@Composable
fun AppBar() {
    TopAppBar(
        title = { Text(stringResource(id = R.string.pinger_challenge)) },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun FloatingButton(onClick: ()-> Unit) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Image(
            painter = painterResource(id = R.drawable.cloud_download),
            contentDescription = stringResource(id = R.string.download_icon)
        )
    }
}

@Composable
fun ScreenContent(
    state: PageSequenceContract.State,
    effectFlow: Flow<PageSequenceContract.Effect>,
    scaffoldState: ScaffoldState,
    scrollState: LazyListState,
    paddingValues: PaddingValues
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = scrollState,
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = state.list,
                itemContent = { item ->
                    ListItem(item)
                }
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        LaunchedEffect(scaffoldState.snackbarHostState) {
            effectFlow.onEach { effect ->
                when (effect) {
                    is PageSequenceContract.Effect.ShowSnackError -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = effect.message.toString()
                        )
                    }
                }
            }.collect()
        }
    }
}

@Composable
fun ListItem(item: Pair<String, Int>) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(2.dp))
    ) {
        Card(
            shape = RoundedCornerShape(2.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextView(name = item.second.toString(), Color.Gray)
                TextView(name = item.first)
            }
        }
    }
}

@Composable
fun TextView(name: String, color: Color = Color.Black) {
    Text(
        text = name,
        style = MaterialTheme.typography.h4,
        color = color,
        fontSize = 16.sp
    )
}