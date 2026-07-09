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

    static <T> Comparator<T> nullsLast(Comparator<T> comparator) {
        Objects.requireNonNull(comparator);
        return (o1, o2) -> {
            if (o1 == o2) {
                return 0;
            } else if (o1 == null) {
                return 1;
            } else if (o2 == null) {
                return -1;
            }
            return comparator.compare(o1, o2);
        };
    }

    default <U extends Comparable<U>> Comparator<T> thenComparing(Function<T, U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (o1, o2) -> {
            int compare = compare(o1, o2);
            return compare != 0 ? compare : comparing(keyExtractor).compare(o1, o2);
        };
    }

}
