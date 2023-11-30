package h10;

import h10.utils.Links;
import h10.utils.RubricOrder;
import h10.utils.converter.ListItemConverter;
import h10.utils.visitor.VisitorElement;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

/**
 * Defines the public tests for H1.
 */
public abstract class H2_PrivateTests extends AbstractTest {

    /**
     * The path to the test resources.
     */
    private static final String TEST_RESOURCE_PATH = "h2/";

    /**
     * The name of the method to test.
     */
    private static final String METHOD_NAME = "cartesianProduct";

    /**
     * The input to validate where we check if the requirements are met.
     */
    protected @Nullable MySet<VisitorElement<Integer>> validateInput;

    /**
     * The output to validate where we check if the requirements are met.
     */
    protected @Nullable MySet<VisitorElement<Integer>> validateOutput;

    /**
     * The context to validate where we check if the requirements are met.
     */
    protected @Nullable Context.Builder<?> validateContext;

    @BeforeAll
    @Override
    public void globalSetup() {
        type = getType();
        method = Links.getMethodLink(type, METHOD_NAME);
    }

    /**
     * Returns the type of the set to test.
     *
     * @return the type of the set to test.
     */
    public abstract TypeLink getType();

    /**
     * Creates a new set from the given head.
     *
     * @param head the head of the list.
     * @return a new set from the given head.
     */
    public abstract MySet<VisitorElement<Integer>> create(ListItem<VisitorElement<Integer>> head);

    /**
     * Checks the requirements after each test.
     */
    @AfterEach
    public void tearDown() {
        Assumptions.assumeTrue(validateInput != null);
        Assumptions.assumeTrue(validateContext != null);
        Assumptions.assumeTrue(validateOutput != null);
        // Check visit count
        for (ListItem<VisitorElement<Integer>> current = validateInput.head; current != null; current = current.next) {
            Assertions2.assertEquals(
                1, current.key.getVisited(),
                validateContext.add("Node was visited more than once", current.key).build(),
                r -> "Node was visited more than once"
            );
        }
        checkRequirements();
    }

    /**
     * Checks the requirements.
     */
    public abstract void checkRequirements();

    @RubricOrder(types = {MySetAsCopy.class, MySetInPlace.class}, orders = {1, 3})
    @DisplayName("Die Methode cartesianProduct(MySet) gibt das korrekte Ergebnis für simple Eingaben zurück.")
    @ParameterizedTest(name = "Source = {0}, Other {1}")
    @JsonClasspathSource({
    })
    public void testSimple(
        @ConvertWith(ListItemConverter.VisitorNode.class) @Property("head") ListItem<VisitorElement<Integer>> head,
        @ConvertWith(ListItemConverter.VisitorNode.class) @Property("otherHead") ListItem<VisitorElement<Integer>> otherHead
    ) {
        MySet<VisitorElement<Integer>> source = create(head);
        MySet<VisitorElement<Integer>> other = create(otherHead);
    }

    @RubricOrder(types = {MySetAsCopy.class, MySetInPlace.class}, orders = {1, 3})
    @DisplayName("Die Methode cartesianProduct(MySet) gibt das korrekte Ergebnis für komplexe Eingaben zurück.")
    @ParameterizedTest(name = "Source = {0}, Other {1}")
    @JsonClasspathSource({
    })
    public void testComplex(
        @ConvertWith(ListItemConverter.VisitorNode.class) @Property("head") ListItem<VisitorElement<Integer>> head,
        @ConvertWith(ListItemConverter.VisitorNode.class) @Property("otherHead") ListItem<VisitorElement<Integer>> otherHead
    ) {
        MySet<VisitorElement<Integer>> source = create(head);
        MySet<VisitorElement<Integer>> other = create(otherHead);
    }
}
