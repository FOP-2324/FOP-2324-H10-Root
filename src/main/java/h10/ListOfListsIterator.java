package h10;

import java.util.Iterator;

public class ListOfListsIterator<T> implements Iterator<T> {


    private ListItem<T> current;
    private ListItem<ListItem<T>> currentLstOfLst;

    /**
     *
     * @param listOfLists
     */
    public ListOfListsIterator(ListItem<ListItem<T>> listOfLists) {
        this.currentLstOfLst = listOfLists;
        this.current = listOfLists.key;
    }

    @Override
    public boolean hasNext() {
        if(this.current.next != null) {
            return true;
        }
        else if(this.currentLstOfLst.next != null) {
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        T tmp = this.current.key;
        if(this.current.next != null) {
            this.current = this.current.next;
        }
        else {
            if(this.currentLstOfLst.next != null) {
                this.currentLstOfLst = this.currentLstOfLst.next;
                this.current = this.currentLstOfLst.key;
            }
        }
        return tmp;
    }
}
