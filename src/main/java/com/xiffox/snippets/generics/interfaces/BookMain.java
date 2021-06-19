package com.xiffox.snippets.generics.interfaces;

public class BookMain {
    public static void main(String[] args) {
        GenericStack gs = new GenericStack();

        // push
        System.out.println("Performing Push Operation : ");
        gs.push(new Book("First Book"));
        gs.push(new Book("Second Book"));
        gs.push(new Book("Third Book"));

        getSizeAndDisplayStack(gs);

        // peek
        System.out.println("Performing Peek Operation : ");
        Book peek = gs.peek();
        System.out.println(peek);

        getSizeAndDisplayStack(gs);

        // pop
        System.out.println("Performing Pop Operation : ");
        Book pop3 = gs.pop();
        System.out.println(pop3);

        getSizeAndDisplayStack(gs);

        System.out.println("Performing Pop Operation : ");
        Book pop2 = gs.pop();
        System.out.println(pop2);

        getSizeAndDisplayStack(gs);

        System.out.println("Performing Pop Operation : ");
        Book pop1 = gs.pop();
        System.out.println(pop1);

        getSizeAndDisplayStack(gs);

        // isEmpty
        System.out.println("Is Stack Empty Operation : ");
        System.out.println(gs.isEmpty());

        getSizeAndDisplayStack(gs);


    }

    private static void getSizeAndDisplayStack(GenericStack gs) {
        // getSize
        System.out.println("Current Stack Size = " + gs.getSize());

        // toString
        System.out.println("Current Stack Elements = " + gs.toString());
    }
}
