package pinger.challenge

import io.reactivex.observers.DisposableObserver

interface PageSequenceContract {

    interface View {
        fun setupPresenter(presenter: Presenter)
        fun changeProgressBarVisibility(visible: Boolean)
        fun updatePathSequenceList(pageSequenceData: List<Pair<String, Int>>)
        fun showErrorMessage(message: String)
    }

    interface Presenter {
        fun fetchMostPopularPathSequences()
        fun getDefaultObserver(): DisposableObserver<String>
        fun cleanUp()
    }
}