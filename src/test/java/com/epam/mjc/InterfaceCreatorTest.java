package com.epam.mjc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

public class InterfaceCreatorTest {

    public static Stream<Arguments> predicateTestCases() {
        return Stream.of(
                // Пустой список
                Arguments.of("EmptyList",
                        List.of(),
                        true),

                // Список с числами
                Arguments.of("ListWithNumber",
                        List.of("47", "Word", "Case", "Human", "Price"), false),

                // Все слова начинаются с заглавной буквы
                Arguments.of("FromListWithUpperCaseWords",
                        List.of("SUPPLIER", "CONSUMER", "FUNCTION", "PREDICATE", "BI-FUNCTION"), true),

                // Все слова с маленькой буквы
                Arguments.of("FromListWithLowerCaseWords",
                        List.of("case", "java", "house", "world"), false)
        );
    }

    public static Stream<Arguments> consumerTestCases() {
        return Stream.of(
                // Все числа четные
                Arguments.of("ListWithAllEvenValues",
                        new ArrayList<>(Arrays.asList(48, 56, 100, 200, 222)),
                        new ArrayList<>(Arrays.asList(48, 56, 100, 200, 222, 48, 56, 100, 200, 222))),

                // Смешанные четные и нечетные
                Arguments.of("FromListWithHalfEvenValues",
                        new ArrayList<>(Arrays.asList(48, 13, 100, 5, 222, 3)),
                        new ArrayList<>(Arrays.asList(48, 13, 100, 5, 222, 3, 48, 100, 222))),

                // Нет четных чисел
                Arguments.of("FromListWithNoEvenValues",
                        new ArrayList<>(Arrays.asList(1, 13, 101, 5, 223, 3)),
                        new ArrayList<>(Arrays.asList(1, 13, 101, 5, 223, 3)))
        );
    }

    public static Stream<Arguments> supplierTestCases() {
        return Stream.of(
                // Все строки корректны
                Arguments.of("FromListWithAllCorrectStrings",
                        new ArrayList<>(Arrays.asList("This is a good way to learn Java Functional Interfaces.",
                                "Hello world in Java.",
                                "MJC is a great school.")),
                        new ArrayList<>(Arrays.asList("This is a good way to learn Java Functional Interfaces.",
                                "Hello world in Java.",
                                "MJC is a great school."))),

                // Одна некорректная строка
                Arguments.of("FromListWithOneIncorrectString",
                        new ArrayList<>(Arrays.asList("This is a good way to learn Java Functional Interfaces.",
                                "hello world in Java.",
                                "MJC is a great school.")),
                        new ArrayList<>(Arrays.asList("This is a good way to learn Java Functional Interfaces.",
                                "MJC is a great school."))),

                // Все строки некорректны
                Arguments.of("FromListWithAllIncorrectStrings",
                        new ArrayList<>(Arrays.asList("his is a good way to learn Java Functional Interfaces.",
                                "Hello world Java.",
                                "MJC is a great school")),
                        new ArrayList<>(List.of()))
        );
    }

    public static Stream<Arguments> functionTestCases() {
        return Stream.of(
                // Все строки короткие
                Arguments.of("FromListWithAllShortStrings",
                        List.of("Hello world in Java.",
                                "MJC is a great school."),
                        Map.of("Hello world in Java.", 20,
                                "MJC is a great school.", 22)),

                // Строки короткие
                Arguments.of("FromListWithWords",
                        List.of("Hello",
                                "MJC"),
                        Map.of("Hello", 5,
                                "MJC", 3)),

                // Числа в виде строк
                Arguments.of("FromListWithNumbers",
                        List.of("222",
                                "91"),
                        Map.of("222", 3,
                                "91", 2))
        );
    }

    public static Stream<Arguments> biFunctionTestCases() {
        return Stream.of(
                // Списки из двух элементов
                Arguments.of("ListWithShortArrays",
                        new ArrayList<>(Arrays.asList(48, 56)),
                        new ArrayList<>(Arrays.asList(10, 12)),
                        new ArrayList<>(Arrays.asList(48, 56, 10, 12))),

                // Длинные списки
                Arguments.of("ListWithLongArrays",
                        new ArrayList<>(Arrays.asList(48, 56, 100, 101, 999, 75, 1, 15)),
                        new ArrayList<>(Arrays.asList(10, 15, 10, 15, 225, 123, 7594, 1203)),
                        new ArrayList<>(Arrays.asList(48, 56, 100, 101, 999, 75, 1, 15, 10, 15, 10, 15, 225, 123, 7594, 1203))),

                // Первый список пустой
                Arguments.of("ListWithEmptyFirstArray",
                        new ArrayList<>(List.of()),
                        new ArrayList<>(Arrays.asList(10, 15, 10, 15, 225, 123, 7594, 1203)),
                        new ArrayList<>(Arrays.asList(10, 15, 10, 15, 225, 123, 7594, 1203)))
        );
    }

    @ParameterizedTest(name = "predicate_{0}_Test")
    @MethodSource(value = "predicateTestCases")
    void isValuesStartWithUpperCaseTest(String name,
                                        List<String> sourceList,
                                        boolean expected) {
        boolean actualResult = new InterfaceCreator().isValuesStartWithUpperCase().test(sourceList);
        assertEquals(expected, actualResult);
    }

    @ParameterizedTest(name = "consumer_{0}_Test")
    @MethodSource(value = "consumerTestCases")
    void addEvenValuesAtTheEndTest(String name,
                                   List<Integer> sourceList,
                                   List<Integer> expected) {
        new InterfaceCreator().addEvenValuesAtTheEnd().accept(sourceList);
        assertIterableEquals(expected, sourceList);
    }

    @ParameterizedTest(name = "supplier_{0}_Test")
    @MethodSource(value = "supplierTestCases")
    void filterCollectionTest(String name,
                              List<String> sourceList,
                              List<String> expected) {
        List<String> result = new InterfaceCreator().filterCollection(sourceList).get();
        assertIterableEquals(expected, result);
    }

    @ParameterizedTest(name = "function_{0}_Test")
    @MethodSource(value = "functionTestCases")
    void stringSizeTest(String name,
                        List<String> sourceList,
                        Map<String, Integer> expected) {
        Map<String, Integer> result = new InterfaceCreator().stringSize().apply(sourceList);
        assertEquals(expected, result);
    }

    @ParameterizedTest(name = "biFunction_{0}_Test")
    @MethodSource(value = "biFunctionTestCases")
    void concatListTest(String name,
                        List<Integer> sourceList1,
                        List<Integer> sourceList2,
                        List<Integer> expected) {
        List<Integer> result = new InterfaceCreator().concatList().apply(sourceList1, sourceList2);
        assertIterableEquals(expected, result);
    }
}

