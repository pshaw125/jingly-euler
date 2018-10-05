package com.rhejinald.euler.lib.iterators;

import java.util.Iterator;

public class LoopingIterator<T> implements Iterator<T> {
    private final Iterable<T> underlying;
    private Iterator<T> iterator;

    public LoopingIterator(Iterable<T> underlying) {
        this.underlying = underlying;
        this.iterator = underlying.iterator();
    }

    @Override
    public boolean hasNext() {
        return (iterator.hasNext() || underlying.iterator().hasNext());
    }

    @Override
    public T next() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            this.iterator = underlying.iterator();
            return iterator.next();
        }
    }
}
