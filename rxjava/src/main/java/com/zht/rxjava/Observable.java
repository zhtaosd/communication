package com.zht.rxjava;

public class Observable<T> {
    private ObservableOnSubscribe onSubscribe;

    public Observable(ObservableOnSubscribe onSubscribe) {
        this.onSubscribe = onSubscribe;
    }
    public static <T> Observable<T> creat(ObservableOnSubscribe<T> onSubscribe){
        return new Observable<T>(onSubscribe);
    }

    public void subScribe(Observer<? super T> subScribe){
        onSubscribe.subscribe(subScribe);
    }
}