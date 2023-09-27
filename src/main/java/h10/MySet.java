package h10;

import java.util.Comparator;

public class MySet<T> {

    public ListItem<T> setList;
    private Comparator<T> cmp;

    public MySet() {}

    public MySet(ListItem<T> lst) {
        this.setList = lst;
    }

    public MySet(ListItem<T> lst, Comparator<T> cmp) {
        this.setList = lst;
        this.cmp = cmp;
    }

    public MySet<T> unionInPlace(MySet<T> set) {
        return union(set, true);
    }


    public MySet<T> unionAsCopy(MySet<T> set) {
        return union(set, false);
    }

    private MySet<T> union(MySet<T> set, boolean inPlace) {
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;

        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            tail.next = inPlace ? p : new ListItem<T>(p.key);
            tail = tail.next;
        }

        for(ListItem<T> p = set.setList; p != null; p = p.next) {
            if(!contains(head.next, p.key)) {
                tail.next = inPlace ? p : new ListItem<T>(p.key);
                tail = tail.next;
            }
        }

        return new MySet<T>(head.next);
    }

    public MySet<T> intersectionInPlace(MyLinkedList<MySet<T>> sets) {
        return intersection(sets, true);
    }

    public MySet<T> intersectionAsCopy(MyLinkedList<MySet<T>> sets) {
        return intersection(sets, false);
    }

    private MySet<T> intersection(MyLinkedList<MySet<T>> sets, boolean inPlace) {

        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;

        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            tail.next = inPlace ? p : new ListItem<T>(p.key);
            tail = tail.next;
        }

        for(ListItem<MySet<T>> set = sets.getHead(); set != null; set = set.next) {
            ListItem<T> intersection = getListOfItemsInSet(head.next, set.key.setList);

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


        return new MySet<T>(head.next, this.cmp);
    }


    public MySet<T> differenceInPlace(MySet<T> set) {
        return null;
    }

    public MySet<T> differenceAsCopy(MySet<T> set) {
        return null;
    }

    private MySet<T> difference(MySet<T> set, boolean inPlace) {
        return null;
    }

    private ListItem<T> getListOfItemsInSet(ListItem<T> lst1, ListItem<T> lst2) {
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;
        for(ListItem<T> p = lst2; p != null; p = p.next) {
            for(ListItem<T> curr = lst1; curr != null; curr = curr.next) {
                if(this.cmp.compare(p.key, curr.key) == 0) {
                    tail.next = new ListItem<T>(curr.key);
                    tail = tail.next;
                }
            }
        }
        return head.next;
    }

    private boolean contains(ListItem<T> set, T key) {
        for(ListItem<T> p = set; p != null; p = p.next) {
            if(this.cmp.compare(p.key, key) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");

        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            builder.append(p.key);
            if(p.next != null) {
                builder.append(", ");
            }
        }

        builder.append("}");
        return builder.toString();
    }

}
