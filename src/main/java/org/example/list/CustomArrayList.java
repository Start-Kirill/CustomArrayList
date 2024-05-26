package org.example.list;

import org.example.list.api.ICustomArrayList;
import org.example.list.api.Sorter;

import java.util.*;

/**
 * Реализация саморасширяемого массива элементов ICustomArrayList интерфейса.
 * CustomArrayList имеет началную ёмкость, которая может быть задана при создании списка или
 * установлена по умолчанию в размере 10 элементов. Список может быть отсортировон. За спосод
 * сортировки отвечает Sorter который может быть передан в конструкторе или установлен по умолчанию.
 * По умолчанию устанавливается алгоритм быстрой сортировке.
 * Реализация не синхронизирована.
 * Так предоставляется возможность воспользоваться Iterator для итеррирования спика. В процессе итеррирования
 * список не может быть изменен. В противном случает будет выброшено ConcurrentModificationException.
 *
 * @param <E> - тип элементов списка
 * @author : Kiryl Staravoitau
 */
public class CustomArrayList<E> implements ICustomArrayList<E> {

    /**
     * Начальная емкость списка по умолчанию
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Реализация сортировки
     */
    private final Sorter<E> sorter;

    /**
     * Элементы списка
     */
    private Object[] data;

    /**
     * Размер списка
     */
    private int size;

    /**
     * Счетчик модификаций. Необходим для отслеживания изменений списка при итерации
     */
    private int modCount = 0;


    /**
     * Создает пустой список с желаемой начальной емкостью и желаемой реализацией сортировки
     *
     * @param capacity - начальная емкость списка
     * @param sorter   - реализация сортировки
     * @throws IllegalArgumentException - если передано отрицательное значение емкости
     */
    public CustomArrayList(int capacity, Sorter<E> sorter) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
        data = new Object[capacity];
        this.sorter = sorter;
    }

    /**
     * Создает пустой список с желаемой начальной емкостью
     *
     * @param capacity - начальная емкость списка
     * @throws IllegalArgumentException - если передано отрицательное значение емкости
     */
    public CustomArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
        data = new Object[capacity];
        this.sorter = new QuickSorter<>();
    }

    /**
     * Создает пустой список с желаемой реализацией сортировки
     *
     * @param sorter - реализация сортировки
     */
    public CustomArrayList(Sorter<E> sorter) {
        data = new Object[DEFAULT_CAPACITY];
        this.sorter = sorter;
    }

    /**
     * Создает пустой списко с начальной емкостью 10 и быстрой сортировкой в качестве алгоритма сортировке
     */
    public CustomArrayList() {
        data = new Object[DEFAULT_CAPACITY];
        this.sorter = new QuickSorter<>();
    }

    /**
     * Добавляет элемент в конец списка
     * @param e - вставляемый элемент
     */
    @Override
    public void add(E e) {
        modCount++;
        ensureCapacity(size + 1);
        data[size++] = e;
    }

    /**
     * Добавляет элемент по индексу. Элементы справа от индекса смещаются на одну позицию вправо
     *
     * @param index - индекс по которому недходимо произвести вставку
     * @param e - вставляемый элемент
     * @throws IndexOutOfBoundsException - если переданный индекс отрицательный или выходит за пределы списка
     */
    @Override
    public void add(int index, E e) {
        modCount++;
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
        ensureCapacity(size + 1);
        System.arraycopy(this.data, index, this.data, index + 1, size - index);
        this.data[index] = e;
        size++;
    }

    /**
     * Получает элемент по индексу
     *
     * @param index - индексы получаемого элемента
     * @return : элемент по искомому индексу
     * @throws IndexOutOfBoundsException - если переданный индекс отрицательный или выходит за пределы списка
     */
    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) this.data[index];
    }

    /**
     * Удаляет элемент спика по индексу. Элменты справа от удаляемого смещаются на одну позицию влево
     *
     * @param index - индекс удаляемго элемента
     * @return - удаленный элемент
     * @throws IndexOutOfBoundsException - если переданный индекс отрицательный или выходит за пределы списка
     */
    @Override
    public E remove(int index) {
        modCount++;
        checkIndex(index);
        E e = get(index);
        int newSize = size--;
        if (index < newSize) {
            System.arraycopy(this.data, index + 1, this.data, index, newSize - index);
        }
        this.data[newSize - 1] = null;
        return e;
    }

    /**
     * Очищает список
     *
     */
    @Override
    public void clear() {
        modCount++;
        Arrays.fill(this.data, null);
        size = 0;
    }

    /**
     * Сортирует список в соответсвии с заданным компоратором
     *
     * @param c - реализация Comparator для элементов списка
     */
    @Override
    public void sort(Comparator<? super E> c) {
        modCount++;
        this.sorter.sort(data, c);
    }

    /**
     * Заменяет елемент списка по индексу
     *
     * @param index - индекс по которому проводится замена
     * @param e - элемент на который нужно заменить
     */
    @Override
    public void replace(int index, E e) {
        modCount++;
        checkIndex(index);
        this.data[index] = e;
    }

    /**
     * Возвращает размер списка
     * @return : размер списка
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Предоставляет итератор для списка
     * @return : итератор для списка
     */
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean needComma = false;
        for (int i = 0; i < size; i++) {
            if (needComma) {
                sb.append(", ");
            }
            needComma = true;
            sb.append(data[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomArrayList<?> that = (CustomArrayList<?>) o;
        return equalsList(that);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sorter, size, modCount);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > this.data.length) {
            grow(minCapacity);
        }
    }

    private void grow(int minCapacity) {
        int currentCapacity = this.data.length;
        int newCapacity;
        if (currentCapacity > 0) {
            int prefGrowth = currentCapacity >= DEFAULT_CAPACITY ? currentCapacity >> 1 : DEFAULT_CAPACITY - currentCapacity;
            int minGrowth = minCapacity - currentCapacity;
            newCapacity = currentCapacity + Math.max(prefGrowth, minGrowth);

            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            this.data = newData;
        } else {
            newCapacity = Math.max(minCapacity, DEFAULT_CAPACITY);
            this.data = new Object[newCapacity];
        }

    }

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
    }

    private boolean equalsList(CustomArrayList<?> that) {
        if (that.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (data[i] != that.data[i]) {
                return false;
            }
        }
        return true;
    }

    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            checkModification();
            int i = cursor;
            if (i >= size) {
                throw new NoSuchElementException();
            }
            Object[] elements = CustomArrayList.this.data;
            if (i >= elements.length) {
                throw new ConcurrentModificationException();
            }
            cursor = i + 1;
            lastRet = i;
            return (E) elements[i];
        }

        void checkModification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}

