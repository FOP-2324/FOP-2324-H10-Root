package h10.utils.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import h10.ListItem;
import h10.utils.visitor.VisitorElement;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

/**
 * Converts an array of JSON nodes to a list item sequence.
 *
 * @param <T> the type of the list items to convert to
 * @author Nhan Huynh
 */
public abstract class ListItemTupleConverter<T> implements ArgumentConverter {
    @Override
    public ListItem<ListItem<T>> convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
        if (!(o instanceof ArrayNode outerNode)) {
            throw new ArgumentConversionException("Expected ArrayNode, got " + o);
        }
        ListItem<ListItem<T>> outerHead = null;
        ListItem<ListItem<T>> outerTail = null;
        for (JsonNode element : outerNode) {
            if (!(element instanceof ArrayNode innerNode)) {
                throw new ArgumentConversionException("Expected ArrayNode, got " + element);
            }
            ListItem<T> innerHead = null;
            ListItem<T> innerTail = null;
            for (JsonNode innerElement : innerNode) {
                ListItem<T> item = new ListItem<>(map(innerElement));
                if (innerHead == null) {
                    innerHead = item;
                } else {
                    innerTail.next = item;
                }
                innerTail = item;
            }
            ListItem<ListItem<T>> item = new ListItem<>(innerHead);
            if (outerHead == null) {
                outerHead = item;
            } else {
                outerTail.next = item;
            }
            outerTail = item;
        }
        return outerHead;
    }

    /**
     * Maps a JSON node to a list item element.
     *
     * @param node the JSON node to map
     * @return the mapped list item element
     */
    protected abstract T map(JsonNode node);

    /**
     * Converts an array of JSON nodes to a list item sequence of visitor elements.
     */
    public static class VisitorNode extends ListItemTupleConverter<VisitorElement<Integer>> {

        @Override
        protected VisitorElement<Integer> map(JsonNode node) {
            if (!(node instanceof IntNode intNode)) {
                throw new ArgumentConversionException("Element in array is not an integer, got " + node.getClass());
            }
            return new VisitorElement<>(intNode.asInt());
        }
    }
}
