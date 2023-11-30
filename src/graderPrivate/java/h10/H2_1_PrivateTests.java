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
@DisplayName("H2.1 | AsCopy")
public class H2_1_PrivateTests extends H2_PrivateTests {

    @Override
    public MySet<VisitorElement<Integer>> create(ListItem<VisitorElement<Integer>> head) {
        return new MySetAsCopy<>(head, cmp);
    }

    @Override
    public TypeLink getType() {
        return Links.getTypeLink(MySetAsCopy.class);
    }
}
