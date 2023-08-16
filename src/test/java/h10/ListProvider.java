package h10;

public class ListProvider {

    /**
     *
     * @param elements
     * @return
     * @param <T>
     */
    public static <T> ListItem<T> provideList(T... elements)  {
        ListItem<T> head = null;
        ListItem<T> tail = null;
        for(var elem : elements) {
            if(head == null) {
                head = tail = new ListItem<T>();
            }
            else {
                tail.next = new ListItem<>();
                tail = tail.next;
            }
            tail.key = elem;
        }
        return head;
    }

    /**
     *
     * @param lst
     * @return
     */
    public static String toString(ListItem<?> lst) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        while(lst != null) {
            builder.append(lst.key instanceof ListItem<?> item ? toString(item) : lst.key);
            if(lst.next != null) {
                builder.append(" ");
            }
            lst = lst.next;
        }
        builder.append(")");
        return builder.toString();
    }
}
