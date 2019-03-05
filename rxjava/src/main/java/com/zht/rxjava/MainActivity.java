package com.zht.rxjava;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

        Observable.creat(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {
                observer.onNext("-----");
            }
        }).map(new Function<String, Bitmap>() {
            @Override
            public Bitmap apply(String s) {
                return Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
            }
        }).subScribe(new Observer<Bitmap>() {
            @Override
            public void onNext(Bitmap bitmap) {
                Log.i("ceshi", "onNext: 3  "  +bitmap);
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
