package h10.utils;

import java.util.Map;

public @interface RubricOrder {

    Class<?>[] types();

    int[] orders();
}
