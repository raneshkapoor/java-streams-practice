package com.streams.practice.library;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Comparator<T> {

    int compare(T o1, T o2);

}
