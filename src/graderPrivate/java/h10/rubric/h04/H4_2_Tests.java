package h10.rubric.h04;

import h10.ListItem;
import h10.MySet;
import h10.MySetInPlace;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Comparator;
import java.util.function.BiFunction;

@TestForSubmission
@DisplayName("H4.2 | In-Place")
public class H4_2_Tests extends H4_Tests {

    @Override
    public Class<?> getClassType() {
        return MySetInPlace.class;
    }

    @Override
    protected BiFunction<
        ListItem<VisitorElement<Integer>>,
        Comparator<? super VisitorElement<Integer>>,
        MySet<VisitorElement<Integer>>
        > converter() {
        return MySetInPlace::new;
    }
}
