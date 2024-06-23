import classes.Employee;
import classes.Position;
import classes.StreamAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        System.out.println(StreamAPI.duplicatesRemoval(Arrays.asList(5, 4, 5, 6, 7, 5, 6, 8, 6)));
        System.out.println(StreamAPI.findTheThirdBiggestNumber(Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13)));
        System.out.println(StreamAPI.findTheThirdUniqueBiggestNumber(Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13)));

        List<Employee> employeeList = new ArrayList<>(Arrays.asList(
                new Employee("Manager_35", 35, Position.MANAGER),
                new Employee("Director_44", 44, Position.DIRECTOR),
                new Employee("Engineer_42", 42, Position.ENGINEER),
                new Employee("Manager_55", 55, Position.MANAGER),
                new Employee("Engineer_25", 25, Position.ENGINEER),
                new Employee("Manager_19", 19, Position.MANAGER),
                new Employee("Engineer_33", 33, Position.ENGINEER),
                new Employee("Engineer_55", 55, Position.ENGINEER),
                new Employee("Manager_37", 37, Position.MANAGER)
        ));

        System.out.println(StreamAPI.findTheThreeOldestEngineers(employeeList));
        System.out.println(StreamAPI.findTheAverageEngineerAge(employeeList));

        System.out.println(StreamAPI.findTheLongestWord(Arrays.asList("Hi", "Hello", "How")));
        System.out.println(StreamAPI.getTheHashMap("hello goodbye hello"));
        System.out.println(StreamAPI.getStringsInWordLengthAndAlphabetOrder(Arrays.asList("Hello", "Hi", "Elephant", "He", "A")));
        System.out.println(StreamAPI.getTheLongestWord(Arrays.asList("Hi Hello How", "He Elephant", "A nice day")));
    }
}
