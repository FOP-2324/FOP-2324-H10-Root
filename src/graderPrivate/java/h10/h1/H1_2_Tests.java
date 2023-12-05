package h10.h1;

import h10.ListItem;
import h10.MySetInPlace;
import h10.VisitorMySet;
import h10.utils.TutorAssertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
@DisplayName("H1.2 | In-Place")
public class H1_2_Tests extends H1_Tests {

    @Override
    public Class<?> getClassType() {
        return MySetInPlace.class;
    }

    @Override
    public VisitorMySet<Integer> create(ListItem<Integer> head) {
        return new VisitorMySet<>(head, DEFAULT_COMPARATOR, MySetInPlace::new);
    }

    @Override
    public void assertRequirement() {
        Assumptions.assumeTrue(source != null);
        Assumptions.assumeTrue(result != null);
        Assumptions.assumeTrue(context != null);
        TutorAssertions.assertInPlace(source, result, context);
    }
}
