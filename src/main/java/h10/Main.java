package h10;

import java.sql.SQLOutput;

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
        ListItem<ListItem<Integer>> lst1 = new ListItem<>();
        ListItem<ListItem<Integer>> lst2 = new ListItem<>();
        ListItem<ListItem<Integer>> lst3 = new ListItem<>();

        lst1.next = lst2;
        lst2.next = lst3;

        ListItem<Integer> ls1 = new ListItem<>(1);
        ListItem<Integer> ls2 = new ListItem<>(2);
        ListItem<Integer> ls3 = new ListItem<>(3);

        ls1.next = ls2;
        ls2.next = ls3;
        lst1.key = ls1;

        ListItem<Integer> ls4 = new ListItem<>(4);
        ListItem<Integer> ls5 = new ListItem<>(5);
        ListItem<Integer> ls6 = new ListItem<>(6);

        ls4.next = ls5;
        ls5.next = ls6;
        lst2.key = ls4;

        ListItem<Integer> ls7 = new ListItem<>(7);
        ListItem<Integer> ls8 = new ListItem<>(8);
        ListItem<Integer> ls9 = new ListItem<>(9);

        ls7.next = ls8;
        ls8.next = ls9;
        lst3.key = ls7;

        String list = ListSolver.toString(lst1);
        System.out.println(list);
        ListItem<ListItem<Integer>> lst = ListSolver.reverseListOfLists(lst1);
        list = ListSolver.toString(lst);
        System.out.println(list);

    }
}
