package h10.rubric.h3;

import h10.MySetAsCopy;
import h10.MySetInPlace;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

/**
 * Defines a base class for testing a method for the H3 assignment (private tests). A subclass of this class needs
 * to implement at least {@link #getClassType()} and {@link #setProvider()} since the criteria for {@link MySetAsCopy}
 * and {@link MySetInPlace} are the same.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H3 | difference(MySet)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class H3_TestsPrivate extends H3_Tests {

    @Order(2)
    @DisplayName("Die Methode difference(MySet) nimmt die verbleibenen Elemente von M auf, falls die Menge M mehr "
        + "Elemente enthält als die Menge N.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H3_Criterion_03.json", customConverters = CUSTOM_CONVERTERS)
    public void testElementsLeft(JsonParameterSet parameters) {
        assertEqualSet(parameters);
    }

    @Order(3)
    @DisplayName("Die Methode difference(MySet) gibt das korrekte Ergebnis für einfache Eingaben zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H3_Criterion_04.json", customConverters = CUSTOM_CONVERTERS)
    public void testSimple(JsonParameterSet parameters) {
        assertEqualSet(parameters);
    }

    @Order(4)
    @DisplayName("Die Methode difference(MySet) gibt das korrekte Ergebnis für komplexe Eingaben zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H3_Criterion_05.json", customConverters = CUSTOM_CONVERTERS)
    public void testComplex(JsonParameterSet parameters) {
        assertEqualSet(parameters);
    }
}
