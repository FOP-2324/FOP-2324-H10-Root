package h10.h1;

import h10.ListItem;
import h10.MySetAsCopy;
import h10.TutorAssertions;
import h10.VisitorMySet;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("H1.1 | As-Copy")
public class H1_1_Tests extends H1_Tests {

    @Override
    protected Class<?> getClassType() {
        return MySetAsCopy.class;
    }

    @Override
    public VisitorMySet<Integer> create(ListItem<Integer> head) {
        return new VisitorMySet<>(head, DEFAULT_COMPARATOR, MySetAsCopy::new);
    }

    @Override
    public void assertRequirement() {
        Assumptions.assumeTrue(source != null);
        Assumptions.assumeTrue(result != null);
        Assumptions.assumeTrue(context != null);
        TutorAssertions.assertAsCopy(source, result, context);
    }
}
