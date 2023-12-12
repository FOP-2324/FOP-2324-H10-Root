package h10.converter;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.params.converter.ArgumentConverter;

public interface GenericArgumentConverter<T> extends ArgumentConverter {

    T map(JsonNode node);
}
