package h10.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import h10.ListItem;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;

public abstract class ListItemInListItemConverter<T> implements GenericArgumentConverter<T> {

    protected final ListItemConverter<T> converter;

    public ListItemInListItemConverter(ListItemConverter<T> converter) {
        this.converter = converter;
    }

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
            ListItem<ListItem<T>> item = new ListItem<>(converter.convert(element, parameterContext));
            if (outerHead == null) {
                outerHead = item;
            } else {
                outerTail.next = item;
            }
            outerTail = item;
        }
        return outerHead;
    }

    @Override
    public T map(JsonNode node) {
        return converter.map(node);
    }

    public static class Int extends ListItemInListItemConverter<Integer> {

        public Int() {
            super(new ListItemConverter.Int());
        }
    }
}
