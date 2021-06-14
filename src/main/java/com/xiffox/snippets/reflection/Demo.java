package com.xiffox.snippets.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Demo {
    public static void main(String[] args) throws Exception{
        test();
    }

    private static void test() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        String animalName = "Dog";

        Class clazz = Class.forName("com.xiffox.snippets.reflection." + animalName);
        Object obj = clazz.newInstance();

        Method method = clazz.getDeclaredMethod("speak");

        Object invoke = method.invoke(obj);
        System.out.println((String) invoke);

    }
}
