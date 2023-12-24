package h10.rubric.h4;

import h10.ListItem;
import h10.MySet;
import h10.MySetInPlace;
import h10.converter.ListItemConverter;
import h10.utils.TestConstants;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

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
    protected BiFunction<
        ListItem<VisitorElement<Integer>>,
        Comparator<? super VisitorElement<Integer>>,
        MySet<VisitorElement<Integer>>
        > converter() {
        return MySetInPlace::new;
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
        super.testEmpty(sourceHead, otherHead, expectedHead);
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
        super.testDifferentSize(sourceHead, otherHead, expectedHead);
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
        super.testComplex(sourceHead, otherHead, expectedHead);
    }
}
