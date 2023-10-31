package h10;

import java.util.Comparator;
import java.util.function.Predicate;

public class MySetInPlace<T> extends MySet<T> {


    protected MySetInPlace(ListItem<T> head) {
        super(head);
    }

    @Override
    public MySet<T> makeSubset(Predicate<? super T> pred) {
        ListItem<T> current = this.head;
        ListItem<T> prev = null;
        ListItem<T> newHead = null;

        while(current != null) {
            if(!pred.test(current.key)) {
                ListItem<T> next = current.next;
                current.next = null;
                if(prev == null) {
                    current = next;
                }
                else {
                    prev.next = current = next;
                }
            }
            else {
                if(newHead == null) {
                    newHead = current;
                }
                prev = current;
                current = current.next;
            }
        }
        this.head = newHead;
        return this;
    }

    @Override
    public MySet<T> difference(MySet<T> other) {

        ListItem<T> p = other.getHead();
        while(p != null) {
            ListItem<T> current = this.head;
            ListItem<T> prev = null;

            while(current != null && !p.key.equals(current.key)) {
                prev = current;
                current = current.next;
            }

            if(current != null) {
                ListItem<T> next = current.next;
                if(prev != null) {
                    prev.next = next;
                }
                if(current == this.head) {
                    this.head = next;
                }
                current.next = null;
            }
            p = p.next;
        }

        return this;
    }

    @Override
    public MySet<T> intersection(ListItem<MySet<T>> others) {
        if(this.head == null) {
            return this;
        }

        for(ListItem<MySet<T>> set = others; set != null; set = set.next) {
            if(set.key.getHead() == null) {
                this.head = null;
                return this;
            }

            ListItem<T> intersection = getListOfItemsInSet(this.head, set.key.getHead());
            ListItem<T> current = this.head;
            ListItem<T> prev = null;
            ListItem<T> newHead = null;

            while(current != null) {
                ListItem<T> next = current.next;
                if(!contains(intersection, current.key)) {
                    current.next = null;
                    if(prev != null) {
                        prev.next = next;
                    }
                }
                else {
                    if(newHead == null) {
                        newHead = current;
                    }
                    prev = current;
                }
                current = next;
            }
            this.head = newHead;
        }
        return this;
    }

    @Override
    public MySet<T> union(ListItem<MySet<T>> others) {
        ListItem<T> tail = this.head;
        for(ListItem<T> p = this.head; p.next != null; p = p.next) {
            tail = tail.next;
        }

        for(ListItem<MySet<T>> set = others; set != null; set = set.next) {
            ListItem<T> current = set.key.getHead();
            ListItem<T> prev = null;
            ListItem<T> newHead = null;

            while(current != null) {
                ListItem<T> next = current.next;
                if(!contains(this.head, current.key)) {
                    tail = tail.next = current;
                    current.next = null;
                    if(prev != null) {
                        prev.next = next;
                    }
                }
                else {
                    if(newHead == null) {
                        newHead = current;
                    }
                    prev = current;
                }
                current = next;
            }
            set.key.setHead(newHead);
         }

        return this;
    }

    @Override
    public MySet<Tuple<T, ListItem<T>>> disjointUnion(ListItem<MySet<T>> others, MySet<T> indexes) {
        return null;
    }
}
