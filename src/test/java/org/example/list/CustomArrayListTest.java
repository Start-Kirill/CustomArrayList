package org.example.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {

    private CustomArrayList<Integer> customArrayList;

    @BeforeEach
    void init() {
        customArrayList = new CustomArrayList<>();
    }

    @Test
    void shouldThrowWhileCreation() {
        assertThrows(IllegalArgumentException.class, () -> new CustomArrayList<>(-1));
    }


    @Test
    void shouldAddOneElement() {
        final Integer elementTest = 100;
        customArrayList.add(elementTest);
        assertEquals(elementTest, customArrayList.get(0));
    }

    @Test
    void shouldAdd100000RandomElements() {
        final Random random = new Random();
        for (int i = 0; i < 100_000; i++) {
            assertDoesNotThrow(() -> this.customArrayList.add(random.nextInt()));
        }
    }

    @Test
    void shouldAdd100000RandomElementsInRandomIndex() {
        final Random random = new Random();
        for (int i = 0; i < 100_000; i++) {
            final int indexOfInsertion = random.nextInt(0, customArrayList.size() + 1);
            assertDoesNotThrow(() -> this.customArrayList.add(indexOfInsertion, random.nextInt()));
        }
    }

    @Test
    void shouldThrowWhileInsertionInNegativePosition() {
        assertThrows(IndexOutOfBoundsException.class, () -> this.customArrayList.add(-1, 100));
    }

    @Test
    void shouldThrowWhileInsertionInOutOfSizePosition() {
        assertThrows(IndexOutOfBoundsException.class, () -> this.customArrayList.add(1, 100));
    }

    @Test
    void shouldGetOneElement() {
        final Integer test = 100;
        customArrayList.add(100);
        assertEquals(test, customArrayList.get(0));
    }

    @Test
    void shouldThrowWhileGettingOutOfNegativePosition() {
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.get(-1));
    }

    @Test
    void shouldThrowWhileGettingOutOfEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.get(0));
    }

    @Test
    void shouldRemove1000ElementsOuOfRandomPosition() {
        fillList(10000);
        final Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            final int index = random.nextInt(0, customArrayList.size());
            assertDoesNotThrow(() -> customArrayList.remove(index));
        }
    }

    @Test
    void shouldThrowWhileRemovingOutOfNegativePosition() {
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.remove(-1));
    }

    @Test
    void shouldThrowWhileRemovingOutOfEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.remove(0));
    }

    @Test
    void shouldClear() {
        fillList(100);
        assertDoesNotThrow(() -> customArrayList.clear());
        assertEquals(0, customArrayList.size());
    }

    @Test
    void shouldThrowWhileInsertionInClearedList() {
        fillList(10);
        customArrayList.clear();
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.add(100, 1));
    }

    @Test
    void shouldSort() {
        CustomArrayList<Integer> unsortedList = createListOfNumbers(0, 3, 4, 1, 5, 6, 2, 9, 7, 8);
        fillList(10);
        unsortedList.sort(Integer::compareTo);
        assertEquals(unsortedList, customArrayList);
    }

    @Test
    void shouldReplaceElementInRandomPosition() {
        final Random random = new Random();
        fillList(1000);
        for (int i = 0; i < customArrayList.size(); i++) {
            final int index = random.nextInt(0, customArrayList.size());
            assertDoesNotThrow(() -> customArrayList.replace(index, random.nextInt()));
        }
    }

    @Test
    void shouldProperlyReplaceElement() {
        CustomArrayList<Integer> firstListOfNumbers = createListOfNumbers(1, 2, 3, 4);
        CustomArrayList<Integer> secListOfNumbers = createListOfNumbers(1, 2, 5, 4);
        secListOfNumbers.replace(2, 3);
        assertEquals(firstListOfNumbers, secListOfNumbers);
    }

    @Test
    void shouldThrowWhileReplacingInNegativePosition() {
        fillList(10);
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.replace(-1, 3));
    }

    @Test
    void shouldThrowWhileReplacingInOutOfSizePosition() {
        fillList(10);
        int size = customArrayList.size();
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.replace(size, 3));
    }

    @Test
    void shouldThrowWhileReplacingInEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.replace(0, 3));
    }

    @Test
    void shouldHaveRightSizeInCaseOfAdding() {
        fillList(100_000);
        assertEquals(100_000, customArrayList.size());
    }

    @Test
    void shouldHaveRightSizeInCaseOfRemoving() {
        fillList(100);
        customArrayList.remove(0);
        customArrayList.remove(0);
        assertEquals(98, customArrayList.size());
    }

    @Test
    void shouldHaveRightSizeInEmptyList() {
        assertEquals(0, customArrayList.size());
    }

    @Test
    void shouldIterate() {
        fillList(10);
        Iterator<Integer> iterator = customArrayList.iterator();
        while (iterator.hasNext()) {
            assertDoesNotThrow(iterator::next);
        }
    }

    @Test
    void shouldThrowWhileIterating() {
        fillList(10);
        Iterator<Integer> iterator = customArrayList.iterator();
        customArrayList.add(1);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    private void fillList(int number) {
        for (int i = 0; i < number; i++) {
            customArrayList.add(i);
        }
    }

    private CustomArrayList<Integer> createListOfNumbers(Integer... integers) {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        for (Integer i : integers) {
            list.add(i);
        }
        return list;
    }

}