package h10.rubric;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Rubric;

import java.io.IOException;

public final class Rubrics {

    private Rubrics() {

    }

    public static Rubric readRubric(String title, String path) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Criterion[].class, new RubricDeserializer());
        mapper.registerModule(module);
        Criterion[] criteria;
        try {
            criteria = mapper.readValue(Rubrics.class.getClassLoader().getResource(path), Criterion[].class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        for (Criterion criterion : criteria) {
            System.out.println(criterion);
        }
        return Rubric.builder()
            .title(title)
            .addChildCriteria(criteria)
            .build();
    }
}
