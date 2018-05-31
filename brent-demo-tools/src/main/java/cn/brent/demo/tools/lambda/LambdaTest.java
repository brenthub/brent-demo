package cn.brent.demo.tools.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by shencunjing on 2018/4/16.
 */
public class LambdaTest {

    @Test
    public void testInnerClass() {
        new Thread(() -> {
            System.out.println("In Java8, Lambda expression rocks !!");
        }).start();
    }

    @Test
    public void testLoop() {
        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.forEach(n -> {
            n = n + ".";
            System.out.println(n);
        });

        features.forEach(System.out::print);
    }

    @Test
    public void testPredicate() {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str) -> str.startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str) -> str.endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str) -> true);

        System.out.println("Print no language : ");
        filter(languages, (str) -> false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str) -> str.length() > 4);


    }

    @Test
    public void testSteam(){
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        languages.stream().filter((n)-> n.startsWith("J")).forEach(System.out::println);

        languages.stream().map((n) -> n = n + ".").forEach(System.out::print);
        System.out.println("\n---------");
        String t = languages.stream().map((n) -> n = n + ".").reduce((sum,n )-> sum+n).get();
        System.out.println(t);
    }

    public void filter(List<String> names, Predicate<String> condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }


}
