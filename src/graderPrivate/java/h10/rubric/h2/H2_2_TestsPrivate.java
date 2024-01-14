package h10.rubric.h2;

import h10.ListItem;
import h10.MySet;
import h10.MySetInPlace;
import h10.rubric.TestConstants;
import h10.util.ListItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Defines public test cases for the H2.2 assignment.
 *
 * @author Nhan Huynh
 */
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
    protected <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> setProvider() {
        return MySetInPlace::new;
    }

    @Order(1)
    @DisplayName("Die Methode cartesianProduct(MySet) gibt das korrekte Ergebnis für komplexe Eingaben zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H2_Criteria_02.json", customConverters = CUSTOM_CONVERTERS)
    @Override
    public void testComplex(JsonParameterSet parameters) {
        super.testComplex(parameters);
    }

    @Order(2)
    @DisplayName("Die Methode cartesianProduct(MySet) entkoppelt die Elemente korrekt.")
    @Test
    public void testDecouple() {
        Context.Builder<?> builder = contextBuilder();
        ListItem<Integer> sourceHead = ListItems.of(1, 2);
        List<ListItem<Integer>> sourcePointers = ListItems.itemStream(sourceHead).toList();
        MySet<Integer> source = createSet(sourceHead);
        ListItem<Integer> otherHead = ListItems.of(3, 4);
        List<ListItem<Integer>> otherPointers = ListItems.itemStream(otherHead).toList();
        MySet<Integer> other = createSet(otherHead);
        builder.add("Input", getInputContext(getDefaultComparator(), source, other));

        MySet<ListItem<Integer>> result = source.cartesianProduct(other);
        Context resultContext = Assertions2.contextBuilder()
            .add("Source pointers afterwards", sourcePointers)
            .add("Other pointers afterwards", otherPointers)
            .add("Actual output set", result.toString())
            .build();
        builder.add("Output", resultContext);

        // Decouple check
        Context context = builder.build();
        sourcePointers.stream().forEach(current -> Assertions2.assertNull(
            current.next,
            context,
            r -> "Source node %s was not decoupled".formatted(current)));
        otherPointers.stream().forEach(current -> Assertions2.assertNull(
            current.next,
            context,
            r -> "Other node %s was not decoupled".formatted(current)));
    }
}
