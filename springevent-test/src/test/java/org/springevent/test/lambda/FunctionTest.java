package org.springevent.test.lambda;

import java.util.function.Function;

public class FunctionTest {
    public static void main(String[] args) {
        /**
         * Function 为lambda表达式，该类提供apply、compose等方法；
         * 泛型中第一个为输入类型，第二个为输出类型；
         */
        Function<Integer, Integer> add = (x) -> {
            return x + 100;
        };

        Function<Integer, Integer> plus = (x) -> {
            return x * 2;
        };

        Integer res = add.compose(plus).apply(3); // 3*2 + 100

        System.out.println(res);
    }


}
