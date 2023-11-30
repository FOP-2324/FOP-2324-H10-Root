package h10;

import h10.utils.Links;
import h10.utils.visitor.VisitorElement;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

/**
 * Defines a base skeleton for simple tests.
 *
 * @author Nhan Huynh
 */
public abstract class SimpleTest extends AbstractTest {

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
        checkVisitCount();
        checkRequirements();
    }

    /**
     * Checks the visit count.
     */
    public abstract void checkVisitCount();

    /**
     * Checks the requirements.
     */
    public abstract void checkRequirements();
}
