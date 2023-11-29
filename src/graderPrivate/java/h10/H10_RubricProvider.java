package h10;

import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

public class H10_RubricProvider implements RubricProvider {

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H10")
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
