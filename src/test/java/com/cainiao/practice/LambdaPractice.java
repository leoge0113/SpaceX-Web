package com.cainiao.practice;

import java.util.Arrays;
import java.util.List;

/*
lambda 表达式

    (params) -> expression
    (params) -> statement
    (params) -> { statements }
*/
//函数式接口
interface FuncInterface {
    void f();
}

interface NotFuncInterface {
    void f();

    void f1();
}

public class LambdaPractice {
    public static void main(String[] args) {

        //Runnable是一个函数式接口
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程由匿名类创建！");
            }
        });
        thread.start();
        new Thread(() -> System.out.println("线程由lambda表达式创建！")).start();


        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (String feature : features) {
            System.out.println(feature);
        }
        features.forEach(n ->System.out.println(n));
    }

}
