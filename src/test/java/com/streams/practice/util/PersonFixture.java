package com.streams.practice.util;

import com.streams.practice.data.Person;

import java.util.List;

public class PersonFixture {

    public static List<Person> getTestData() {
        return List.of(
                new Person("Mark", "Henry", 25, 100000),
                new Person("John", "Cena", 24, 150000),
                new Person("Big", "Show", 30, 125000),
                new Person("Shawn", "Micheal", 25, 125000),
                new Person("Randy", "Orton", 20, 150000)
        );
    }

}
