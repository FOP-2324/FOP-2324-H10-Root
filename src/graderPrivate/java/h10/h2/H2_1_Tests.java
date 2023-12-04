package h10.h2;

import h10.ListItem;
import h10.MySetAsCopy;
import h10.VisitorMySet;

public class H2_1_Tests extends H2_Tests {

    @Override
    protected Class<?> getClassType() {
        return MySetAsCopy.class;
    }

    @Override
    public VisitorMySet<Integer> create(ListItem<Integer> head) {
        return new VisitorMySet<>(head, DEFAULT_COMPARATOR, MySetAsCopy::new);
    }
}
