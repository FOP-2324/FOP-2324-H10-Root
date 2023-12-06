package h10.h3;

import h10.ListItem;
import h10.MySetInPlace;
import h10.VisitorMySet;
import org.junit.jupiter.api.DisplayName;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
@DisplayName("H3.1 | In-Place")
public class H3_2_Tests extends H3_Tests {

    @Override
    public Class<?> getClassType() {
        return MySetInPlace.class;
    }

    @Override
    public VisitorMySet<Integer> create(ListItem<Integer> head) {
        return new VisitorMySet<>(head, DEFAULT_COMPARATOR, MySetInPlace::new);
    }
}
