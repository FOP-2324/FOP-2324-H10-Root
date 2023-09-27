package h10;

import java.sql.SQLOutput;
import java.util.Comparator;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        ListItem<Integer> lst1 = listProvider(new Integer[]{1,2,3,4});
        ListItem<Integer> lst2 = listProvider(new Integer[]{3,4,5,6});
        ListItem<Integer> lst3 = listProvider(new Integer[]{3,4,5,7});
        MySet<Integer> set1 = new MySet<>(lst1, Comparator.naturalOrder());
        MySet<Integer> set2 = new MySet<>(lst2);
        MySet<Integer> set3 = new MySet<>(lst3);
        MyLinkedList<MySet<Integer>> sets = new MyLinkedList<>(new ListItem<>(set2));
        sets.add(set3);
        MySet<Integer> set4 = set1.intersectionAsCopy(sets);
        System.out.println(set4.toString());
        System.out.println(set3.toString());
        System.out.println(set2.toString());
        System.out.println(set1.toString());

    }

    public static <T> ListItem<T> listProvider(T[] array) {
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;
        for(int i = 0; i < array.length; i++) {
            tail.next = new ListItem<T>(array[i]);
            tail = tail.next;
        }
        return head.next;
    }
}
