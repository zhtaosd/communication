package com.zht.rxjava;

public class OnSubscribeLift<T,R> implements ObservableOnSubscribe<R> {

    ObservableOnSubscribe<T> parent;
    Operator<? extends R,? super T> operator;

    public OnSubscribeLift(ObservableOnSubscribe<T> parent, Function<? super T, ? extends R> function) {
        this.parent = parent;
        this.operator = new OpertorMap<T,R>(function);
    }

    @Override
    public void subscribe(Observer<? super R> observer) {
        Observer<? super T> st = operator.apply(observer);
        parent.subscribe(st);
    }
}
