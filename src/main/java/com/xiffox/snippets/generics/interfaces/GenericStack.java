package com.xiffox.snippets.generics.interfaces;

import java.util.ArrayList;
import java.util.List;

public class GenericStack implements StackInterface<Book> {

    private List<Book> list = new ArrayList<>();

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Book peek() {
        return list.get(getSize() - 1);
    }

    @Override
    public Book pop() {
        Book o = list.get(getSize() - 1);
        list.remove(getSize() - 1);
        return o;
    }

    @Override
    public void push(Book e) {
        list.add(e);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenericStack{");
        sb.append("list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}