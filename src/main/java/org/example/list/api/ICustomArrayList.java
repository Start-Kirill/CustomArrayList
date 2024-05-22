package org.example.list.api;

import java.util.Comparator;

public interface ICustomArrayList<E> {

    int size();

    void add(E e);

    void add(int index, E e);

    E get(int index);

    E remove(int index);

    void clear();

    void sort(Comparator<? super E> c);

    void replace(int index, E e);

}
