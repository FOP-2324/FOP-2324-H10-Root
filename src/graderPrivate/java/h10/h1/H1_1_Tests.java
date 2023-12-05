package h10.h1;

import h10.ListItem;
import h10.MySetAsCopy;
import h10.VisitorMySet;
import h10.utils.TutorAssertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
@DisplayName("H1.1 | As-Copy")
public class H1_1_Tests extends H1_Tests {

    @Override
    public Class<?> getClassType() {
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
