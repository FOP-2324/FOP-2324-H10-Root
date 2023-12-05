package h10;

import h10.h1.H1_1_Tests;
import h10.h1.H1_2_Tests;
import h10.h1.H1_Tests;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

public class H10_RubricProvider implements RubricProvider {

    private static final Criterion H1 = Criterion.builder()
        .shortDescription("H1 | subset(MySet)")
        .addChildCriteria(
            Rubrics.criteria(H1_1_Tests.class),
            Rubrics.criteria(H1_2_Tests.class)
        )
        .build();
    public static final Rubric RUBRIC = Rubric.builder()
        .title("H10 | Verzeigerte Strukturen - Private Tests")
        .addChildCriteria(H1)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
