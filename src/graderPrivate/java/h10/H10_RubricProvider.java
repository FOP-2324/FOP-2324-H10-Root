package h10;


import h10.rubric.Rubrics;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

/**
 * The {@link RubricProvider} for the H10 assignment.
 */
public class H10_RubricProvider implements RubricProvider {

    /**
     * The rubric for this assignment.
     */
    public static final Rubric RUBRIC = Rubrics.read(
        "H10 | Verzeigerte Strukturen - Private Tests",
        "rubric.json"
    );

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
