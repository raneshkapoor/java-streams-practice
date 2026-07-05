package com.streams.practice;

import com.streams.practice.library.Consumer;
import com.streams.practice.library.Predicate;
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

        String str = "one";

        Predicate<String> predicate = s -> s.length() == 3;

        Assertions.assertTrue(predicate.test(str));
    }

    @Test
    void testPredicateChainAnd() {

        String str1 = "one";
        String str2 = "java";

        Predicate<String> predicate1 = s -> !s.isEmpty();
        Predicate<String> predicate2 = s -> s.length() == 3;

        Predicate<String> predicate = null;

        //  TODO Implement predicate chaining
        //Predicate<String> predicate = predicate1.and(predicate2);

        Assertions.assertTrue(predicate.test(str1));
        Assertions.assertFalse(predicate.test(str2));
    }

    @Test
    void testPredicateChainOr() {

        String str1 = "one";
        String str2 = "java";
        String str3 = "three";

        Predicate<String> predicate1 = s -> s.length() == 4;
        Predicate<String> predicate2 = s -> s.length() == 3;

        Predicate<String> predicate = null;

        //  TODO Implement predicate chaining
        //Predicate<String> predicate = predicate1.or(predicate2);

        Assertions.assertTrue(predicate.test(str1));
        Assertions.assertTrue(predicate.test(str2));
        Assertions.assertFalse(predicate.test(str3));
    }

    @Test
    void testPredicateNegate() {

        String str = "one";

        Predicate<String> predicate = String::isEmpty;

        Predicate<String> predicateNegate = null;

        //  TODO Implement predicate negate
        //Predicate<String> predicateNegate = predicate.negate();

        Assertions.assertTrue(predicateNegate.test(str));
    }

}
