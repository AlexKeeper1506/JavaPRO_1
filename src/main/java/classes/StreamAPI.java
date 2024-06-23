package classes;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPI {
    public static List<?> duplicatesRemoval(List<?> list) {
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public static Integer findTheThirdBiggestNumber(List<Integer> list) {
        return list.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .get();
    }

    public static Integer findTheThirdUniqueBiggestNumber(List<Integer> list) {
        return list.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .get();
    }

    public static List<String> findTheThreeOldestEngineers(List<Employee> list) {
        return list.stream()
                .filter(employee -> employee.getPosition() == Position.ENGINEER)
                .sorted(Comparator.comparingInt(Employee::getAge).reversed())
                .limit(3)
                .map(Employee::getName)
                .toList();
    }

    public static Double findTheAverageEngineerAge(List<Employee> list) {
        return list.stream()
                .filter(employee -> employee.getPosition() == Position.ENGINEER)
                .mapToDouble(Employee::getAge)
                .average()
                .getAsDouble();
    }

    public static String findTheLongestWord(List<String> list) {
        return list.stream()
                .max(Comparator.comparing(String::length))
                .get();
    }

    public static Map<String, Long> getTheHashMap(String string) {
        return Stream.of(string.split(" "))
                .collect(
                        Collectors.groupingBy(s -> s, Collectors.counting())
                );
    }

    public static List<String> getStringsInWordLengthAndAlphabetOrder(List<String> list) {
        return list.stream()
                .sorted(
                        Comparator.comparing(String::length)
                                .thenComparing(Comparator.naturalOrder())
                )
                .toList();
    }

    public static String getTheLongestWord(List<String> list) {
        return list.stream()
                .map(str -> str.split(" "))
                .flatMap(Stream::of)
                .max(Comparator.comparing(String::length))
                .get();
    }
}
