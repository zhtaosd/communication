package com.zht.rxjava;


import android.os.Handler;
import android.os.Looper;

public class OnSubscibeMain<T> implements ObservableOnSubscribe<T> {

    private Handler handler;
    private Observable<T> observable;

    public OnSubscibeMain(Observable<T> observable) {
        this.observable = observable;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void subscribe( final Observer<? super T> observer) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                observable.subScribe(observer);
            }
        });
    }
}
