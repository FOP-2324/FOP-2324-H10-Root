package h10.rubric.h1;

import h10.ListItem;
import h10.MySet;
import h10.MySetInPlace;
import h10.converter.ListItemConverter;
import h10.converter.PredicateConverter;
import h10.utils.TutorAssertions;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Predicate;

@TestForSubmission
@DisplayName("H1.2 | In-Place")
public class H1_2_TestsPublic extends H1_TestsPublic {

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

    @Override
    public void assertRequirement() {
        Assumptions.assumeTrue(source != null);
        Assumptions.assumeTrue(result != null);
        Assumptions.assumeTrue(context != null);
        TutorAssertions.assertInPlace(source, result, context);
    }

    @Order(0)
    @DisplayName("Die Methode subset(MySet) ninmmt Elemente in die Ergebnismenge nicht auf, falls das Prädikat nicht "
        + "erfüllt wird.")
    @ParameterizedTest(name = "Elements = {0}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion1_testcase1.json",
        TEST_RESOURCE_PATH + "criterion1_testcase2.json",
        TEST_RESOURCE_PATH + "criterion1_testcase3.json",
    })
    @Override
    public void testPredicateFalse(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> head
    ) {
        super.testPredicateFalse(head);
    }

    @Order(1)
    @DisplayName("Die Methode subset(MySet) gibt das korrekte Ergebnis für eine komplexe Eingabe zurück.")
    @ParameterizedTest(name = "Elements = {0}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion2_testcase1.json",
        TEST_RESOURCE_PATH + "criterion2_testcase2.json",
        TEST_RESOURCE_PATH + "criterion2_testcase3.json",
    })
    @Override
    public void testPredicateComplex(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> head,
        @ConvertWith(PredicateConverter.BasicIntMath.class) @Property("predicate") Predicate<Integer> predicate,
        @ConvertWith(ListItemConverter.Int.class) @Property("expected") ListItem<Integer> expected
    ) {
        super.testPredicateComplex(head, predicate, expected);
    }
}
