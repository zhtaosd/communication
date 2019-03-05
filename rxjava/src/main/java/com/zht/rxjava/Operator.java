package com.zht.rxjava;

public interface Operator<T,R> extends Function<Observer<? super T>,Observer<? super R>> {
}
