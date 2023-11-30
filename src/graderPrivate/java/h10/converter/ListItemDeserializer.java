package h10.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import h10.ListItem;

import java.io.IOException;

public class ListItemDeserializer extends JsonDeserializer<ListItem<Integer>> {
    @Override
    public ListItem<Integer> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec mapper = p.getCodec();
        ArrayNode arrayNode = mapper.readTree(p);
        ListItem<Integer> head = null;
        ListItem<Integer> tail = null;
        for (JsonNode node : arrayNode) {
            ListItem<Integer> item = new ListItem<>(node.asInt());
            if (head == null) {
                head = item;
            } else {
                tail.next = item;
            }
            tail = item;
        }
        return head;
    }
}
