package h10.rubric.h4;

import h10.MySetAsCopy;
import h10.MySetInPlace;
import h10.rubric.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.concurrent.TimeUnit;

/**
 * Defines a base class for testing a method for the H4 assignment (private tests). A subclass of this class needs
 * to implement at least {@link #getClassType()} and {@link #setProvider()} since the criteria for {@link MySetAsCopy}
 * and {@link MySetInPlace} are the same.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H4 | intersectionListItems(ListItem)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(
    value = TestConstants.TEST_TIMEOUT_IN_SECONDS,
    unit = TimeUnit.SECONDS,
    threadMode = Timeout.ThreadMode.SEPARATE_THREAD
)
public abstract class H4_TestsPrivate extends H4_Tests {

    @Order(3)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis für Mengen mit "
        + "unterschiedlicher Länge zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_04.json", customConverters = CUSTOM_CONVERTERS)
    public void testDifferentSize(JsonParameterSet parameters) {
        assertEqualSet(parameters);
    }

    @Order(4)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis für einfache Eingaben zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_05.json", customConverters = CUSTOM_CONVERTERS)
    public void testSimple(JsonParameterSet parameters) {
        assertEqualSet(parameters);
    }

    @Order(5)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis für komplexe Eingaben zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_06.json", customConverters = CUSTOM_CONVERTERS)
    public void testComplex(JsonParameterSet parameters) {
        assertEqualSet(parameters);
    }
}
