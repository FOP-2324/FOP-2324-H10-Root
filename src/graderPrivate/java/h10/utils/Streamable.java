package h10.utils;

import java.util.stream.Stream;

public interface Streamable<T> {

    Stream<T> stream();
}
