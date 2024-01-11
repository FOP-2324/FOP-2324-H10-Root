package h10;

import h10.jagr.MySetValidationTransformer;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Grader;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class H10_RubricProviderPublic implements RubricProvider {

    @SafeVarargs
    private static Criterion createCriterion(String shortDescription, Callable<Method>... methodReferences) {
        return createCriterion(shortDescription, 0, 1, methodReferences);
    }

    @SafeVarargs
    private static Criterion createCriterion(String shortDescription, int minPoints, int maxPoints, Callable<Method>... methodReferences) {
        return createCriterion(shortDescription, minPoints, maxPoints, Arrays.stream(methodReferences).map(JUnitTestRef::ofMethod).toArray(JUnitTestRef[]::new));
    }

    private static Criterion createCriterion(String shortDescription, int minPoints, int maxPoints, JUnitTestRef... testReferences) {

        if (testReferences.length == 0) {
            return Criterion.builder()
                .shortDescription(shortDescription)
                .maxPoints(1)
                .build();
        }

        Grader.TestAwareBuilder graderBuilder = Grader.testAwareBuilder();

        for (JUnitTestRef reference : testReferences) {
            graderBuilder.requirePass(reference);
        }

        return Criterion.builder()
            .shortDescription(shortDescription)
            .minPoints(minPoints)
            .grader(graderBuilder
                .pointsFailedMin()
                .pointsPassedMax()
                .build())
            .maxPoints(maxPoints)
            .build();
    }

    public static final Criterion H1_1 = createCriterion("Test.",
        () -> TransformerTest.class.getDeclaredMethod("test"));
    public static final Rubric RUBRIC = Rubric.builder()
        .title("H10")
        .addChildCriteria(H1_1)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        configuration.addTransformer(new MySetValidationTransformer());
    }
}
