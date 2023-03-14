package pinger.challenge.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pinger.challenge.intent.DataState
import pinger.challenge.intent.PageSequenceContract
import pinger.challenge.repository.PageSequenceRepository
import javax.inject.Inject

@HiltViewModel
class PageSequenceViewModel @Inject constructor(
    private val pageSequenceRepository: PageSequenceRepository
) : BaseViewModel<PageSequenceContract.Event, PageSequenceContract.State, PageSequenceContract.Effect>() {

    init {
        setEvent(PageSequenceContract.Event.FetchMostPopularPathSequences)
    }

    override fun setInitialState() = PageSequenceContract.State(isLoading = true)

    override fun handleEvent(event: PageSequenceContract.Event) {
        setState { copy(isLoading = true) }
        when (event) {
            is PageSequenceContract.Event.FetchMostPopularPathSequences -> {
                viewModelScope.launch(IO) {
                    pageSequenceRepository
                        .fetchMostPopularPathSequences(this)
                        .onEach { dataState ->
                            when (dataState) {
                                is DataState.Success -> {
                                    setState {
                                        copy(list = dataState.data, isLoading = false)
                                    }
                                }
                                is DataState.Error -> {
                                    setState { copy(isLoading = false) }
                                    setEffect { PageSequenceContract.Effect.ShowSnackError(
                                            dataState.exception.message) }
                                }
                            }
                        }.launchIn(this)
                }
            }
        }
    }
}
