import java.util.Arrays;
import java.util.Comparator;

/**
 * Реализация списка ArrayList и его основных методов.
 *
 * @param <T> тип элементов списка
 */
public class MyArrayList<T> {
    /** Константа, представляющая емкость списка по умолчанию */
    private static final int DEFAULT_CAPACITY = 10;

    /** Внутренний массив элементов списка */
    private Object [] array;

    /** Размер списка (количество элементов) */
    private int size;

    /**
     * Конструктор, cоздает новый пустой список с начальной емкостью 10.
     */
    public MyArrayList(){
        array = new Object [DEFAULT_CAPACITY];
    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param item добавляемый элемент
     */
    public void add(T item){
        ensureCapacity(array.length);
        array[size++] = item;
    }

    /**
     * Добавляет элемент по индексу.
     *
     * @param index индекс, по которому нужно добавить элемент
     * @param item  добавляемый элемент
     */
    public void add(int index, T item){
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + " size: " + size);
        ensureCapacity(array.length);
        System.arraycopy(array, index, array, index + 1 ,size-index);
        array[index] = item;
        size++;
    }

    /**
     * Возвращает элемент по индексу.
     *
     * @param index индекс элемента
     * @return элемент списка по индексу
     */
    public T get(int index){
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + " size: " + size);
        return (T) array[index];
    }

    /**
     * Удаляет элемент по индексу.
     *
     * @param index индекс удаляемого элемента
     */
    public void remove (int index){
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + " size: " + size);
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--;
        trimToSize();
    }

    /**
     * Удаляет все элементы списка и делает его пустым.
     */
    public void clear(){
        Arrays.fill(array,null);
        size = 0;
        trimToSize();
    }

    /**
     * Сортирует лист в естественном порядке.
     */
    public void sort(){
        Arrays.sort(array,0,size);
    }

    /**
     * Сортирует элементы списка с использованием компаратора.
     * Использует алгоритм сортировки quicksort.
     *
     * @param comparator компаратор для сортировки списка
     */
    public void sort(Comparator<? super T> comparator){
        if (array == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator cannot be null");
        }
        quickSort(0, size - 1, comparator);
    }

    /**
     * Реализует алгоритм быстрой сортировки quicksort для элементов списка
     * с использованием заданного компаратора.
     *
     * @param low индекс нижней границы для сортировки
     * @param high индекс верхней границы для сортировки
     * @param comparator компаратор для сравнения элементов
     */
    private void quickSort(int low, int high, Comparator<? super T> comparator){
        if (low >= high) return;
        int pi = partition(low, high, comparator);
        quickSort(low, pi - 1, comparator);
        quickSort(pi, high, comparator);
    }

    /**
     * Разделяет массив на две части относительно опорного элемента
     * так, чтобы элементы меньше опорного были слева, а элементы больше - справа.
     *
     * @param low индекс нижней границы для разделения
     * @param high индекс верхней границы для разделения
     * @param comparator компаратор для сравнения элементов
     * @return индекс опорного элемента после разделения
     */
    private int partition (int low, int high, Comparator<? super T> comparator){
        T pivot = (T) array[(high + low)/2];
        while (low <= high){
            while (comparator.compare((T)array [low], pivot) < 0)
                low++;
            while (comparator.compare((T)array [high], pivot) > 0)
                high--;
            if (low <= high){
                swap(low, high);
                low++;
                high--;
            }
        }
        return low;
    }

    /**
     * Меняет местами выбранные элементы массива.
     *
     * @param i индекс 1-ого элемента
     * @param j индекс 1-ого элемента
     */
    private void swap(int i, int j){
        T temp = (T) array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Возвращает количество элементов в списке.
     *
     * @return количество элементов в списке
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, является ли список пустым.
     *
     * @return true, если список пустой, иначе - false
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Проверяет наличие указанного элемента в списке.
     *
     * @param obj элемент, который нужно найти
     * @return true, если элемент есть в списке, иначе - false
     */
    public boolean contains(Object obj){
        return indexOf(obj) != -1;
    }

    /**
     * Возвращает индекс первого вхождения указанного элемента в список.
     *
     * @param obj элемент, который нужно найти
     * @return индекс первого вхождения элемента в список, либо -1, если элемент не найден
     */
    public int indexOf(Object obj){
        for (int i = 0; i < size; i++){
            if (obj.equals(array[i]))
                return i;
        }
        return -1;
    }

    /**
     * При добавлении элемента увеличивает емкость массива в два раза в случае, если
     * список уже полностью заполнен.
     *
     * @param length текущая длина внутреннего массива
     */
    public void ensureCapacity(int length) {
        if (size == array.length) {
            Object[] newArray = new Object[length*2];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    /**
     * Уменьшает емкость внутреннего массива до size в случае, если
     * текущая емкость больше дефолтной (10), а количество элементов в списке
     * в два и более раза меньше, чем емкость внутреннего массива.
     * В случае, если после уменьшения емкость становится меньше дефолтной, то
     * размер внутреннего массива становится равен дефолтной емкости.
     *
     */
    public void trimToSize(){
        if (array.length > DEFAULT_CAPACITY && 2*size < array.length){
            array = Arrays.copyOf(array, Math.max(size, DEFAULT_CAPACITY));
        }
    }

    /**
     * Преобразует список в массив с размером size.
     *
     * @return массив размером size
     */
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    /**
     * Возвращает строковое представление списка.
     *
     * @return строковое представление списка
     */
    @Override
    public String toString() {
        Object [] newArray = Arrays.copyOf(array, size);
        return Arrays.toString(newArray);
    }
}
