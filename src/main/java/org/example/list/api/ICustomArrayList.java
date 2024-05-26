package org.example.list.api;

import java.util.Comparator;

/**
 * Интерфейс авторской реализии коллекции ArrayList.
 * Данный интерфес предоставляет:
 *  - 1 метод для получения элемента спика по индексу.
 *  - 2 метода добавления элементов в список (добавление в конец списка и добавление по индексу)
 *  - 1 метод заенты илемента по индексу
 *  - 1 метод получения размера списка
 *  - 1 метод удаления элемента по индексу
 *  - 1 метод очистки списка
 *  - 1 метод сортировки списка
 *  Так же данный интерфей наследует интерфес Iterable, что означает предоставление метода
 *  iterator для итеррирования по списку.
 *
 * @author : Kiry Staravoitau
 * @param <E> - тип элементов списка
 */
public interface ICustomArrayList<E> extends Iterable<E> {

    int size();

    void add(E e);

    void add(int index, E e);

    E get(int index);

    E remove(int index);

    void clear();

    void sort(Comparator<? super E> c);

    void replace(int index, E e);

}
