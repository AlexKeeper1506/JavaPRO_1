package classes;

import annotations.*;

import java.util.List;

public class Student {
    public Student() {
    }

    @BeforeSuite
    public static void beforeSuiteMethod() {
        System.out.println("Запускаюсь один раз в самом начале!");
    }

    @AfterSuite
    public static void afterSuiteMethod() {
        System.out.println("Запускаюсь один раз в самом конце!");
    }

    @BeforeTest
    public static void beforeTestMethod() {
        System.out.println("----------------");
        System.out.println("Запускаюсь перед каждым тестом!");
    }

    @AfterTest
    public static void afterTestMethod() {
        System.out.println("Запускаюсь после каждого теста");
        System.out.println("----------------");
    }

    @Test(3)
    public void testMethod1() {
        System.out.println("Мой приоритет 3!");
    }

    @Test(7)
    public void testMethod2() {
        System.out.println("Мой приоритет 7!");
    }

    @Test
    public void testMethod3() {
        System.out.println("Мой приоритет 5!");
    }

    @Test
    @CsvSource("10, Java, 20, true")
    public void testMethodWithParameters(int a, String b, int c, boolean d) {
        System.out.println(a + " " + b + " " + c + " " + d);
        System.out.println("Мой приоритет 5!");
    }
}
