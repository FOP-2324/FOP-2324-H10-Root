package h10.rubric.h04;

import h10.ListItem;
import h10.MySet;
import h10.MySetAsCopy;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Comparator;
import java.util.function.BiFunction;


@TestForSubmission
@DisplayName("H4.1 | As-Copy")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class H4_1_Tests extends H4_Tests {

    @Override
    public Class<?> getClassType() {
        return MySetAsCopy.class;
    }

    @Override
    protected BiFunction<ListItem<VisitorElement<Integer>>, Comparator<? super VisitorElement<Integer>>, MySet<VisitorElement<Integer>>> converter() {
        return MySetAsCopy::new;
    }


}
