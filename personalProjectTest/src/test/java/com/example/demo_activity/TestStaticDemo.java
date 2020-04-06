package com.example.demo_activity;

/**
 * @Author: yongl
 * @DATE: 2020/3/30 17:59
 */

public class TestStaticDemo {
    public static void main(String[] args) {
        new TestStaticDemo();                         //4.第四步，new一个类，但在new之前要处理匿名代码块
    }

    static int num = 4;                    //2.第二步，静态变量和静态代码块的加载顺序由编写先后决定

    {
        num += 3;
        System.out.println("b");           //5.第五步，按照顺序加载匿名代码块，代码块中有打印
    }

    int a = 5;                             //6.第六步，按照顺序加载变量

    { // 成员变量第三个
        System.out.println("c");           //7.第七步，按照顺序打印c
    }

    TestStaticDemo() { // 类的构造函数，第四个加载
        System.out.println("d");           //8.第八步，最后加载构造函数，完成对象的建立
    }

    static {                              // 3.第三步，静态块，然后执行静态代码块，因为有输出，故打印a(静态代码块只加载一次)
        System.out.println("a");
    }

    static void run()                    // 静态方法，调用的时候才加载// 注意看，e没有加载
    {
        System.out.println("e");
    }
}
