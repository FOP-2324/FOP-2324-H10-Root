package h10.utils;

import h10.MySetAsCopy;
import h10.MySetInPlace;

public @interface RubricOrder {

    Class<?>[] classes() default {MySetAsCopy.class, MySetInPlace.class};

    int[] criteria();
}
