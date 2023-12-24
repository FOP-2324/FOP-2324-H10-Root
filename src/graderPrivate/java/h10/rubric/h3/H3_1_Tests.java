package h10.rubric.h3;

import h10.ListItem;
import h10.MySet;
import h10.MySetAsCopy;
import h10.converter.ListItemConverter;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.conversion.ArrayConverter;

import java.util.Comparator;
import java.util.function.BiFunction;


@TestForSubmission
@DisplayName("H3.1 | As-Copy")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class H3_1_Tests extends H3_Tests {

    @Override
    public Class<?> getClassType() {
        return MySetAsCopy.class;
    }

    @Override
    protected BiFunction<
        ListItem<VisitorElement<Integer>>,
        Comparator<? super VisitorElement<Integer>>,
        MySet<VisitorElement<Integer>>
        > converter() {
        return MySetAsCopy::new;
    }


    @Order(0)
    @DisplayName("Die Methode difference(MySet) nimmt ein Element nicht auf, falls ein Element sowohl in M als auch "
        + "in N ist.")
    @ParameterizedTest(name = "Source = {0}, Other = {1}, Expected = {2}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion1_testcase1.json",
        TEST_RESOURCE_PATH + "criterion1_testcase2.json",
    })
    public void testNotAddElements(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("expected") ListItem<Integer> expectedHead
    ) {
        super.testNotAddElements(sourceHead, otherHead, expectedHead);
    }

    @Order(1)
    @DisplayName("Die Methode difference(MySet) setzt die Laufvariable von der Menge M korrekt auf das nächste "
        + "Element, falls x < y gilt für x in M und y in N.")
    @ParameterizedTest(name = "Source = {0}, Other = {1}, Source Visitation = {2}, Other Visitation = {3}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion2_testcase1.json",
        TEST_RESOURCE_PATH + "criterion2_testcase2.json",
    })
    public void testXSmallerY(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ArrayConverter.Auto.class) @Property("sourceVisitation") Integer[] sourceVisitation,
        @ConvertWith(ArrayConverter.Auto.class) @Property("otherVisitation") Integer[] otherVisitation
    ) {
        super.testXSmallerY(sourceHead, otherHead, sourceVisitation, otherVisitation);
    }

    @Order(2)
    @DisplayName("Die Methode difference(MySet) setzt die Laufvariable von der Menge M korrekt auf das nächste "
        + "Element, falls x > y gilt für x in M und y in N.")
    @ParameterizedTest(name = "Source = {0}, Other = {1}, Source Visitation = {2}, Other Visitation = {3}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion3_testcase1.json",
        TEST_RESOURCE_PATH + "criterion3_testcase2.json",
    })
    public void testXGreaterY(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ArrayConverter.Auto.class) @Property("sourceVisitation") Integer[] sourceVisitation,
        @ConvertWith(ArrayConverter.Auto.class) @Property("otherVisitation") Integer[] otherVisitation
    ) {
        super.testXGreaterY(sourceHead, otherHead, sourceVisitation, otherVisitation);
    }

    @Order(3)
    @DisplayName("Die Methode difference(MySet) nimmt die verbleibenen Elemente von A auf, falls die Menge M mehr "
        + "Elemente enthält als die Menge N.")
    @ParameterizedTest(name = "Source = {0}, Other = {1}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion4_testcase1.json",
        TEST_RESOURCE_PATH + "criterion4_testcase2.json",
    })
    public void testMGreaterN(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("expected") ListItem<Integer> expectedHead
    ) {
        super.testMGreaterN(sourceHead, otherHead, expectedHead);
    }

    @Order(4)
    @DisplayName("Die Methode difference(MySet) gibt das korrekte Ergebnis für komplexe Eingaben zurück.")
    @ParameterizedTest(name = "Source = {0}, Other = {1}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion5_testcase1.json",
        TEST_RESOURCE_PATH + "criterion5_testcase2.json",
        TEST_RESOURCE_PATH + "criterion5_testcase3.json",
        TEST_RESOURCE_PATH + "criterion5_testcase4.json",
        TEST_RESOURCE_PATH + "criterion5_testcase5.json",
        TEST_RESOURCE_PATH + "criterion5_testcase6.json",
    })
    public void testComplex(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("expected") ListItem<Integer> expectedHead
    ) {
        super.testComplex(sourceHead, otherHead, expectedHead);
    }
}
