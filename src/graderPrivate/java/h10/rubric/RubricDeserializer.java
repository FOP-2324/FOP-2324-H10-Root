package h10.rubric;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.DisplayName;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.tudalgo.algoutils.tutor.general.jagr.RubricUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RubricDeserializer extends JsonDeserializer<Criterion[]> {

    @Override
    public Criterion[] deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        /*
        Format:  {
    "title": "H1 | subset(Predicate)",
    "criteria": [
         {
        "order": 1,
        "title": "Die Methode subset(MySet) ninmmt Elemente in die Ergebnismenge nicht auf, falls das Prädikat nicht erfüllt wird.",
        "class": "H1_1_Tests"
      },
    }
         */
        JsonNode node = parser.getCodec().readTree(parser);
        if (!(node instanceof ArrayNode criteriaNode)) {
            throw new IOException("Expected array of criteria");
        }
        Criterion[] criteria = new Criterion[criteriaNode.size()];
        for (int i = 0; i < criteriaNode.size(); i++) {
            criteria[i] = deserializeCriterion(criteriaNode.get(i));
        }
        return criteria;
    }

    private Criterion deserializeCriterion(JsonNode node) throws IOException {
        if (!node.has("title")) {
            throw new IOException("Missing title");
        }
        String title = node.get("title").asText();
        if (!node.has("criteria")) {
            throw new IOException("Missing criteria");
        }
        if (!(node.get("criteria") instanceof ArrayNode criteriaNode)) {
            throw new IOException("Expected array of criteria");
        }
        Map<Integer, Criterion> criteria = new HashMap<>(criteriaNode.size());
        for (int i = 0; i < criteriaNode.size(); i++) {
            Map.Entry<Integer, Criterion> entry = deserializeSubCriterion(criteriaNode.get(i));
            criteria.put(entry.getKey(), entry.getValue());
        }
        return Criterion.builder()
            .shortDescription(title)
            .addChildCriteria(criteria.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .toArray(Criterion[]::new)
            ).build();
    }

    private List<Method> getMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>(List.of(clazz.getDeclaredMethods()));
        if (methods.isEmpty() || clazz.getSuperclass() == null) {
            return methods;
        }
        methods.addAll(getMethods(clazz.getSuperclass()));
        return methods;
    }

    private Map.Entry<Integer, Criterion> deserializeSubCriterion(JsonNode node) throws IOException {
        if (!node.has("order")) {
            throw new IOException("Missing order");
        }
        int order = node.get("order").asInt();
        if (!node.has("title")) {
            throw new IOException("Missing title");
        }
        String title = node.get("title").asText();
        if (!node.has("class")) {
            throw new IOException("Missing class");
        }
        if (!node.has("class")) {
            throw new IOException("Missing class");
        }
        String className = node.get("class").asText();
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
        List<Method> methods = getMethods(clazz);
        return methods.parallelStream()
            .filter(m -> m.isAnnotationPresent(DisplayName.class))
            .filter(m -> m.getAnnotation(DisplayName.class).value().equals(title))
            .findFirst()
            .map(value -> Map.entry(order, RubricUtils.criterion(title, JUnitTestRef.ofMethod(value))))
            .orElseGet(() -> Map.entry(order, Criterion.builder().shortDescription(title).build()));
    }
}
