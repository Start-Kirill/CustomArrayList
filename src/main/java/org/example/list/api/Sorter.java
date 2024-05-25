package org.example.list.api;

import java.util.Comparator;

public interface Sorter<E> {
    void sort(Object[] data, Comparator<? super E> comparator);
}
