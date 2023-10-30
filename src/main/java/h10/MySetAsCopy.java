package h10;

import java.util.Comparator;
import java.util.function.Predicate;

public class MySetAsCopy<T> extends MySet<T> {


    protected MySetAsCopy(ListItem<T> head) {
        super(head);
    }

    @Override
    public MySet<T> makeSubset(Predicate<? super T> pred) {
        ListItem<T> head = new ListItem<>();
        ListItem<T> tail = head;
        for(ListItem<T> p = this.head; p != null; p = p.next) {
            if(pred.test(p.key)) {
                tail.next = new ListItem<>(p.key);
                tail = tail.next;
            }
        }
        MySet<T> subSet = new MySetAsCopy<>(head.next);
        return subSet;
    }

    @Override
    public MySet<T> difference(MySet<T> other) {
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;

        for(ListItem<T> p = this.head; p != null; p = p.next) {
            tail = tail.next = new ListItem<T>(p.key);
        }

        ListItem<T> p = other.getHead();

        while(p != null) {
            ListItem<T> current = head.next;
            ListItem<T> prev = head;

            while(current != null && !p.equals(current)) {
                current = current.next;
                prev = prev.next;
            }

            if(current != null) {
                prev.next = current.next;
                current.next = null;
            }
            p = p.next;
        }


        return new MySetAsCopy<>(head.next);
    }

    @Override
    public MySet<T> intersection(ListItem<MySet<T>> others) {
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;

        if(this.head == null) {
            return new MySetAsCopy<T>(null);
        }

        for(ListItem<T> p = this.head; p != null; p = p.next) {
            tail = tail.next = new ListItem<T>(p.key);
        }

        for(ListItem<MySet<T>> set = others; set != null; set = set.next) {
            if(set.key.getHead() == null) {
                return new MySetAsCopy<>(null);
            }

            ListItem<T> intersection = getListOfItemsInSet(head.next, set.key.getHead());

            ListItem<T> current = head.next;
            ListItem<T> prev = head;

            while(current != null) {
                if(!contains(intersection, current.key)) {
                    prev.next = current.next;
                    current.next = null;
                    current = prev.next;
                }
                else {
                    current = current.next;
                    prev = prev.next;
                }
            }

        }

        return new MySetAsCopy<>(head.next);
    }

    @Override
    public MySet<T> union(ListItem<MySet<T>> others) {
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;

        for(ListItem<T> p = this.head; p != null; p = p.next) {
            tail = tail.next = new ListItem<T>(p.key);
        }

        for(ListItem<MySet<T>> set = others; set != null; set = set.next) {
            ListItem<T> p = set.key.getHead();

            while(p != null) {
                ListItem<T> next = p.next;

                if(!contains(head.next, p.key)) {
                    tail = tail.next = new ListItem<>(p.key);
                }
            }
        }

        return new MySetAsCopy<>(head.next);
    }

    @Override
    public MySet<Tuple<T, ListItem<T>>> disjointUnion(ListItem<MySet<T>> others, MySet<T> indexes) {
        ListItem<Tuple<T, ListItem<T>>> head = new ListItem<>();
        ListItem<Tuple<T, ListItem<T>>> tail = head;

        ListItem<T> index = indexes.getHead();
        ListItem<T> current = this.head;

        while(current != null) {
            tail = tail.next = new ListItem<>(new Tuple<>(index.key, new ListItem<>(current.key)));
            current = current.next;
        }

        index = index.next;
        ListItem<MySet<T>> set = others;

        while(index != null) {
            current = set.key.getHead();
            while(current != null) {
                tail = tail.next = new ListItem<>(new Tuple<>(index.key, new ListItem<>(current.key)));
                current = current.next;
            }
            set = set.next;
            index = index.next;
        }

        return new MySetAsCopy<>(head.next);
    }
}
