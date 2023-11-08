package h10;

/**
 * Tuple class for holding two values.
 *
 * @param first  the first value of the tuple
 * @param second the second value of the tuple
 * @param <F>    the type of the first value
 * @param <S>    the type of the second value
 * @author Lars Waßmann, Nhan Huynh
 */
public record Tuple<F, S>(F first, S second) {
}
