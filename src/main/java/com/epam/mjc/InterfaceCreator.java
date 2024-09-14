package com.epam.mjc;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InterfaceCreator {

    // Реализация предиката, который проверяет, что все строки в списке начинаются с заглавной буквы
    public Predicate<List<String>> isValuesStartWithUpperCase() {
        return list -> list.stream().allMatch(str -> !str.isEmpty() && Character.isUpperCase(str.charAt(0)));
    }

    // Реализация потребителя, который находит четные значения и добавляет их в конец списка
    public Consumer<List<Integer>> addEvenValuesAtTheEnd() {
        return list -> {
            List<Integer> evenValues = list.stream().filter(n -> n % 2 == 0).toList();
            list.addAll(evenValues);
        };
    }

    // Реализация поставщика, который фильтрует коллекцию по правилам: строка начинается с заглавной буквы, 
    // заканчивается точкой и содержит больше 3 слов
    public Supplier<List<String>> filterCollection(List<String> values) {
        return () -> values.stream()
                .filter(str -> !str.isEmpty() && Character.isUpperCase(str.charAt(0)))
                .filter(str -> str.endsWith("."))
                .filter(str -> str.split("\\s+").length > 3)
                .collect(Collectors.toList());
    }

    // Реализация функции, которая создает карту, где ключом является строка, а значением — её длина
    public Function<List<String>, Map<String, Integer>> stringSize() {
        return list -> list.stream()
                .collect(Collectors.toMap(str -> str, String::length));
    }

    // Реализация би-функции, которая объединяет два списка
    public BiFunction<List<Integer>, List<Integer>, List<Integer>> concatList() {
        return (list1, list2) -> {
            List<Integer> resultList = list1.stream().collect(Collectors.toList());
            resultList.addAll(list2);
            return resultList;
        };
    }
}
