package h10.converter;


import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public abstract class PredicateConverter<T> implements ArgumentConverter {

    @Override
    public Predicate<T> convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
        if (!(o instanceof String string)) {
            throw new ArgumentConversionException("Expected String, got " + o);
        }

        return parse(string);
    }

    protected abstract Predicate<T> parse(String string);

    public static class BasicIntMath extends PredicateConverter<Integer> {

        public static final Pattern OPERATORS = Pattern.compile("==|!=|<=|>=|<|>|%");

        private static final Pattern PATTERN_INT = Pattern.compile("-?\\d+");

        @Override
        protected Predicate<Integer> parse(String string) {
            Iterator<String> ops = OPERATORS.matcher(string).results().map(MatchResult::group).iterator();
            Iterator<Integer> operands = PATTERN_INT.matcher(string).results().map(MatchResult::group)
                .map(Integer::parseInt)
                .iterator();
            // Base
            String op = ops.next();
            int operand = operands.next();
            Predicate<Integer> predicate = new Predicate<Integer>() {
                @Override
                public boolean test(Integer x) {
                    return evaluate(x, operand, op);
                }

                @Override
                public String toString() {
                    return "x" + BasicIntMath.this.toString(operand, op);
                }
            };


            // Chained operations
            while (ops.hasNext()) {
                String nextOp = ops.next();
                int nextOperand = operands.next();
                Predicate<Integer> tmp = predicate;
                predicate = new Predicate<>() {
                    @Override
                    public boolean test(Integer x) {
                        return tmp.test(x) && evaluate(x, nextOperand, nextOp);
                    }

                    @Override
                    public String toString() {
                        return tmp + BasicIntMath.this.toString(nextOperand, nextOp);
                    }
                };
            }

            return predicate;
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

        private String toString(int y, String op) {
            return switch (op) {
                case "==" -> " == " + y;
                case "!=" -> " != " + y;
                case "<=" -> " <= " + y;
                case ">=" -> " >= " + y;
                case "<" -> " < " + y;
                case ">" -> " > " + y;
                default -> throw new IllegalArgumentException("Unexpected value: " + op);
            };
        }
    }
}
