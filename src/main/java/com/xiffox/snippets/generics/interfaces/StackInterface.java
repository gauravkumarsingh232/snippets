package com.xiffox.snippets.generics.interfaces;

public interface StackInterface<E> {
    int getSize();
    E peek();
    E pop();
    void push(E e);
    boolean isEmpty();
}