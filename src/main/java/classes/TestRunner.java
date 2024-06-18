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

        Method method;

        //Предполагаем, что у класса есть конструктор без параметров (не знаю как это проверить)
        Constructor<?> constructor = c.getConstructor();
        Object object = constructor.newInstance();

        //Предполагаем, что метод с аннотаций BeforeSuite не имеет входных параметров (не знаю как это проверить)
        if (beforeSuiteMethod != null) beforeSuiteMethod.invoke(object);

        List<Integer> priorityList = new ArrayList<>(testMethods.keySet());
        Collections.reverse(priorityList);

        //Предполагаем, что методы с аннотациями BeforeTest и AfterTest не имеют входных параметров (не знаю как это проверить)
        //Методы с аннотацией Test проверяем на параметры с помощью аннотации CsvSource. При её отсутствии параметров нет
        for (Integer priority : priorityList) {
            if (beforeTestMethod != null) beforeTestMethod.invoke(object);

            method = testMethods.get(priority);
            if (method.isAnnotationPresent(CsvSource.class)) {
                String[] parameterValues = method.getDeclaredAnnotation(CsvSource.class).value().split(", ");
                Class<?>[] parameterTypes = method.getParameterTypes();

                int length = parameterValues.length;
                if (length != parameterTypes.length) throw new RuntimeException("Количество аргументов, переданных в аннотации CsvSource, не соответствует реальному количеству аргументов");

                Object[] parameters = new Object[length];
                for (int i = 0; i < length; i++) parameters[i] = toObject(parameterTypes[i], parameterValues[i]);

                method.invoke(object, parameters);
            } else method.invoke(object);

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
        int priority;
        Map<Integer, Method> resultMethods = new HashMap<>();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                if (Modifier.isStatic(method.getModifiers())) throw new RuntimeException("Аннотацию Test можно использовать только на нестатических методах");

                priority = method.getDeclaredAnnotation(Test.class).value();
                if (priority < 1 || priority > 10) throw new RuntimeException("Приоритет метода должен быть в пределах от 1 до 10");

                while (resultMethods.containsKey(priority)) priority++;
                resultMethods.put(priority, method);
            }
        }

        return resultMethods;
    }

    private static Object toObject(Class<?> clazz, String value) {
        if (boolean.class == clazz) return Boolean.parseBoolean(value);
        if (int.class == clazz) return Integer.parseInt(value);
        return value;
    }
}
