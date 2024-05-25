package org.example.list;

import org.example.list.api.ICustomArrayList;
import org.example.list.api.Sorter;

import java.util.*;

public class CustomArrayList<E> implements ICustomArrayList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private final Sorter<E> sorter;

    private Object[] data;

    private int size;

    private int modCount = 0;


    public CustomArrayList(int capacity, Sorter<E> sorter) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
        data = new Object[capacity];
        this.sorter = sorter;
    }

    public CustomArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
        data = new Object[capacity];
        this.sorter = new QuickSorter<>();
    }

    public CustomArrayList(Sorter<E> sorter) {
        data = new Object[DEFAULT_CAPACITY];
        this.sorter = sorter;
    }

    public CustomArrayList() {
        data = new Object[DEFAULT_CAPACITY];
        this.sorter = new QuickSorter<>();
    }


    @Override
    public void add(E e) {
        modCount++;
        ensureCapacity(size + 1);
        data[size++] = e;
    }

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

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) this.data[index];
    }

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

    @Override
    public void clear() {
        modCount++;
        Arrays.fill(this.data, null);
        size = 0;
    }

    @Override
    public void sort(Comparator<? super E> c) {
        modCount++;
        this.sorter.sort(data, size, c);
    }

    @Override
    public void replace(int index, E e) {
        modCount++;
        checkIndex(index);
        this.data[index] = e;
    }

    @Override
    public int size() {
        return size;
    }

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

