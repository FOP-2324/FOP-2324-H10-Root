package h10.h3;

import h10.AbstractTest;
import h10.ListItem;
import h10.VisitorMySet;
import h10.utils.ListItemConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

public abstract class H3_Tests extends AbstractTest {
    protected static final String TEST_RESOURCE_PATH = "h3/";

    protected static final String METHOD_NAME = "difference";

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    @Order(1)
    @DisplayName("Die Methode difference(MySet) nimmt ein Element nicht auf, falls ein Element sowohl in M als auch in N ist.")
    @ParameterizedTest(name = "Source = {0}, Other {1}, Expected = {2}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion1_testcase1.json",
        //   TEST_RESOURCE_PATH + "criterion1_testcase2.json",
        //  TEST_RESOURCE_PATH + "criterion1_testcase3.json",
    })
    public void testNotAddElements(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("expected") ListItem<Integer> expectedHead
    ) {
        VisitorMySet<Integer> source = create(sourceHead);
        VisitorMySet<Integer> other = create(otherHead);
        VisitorMySet<Integer> expected = create(expectedHead);
        Context.Builder<?> builder = defaultBuilder().add("Source", source.toString())
            .add("Other", other.toString())
            .add("Expected", expected.toString());
        VisitorMySet<Integer> result = new VisitorMySet<>(source.difference(other));
        builder.add("Result", result.toString());
        Assertions2.assertEquals(expected, result, builder.build(),
            r -> "Expected set %s, but given %s".formatted(expected, result));
    }
}
