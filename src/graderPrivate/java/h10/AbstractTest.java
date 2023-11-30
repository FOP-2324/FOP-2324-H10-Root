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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTest {

    protected final Comparator<VisitorElement<Integer>> cmp = new VisitorComparator<>(Comparator.naturalOrder());

    protected TypeLink type;

    protected MethodLink method;

    @BeforeAll
    public abstract void globalSetup();

    public Context.Builder<?> defaultBuilder() {
        return Assertions2.contextBuilder().subject(method);
    }

    public String toString(ListItem<?> item) {
        return item.getClass().getName() + "@" + System.identityHashCode(item);
    }
}
