package pinger.challenge.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pinger.challenge.intent.PageSequenceAction
import pinger.challenge.intent.DataState
import pinger.challenge.repository.PageSequenceRepository
import javax.inject.Inject

@HiltViewModel
class PageSequenceViewModel @Inject constructor(
    private val pageSequenceRepository: PageSequenceRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**private val _pathSequenceList : MutableLiveData<MutableList<String>> = savedStateHandle.getLiveData("list")
    val pathSequenceList : LiveData<MutableList<String>>
        get() = _pathSequenceList**/

    private val _dataState: MutableLiveData<DataState<MutableList<Pair<String, Int>>>> = MutableLiveData()
    val dataState: LiveData<DataState<MutableList<Pair<String, Int>>>>
        get() = _dataState

    fun triggerAction(event: PageSequenceAction) {
        viewModelScope.launch {
            when (event) {
                is PageSequenceAction.FetchMostPopularPathSequencesAction -> {
                    pageSequenceRepository.fetchMostPopularPathSequences(CoroutineScope(IO))
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }.launchIn(viewModelScope)
                }
            }
        }
    }
}