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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pinger.challenge.R
import pinger.challenge.intent.DataState
import pinger.challenge.intent.PageSequenceAction
import pinger.challenge.viewmodel.PageSequenceViewModel

@Composable
fun MainActivityScreen(viewModel: PageSequenceViewModel) {
    val dataState by viewModel.dataState.observeAsState()
    val scrollState = rememberLazyListState()
    val isLoading = remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()

    viewModel.triggerAction(PageSequenceAction.FetchMostPopularPathSequencesAction)

    Scaffold(
        topBar = { AppBar() },
        content = { RecyclerView(dataState, scrollState, it) },
        floatingActionButton = { FloatingButton(isLoading) }
    )
}

@Composable
fun LoadingView() {
    Box(
        contentAlignment = Alignment.Center, // you apply alignment to all children
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center) // or to a specific child
        )
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        title = { Text(stringResource(id = R.string.pinger_challenge)) },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun FloatingButton(isLoading : MutableState<Boolean>) {
    FloatingActionButton(onClick = {
        isLoading.value = true
        // TODO do action here
        // TODO isLoading.value = false
    }) {
        Image(
            painter = painterResource(id = R.drawable.cloud_download),
            contentDescription = stringResource(id = R.string.download_icon)
        )
    }
}

@Composable
fun RecyclerView(dataState: DataState<MutableList<Pair<String, Int>>>?, scrollState: LazyListState, paddingValues: PaddingValues) {
    when (dataState) {
        is DataState.Success -> {
            LazyColumn(state = scrollState, contentPadding = paddingValues, modifier = Modifier.fillMaxSize()) {
                items(
                    items = dataState.data,
                    itemContent = { item ->
                        ListItem(item)
                    }
                )
            }
        }
        is DataState.Error -> {

        }
        is DataState.Loading -> {
            LoadingView()
        }
        else -> {
            // null state show error

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
            shape = RoundedCornerShape(2.dp),
            backgroundColor = MaterialTheme.colors.background,
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