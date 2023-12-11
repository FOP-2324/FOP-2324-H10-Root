package h10.h1;

import h10.ListItem;
import h10.MySet;
import h10.MySetInPlace;
import h10.utils.TutorAssertions;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Comparator;
import java.util.function.BiFunction;

@TestForSubmission
@DisplayName("H1.2 | In-Place")
public class H1_2_Tests extends H1_Tests {

    @Override
    public Class<?> getClassType() {
        return MySetInPlace.class;
    }

    @Override
    protected BiFunction<ListItem<VisitorElement<Integer>>, Comparator<? super VisitorElement<Integer>>, MySet<VisitorElement<Integer>>> mapper() {
        return MySetInPlace::new;
    }

    @Override
    public void assertRequirement() {
        Assumptions.assumeTrue(source != null);
        Assumptions.assumeTrue(result != null);
        Assumptions.assumeTrue(context != null);
        TutorAssertions.assertInPlace(source, result, context);
    }
}
