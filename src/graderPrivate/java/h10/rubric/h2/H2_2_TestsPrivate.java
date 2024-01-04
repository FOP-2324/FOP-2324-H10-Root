package h10.rubric.h2;

import h10.ListItem;
import h10.MySet;
import h10.MySetInPlace;
import h10.VisitorSet;
import h10.converter.ListItemConverter;
import h10.converter.ListItemInListItemConverter;
import h10.utils.ListItems;
import h10.utils.TestConstants;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

@TestForSubmission
@DisplayName("H2.2 | In-Place")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(
    value = TestConstants.TEST_TIMEOUT_IN_SECONDS,
    unit = TimeUnit.SECONDS,
    threadMode = Timeout.ThreadMode.SEPARATE_THREAD
)
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H2_2_TestsPrivate extends H2_TestsPrivate {

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

    @Order(1)
    @DisplayName("Die Methode cartesianProduct(MySet) gibt das korrekte Ergebnis für komplexe Eingaben zurück.")
    @ParameterizedTest(name = "Source = {0}, Other {1}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion2_testcase1.json",
        TEST_RESOURCE_PATH + "criterion2_testcase2.json",
        TEST_RESOURCE_PATH + "criterion2_testcase3.json",
        TEST_RESOURCE_PATH + "criterion2_testcase4.json",
    })
    @Override
    public void testComplex(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> head,
        @ConvertWith(ListItemConverter.Int.class) @Property("otherHead") ListItem<Integer> otherHead,
        @ConvertWith(ListItemInListItemConverter.Int.class) @Property("expected") ListItem<ListItem<Integer>> expected
    ) {
        super.testComplex(head, otherHead, expected);
    }

    @Order(2)
    @DisplayName("Die Methode cartesianProduct(MySet) entkoppelt die Elemente korrekt.")
    @Test
    public void testDecouple() {
        VisitorSet<Integer> source = visit(ListItems.of(1, 2));
        VisitorSet<Integer> other = visit(ListItems.of(3, 4));
        Context.Builder<?> builder = contextBuilder()
            .add("Source", source.toString())
            .add("Other", other.toString());
        MySet<ListItem<VisitorElement<Integer>>> result = source.cartesianProduct(other);
        builder.add("Result", result.toString());

        // Decouple check
        source.underlyingStream().forEach(current -> Assertions2.assertNull(
            current.next,
            builder.add("Not decoupled source node", current).build(),
            r -> "Source node %s was not decoupled".formatted(current)));
        other.underlyingStream().forEach(current -> Assertions2.assertNull(
            current.next,
            builder.add("Not decoupled other node", current).build(),
            r -> "Other node %s was not decoupled".formatted(current)));
    }
}