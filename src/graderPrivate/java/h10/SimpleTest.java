package h10;

import h10.utils.Links;
import h10.utils.visitor.VisitorElement;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

/**
 * Defines a base skeleton for simple tests.
 *
 * @author Nhan Huynh
 */
public abstract class SimpleTest extends AbstractTest {


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
        method = Links.getMethodLink(type, getMethodName());
    }

    /**
     * Returns the type of the set to test.
     *
     * @return the type of the set to test.
     */
    public abstract TypeLink getType();

    public abstract String getMethodName();

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
}
