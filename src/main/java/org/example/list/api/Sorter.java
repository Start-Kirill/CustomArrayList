package org.example.list.api;

import java.util.Comparator;

/**
 *  Данный интерфес предоствляет метод сортировки массива объектов типа Е с помощью Comparator
 *
 * @author : Kiryl Staravoitau
 * @param <E> - типа объектов массива
 */
public interface Sorter<E> {
    void sort(Object[] data, Comparator<? super E> comparator);
}
