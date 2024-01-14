package h10.rubric.h4;

import h10.ListItem;
import h10.MySet;
import h10.MySetInPlace;
import h10.TutorMySetInPlace;
import h10.rubric.TestConstants;
import h10.rubric.TutorAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Defines private test cases for the H4.2 assignment.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H4.2 | In-Place")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(
    value = TestConstants.TEST_TIMEOUT_IN_SECONDS,
    unit = TimeUnit.SECONDS,
    threadMode = Timeout.ThreadMode.SEPARATE_THREAD
)
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H4_2_TestsPrivate extends H4_TestsPrivate {

    @Override
    public Class<?> getClassType() {
        return MySetInPlace.class;
    }

    @Override
    protected <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> setProvider() {
        return MySetInPlace::new;
    }

    @Override
    protected <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> fallbackProvider() {
        return TutorMySetInPlace::new;
    }

    @Override
    protected <T extends Comparable<T>> QuadConsumer<MySet<T>, MySet<T>[], MySet<T>, Context.Builder<?>> requirementCheck() {
        return TutorAssertions::assertInPlace;
    }

    @Order(3)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis für Mengen mit "
        + "unterschiedlicher Länge zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_04.json", customConverters = CUSTOM_CONVERTERS)
    @Override
    public void testDifferentSize(JsonParameterSet parameters) {
        super.testDifferentSize(parameters);
    }

    @Order(4)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis für einfache Eingaben zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_05.json", customConverters = CUSTOM_CONVERTERS)
    @Override
    public void testSimple(JsonParameterSet parameters) {
        super.testSimple(parameters);
    }

    @Order(5)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis für komplexe Eingaben zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_06.json", customConverters = CUSTOM_CONVERTERS)
    @Override
    public void testComplex(JsonParameterSet parameters) {
        super.testComplex(parameters);
    }
}
