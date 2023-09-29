package h10;

import java.util.Comparator;
import java.util.function.Predicate;

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

        ListItem<T> p = set.setList;
        while(p != null) {
            ListItem<T> next = p.next;
            if(!contains(head.next, p.key)) {
                if(inPlace) {
                    tail.next = p;
                    tail = tail.next;
                    p.next = null;
                }
                else {
                    tail.next = new ListItem<>(p.key);
                    tail = tail.next;
                }
            }
            p = next;
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

        if(this.setList == null) {
            return new MySet<>(null, this.cmp);
        }

        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            tail.next = inPlace ? p : new ListItem<T>(p.key);
            tail = tail.next;
        }

        for(ListItem<MySet<T>> set = sets.getHead(); set != null; set = set.next) {

            if(set.key.setList == null) {
                return new MySet<>(null, this.cmp);
            }

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

    public MySet<T> makeSubsetInPlace(Predicate<T> pred) {
        return makeSubset(pred, true);
    }

    public MySet<T> makeSubsetAsCopy(Predicate<T> pred) {
        return makeSubset(pred, false);
    }

    private MySet<T> makeSubset(Predicate<T> pred, boolean inPlace) {
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;

        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            if(pred.test(p.key)) {
                tail.next = inPlace ? p : new ListItem<T>(p.key);
                tail = tail.next;
            }
        }

        return new MySet<>(head.next);
    }


    public MySet<T> differenceInPlace(MySet<T> set) {
        return difference(set, true);
    }

    public MySet<T> differenceAsCopy(MySet<T> set) {
        return difference(set, false);
    }

    private MySet<T> difference(MySet<T> set, boolean inPlace) {

        ListItem<T> head = new ListItem<>();
        ListItem<T> tail = head;

        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            tail.next = inPlace ? p : new ListItem<>(p.key);
            tail = tail.next;
        }

        ListItem<T> current = head.next;
        ListItem<T> prev = head;
        ListItem<T> p = set.setList;

        while(p != null) {
            if(contains(head.next, p.key)) {
                prev.next = current.next;
                current.next = null;
                current = prev.next;
            }
            else {
                current = current.next;
                prev = prev.next;
            }
            p = p.next;
        }

        return new MySet<>(head.next, this.cmp);
    }


    private MySet<Tuple<T, ListItem<T>>> disjointUnion(MyLinkedList<MySet<T>> sets, MySet<T> indexSet,boolean inPlace) {
        ListItem<Tuple<T, ListItem<T>>> head = new ListItem<>();
        ListItem<Tuple<T, ListItem<T>>> tail = head;

        ListItem<T> index = indexSet.setList;
        ListItem<T> current = this.setList;

        while(current != null) {
            if(inPlace) {
                ListItem<T> next = current.next;
                current.next = null;
                tail.next = new ListItem<>(new Tuple<>(index.key, current));
                current = next;
            }
            else {
                tail.next = new ListItem<>(new Tuple<>(index.key, new ListItem<>(current.key)));
                current = current.next;
            }
            tail = tail.next;
        }

        index = index.next;
        ListItem<MySet<T>> set = sets.getHead();

        while(index != null) {
            current = set.key.setList;
            while(current != null) {
                if(inPlace) {
                    ListItem<T> next = current.next;
                    current.next = null;
                    tail.next = new ListItem<>(new Tuple<>(index.key, current));
                    current = next;
                }
                else {
                    tail.next = new ListItem<>(new Tuple<>(index.key, new ListItem<>(current.key)));
                    current = current.next;
                }
                tail = tail.next;
            }
            set = set.next;
            index = index.next;

        }

        return new MySet<>(head.next);
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
