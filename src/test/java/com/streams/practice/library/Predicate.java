package com.streams.practice.library;

@FunctionalInterface
public interface Predicate<T> {

    boolean test(T t);

}
