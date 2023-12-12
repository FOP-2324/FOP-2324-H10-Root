package h10.rubric.h1;

import h10.ListItem;
import h10.MySet;
import h10.MySetAsCopy;
import h10.utils.TutorAssertions;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Comparator;
import java.util.function.BiFunction;

@TestForSubmission
@DisplayName("H1.1 | As-Copy")
public class H1_1_Tests extends H1_Tests {

    @Override
    public Class<?> getClassType() {
        return MySetAsCopy.class;
    }

    @Override
    protected BiFunction<ListItem<VisitorElement<Integer>>, Comparator<? super VisitorElement<Integer>>, MySet<VisitorElement<Integer>>> mapper() {
        return MySetAsCopy::new;
    }

    @Override
    public void assertRequirement() {
        Assumptions.assumeTrue(source != null);
        Assumptions.assumeTrue(result != null);
        Assumptions.assumeTrue(context != null);
        TutorAssertions.assertAsCopy(source, result, context);
    }
}
