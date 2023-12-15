package h10.rubric.h04;

import h10.ListItem;
import h10.MySet;
import h10.VisitorSet;
import h10.converter.ListItemConverter;
import h10.rubric.AbstractComplexTest;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.tudalgo.algoutils.tutor.general.conversion.ArrayConverter;

import java.util.function.BiFunction;

public abstract class H4_Tests extends AbstractComplexTest {
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

    @Order(1)
    @DisplayName("Die Methode difference(MySet) nimmt ein Element nicht auf, falls ein Element sowohl in M als auch in N ist.")
    @ParameterizedTest(name = "source = {0}, other = {1}, expected = {2}")
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

    @Order(2)
    @DisplayName("Die Methode difference(MySet) setzt die Laufvariable von der Menge M korrekt auf das nächste "
        + "Element, falls x < y gilt für x in M und y in N.")
    //   @ParameterizedTest(name = "sourceHead={0}, otherHead={1}, sourceVisitation={2}, otherVisitation={3}")
    @JsonClasspathSource({

    })
    public void testXSmallerY(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ArrayConverter.Auto.class) @Property("sourceVisitation") Integer[] sourceVisitation,
        @ConvertWith(ArrayConverter.Auto.class) @Property("otherVisitation") Integer[] otherVisitation
    ) {
        super.testXSmallerY(sourceHead, otherHead, sourceVisitation, otherVisitation);
    }

    @Order(3)
    @DisplayName("Die Methode difference(MySet) setzt die Laufvariable von der Menge M korrekt auf das nächste "
        + "Element, falls x > y gilt für x in M und y in N.")
    //@ParameterizedTest(name = "sourceHead={0}, otherHead={1}, sourceVisitation={2}, otherVisitation={3}")
    @JsonClasspathSource({

    })
    public void testXGreaterY(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ArrayConverter.Auto.class) @Property("sourceVisitation") Integer[] sourceVisitation,
        @ConvertWith(ArrayConverter.Auto.class) @Property("otherVisitation") Integer[] otherVisitation
    ) {
        super.testXGreaterY(sourceHead, otherHead, sourceVisitation, otherVisitation);
    }
}
