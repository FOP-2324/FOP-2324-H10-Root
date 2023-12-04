package h10.h2;

import h10.ListItem;
import h10.MySetInPlace;
import h10.VisitorMySet;

public class H2_2_Tests extends H2_Tests {

    @Override
    protected Class<?> getClassType() {
        return MySetInPlace.class;
    }

    @Override
    public VisitorMySet<Integer> create(ListItem<Integer> head) {
        return new VisitorMySet<>(head, DEFAULT_COMPARATOR, MySetInPlace::new);
    }
}
