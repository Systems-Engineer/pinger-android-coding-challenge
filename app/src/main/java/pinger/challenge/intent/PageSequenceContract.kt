package pinger.challenge.intent

class PageSequenceContract {
    sealed class Event : ViewEvent {
        object FetchMostPopularPathSequences : Event()
    }

    data class State (
        val list: MutableList<Pair<String, Int>> = mutableListOf(),
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class ShowSnackError(val message: String?) : Effect()
    }
}