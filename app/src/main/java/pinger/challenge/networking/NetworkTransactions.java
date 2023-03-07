package pinger.challenge.networking;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okio.BufferedSource;

import java.io.IOException;

public class NetworkTransactions {

    public FileDownloadAPI api;

    public synchronized FileDownloadAPI getApi() {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl("https://raw.githubusercontent.com")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(FileDownloadAPI.class);
        }
        return api;
    }

    public void downloadApacheFile(Observer<String> observer) {
        downloadApacheFile(observer, Schedulers.io(), AndroidSchedulers.mainThread());
    }

    public void downloadApacheFile(Observer<String> observer, Scheduler subscribeScheduler, Scheduler observeScheduler) {
        getApi().downloadApacheLogStream().flatMap(responseBody -> getLinesOfInputFromSource(responseBody.source()))
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)
                .subscribe(observer);
    }

    private Observable<String> getLinesOfInputFromSource(BufferedSource source) {
        return Observable.create(emitter -> {
            try {
                while (!source.exhausted()) {
                    String line = source.readUtf8Line();
                    if (line != null) {
                        emitter.onNext(line);
                    }
                }
                emitter.onComplete();
            } catch (IOException ex) {
                ex.printStackTrace();
                emitter.onError(ex);
            }
        });
    }
}
