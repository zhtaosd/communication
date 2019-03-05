package com.zht.rxjava;

public class OpertorMap<T,R> implements Operator<R,T> {


    Function<? super T,? extends R> function;

    public OpertorMap(Function<? super T, ? extends R> function) {
        this.function = function;
    }

    @Override
    public Observer<? super T> apply(Observer<? super R> observer) {
        return new MapSubscribe<>(observer,function);
    }

    private class MapSubscribe<T,R> extends Observer<T>{
        private Observer<? super R> actual;

        private Function<? super T,? extends R> transform;

        public MapSubscribe(Observer<? super R> actual, Function<? super T, ? extends R> transform) {
            this.actual = actual;
            this.transform = transform;
        }


        @Override
        public void onNext(T t) {
            R r = transform.apply(t);
            actual.onNext(r);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
