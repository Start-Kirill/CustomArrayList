package org.example.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса CustomArrayList
 */
class CustomArrayListTest {

    private CustomArrayList<Integer> customArrayList;

    @BeforeEach
    void init() {
        customArrayList = new CustomArrayList<>();
    }

    /**
     * Тестируется невозможность создания листа с отрицательной емкостью
     */
    @Test
    void shouldThrowWhileCreation() {
        assertThrows(IllegalArgumentException.class, () -> new CustomArrayList<>(-1));
    }

    /**
     * Тестируется добавление одного элемента в конец списка
     */
    @Test
    void shouldAddOneElement() {
        final Integer elementTest = 100;
        customArrayList.add(elementTest);
        assertEquals(elementTest, customArrayList.get(0));
    }

    /**
     * Тестируется добавление 100_000 элементов в список
     */
    @Test
    void shouldAdd100000RandomElements() {
        final Random random = new Random();
        for (int i = 0; i < 100_000; i++) {
            assertDoesNotThrow(() -> this.customArrayList.add(random.nextInt()));
        }
    }

    /**
     * Тестируется добавление 100_000 элементов на случайные позиции списка
     */
    @Test
    void shouldAdd100000RandomElementsInRandomIndex() {
        final Random random = new Random();
        for (int i = 0; i < 100_000; i++) {
            final int indexOfInsertion = random.nextInt(0, customArrayList.size() + 1);
            assertDoesNotThrow(() -> this.customArrayList.add(indexOfInsertion, random.nextInt()));
        }
    }

    /**
     * Тестируется невозможность добавления элемента по отрицательному индексу
     */
    @Test
    void shouldThrowWhileInsertionInNegativePosition() {
        assertThrows(IndexOutOfBoundsException.class, () -> this.customArrayList.add(-1, 100));
    }

    /**
     * Тестируется невозможность непоследовательного добавления элемента
     */
    @Test
    void shouldThrowWhileInsertionInOutOfSizePosition() {
        assertThrows(IndexOutOfBoundsException.class, () -> this.customArrayList.add(1, 100));
    }

    /**
     * Тестируется получение элемента списка
     */
    @Test
    void shouldGetOneElement() {
        final Integer test = 100;
        customArrayList.add(100);
        assertEquals(test, customArrayList.get(0));
    }

    /**
     * Тестируется невозможность получения элемента списка по отрицательному индексу
     */
    @Test
    void shouldThrowWhileGettingOutOfNegativePosition() {
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.get(-1));
    }

    /**
     * Тестируется невозможность получения элемента из пустого списка
     */
    @Test
    void shouldThrowWhileGettingOutOfEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.get(0));
    }

    /**
     * Тестируется корректность удаления элемента из середины списка
     */
    @Test
    void shouldRemoveElementsOuOfMiddlePosition() {
        CustomArrayList<Integer> target = createListOfNumbers(1, 2, 3, 4);
        CustomArrayList<Integer> expected = createListOfNumbers(1, 2, 4);
        target.remove(2);
        assertEquals(target.size(), expected.size());
        assertEquals(expected, target);
    }

    /**
     * Тестируется корректность удаления элемента из начала списка
     */
    @Test
    void shouldRemoveElementsOuOfBeginningPosition() {
        CustomArrayList<Integer> target = createListOfNumbers(1, 2, 3, 4);
        CustomArrayList<Integer> expected = createListOfNumbers(2, 3, 4);
        target.remove(0);
        assertEquals(target.size(), expected.size());
        assertEquals(expected, target);
    }

    /**
     * Тестируется корректность удаления элемента из конца списка
     */
    @Test
    void shouldRemoveElementsOuOfEndPosition() {
        CustomArrayList<Integer> target = createListOfNumbers(1, 2, 3, 4);
        CustomArrayList<Integer> expected = createListOfNumbers(1, 2, 3);
        target.remove(3);
        assertEquals(target.size(), expected.size());
        assertEquals(expected, target);
    }

    /**
     * Тестируется удаление 10_000 элементов из случайных позиций списка
     */
    @Test
    void shouldRemove1000ElementsOuOfRandomPosition() {
        fillList(10000);
        final Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            final int index = random.nextInt(0, customArrayList.size());
            assertDoesNotThrow(() -> customArrayList.remove(index));
        }
    }

    /**
     * Тестируется невозможность удаления элемента списка по отрицательному индексу
     */
    @Test
    void shouldThrowWhileRemovingOutOfNegativePosition() {
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.remove(-1));
    }

    /**
     * Тестируется невозможность удаления из пустого списка
     */
    @Test
    void shouldThrowWhileRemovingOutOfEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.remove(0));
    }

    /**
     * Тестируется ичищение списка
     */
    @Test
    void shouldClear() {
        fillList(100);
        assertDoesNotThrow(() -> customArrayList.clear());
        assertEquals(0, customArrayList.size());
    }

    /**
     * Тестируется невозможность непоследовательного добавления элемента в список после его очищения
     */
    @Test
    void shouldThrowWhileInsertionInClearedList() {
        fillList(10);
        customArrayList.clear();
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.add(9, 1));
    }

    /**
     * Тестирется сортировка списка
     */
    @Test
    void shouldSort() {
        CustomArrayList<Integer> unsortedList = createListOfNumbers(0, 3, 4, 1, 5, 6, 2, 9, 7, 8);
        fillList(10);
        unsortedList.sort(Integer::compareTo);
        assertEquals(unsortedList, customArrayList);
    }

    /**
     * Тестируется замена 1000 элемента в случайной позиции
     */
    @Test
    void shouldReplaceElementInRandomPosition() {
        final Random random = new Random();
        fillList(1000);
        for (int i = 0; i < customArrayList.size(); i++) {
            final int index = random.nextInt(0, customArrayList.size());
            assertDoesNotThrow(() -> customArrayList.replace(index, random.nextInt()));
        }
    }

    /**
     * Тестируется корректность замены элемента
     */
    @Test
    void shouldProperlyReplaceElement() {
        CustomArrayList<Integer> firstListOfNumbers = createListOfNumbers(1, 2, 3, 4);
        CustomArrayList<Integer> secListOfNumbers = createListOfNumbers(1, 2, 5, 4);
        secListOfNumbers.replace(2, 3);
        assertEquals(firstListOfNumbers, secListOfNumbers);
    }

    /**
     * Тестирует невозможность замены элементы по отрицательному индексу
     */
    @Test
    void shouldThrowWhileReplacingInNegativePosition() {
        fillList(10);
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.replace(-1, 3));
    }

    /**
     * Тестирует невозможность замены элементы по индексу выходящему за пределы списка
     */
    @Test
    void shouldThrowWhileReplacingInOutOfSizePosition() {
        fillList(10);
        int size = customArrayList.size();
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.replace(size, 3));
    }

    /**
     * Тестирует невозможность замены элементы в пустом списке
     */
    @Test
    void shouldThrowWhileReplacingInEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.replace(0, 3));
    }

    /**
     * Тестируется корректное получение размера списка в случае добавления элементов
     */
    @Test
    void shouldHaveRightSizeInCaseOfAdding() {
        fillList(100_000);
        assertEquals(100_000, customArrayList.size());
    }

    /**
     * Тестируется корректное получение размера списка в случае удаления элементов
     */
    @Test
    void shouldHaveRightSizeInCaseOfRemoving() {
        fillList(100);
        customArrayList.remove(0);
        customArrayList.remove(0);
        assertEquals(98, customArrayList.size());
    }

    /**
     * Тестируется корректное получение размера списка в пустом списке
     */
    @Test
    void shouldHaveRightSizeInEmptyList() {
        assertEquals(0, customArrayList.size());
    }

    /**
     * Тестируется работа итератора
     */
    @Test
    void shouldIterate() {
        fillList(10);
        Iterator<Integer> iterator = customArrayList.iterator();
        while (iterator.hasNext()) {
            assertDoesNotThrow(iterator::next);
        }
    }

    /**
     * Тестируется невозможность модификации списка во время работы итератора
     */
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