package h10.utils;

import h10.MySetAsCopy;
import h10.MySetInPlace;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RubricOrder {

    Class<?>[] classes() default {MySetAsCopy.class, MySetInPlace.class};

    int[] criteria();
}
