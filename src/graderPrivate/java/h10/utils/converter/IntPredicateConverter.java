package h10.utils.converter;

import h10.utils.visitor.VisitorElement;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * Converts a String to a Predicate<VisitorElement<Integer>></Integer>.
 *
 * @author Nhan Huynh
 */
public class IntPredicateConverter implements ArgumentConverter {

    /**
     * Matches all operators in a String.
     */
    private static final Pattern OPERATORS = Pattern.compile("==|!=|<=|>=|<|>|%");

    /**
     * Matches all integers in a String.
     */
    private static final Pattern PATTERN_INT = Pattern.compile("-?\\d+");

    @Override
    public Predicate<VisitorElement<Integer>> convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
        if (!(o instanceof String string)) {
            throw new ArgumentConversionException("Expected String, got " + o);
        }

        Iterator<String> ops = OPERATORS.matcher(string).results().map(MatchResult::group).iterator();
        Iterator<Integer> operands = PATTERN_INT.matcher(string).results().map(MatchResult::group)
            .map(Integer::parseInt)
            .iterator();

        // Base
        String op = ops.next();
        int operand = operands.next();
        Predicate<Integer> predicate = x -> evaluate(x, operand, op);

        // Chained operations
        while (ops.hasNext()) {
            String nextOp = ops.next();
            int nextOperand = operands.next();
            predicate = predicate.and(x -> evaluate(x, nextOperand, nextOp));
        }

        Predicate<Integer> result = predicate;
        return x -> result.test(x.getElement());
    }

    /**
     * Evaluates the given operation.
     *
     * @param x  the first operand
     * @param y  the second operand
     * @param op the operator
     * @return the result of the operation
     */
    private boolean evaluate(int x, int y, String op) {
        return switch (op) {
            case "==" -> x == y;
            case "!=" -> x != y;
            case "<=" -> x <= y;
            case ">=" -> x >= y;
            case "<" -> x < y;
            case ">" -> x > y;
            default -> throw new IllegalArgumentException("Unexpected value: " + op);
        };
    }
}
