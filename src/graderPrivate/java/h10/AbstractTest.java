package h10;

import h10.utils.Links;
import h10.visitor.VisitorElement;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.Comparator;
import java.util.function.BiFunction;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTest {

    public static final Comparator<Integer> DEFAULT_COMPARATOR = new Comparator<>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }

        @Override
        public String toString() {
            return "o1 <= o2";
        }
    };


    private @Nullable TypeLink type;

    private @Nullable MethodLink method;

    @BeforeAll
    public void globalSetup() {
        type = Links.getTypeLink(getClassType());
        method = Links.getMethodLink(type, getMethodName());
    }

    public abstract Class<?> getClassType();

    public abstract String getMethodName();

    public TypeLink getType() {
        if (type == null) {
            throw new IllegalStateException("Type not initialized");
        }
        return type;
    }

    public MethodLink getMethod() {
        if (method == null) {
            throw new IllegalStateException("Method not initialized");
        }
        return method;
    }

    protected abstract BiFunction<ListItem<VisitorElement<Integer>>, Comparator<? super VisitorElement<Integer>>, MySet<VisitorElement<Integer>>> mapper();

    public VisitorSet<Integer> create(ListItem<Integer> head) {
        return VisitorSet.of(head, DEFAULT_COMPARATOR, mapper());
    }

    public VisitorSet<Integer> create(MySet<VisitorElement<Integer>> set) {
        return VisitorSet.of(set, mapper());
    }

    public Context.Builder<?> defaultBuilder() {
        return Assertions2.contextBuilder().subject(getMethod());
    }
}
