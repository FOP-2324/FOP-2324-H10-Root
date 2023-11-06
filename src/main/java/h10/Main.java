package h10;

import java.util.function.Predicate;

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
        boolean inPlace = true;
        testIntersection(inPlace);
        testUnion(inPlace);
        testDifference(inPlace);
        testDisjointUnion(inPlace);
    }


    public static <T> MySet<T> generateSet(ListItem<T> list, boolean inPlace) {
        MySet<T> set = null;
        if(inPlace) {
            set = new MySetInPlace<>(list);
        }
        else {
            set = new MySetAsCopy<>(list);
        }
        return set;
    }

    public static <T> ListItem<T> generateList(T[] elements) {
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;

        for(int i = 0; i < elements.length; i++) {
            tail = tail.next = new ListItem<>(elements[i]);
        }

        return head.next;
    }


    public static void testUnion(boolean inPlace) {
        if(inPlace) {
            System.out.println("#### TEST UNION IN-PLACE ####");
        }
        else {
            System.out.println("#### TEST UNION AS-COPY ####");
        }
        Integer[] elems1 = new Integer[]{1,2,3,4,5,6};
        Integer[] elems2 = new Integer[]{4,5,6,7,8,9};
        Integer[] elems3 = new Integer[]{};
        ListItem<Integer> lst1 = generateList(elems1);
        ListItem<Integer> lst2 = generateList(elems2);
        ListItem<Integer> lst3 = generateList(elems3);
        MySet<Integer> set1 = generateSet(lst1, inPlace);
        MySet<Integer> set2 = generateSet(lst2, inPlace);
        MySet<Integer> set3 = generateSet(lst3, inPlace);
        ListItem<MySet<Integer>> sets = new ListItem<>(set2);
        sets.next = new ListItem<>(set3);
        System.out.println("Set to check method on:\n" + set1.toString());
        System.out.println("List of sets to check:");
        printList(sets);
        MySet<Integer> result = set1.union(sets);
        System.out.println("Result of parameter list:");
        printList(sets);
        if(inPlace) {
            System.out.println("Result set:\n" + set1.toString());
        }
        else {
            System.out.println("Result set:\n" + result.toString());
        }
    }

    public static void testIntersection(boolean inPlace) {
        if(inPlace) {
            System.out.println("#### TEST INTERSECTION IN-PLACE ####");
        }
        else {
            System.out.println("#### TEST INTERSECTION AS-COPY ####");
        }
        Integer[] elems1 = new Integer[]{1,2,3,4,5,6};
        Integer[] elems2 = new Integer[]{4,5,6,7,8,9};
        Integer[] elems3 = new Integer[]{};
        ListItem<Integer> lst1 = generateList(elems1);
        ListItem<Integer> lst2 = generateList(elems2);
        ListItem<Integer> lst3 = generateList(elems3);
        MySet<Integer> set1 = generateSet(lst1, inPlace);
        MySet<Integer> set2 = generateSet(lst2, inPlace);
        MySet<Integer> set3 = generateSet(lst3, inPlace);
        ListItem<MySet<Integer>> sets = new ListItem<MySet<Integer>>(set2);
        sets.next = new ListItem<>(set3);
        System.out.println("Set to check method on:\n" + set1.toString());
        System.out.println("List of sets to check:");
        printList(sets);
        MySet<Integer> result = set1.intersection(sets);
        System.out.println("Result of parameter list:");
        printList(sets);
        if(inPlace) {
            System.out.println("Result set:\n" + set1.toString());
        }
        else {
            System.out.println("Result set:\n" + result.toString());
        }
    }

    public static void testDifference(boolean inPlace) {
        if(inPlace) {
            System.out.println("#### TEST DIFFERENCE IN-PLACE ####");
        }
        else {
            System.out.println("#### TEST DIFFERENCE AS-COPY ####");
        }
        Integer[] elems1 = new Integer[]{1,2,3,4,5,6};
        Integer[] elems2 = new Integer[]{1,2,3,7,8,9};
        ListItem<Integer> lst1 = generateList(elems1);
        ListItem<Integer> lst2 = generateList(elems2);

        MySet<Integer> set1 = generateSet(lst1, inPlace);
        MySet<Integer> set2 = generateSet(lst2, inPlace);

        System.out.println("Set to check method on:\n" + set1.toString());
        System.out.println("List of sets to check:\n" + set2.toString());

        MySet<Integer> result = set1.difference(set2);
        System.out.println("Result of parameter list:\n" + set2.toString());
        if(inPlace) {
            System.out.println("Result set:\n" + set1.toString());
        }
        else {
            System.out.println("Result set:\n" + result.toString());
        }
    }

    public static void testDisjointUnion(boolean inPlace) {
        if(inPlace) {
            System.out.println("#### TEST DISJOINT UNION IN-PLACE ####");
        }
        else {
            System.out.println("#### TEST DISJOINT UNION AS-COPY ####");
        }
        Integer[] elems1 = new Integer[]{1,2,3,4,5,6};
        Integer[] elems2 = new Integer[]{1,2,3,7,8,9};
        Integer[] elems3 = new Integer[]{1,3,5,7,9};
        ListItem<Integer> lst1 = generateList(elems1);
        ListItem<Integer> lst2 = generateList(elems2);
        ListItem<Integer> lst3 = generateList(elems3);

        MySet<Integer> set1 = generateSet(lst1, inPlace);
        MySet<Integer> set2 = generateSet(lst2, inPlace);
        MySet<Integer> set3 = generateSet(lst3, inPlace);
        ListItem<MySet<Integer>> sets = new ListItem<>(set2);
        sets.next = new ListItem<>(set3);

        System.out.println("Set to check method on:\n" + set1.toString());
        System.out.println("List of sets to check:");
        printList(sets);

        Integer[] indexArray = new Integer[]{1,2,3};
        ListItem<Integer> idxList = generateList(indexArray);
        MySet<Integer> indices = generateSet(idxList, true);

        MySet<Tuple<Integer, ListItem<Integer>>> result = set1.disjointUnion(sets, indices);
        System.out.println("Result of parameter list:");
        printList(sets);
        System.out.println("Result set:\n" + result.toString());
    }

    public static <T> void printList(ListItem<T> lst) {
        StringBuilder builder = new StringBuilder();
        for(ListItem<T> p = lst; p != null; p = p.next) {
            builder.append("{");
            builder.append(p.toString());
            builder.append("}");
            if(p.next != null) {
                builder.append(" -> ");
            }
        }
        System.out.println(builder.toString());
    }
}
