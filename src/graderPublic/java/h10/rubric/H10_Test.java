package h10.rubric;

import com.fasterxml.jackson.databind.JsonNode;
import h10.ListItem;
import h10.MySet;
import h10.MySetAsCopy;
import h10.MySetInPlace;
import h10.json.JsonConverters;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Defines a base class for testing a method for the H10 assignment.
 *
 * @author Nhan Huynh
 * @see MySet
 * @see MySetAsCopy
 * @see MySetInPlace
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class H10_Test {

    /**
     * The name of the field containing the custom converters for this test class (JSON Converters).
     */
    public static final String CUSTOM_CONVERTERS = "CONVERTERS";

    /**
     * The custom converters for this test class (JSON Converters).
     *
     * @see JsonParameterSet
     */
    @SuppressWarnings("unused")
    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = new HashMap<>(
        Map.of(
            "source", JsonConverters::toListItemInteger,
            "predicate", JsonConverters::toPredicateInteger,
            "expected", JsonConverters::toListItemInteger,
            "other", JsonConverters::toListItemInteger,
            "expected2D", JsonConverters::toListItemListItemInteger
        )
    );

    /**
     * The type of the class to be tested.
     */
    private @Nullable TypeLink type;

    /**
     * The method to be tested.
     */
    private @Nullable MethodLink method;

    /**
     * Returns a comparator that compares elements using their natural order. This comparator provides a better error
     * message than {@link Comparator#naturalOrder()} since we override the {@link Object#toString()} method.
     *
     * @param <T> the type of the elements to compare
     * @return a comparator that compares elements using their natural order
     */
    protected static <T extends Comparable<? super T>> Comparator<T> getDefaultComparator() {
        return new Comparator<>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }

            @Override
            public String toString() {
                return "o1 <= o2";
            }
        };
    }

    /**
     * Initializes needed information for the testing before all tests.
     */
    @BeforeAll
    public void globalSetup() {
        type = Links.getType(getClassType());
        method = Links.getMethod(type, getMethodName());
    }

    /**
     * Returns the type of the class to be tested.
     *
     * @return the type of the class to be tested
     */
    public abstract Class<?> getClassType();

    /**
     * Returns the name of the method to be tested.
     *
     * @return the name of the method to be tested
     */
    public abstract String getMethodName();

    /**
     * Returns the type of the class to be tested.
     *
     * @return the type of the class to be tested
     */
    public TypeLink getType() {
        if (type == null) {
            throw new IllegalStateException("Type not initialized");
        }
        return type;
    }

    /**
     * Returns the method to be tested.
     *
     * @return the method to be tested
     */
    public MethodLink getMethod() {
        if (method == null) {
            throw new IllegalStateException("Method not initialized");
        }
        return method;
    }

    /**
     * Returns a {@link Context.Builder} with the method to be tested as the subject.
     *
     * @return a {@link Context.Builder} with the method to be tested as the subject
     */
    protected Context.Builder<?> contextBuilder() {
        return Assertions2.contextBuilder().subject(getMethod());
    }

    /**
     * Returns a function that creates a set from the given head and comparator.
     *
     * @param <T> the type of the elements in the set
     * @return a function that creates a set from the given head and comparator
     */
    protected abstract <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> setProvider();

    /**
     * Creates a set from the given head and comparator.
     *
     * @param head the head of the list to create a set from
     * @param <T>  the type of the elements in the set
     * @return a set from the given head and comparator
     */
    public <T extends Comparable<? super T>> MySet<T> createSet(ListItem<T> head) {
        return this.<T>setProvider().apply(head, getDefaultComparator());
    }
}
