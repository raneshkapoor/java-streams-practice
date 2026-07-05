package com.streams.practice;

import com.streams.practice.library.Consumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FunctionalInterfaceTest {

    @Test
    void testConsumer() {

        List<String> list = new ArrayList<>();

        Consumer<List<String>> consumer = l -> l.add("one");
        consumer.accept(list);

        Assertions.assertEquals(1, list.size());
    }

    @Test
    void testConsumerChain() {

        List<String> list = new ArrayList<>();

        Consumer<List<String>> consumer1 = l -> l.add("one");
        Consumer<List<String>> consumer2 = l -> l.add("two");

        Consumer<List<String>> consumer = null;

        //  TODO Implement consumer chaining
        //Consumer<List<String>> consumer = consumer1.andThen(consumer2);
        //consumer.accept(list);

        Assertions.assertEquals(2, list.size());
    }

    @Test
    void testPredicate() {



    }

}
