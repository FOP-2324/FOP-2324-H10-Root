package h10;

import h10.utils.visitor.VisitorComparator;
import h10.utils.visitor.VisitorElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.Comparator;

/**
 * A base setup for all tests.
 *
 * @author Nhan Huynh
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTest {

    /**
     * The comparator to use for the comparisons of the elements in the tests.
     */
    protected final Comparator<VisitorElement<Integer>> cmp = new VisitorComparator<>(Comparator.naturalOrder());

    /**
     * The type of the class to test.
     */
    protected TypeLink type;

    /**
     * The method to test.
     */
    protected MethodLink method;

    /**
     * Setups the type and method to test.
     */
    @BeforeAll
    public abstract void globalSetup();

    /**
     * Returns a default context builder with the subject set to the method to test.
     *
     * @return a default context builder with the subject set to the method to test.
     */
    public Context.Builder<?> defaultBuilder() {
        return Assertions2.contextBuilder().subject(method);
    }
}
