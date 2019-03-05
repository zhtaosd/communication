package com.zht.rxjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OnSubscibeIO<T> implements ObservableOnSubscribe<T> {

    private static ExecutorService executorService = Executors.newCachedThreadPool();
    Observable<T> source;

    public OnSubscibeIO(Observable<T> tObservable) {
        this.source = source;
    }

    @Override
    public void subscribe(final Observer<? super T> observer) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                source.subScribe(observer);
            }
        });
    }
}
