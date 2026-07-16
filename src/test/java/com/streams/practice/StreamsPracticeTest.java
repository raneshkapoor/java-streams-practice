package com.streams.practice;

import com.streams.practice.data.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamsPracticeTest {

    private static final List<Person> PERSONS = List.of(
            new Person("Mark", "Henry", 25, 100000),
            new Person("John", "Cena", 24, 150000),
            new Person("Big", "Show", 30, 125000),
            new Person("Shawn", "Micheal", 25, 125000),
            new Person("Randy", "Orton", 20, 150000)
    );

    private static final List<Integer> NUMBERS = List.of(
            43, 21, 76, 32, 90, 14, 75, 57, 99, 44,
            89, 17, 23, 67, 74, 10, 66, 47, 51, 39,
            68, 95, 20, 61, 77, 83, 45, 16, 55, 93
    );

    private static final List<String> WORDS = List.of(
            "alpha", "bravo", "charlie", "delta", "echo",
            "foxtrot", "golf", "hotel", "india", "juliet", "kilo",
            "lima", "mike", "november", "oscar", "papa", "quebec",
            "romeo", "sierra", "tango", "uniform", "victor",
            "whiskey", "x-ray", "yankee", "zulu"
    );

    private static final String STR = "java programming";

    private static final String SENTENCE = "this is a sample sentence";

    @Test
    void testStreams_findEvenNumbers() {

        List<Integer> evens = NUMBERS.stream()
                .filter(number -> ((number & 1) == 0))
                .collect(Collectors.toList());

        Assertions.assertEquals(11, evens.size());
    }

    @Test
    void testStreams_findMaximumNumber() {

        Integer max = NUMBERS.stream()
                .max(Integer::compareTo)
                .orElseThrow(AssertionError::new);

        Assertions.assertEquals(99, max);
    }

    @Test
    void testStreams_reverseSortNumbers() {

        List<Integer> reverseSort = NUMBERS.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        Assertions.assertEquals(99, reverseSort.get(0));
    }

    @Test
    void testStreams_countStringsWithSpecificPrefix() {

        //  Number of words starting with "A"

        List<String> newList = WORDS.stream()
                .filter(word -> word.startsWith("a"))
                .collect(Collectors.toList());

        Assertions.assertEquals(1, newList.size());
    }

    @Test
    void testStreams_FirstNonRepeatingCharacterInString() {

        String str = "streams";

        Character character = str.chars()
                .mapToObj(ch -> (char) ch)
                .filter(ch -> str.indexOf(ch) == str.lastIndexOf(ch))
                .findFirst()
                .orElseThrow(AssertionError::new);

        Assertions.assertEquals('t', character);
    }

    @Test
    void testStreams_convertStringsToUpperCase() {

        List<String> upperWords = WORDS.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        Assertions.assertEquals("ALPHA",  upperWords.get(0));
    }

    @Test
    void testStreams_sumOfNumbers() {

        int sum = NUMBERS.stream()
                .mapToInt(Integer::intValue)
                .sum();

        Assertions.assertEquals(1647, sum);

        sum = NUMBERS.stream()
                .reduce(0, Integer::sum);

        Assertions.assertEquals(1647, sum);
    }

    @Test
    void testStreams_duplicateElementsInList() {

        List<Integer> NUM_LIST = new ArrayList<>(NUMBERS);
        NUM_LIST.addAll(List.of(76, 10, 14, 14));

        //  Frequency Map Approach

        Map<Integer, Long> freq = NUM_LIST.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        Assertions.assertEquals(2, freq.get(10));
        Assertions.assertEquals(2, freq.get(76));
        Assertions.assertEquals(3, freq.get(14));

        //  HashSet Approach

        Set<Integer> distinct = new HashSet<>();

        Set<Integer> duplicates = NUM_LIST.stream()
                .filter(i -> !distinct.add(i))
                .collect(Collectors.toSet());

        Assertions.assertEquals(3, duplicates.size());
    }

    @Test
    void testStreams_groupStringsByLength() {

        Map<Integer, List<String>> map = WORDS.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.toList()
                ));

        Assertions.assertEquals(7, map.get(4).size());
    }

    @Test
    void testStreams_flattenListsOfLists() {

        List<List<Integer>> listOfLists = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5),
                Arrays.asList(6, 7, 8, 9)
        );

        List<Integer> list = listOfLists.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Assertions.assertEquals(9, list.size());
    }

    @Test
    void testStreams_concatenateStrings() {

        List<String> words = List.of("This", "is", "Sample");

        //  Reduce Approach

        String resReduce = words.stream()
                .reduce("", (s1, s2) -> s1 + " " + s2)
                .trim();

        Assertions.assertEquals("This is Sample", resReduce);

        //  String join Approach

        String resCollect = String.join(" ", words);

        Assertions.assertEquals("This is Sample", resCollect);
    }

    @Test
    void testStreams_findLongestString() {

        //  Sorting Approach

        String resSorted = WORDS.stream()
                .max(Comparator.comparing(String::length))
                .orElseThrow(AssertionError::new);

        Assertions.assertEquals("november", resSorted);

        //  Reduce Approach

        String resReduce = WORDS.stream()
                .reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2)
                .orElseThrow(AssertionError::new);

        Assertions.assertEquals("november", resReduce);
    }

    @Test
    void testStreams_frequencyOfCharactersInString() {

        Map<Character, Long> map = STR.chars()
                .mapToObj(ch -> (char) ch)
                .filter(ch -> ch != ' ')
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        Assertions.assertEquals(3, map.get('a'));
    }

    @Test
    void testStreams_numbersAverage() {

        double avg = NUMBERS.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(AssertionError::new);

        Assertions.assertEquals(54.9, avg);
    }

    @Test
    void testStreams_stringsAndLengthMap() {

        Map<String, Integer> wordLength = WORDS.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        String::length
                ));

        Assertions.assertEquals(5, wordLength.get("alpha"));
    }

    @Test
    void testStreams_partitionNumbersByEvenOdd() {

        //  Collectors groupingBy Approach

        Map<Boolean, List<Integer>> groupMap = NUMBERS.stream()
                .collect(Collectors.groupingBy(num -> ((num & 1) == 1)));

        Assertions.assertEquals(2, groupMap.size());

        //  Collectors partitioningBy Approach

        Map<Boolean, List<Integer>> partitionMap = NUMBERS.stream()
                .collect(Collectors.partitioningBy(num -> ((num & 1) == 1)));

        Assertions.assertEquals(2, partitionMap.size());
    }

    @Test
    void testStreams_NthLargestNumber() {

        //  To find 4th Largest Number

        int max = NUMBERS.stream()
                .sorted(Comparator.reverseOrder())
                .skip(3)
                .findFirst()
                .orElseThrow(AssertionError::new);

        Assertions.assertEquals(90, max);
    }

    @Test
    void testStreams_filterMapWithGreaterThanValues() {

        Map<Character, Long> map = WORDS.stream()
                .flatMap(word -> word.chars().mapToObj(ch -> (char) ch))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        List<Character> characters = map.entrySet().stream()
                .filter(entry -> entry.getValue() > 10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Assertions.assertEquals(5, characters.size());
    }

    @Test
    void testStreams_removeDuplicatesWithoutCollectors() {

        List<Integer> list = new ArrayList<>(NUMBERS);
        list.addAll(List.of(76, 10, 14, 14));

        List<Integer> distinct = list.stream()
                .distinct()
                .collect(Collectors.toList());

        Assertions.assertEquals(30, distinct.size());
    }

    @Test
    void testStreams_mostFrequentCharacterInString() {

        Character max = STR.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(AssertionError::new);

        Assertions.assertEquals('a', max);
    }

    @Test
    void testStreams_commonBetweenTwoLists() {

        List<Integer> list1 = List.of(1, 2, 3, 5, 9);
        List<Integer> list2 = List.of(1, 3, 6, 8, 9);

        List<Integer> common = list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toList());

        Assertions.assertEquals(3, common.size());
    }

    @Test
    void testStreams_sumOfSquaresOfEvenNumbers() {

        int sum = NUMBERS.stream()
                .filter(num -> ((num & 1) == 0))
                .mapToInt(num -> num * num)
                .sum();

        Assertions.assertEquals(32244, sum);
    }

    @Test
    void testStreams_skipAndLimit() {

        //  Skip first 5 elements from list and limit the result to 3

        List<Integer> newList = NUMBERS.stream()
                .skip(5)
                .limit(3)
                .collect(Collectors.toList());

        Assertions.assertEquals(3, newList.size());
    }

    @Test
    void testStreams_longestWordFromSentence() {

        String word = Arrays.stream(SENTENCE.split(" "))
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(AssertionError::new);

        Assertions.assertEquals("sentence", word);
    }

    @Test
    void testStreams_TopNObjectsByValue() {

        //  Return top 2 Persons by their salary

        List<Person> topPersons = PERSONS.stream()
                .sorted(
                        Comparator.comparing(Person::getSalary, Comparator.reverseOrder())
                                .thenComparing(Person::getAge, Comparator.reverseOrder()))
                .limit(2)
                .collect(Collectors.toList());

        Assertions.assertEquals(2, topPersons.size());
        Assertions.assertEquals(150000, topPersons.get(0).getSalary());
    }

    @Test
    void testStreams_groupStringsByFirstCharacter() {

        List<String> wordsList = new ArrayList<>(WORDS);
        wordsList.addAll(List.of("alpha", "bravo", "hotel"));

        Map<Character, List<String>> map = wordsList.stream()
                .collect(Collectors.groupingBy(word -> word.charAt(0)));

        Assertions.assertEquals(2, map.get('a').size());
        Assertions.assertEquals(2, map.get('b').size());
        Assertions.assertEquals(2, map.get('h').size());
    }

    @Test
    void testStreams_reduceToConcatenateStringsReverseOrder() {

        String reversed = Arrays.stream(SENTENCE.split(" "))
                .reduce((w1, w2) -> w2 + " " + w1)
                .orElseThrow(AssertionError::new);

        Assertions.assertEquals("sentence sample a is this", reversed.trim());
    }

    @Test
    void testStreams_groupPersonsBySalaryAndAge() {

        //  Group all persons by salary, then group all persons in each salary by age

        Map<Integer, Map<Integer, List<Person>>> map = PERSONS.stream()
                .collect(Collectors.groupingBy(
                        Person::getSalary,
                        Collectors.groupingBy(Person::getAge)
                ));

        Assertions.assertEquals(2, map.get(150000).size());
        Assertions.assertEquals(2, map.get(125000).size());
        Assertions.assertEquals(1, map.get(100000).size());

        Assertions.assertEquals(1, map.get(150000).get(20).size());
        Assertions.assertEquals(1, map.get(150000).get(24).size());

        Assertions.assertEquals(1, map.get(125000).get(25).size());
        Assertions.assertEquals(1, map.get(125000).get(30).size());
    }

    @Test
    void testStreams_getPersonBySmallestAgeForEachSalary() {

        //  For each salary, get the person with smallest age

        Map<Integer, Person> map = PERSONS.stream()
                .collect(Collectors.groupingBy(
                        Person::getSalary,
                        Collectors.minBy(Comparator.comparingInt(Person::getAge))
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().orElseThrow(AssertionError::new)
                ));

        Assertions.assertEquals(25, map.get(125000).getAge());
        Assertions.assertEquals(20, map.get(150000).getAge());
    }

    @Test
    void testStreams_groupWordsByLengthAndSort() {

        //  Group words by length and sort them

        Map<Integer, List<String>> map = WORDS.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    list.sort(Comparator.naturalOrder());
                                    return list;
                                }
                        )
                ));

        Assertions.assertTrue(map.get(4).get(0).compareTo(map.get(4).get(1)) < 0);
    }

    @Test
    void testStreams_productOfNumbers() {

        List<Integer> nums = List.of(54, 23, 87, 45);

        int product = nums.stream()
                .reduce(1, (a, b) -> a * b);

        Assertions.assertEquals(4862430, product);
    }

    @Test
    void testStreams_slidingWindowOfNumbers() {

        List<Integer> nums = NUMBERS.subList(0, 10);
        int slidingWindowSize = 3;

        List<List<Integer>> lists = IntStream.range(0, nums.size() - (slidingWindowSize - 1))
                .mapToObj(i -> nums.subList(i, i + slidingWindowSize))
                .collect(Collectors.toList());

        Assertions.assertEquals(nums.size() - slidingWindowSize + 1, lists.size());
    }

    @Test
    void testStreams_findNonRepeatingCharactersInString() {

        List<Character> list = STR.chars()
                .mapToObj(ch -> (char) ch)
                .filter(ch -> ch != ' ')
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Assertions.assertEquals(6, list.size());
    }

    @Test
    void testStreams_AverageSalaryForEachAge() {

        Map<Integer, Double> map = PERSONS.stream()
                .collect(Collectors.groupingBy(
                        Person::getAge,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .map(Person::getSalary)
                                        .mapToInt(Integer::intValue)
                                        .average().orElseThrow(AssertionError::new)
                        )
                ));

        Assertions.assertEquals(112500.0, map.get(25));
    }

}
