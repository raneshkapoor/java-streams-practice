package com.streams.practice.library;

import java.util.Objects;

@FunctionalInterface
public interface Consumer<T> {

    void accept(T t);

}
