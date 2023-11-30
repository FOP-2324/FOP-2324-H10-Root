package h10.utils;

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
    Class<?>[] types();

    /**
     * The order of the rubric items to grade.
     *
     * @return the order of the rubric items to grade
     */
    int[] orders();
}
