package h10.rubric.h4;

import h10.ListItem;
import h10.MySet;
import h10.VisitorSet;
import h10.converter.ListItemConverter;
import h10.rubric.ComplexTestPrivate;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;

import java.util.function.BiFunction;

public abstract class H4_TestsPrivate extends ComplexTestPrivate {
    protected static final String TEST_RESOURCE_PATH = "h4/";

    protected static final String METHOD_NAME = "intersectionListItems";

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    @Override
    protected BiFunction<VisitorSet<Integer>, VisitorSet<Integer>, MySet<VisitorElement<Integer>>> operation() {
        return MySet::intersection;
    }

    @Order(3)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis für eine leere Menge zurück.")
    @ParameterizedTest(name = "Source = {0}, Other = {1}}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion4_testcase1.json",
        TEST_RESOURCE_PATH + "criterion4_testcase2.json",
    })
    public void testEmpty(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("expected") ListItem<Integer> expectedHead
    ) {
        assertEqualElements(sourceHead, otherHead, expectedHead);
    }

    @Order(4)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis für Mengen mit "
        + "unterschiedlicher Länge zurück.")
    @ParameterizedTest(name = "Source = {0}, Other = {1}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion5_testcase1.json",
        TEST_RESOURCE_PATH + "criterion5_testcase2.json",
    })
    public void testDifferentSize(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("expected") ListItem<Integer> expectedHead
    ) {
        assertEqualElements(sourceHead, otherHead, expectedHead);
    }

    @Order(5)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis für komplexe Eingaben "
        + "zurück.")
    @ParameterizedTest(name = "Source = {0}, Other = {1}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion6_testcase1.json",
        TEST_RESOURCE_PATH + "criterion6_testcase2.json",
        TEST_RESOURCE_PATH + "criterion6_testcase3.json",
        TEST_RESOURCE_PATH + "criterion6_testcase4.json",
        TEST_RESOURCE_PATH + "criterion6_testcase5.json",
        TEST_RESOURCE_PATH + "criterion6_testcase6.json",
    })
    public void testComplex(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("expected") ListItem<Integer> expectedHead
    ) {
        assertEqualElements(sourceHead, otherHead, expectedHead);
    }
}
