
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class MyArrayListTest {
    private MyArrayList<Integer> integerList;
    private MyArrayList<String> stringList;

    @BeforeEach
    public void setUp() {
        integerList = new MyArrayList<>();
        stringList = new MyArrayList<>();
    }

    @Test
    public void testAddAndGet() {
        for (int i = 0; i < 1000; i++) {
            integerList.add(i);
            stringList.add("number: " + i);
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(i, integerList.get(i));
            assertEquals("number: " + i, stringList.get(i));
        }
    }

    @Test
    public void testAddAndGetNull() {
        for (int i = 0; i < 10; i++) {
            integerList.add(i);
            stringList.add("number: " + i);
        }
        integerList.add(null);
        stringList.add(null);

        assertNull(integerList.get(10));
        assertNull(stringList.get(10));
    }

    @Test
    public void testGetInvalidIndex() {
        for (int i = 0; i < 10; i++) {
            integerList.add(i);
        }
        assertThrows(IndexOutOfBoundsException.class, () -> integerList.get(10));
        assertThrows(IndexOutOfBoundsException.class, () -> integerList.get(-1));
    }

    @Test
    public void testAddWithIndex() {
        integerList.add(0, 1);
        integerList.add(1, 3);
        integerList.add(1, 2);
        assertEquals(1, integerList.get(0));
    }

    @Test
    public void testRemove() {

        for (int i = 0; i < 10; i++) {
            integerList.add(i);
        }
        for (int i = 0; i < 10; i++) {
            integerList.remove(0);
            assertEquals(9 - i, integerList.size());
        }
        assertTrue(integerList.isEmpty());
    }

    @Test
    public void testRemoveInvalidIndex() {
        for (int i = 0; i < 10; i++) {
            integerList.add(i);
        }
        assertThrows(IndexOutOfBoundsException.class, () -> integerList.remove(10));
        assertThrows(IndexOutOfBoundsException.class, () -> integerList.remove(-1));

    }
    @Test
    public void testClear() {
        for (int i = 0; i < 10; i++) {
            integerList.add(i);
        }
        integerList.clear();
        assertArrayEquals(new Integer[]{}, integerList.toArray());
        assertEquals(0, integerList.size());
    }

    @Test
    public void testSort() {
        Integer[] integerArray = new Integer[10];
        String[] stringArray = new String[10];
        for (int i = 9; i >= 0; i--) {
            integerList.add(i);
            stringList.add("number: " + i);
        }
        for (int i = 0; i < 10; i++) {
            integerArray[i] = i;
            stringArray [i] = "number: " + i;
        }
        integerList.sort(Comparator.naturalOrder());
        stringList.sort(Comparator.naturalOrder());

        assertArrayEquals(integerArray, integerList.toArray());
        assertArrayEquals(stringArray, stringList.toArray());
    }

    @Test
    public void testSortWithNull() {
        for (int i = 0; i < 5; i++) {
            integerList.add(i);
        }
        integerList.add(null);
        assertThrows(NullPointerException.class, () -> integerList.sort(Comparator.naturalOrder()));
    }

    @Test
    public void testSortNullWithoutComparator() {
        for (int i = 0; i < 5; i++) {
            integerList.add(i);
        }
        integerList.add(null);
        assertThrows(NullPointerException.class, () -> integerList.sort());
    }

    @Test
    public void testSize() {
        for (int i = 0; i < 20; i++) {
            integerList.add(i);
        }
        for (int i = 0; i < 10; i++) {
            integerList.remove(i);
        }
        assertEquals(10, integerList.size());
    }

    @Test
    public void testIsEmpty() {
        integerList.add(0);
        integerList.remove(0);
        assertTrue(integerList.isEmpty());
    }

    @Test
    public void TestisEmptyNotEmptyList() {
        integerList.add(1);
        stringList.add("Foo");
        assertFalse(integerList.isEmpty());
        assertFalse(stringList.isEmpty());
    }

    @Test
    public void testContains() {
        for (int i = 0; i < 10; i++) {
            integerList.add(i);
            assertTrue(integerList.contains(i));
        }
    }

    @Test
    public void testContainsEmptyList() {
        assertFalse(integerList.contains(1));
    }

    @Test
    public void testIndexOf() {
        for (int i = 0; i < 10; i++) {
            integerList.add(i);
            assertEquals(i, integerList.indexOf(i));
        }
            assertEquals(-1, integerList.indexOf(11));
    }

    @Test
    public void testIndexOfEmptyList() {
        assertEquals(-1, integerList.indexOf(1));
    }

    @Test
    public void testEnsureCapacity() {
        int defaultLength = 10;
        for (int i = 0; i < 20; i++) {
            integerList.add(i);
        }
        assertEquals(defaultLength * 2, integerList.toArray().length);
    }

    @Test
    public void testTrimToSizeWhenCapacityIsGreaterThanDefault() {
        for (int i = 0; i < 15; i++) {
            integerList.add(i);
        }
        integerList.trimToSize();
        assertEquals(15, integerList.toArray().length);
    }

    @Test
    public void testToString() {
        for (int i = 0; i < 5; i++) {
            integerList.add(i);
            stringList.add("num: "+ i);
        }
        assertEquals("[0, 1, 2, 3, 4]", integerList.toString());
        assertEquals("[num: 0, num: 1, num: 2, num: 3, num: 4]", stringList.toString());
    }
}

