package h10.util;

import com.fasterxml.jackson.databind.JsonNode;
import h10.ListItem;
import h10.json.PredicateParser;

import java.util.function.Predicate;


public class JsonConverters extends org.tudalgo.algoutils.tutor.general.json.JsonConverters {

    public static ListItem<Integer> toListItemInteger(JsonNode node) {
        if (!node.isArray()) {
            throw new IllegalArgumentException("Node is not an array");
        }
        return ListItems.convert(toList(node, JsonNode::asInt));
    }

    public static Predicate<Integer> toPredicateInteger(JsonNode node) {
        return new PredicateParser.BasicIntMath().parse(node);
    }
}
