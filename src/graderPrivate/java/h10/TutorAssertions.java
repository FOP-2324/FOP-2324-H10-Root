package h10;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.List;

public class TutorAssertions {

    private TutorAssertions() {

    }

    public static String identityHashCode(VisitorElement<?> element) {
        return element.peek().getClass() + "@" + System.identityHashCode(element.peek());
    }

    public static String identityHashCode(ListItem<?> item) {
        return item.getClass().getName() + "@" + System.identityHashCode(item);
    }

    public static void assertAsCopy(VisitorMySet<?> input, VisitorMySet<?> output, Context.Builder<?> context) {
        List<String> otherIdentityHashCodes = output.itemStream().map(TutorAssertions::identityHashCode).toList();
        input.itemStream()
            .map(TutorAssertions::identityHashCode)
            .forEach(current -> otherIdentityHashCodes.forEach(other ->
                    Assertions2.assertNotEquals(
                        current, other,
                        context.add("Node before operation", current)
                            .add("Node after operation", other)
                            .build(),
                        r -> "Node %s was not copied, got %s".formatted(current, other)
                    )
                )
            );
    }

    public static void assertInPlace(VisitorMySet<?> input, VisitorMySet<?> output, Context.Builder<?> context) {
        List<String> otherIdentityHashCodes = output.itemStream().map(TutorAssertions::identityHashCode).toList();
        input.stream()
            .map(TutorAssertions::identityHashCode)
            .forEach(current -> {
                    if (otherIdentityHashCodes.stream().anyMatch(current::equals)) {
                        Assertions2.fail(
                            context.add("Node before operation", current)
                                .add("Node after operation", "Not found")
                                .build(),
                            r -> "Cannot find the same node after the operation. Node was probably copied.");
                    }
                }
            );
    }
}
