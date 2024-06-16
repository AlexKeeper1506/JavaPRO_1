package classes;

import annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class TestRunner {
    public static void runTests(Class<?> c) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method[] methods = c.getDeclaredMethods();

        Method beforeSuiteMethod = suiteCheck(methods, BeforeSuite.class);
        Method afterSuiteMethod  = suiteCheck(methods, AfterSuite.class);
        Method beforeTestMethod  = suiteCheck(methods, BeforeTest.class);
        Method afterTestMethod   = suiteCheck(methods, AfterTest.class);

        Map<Integer, Method> testMethods = getTestMethods(methods);

        //Предполагаем, что у класса есть конструктор без параметров (не знаю как это проверить)
        Constructor<?> constructor = c.getConstructor();
        Object object = constructor.newInstance();

        //Предполагаем, что метод с аннотаций BeforeSuite не имеет входных параметров (не знаю как это проверить)
        if (beforeSuiteMethod != null) beforeSuiteMethod.invoke(object);

        List<Integer> priorityList = new ArrayList<>(testMethods.keySet());
        Collections.sort(priorityList);

        //Предполагаем, что все методы с аннотациями Test, BeforeTest и AfterTest не имеют входных параметров (не знаю как это проверить)
        for (Integer priority : priorityList) {
            if (beforeTestMethod != null) beforeTestMethod.invoke(object);
            testMethods.get(priority).invoke(object);
            if (afterTestMethod != null) afterTestMethod.invoke(object);
        }

        //Предполагаем, что метод с аннотаций AfterSuite не имеет входных параметров (не знаю как это проверить)
        if (afterSuiteMethod != null) afterSuiteMethod.invoke(object);
    }

    private static Method suiteCheck(Method[] methods, Class<? extends Annotation> annotation) {
        int count = 0;
        Method staticMethod = null;

        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                if (!Modifier.isStatic(method.getModifiers())) throw new RuntimeException("Аннотацию " + annotation + " можно использовать только на статических методах");

                count++;
                if (count >= 2) throw new RuntimeException("У класса должен быть максимум 1 статический метод с аннотацией " + annotation);

                staticMethod = method;
            }
        }

        return staticMethod;
    }

    private static Map<Integer, Method> getTestMethods(Method[] methods) {
        Map<Integer, Method> resultMethods = new HashMap<>();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                if (Modifier.isStatic(method.getModifiers())) throw new RuntimeException("Аннотацию Test можно использовать только на нестатических методах");

                Integer priority = method.getDeclaredAnnotation(Test.class).priority();
                if (priority < 1 || priority > 10) throw new RuntimeException("Приоритет метода должен быть в пределах от 1 до 10");

                while (resultMethods.containsKey(priority)) priority++;
                resultMethods.put(priority, method);
            }
        }

        return resultMethods;
    }
}
