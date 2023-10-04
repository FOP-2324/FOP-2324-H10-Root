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


    /**
     *
     * Helper method for intersection.
     * Returns a list with every element which appears in both lists, given as parameter.
     *
     * @param lst1
     * @param lst2
     * @return
     */
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


    /**
     *
     * Helper method for checking if the current list in the parameter contains the key
     *
     * @param set
     * @param key
     * @return
     */
    private boolean contains(ListItem<T> set, T key) {
        for(ListItem<T> p = set; p != null; p = p.next) {
            if(this.cmp.compare(p.key, key) == 0) {
                return true;
            }
        }
        return false;
    }


    /**
     *
     * @param sets
     * @return
     */
    public MySet<T> unionInPlace(MyLinkedList<MySet<T>> sets) {
        return union(sets, true);
    }

    /**
     *
     * @param sets
     * @return
     */
    public MySet<T> unionAsCopy(MyLinkedList<MySet<T>> sets) {
        return union(sets, false);
    }


    /**
     *
     * @param sets
     * @param inPlace
     * @return
     */
    private MySet<T> union(MyLinkedList<MySet<T>> sets, boolean inPlace) {
        // Create dummy elements
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;

        // add set of current object to new MySet-object (in-place/as-copy)
        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            tail.next = inPlace ? p : new ListItem<T>(p.key);
            tail = tail.next;
        }

        // Add elements from sets parameter to new MySet-object
        for(ListItem<MySet<T>> set = sets.getHead(); set != null; set = set.next) {
            ListItem<T> p = set.key.setList;
            // Iterate over current set
            while(p != null) {
                ListItem<T> next = p.next;
                // check if the current element is in the current list to return
                if(!contains(head.next, p.key)) {
                    // add element in-place
                    if(inPlace) {
                        tail.next = p;
                        tail = tail.next;
                        p.next = null;
                    }
                    // add element as-copy
                    else {
                        tail.next = new ListItem<>(p.key);
                        tail = tail.next;
                    }
                }
                p = next;
            }
        }

        return new MySet<T>(head.next);
    }


    /**
     *
     * @param sets
     * @return
     */
    public MySet<T> intersectionInPlace(MyLinkedList<MySet<T>> sets) {
        return intersection(sets, true);
    }


    /**
     *
     * @param sets
     * @return
     */
    public MySet<T> intersectionAsCopy(MyLinkedList<MySet<T>> sets) {
        return intersection(sets, false);
    }


    /**
     *
     * @param sets
     * @param inPlace
     * @return
     */
    private MySet<T> intersection(MyLinkedList<MySet<T>> sets, boolean inPlace) {
        // create dummy elements
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;

        // check if current object is an empty set
        if(this.setList == null) {
            return new MySet<>(null, this.cmp);
        }

        // add current set to the new set
        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            tail.next = inPlace ? p : new ListItem<T>(p.key);
            tail = tail.next;
        }

        for(ListItem<MySet<T>> set = sets.getHead(); set != null; set = set.next) {
            // return the empty set if one set in the parameter is an empty set
            if(set.key.setList == null) {
                return new MySet<>(null, this.cmp);
            }

            // get a list of every item which is in the new set and the current set of the parameter
            ListItem<T> intersection = getListOfItemsInSet(head.next, set.key.setList);

            // create pointer for deleting elements
            ListItem<T> current = head.next;
            ListItem<T> prev = head;

            // iterate over the current set to return
            while(current != null) {
                // remove every element in the current set which does not belong to the intersection
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


    /**
     *
     * @param pred
     * @return
     */
    public MySet<T> makeSubsetInPlace(Predicate<T> pred) {
        return makeSubset(pred, true);
    }


    /**
     *
     * @param pred
     * @return
     */
    public MySet<T> makeSubsetAsCopy(Predicate<T> pred) {
        return makeSubset(pred, false);
    }


    /**
     *
     * @param pred
     * @param inPlace
     * @return
     */
    private MySet<T> makeSubset(Predicate<T> pred, boolean inPlace) {
        // create dummy elements
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;

        // iterate over current set
        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            // add element (in-place/as-copy) to the new set if the element satisfies the condition
            if(pred.test(p.key)) {
                tail.next = inPlace ? p : new ListItem<T>(p.key);
                tail = tail.next;
            }
        }

        return new MySet<>(head.next);
    }


    /**
     *
     * @param set
     * @return
     */
    public MySet<T> differenceInPlace(MySet<T> set) {
        return difference(set, true);
    }


    /**
     *
     * @param set
     * @return
     */
    public MySet<T> differenceAsCopy(MySet<T> set) {
        return difference(set, false);
    }


    /**
     *
     * @param set
     * @param inPlace
     * @return
     */
    private MySet<T> difference(MySet<T> set, boolean inPlace) {
        // create dummy elements
        ListItem<T> head = new ListItem<>();
        ListItem<T> tail = head;

        // add current set to new set
        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            tail.next = inPlace ? p : new ListItem<>(p.key);
            tail = tail.next;
        }


        ListItem<T> p = set.setList;

        while(p != null) {
            // create pointers for removing items from the set, which will be returned
            ListItem<T> current = head.next;
            ListItem<T> prev = head;

            // move to the positions where current element has to be removed (item does not exist in parameter set)
            while(current != null && this.cmp.compare(current.key, p.key) != 0) {
                current = current.next;
                prev = prev.next;
            }

            // remove item from set
            if(current != null) {
                prev.next = current.next;
                current.next = null;
            }
            p = p.next;
        }

        return new MySet<>(head.next, this.cmp);
    }


    /**
     *
     * @param sets
     * @param indexSet
     * @return
     */
    public MySet<Tuple<T, ListItem<T>>> disjointUnionInPlace(MyLinkedList<MySet<T>> sets, MySet<T> indexSet) {
        return disjointUnion(sets, indexSet, true);
    }


    /**
     *
     * @param sets
     * @param indexSet
     * @return
     */
    public MySet<Tuple<T, ListItem<T>>> disjointUnionAsCopy(MyLinkedList<MySet<T>> sets, MySet<T> indexSet) {
        return disjointUnion(sets, indexSet, false);
    }


    /**
     *
     * @param sets
     * @param indexSet
     * @param inPlace
     * @return
     */
    private MySet<Tuple<T, ListItem<T>>> disjointUnion(MyLinkedList<MySet<T>> sets, MySet<T> indexSet,boolean inPlace) {
        // create dummy item
        ListItem<Tuple<T, ListItem<T>>> head = new ListItem<>();
        ListItem<Tuple<T, ListItem<T>>> tail = head;

        ListItem<T> index = indexSet.setList;
        ListItem<T> current = this.setList;

        // add current set to the new set
        while(current != null) {
            // add items in place
            if(inPlace) {
                // remove reference to the next item in the set
                ListItem<T> next = current.next;
                current.next = null;
                // add current item to new set
                tail.next = new ListItem<>(new Tuple<>(index.key, current));
                current = next;
            }
            else {
                // add copy of the current item to the set
                tail.next = new ListItem<>(new Tuple<>(index.key, new ListItem<>(current.key)));
                current = current.next;
            }
            tail = tail.next;
        }

        index = index.next;
        ListItem<MySet<T>> set = sets.getHead();

        // iterate over the index list
        while(index != null) {
            // get current set in parameter sets
            current = set.key.setList;
            while(current != null) {
                if(inPlace) {
                    // remove reference to the next item in the current set
                    ListItem<T> next = current.next;
                    current.next = null;
                    // add item to the new set
                    tail.next = new ListItem<>(new Tuple<>(index.key, current));
                    current = next;
                }
                else {
                    // add a copy of the item to the new set
                    tail.next = new ListItem<>(new Tuple<>(index.key, new ListItem<>(current.key)));
                    current = current.next;
                }
                tail = tail.next;
            }
            // move along in the sets parameter and the indexSet parameter
            set = set.next;
            index = index.next;

        }

        return new MySet<>(head.next);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");

        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            builder.append(p.toString());
            if(p.next != null) {
                builder.append(", ");
            }
        }

        builder.append("}");
        return builder.toString();
    }

}
