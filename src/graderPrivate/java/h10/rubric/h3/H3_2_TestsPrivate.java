package h10.rubric.h3;

import h10.ListItem;
import h10.MySet;
import h10.MySetInPlace;
import h10.rubric.TestConstants;
import h10.rubric.TutorAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.Comparator;
import java.util.function.BiFunction;

/**
 * Defines public test cases for the H3.1 assignment.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H3.2 | In-Place")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H3_2_TestsPrivate extends H3_TestsPrivate {

    @Override
    public Class<?> getClassType() {
        return MySetInPlace.class;
    }

    @Override
    protected <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> setProvider() {
        return MySetInPlace::new;
    }

    @Override
    protected <T extends Comparable<T>> QuadConsumer<MySet<T>, MySet<T>[], MySet<T>, Context.Builder<?>> requirementCheck() {
        return TutorAssertions::assertInPlace;
    }

    @Order(2)
    @DisplayName("Die Methode difference(MySet) nimmt die verbleibenen Elemente von M auf, falls die Menge M mehr "
        + "Elemente enthält als die Menge N.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H3_Criterion_03.json", customConverters = CUSTOM_CONVERTERS)
    public void testElementsLeft(JsonParameterSet parameters) {
        super.testElementsLeft(parameters);
    }

    @Order(3)
    @DisplayName("Die Methode difference(MySet) gibt das korrekte Ergebnis für einfache Eingaben zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H3_Criterion_04.json", customConverters = CUSTOM_CONVERTERS)
    public void testSimple(JsonParameterSet parameters) {
        super.testSimple(parameters);
    }

    @Order(4)
    @DisplayName("Die Methode difference(MySet) gibt das korrekte Ergebnis für komplexe Eingaben zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H3_Criterion_05.json", customConverters = CUSTOM_CONVERTERS)
    public void testComplex(JsonParameterSet parameters) {
        super.testComplex(parameters);
    }
}
