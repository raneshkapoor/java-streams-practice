package com.streams.practice;

import com.streams.practice.data.Person;
import com.streams.practice.library.Comparator;
import com.streams.practice.util.PersonFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ComparatorTest {

    @Test
    void testComparator_withAge() {

        List<Person> persons = PersonFixture.getTestData();
        Comparator<Person> personComparator = Comparator.comparing(Person::getAge);

        Assertions.assertEquals(0, personComparator.compare(persons.get(0), persons.get(3)));
        Assertions.assertEquals(1, personComparator.compare(persons.get(2), persons.get(3)));
        Assertions.assertEquals(-1, personComparator.compare(persons.get(1), persons.get(2)));
    }

    @Test
    void testComparator_withSalary() {

        List<Person> persons = PersonFixture.getTestData();
        Comparator<Person> personComparator = Comparator.comparing(Person::getSalary);

        Assertions.assertEquals(-1, personComparator.compare(persons.get(0), persons.get(3)));
        Assertions.assertEquals(0, personComparator.compare(persons.get(2), persons.get(3)));
        Assertions.assertEquals(1, personComparator.compare(persons.get(1), persons.get(2)));
    }

    @Test
    void testComparator_thenComparing() {

        List<Person> persons = PersonFixture.getTestData();
        Comparator<Person> personComparator = Comparator.comparing(Person::getAge).thenComparing(Person::getSalary);

        Assertions.assertEquals(-1, personComparator.compare(persons.get(0), persons.get(3)));
        Assertions.assertEquals(1, personComparator.compare(persons.get(2), persons.get(3)));
    }

    @Test
    void testComparator_nullsLast() {

        List<Person> persons = PersonFixture.getTestData();
        Comparator<Person> personComparator = Comparator.comparing(Person::getFirstName);
        Comparator<Person> personComparatorNull = Comparator.nullsLast(personComparator);

        System.out.println(personComparatorNull.compare(persons.get(0), null));
        System.out.println(personComparatorNull.compare(null, persons.get(0)));
        System.out.println(personComparatorNull.compare(null, null));
    }

    @Test
    void testComparator_reversed() {

        List<Person> persons = PersonFixture.getTestData();
        Comparator<Person> personComparatorReversed = Comparator.comparing(Person::getSalary).reversed();

        Assertions.assertEquals(1, personComparatorReversed.compare(persons.get(0), persons.get(3)));
        Assertions.assertEquals(0, personComparatorReversed.compare(persons.get(2), persons.get(3)));
        Assertions.assertEquals(-1, personComparatorReversed.compare(persons.get(1), persons.get(2)));
    }

}
