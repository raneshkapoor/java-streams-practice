package com.streams.practice.library;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Comparator<T> {

    int compare(T o1, T o2);

    static <T, U extends Comparable<U>> Comparator<T> comparing(Function<T, U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (o1, o2) -> keyExtractor.apply(o1).compareTo(keyExtractor.apply(o2));
    }

}
