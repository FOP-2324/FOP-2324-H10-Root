package h10;

import h10.utils.Links;
import h10.utils.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

/**
 * Defines the public tests for H1.2.
 *
 * @author Nhan Huynh
 */
@DisplayName("H1.2 | InPlace")
public class H1_2_PublicTests extends H1_PublicTests {

    @Override
    public MySet<VisitorElement<Integer>> create(ListItem<VisitorElement<Integer>> head) {
        return new MySetInPlace<>(head, cmp);
    }

    @Override
    public TypeLink getType() {
        return Links.getTypeLink(MySetInPlace.class);
    }

    @Override
    public void checkRequirements() {
        assert validateInput != null;
        assert validateOutput != null;
        assert validateContext != null;
        int index = 0;
        for (ListItem<VisitorElement<Integer>> other = validateOutput.head; other != null; other = other.next, index++) {
            boolean found = false;
            for (ListItem<VisitorElement<Integer>> current = validateInput.head; current != null; current = current.next) {
                String currentHash = toString(current);
                String otherHash = toString(other);
                if (currentHash.equals(otherHash)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String hash = toString(other);
                int currentIndex = index;
                Assertions2.fail(validateContext.build(), r -> "Node %s at %s was copied".formatted(hash, currentIndex));
            }
        }
    }
}
