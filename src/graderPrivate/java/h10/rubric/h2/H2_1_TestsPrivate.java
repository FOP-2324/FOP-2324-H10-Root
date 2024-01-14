package h10.rubric.h2;

import h10.ListItem;
import h10.MySet;
import h10.MySetAsCopy;
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
import org.tudalgo.algoutils.tutor.general.match.Matcher;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Defines public test cases for the H2.1 assignment.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H2.1 | As-Copy")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(
    value = TestConstants.TEST_TIMEOUT_IN_SECONDS,
    unit = TimeUnit.SECONDS,
    threadMode = Timeout.ThreadMode.SEPARATE_THREAD
)
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H2_1_TestsPrivate extends H2_TestsPrivate {
    @Override
    public Class<?> getClassType() {
        return MySet.class;
    }

    @Override
    protected <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> setProvider() {
        return MySetAsCopy::new;
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
    @DisplayName("Die Methode cartesianProduct(MySet) definiert den Comparator für ein Tupel korrekt.")
    @Test
    public void testComparator() {
        Context.Builder<?> contextBuilder = contextBuilder();
        MySet<Integer> source = createSet(ListItems.of(1, 2));
        MySet<Integer> other = createSet(ListItems.of(3, 4));
        contextBuilder.add("Input", getInputContext(getDefaultComparator(), source, other));

        MySet<ListItem<Integer>> result = source.cartesianProduct(other);
        Comparator<ListItem<Integer>> cmp = getType().getField(Matcher.of(field -> field.name().equals("cmp"))).get(result);
        Context resultContext = Assertions2.contextBuilder()
            .add("Output", result.toString())
            .add("Comparator", cmp)
            .build();
        contextBuilder.add("Output", resultContext);

        ListItem<Integer> data1 = ListItems.of(1, 5);
        ListItem<Integer> data2 = ListItems.of(1, 6);
        ListItem<Integer> data3 = ListItems.of(1, 4);
        ListItem<Integer> data4 = ListItems.of(2, 5);
        List<ListItem<Integer>> data = List.of(data1, data2, data3, data4);

        Context.Builder<?> testDataBuilder = Assertions2.contextBuilder();
        int i = 1;
        for (ListItem<Integer> d : data) {
            testDataBuilder.add("Data " + i++, d);
        }
        Context context = testDataBuilder.build();
        Assertions2.assertEquals(-1, cmp.compare(data1, data2), context,
            r -> "Data 1 %s should be < Data 2 %s".formatted(data.get(0), data.get(1)));
        Assertions2.assertEquals(1, cmp.compare(data1, data3), context,
            r -> "Data 1 %s should be > Data 3 %s".formatted(data.get(0), data.get(2)));
        Assertions2.assertEquals(-1, cmp.compare(data1, data4), context,
            r -> "Data 1 %s should be < Data 4 %s".formatted(data.get(0), data.get(3)));
        Assertions2.assertEquals(1, cmp.compare(data4, data1), context,
            r -> "Data 4 %s should be > Data 1 %s".formatted(data.get(3), data.get(0)));
        Assertions2.assertEquals(0, cmp.compare(data1, data1), context,
            r -> "Data 1 %s should be = Data 1 %s".formatted(data.get(0), data.get(0)));
    }
}
