package h10.utils.converter;

import h10.utils.visitor.VisitorElement;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class IntPredicateConverter implements ArgumentConverter {

    private static final Pattern OPERATORS = Pattern.compile("==|!=|<=|>=|<|>|%");

    private static final Pattern PATTERN_INT = Pattern.compile("-?\\d+");

    @Override
    public Predicate<VisitorElement<Integer>> convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
        if (!(o instanceof String string)) {
            throw new ArgumentConversionException("Expected String, got " + o);
        }

        Iterator<String> ops = OPERATORS.matcher(string).results().map(MatchResult::group).iterator();
        Iterator<Integer> operands = PATTERN_INT.matcher(string).results().map(MatchResult::group).map(Integer::parseInt).iterator();
        String op = ops.next();
        int operand = operands.next();
        Predicate<Integer> predicate = x -> evaluate(x, operand, op);
        while (ops.hasNext()) {
            String nextOp = ops.next();
            int nextOperand = operands.next();
            predicate = predicate.and(x -> evaluate(x, nextOperand, nextOp));
        }

        Predicate<Integer> result = predicate;
        return x -> result.test(x.getElement());
    }

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
