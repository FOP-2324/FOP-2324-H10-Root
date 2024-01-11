package h10;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Comparator;

@TestForSubmission
public class TransformerTest {

    @Test
    void test() {
        ListItem<Integer> head = new ListItem<>(1);
        head.next = new ListItem<>(5);
        head.next.next = new ListItem<>(2);
        Comparator<Integer> cmp = Comparator.naturalOrder();
        Assertions.assertDoesNotThrow(() -> new MySetInPlace<>(head, cmp));
    }
}
