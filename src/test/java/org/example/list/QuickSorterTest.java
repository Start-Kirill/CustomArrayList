package org.example.list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QuickSorterTest {

    private final QuickSorter<String> quickSorter = new QuickSorter<>();

    @Test
    void shouldSortWhenNullInMiddle() {
        String[] target = new String[]{"BSecond", null, "AFirst", null, null};
        String[] expected = new String[]{"AFirst", "BSecond", null, null, null};
        quickSorter.sort(target, 3, String::compareTo);
        for (int i = 0; i < target.length; i++) {
            assertEquals(target[i], expected[i]);
        }
    }

    @Test
    void shouldSortWhenNullInEnd() {
        String[] target = new String[]{"BSecond", "CThird", "AFirst", null};
        String[] expected = new String[]{"AFirst", "BSecond", "CThird", null};
        quickSorter.sort(target, 4, String::compareTo);
        for (int i = 0; i < target.length; i++) {
            assertEquals(target[i], expected[i]);
        }
    }

    @Test
    void shouldSortWhenNullInBeginning() {
        String[] target = new String[]{null, "BSecond", null, "AFirst", null, null};
        String[] expected = new String[]{"AFirst", "BSecond", null, null, null, null};
        quickSorter.sort(target, 4, String::compareTo);
        for (int i = 0; i < target.length; i++) {
            assertEquals(target[i], expected[i]);
        }
    }

    @Test
    void shouldSortWhenOnlyNulls() {
        String[] target = new String[]{null, null, null, null};
        assertDoesNotThrow(() -> quickSorter.sort(target, 4, String::compareTo));
    }

    @Test
    void shouldSortWhenEmpty() {
        String[] target = new String[]{};
        assertDoesNotThrow(() -> quickSorter.sort(target, 0, String::compareTo));
    }


}