package h10.utils.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import h10.ListItem;
import h10.utils.visitor.VisitorElement;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

public abstract class ListItemConverter<T> implements ArgumentConverter {
    @Override
    public ListItem<T> convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
        if (!(o instanceof ArrayNode arrayNode)) {
            throw new ArgumentConversionException("Expected ArrayNode, got " + o);
        }
        ListItem<T> head = null;
        ListItem<T> tail = null;
        for (JsonNode element : arrayNode) {
            ListItem<T> item = new ListItem<>(map(element));
            if (head == null) {
                head = item;
            } else {
                tail.next = item;
            }
            tail = item;
        }
        return head;
    }

    protected abstract T map(JsonNode node);

    public static class VisitorNode extends ListItemConverter<VisitorElement<Integer>> {
        @Override
        protected VisitorElement<Integer> map(JsonNode node) {
            if (!(node instanceof IntNode intNode)) {
                throw new ArgumentConversionException("Element in array is not an integer, got " + node.getClass());
            }
            return new VisitorElement<>(intNode.asInt());
        }
    }

}
