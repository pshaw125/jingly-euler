package com.rhejinald.euler.lib;

public class TwoTuple <T>{
    private final T t1;
    private final T t2;

    public TwoTuple(T t1, T t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public T getT1() {
        return t1;
    }

    public T getT2() {
        return t2;
    }
}
