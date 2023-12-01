package h10.utils;

import h10.MySetAsCopy;
import h10.MySetInPlace;

/**
 * This annotation can be used to specify the order of the rubric items.
 *
 * @author Nhan Huynh
 */
public @interface RubricOrder {

    /**
     * The types of the rubric items to grade.
     *
     * @return the types of the rubric items to grade
     */
    Class<?>[] types() default {MySetAsCopy.class, MySetInPlace.class};

    /**
     * The criteria number of the rubric items to grade.
     *
     * @return criteria number of the rubric items to grade
     */
    int[] criteria();
}
