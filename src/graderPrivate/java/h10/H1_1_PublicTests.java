package h10;

import h10.utils.Links;
import h10.utils.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

/**
 * Defines the public tests for H1.1.
 *
 * @author Nhan Huynh
 */
@DisplayName("H1.1 | AsCopy")
public class H1_1_PublicTests extends H1_PublicTests {

    @Override
    public MySet<VisitorElement<Integer>> create(ListItem<VisitorElement<Integer>> head) {
        return new MySetAsCopy<>(head, cmp);
    }

    @Override
    public TypeLink getType() {
        return Links.getTypeLink(MySetAsCopy.class);
    }

    @Override
    public void checkRequirements() {
        assert validateInput != null;
        assert validateOutput != null;
        assert validateContext != null;
        AssertionUtils.assertAsCopy(validateInput, validateOutput, validateContext);
    }
}
