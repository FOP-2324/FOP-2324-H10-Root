package h10.h3;

import h10.ListItem;
import h10.MySetAsCopy;
import h10.VisitorMySet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;


@TestForSubmission
@DisplayName("H3.1 | As-Copy")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class H3_1_Tests extends H3_Tests {

    @Override
    public Class<?> getClassType() {
        return MySetAsCopy.class;
    }

    @Override
    public VisitorMySet<Integer> create(ListItem<Integer> head) {
        return new VisitorMySet<>(head, DEFAULT_COMPARATOR, MySetAsCopy::new);
    }
}
