package org.example.list;

import org.example.list.api.Sorter;

import java.util.Comparator;
import java.util.Random;

public class QuickSorter<E> implements Sorter<E> {

    private static final Random random = new Random();

    @Override
    public void sort(Object[] data, Comparator<? super E> comparator) {
        int shift = shiftNulls(data);
        int high = data.length - 1 - shift;
        quickSort(data, 0, high, comparator);
    }

    private void quickSort(Object[] data, int low, int high, Comparator<? super E> comparator) {
        if (low < high) {
            int partition = partition(data, low, high, comparator);

            quickSort(data, low, partition - 1, comparator);
            quickSort(data, partition + 1, high, comparator);
        }
    }

    private int partition(Object[] data, int low, int high, Comparator<? super E> comparator) {
        int rand = random.nextInt(low, high + 1);

        E pivot = (E) data[rand];

        Object temp = data[rand];
        data[rand] = data[high];
        data[high] = temp;

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare((E) data[j], pivot) < 0) {
                i++;

                temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }

        temp = data[++i];
        data[i] = data[high];
        data[high] = temp;

        return i;
    }

    private int shiftNulls(Object[] data) {
        int count = 0;
        int high = data.length - 1;
        for (int i = high; i >= 0; i--) {
            if (data[i] == null) {
                if (i < high) {
                    data[i] = data[high - count];
                    data[high - count] = null;
                }
                count++;
            }
        }
        return count;
    }
}
