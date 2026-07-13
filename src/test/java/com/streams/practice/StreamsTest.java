package com.streams.practice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsTest {

    private static final List<String> WORDS = List.of(
            "alpha", "bravo", "charlie", "delta", "echo",
            "foxtrot", "golf", "hotel", "india", "juliet", "kilo",
            "lima", "mike", "november", "oscar", "papa", "quebec",
            "romeo", "sierra", "tango", "uniform", "victor",
            "whiskey", "x-ray", "yankee", "zulu"
    );

    private static final List<String> SONNET = List.of(
            "From fairest creatures we desire increase,",
                "That thereby beauty’s rose might never die,",
                "But as the riper should by time decease,",
                "His tender heir might bear his memory;",
                "But thou, contracted to thine own bright eyes,",
                "Feed’st thy light’s flame with self-substantial fuel,",
                "Making a famine where abundance lies,",
                "Thyself thy foe, to thy sweet self too cruel.",
                "Thou that art now the world’s fresh ornament",
                "And only herald to the gaudy spring,",
                "Within thine own bud buriest thy content,",
                "And, tender churl, mak’st waste in niggarding.",
                "Pity the world, or else this glutton be,",
                "To eat the world’s due, by the grave and thee."
    );

    private List<String> wordsInSonnet() {

        Function<String, String> removeSymbols = str ->
                Arrays.asList(',', ';', '.').contains(str.charAt(str.length() - 1)) ?
                        str.substring(0, str.length() - 1) : str;

        return SONNET.stream()
                .flatMap(line -> Stream.of(line.split(" ")))
                .map(String::toLowerCase)
                .map(removeSymbols)
                .collect(Collectors.toList());
    }

    @Test
    void testStreams_mapFilter() {

        //  Mark to upper case
        //  Keep only words with six letters

        List<String> result = WORDS.stream()
                .map(String::toUpperCase)
                .filter(word -> word.length() == 6)
                .collect(Collectors.toList());

        Assertions.assertEquals(5, result.size());
    }

    @Test
    void testStreams_flatMap() {

        //  Collect all words from sonnet using flatmap

        List<String> words = SONNET.stream()
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .collect(Collectors.toList());

        Assertions.assertEquals(105, words.size());
    }

    @Test
    void testStreams_reduce() {

        //  Calculate factorial of all numbers in given range
        //  For bigger values, use BigInteger class

        BigInteger result = IntStream.rangeClosed(1, 20)
                .mapToObj(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply);

        System.out.println(result);
    }

    @Test
    void testStreams_collectorsToMap() {

        //  Collect all words in a map, first character as their key, and word as value

        Map<String, String> map = WORDS.stream()
                .collect(Collectors.toMap(
                        word -> word.substring(0, 1),
                        word -> word
                ));

        System.out.println(map);
    }

    @Test()
    void testStreams_collectorsToMap_duplicateValues() {

        List<String> NEW_WORDS = new ArrayList<>(WORDS);
        NEW_WORDS.add("sample");

        Assertions.assertThrows(IllegalStateException.class, () -> NEW_WORDS.stream()
                .collect(Collectors.toMap(
                        word -> word.substring(0, 1),
                        word -> word
                )));

        Map<String, String> map = NEW_WORDS.stream()
                .collect(Collectors.toMap(
                        word -> word.substring(0, 1),
                        word -> word,
                        (w1, w2) -> w1 + System.lineSeparator() + w2
                ));

        System.out.println(map);
    }

    @Test
    void testStreams_collectorsGroupingBy() {

        //  Collect all words in a map, length of the words as the kay, list of strings as value

        Map<Integer, List<String>> map = WORDS.stream()
                .collect(Collectors.groupingBy(String::length));

        System.out.println(map);
    }

    @Test
    void testStreams_collectorsGroupingBy_downstreamCollector() {

        //  Collect all words count in a map, length of the words as the kay, size of list of strings as value
        //  This uses downstream collector as second argument in groupingBy

        Map<Integer, Long> map = WORDS.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.counting()
                ));

        System.out.println(map);
    }

    @Test
    void testStreams_collectorsGroupingBy_downstreamCollector_mapping() {

        //  Collect all lines as list in a map, length of line as the key, list of lines in upper case as value
        //  This uses downstream collector Collectors.mapping

        Map<Integer, List<String>> map = SONNET.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.mapping(String::toUpperCase, Collectors.toList())
                ));

        System.out.println(map);
    }

    @Test
    void testStreams_collectorsGroupingBy_downstreamCollector_joining() {

        //  Collect all words as string in a map, length of words as key, string of joined words as value
        //  This uses downstream collector Collectors.joining

        Map<Integer, String> map = WORDS.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.joining(" : ")
                ));

        System.out.println(map);
    }

    @Test
    void testStreams_frequencyOfLettersInWORDS() {

        //  Collect the frequency of all letters in WORDS list in a map

        Map<Character, Long> map = WORDS.stream()
                .flatMap(word -> word.chars().mapToObj(ch -> (char) ch))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        System.out.println(map);
    }

    @Test
    void testStreams_maxFrequencyOfWordsInSonnet() {

        //  Get the word with maximum frequency in the sonnet
        //  This approach returns one the max values, and it is not predictable as it returns one of the max value

        Map<String, Long> map = wordsInSonnet().stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        System.out.println(map);

        Map.Entry<String, Long> max = map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(IllegalStateException::new);

        System.out.println(max);
    }

    @Test
    void testStreams_invertingFrequencyMap() {

        //  Get the frequency map of words in inverted manner, where key is the frequency of the element,
        //  and the value is list of words with that frequency

        Map<String, Long> map = wordsInSonnet().stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        System.out.println(map);

        Map<Long, List<String>> invertedMap = map.entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                ));

        System.out.println(invertedMap);
    }

}
