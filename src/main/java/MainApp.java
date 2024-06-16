import classes.Student;
import classes.TestRunner;

import java.lang.reflect.InvocationTargetException;

public class MainApp {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestRunner.runTests(Student.class);
    }
}
