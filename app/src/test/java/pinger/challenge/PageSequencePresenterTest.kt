package pinger.challenge

import pinger.challenge.networking.MockNetworkClass
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.observers.DisposableObserver
import org.junit.Before
import org.junit.Test


class PageSequencePresenterTest {

    private lateinit var presenter: PageSequencePresenter
    private val mockNetworkClass = MockNetworkClass()
    private val mockedView = mock<PageSequenceContract.View>()

    @Before
    fun setUp() {
        presenter = PageSequencePresenter(mockedView, mockNetworkClass)
    }

    @Test
    fun observerSetsDataAndViews() {
        val observer = presenter.getDefaultObserver()
        addMockDataToObserver(observer)
        observer.onComplete()

        verify(mockedView).updatePathSequenceList(mutableListOf(Pair("/products/\n/products/admin/\n/products/about/", 1)))
        verify(mockedView).changeProgressBarVisibility(false)
    }

    @Test
    fun fetchMostPopularPathSequencesShowsProgressBar() {
        presenter.fetchMostPopularPathSequences()
        verify(mockedView).changeProgressBarVisibility(true)
    }

    private fun addMockDataToObserver(observer: DisposableObserver<String>) {
        observer.onNext("123.4.5.5 - - [03/Sep/2013:18:36:28 -0600] \"GET /about/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0\"\n")
        observer.onNext("123.4.5.7 - - [03/Sep/2013:18:36:48 -0600] \"GET /products/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0\"\n")
        observer.onNext("123.4.5.7 - - [03/Sep/2013:18:36:48 -0600] \"GET /products/admin/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0\"\n")
        observer.onNext("123.4.5.7 - - [03/Sep/2013:18:36:48 -0600] \"GET /products/about/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0\"\n")
    }
}