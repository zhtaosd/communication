package com.zht.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ObservableOnSubscribe<String> subscribe = new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {
                observer.onNext("-----");
            }
        };
        Observable observable = Observable.creat(subscribe);
        Observer observer = new Observer<String>() {
            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subScribe(observer);

        Observable.creat(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {

            }
        }).subScribe(new Observer<String>() {
            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


}
