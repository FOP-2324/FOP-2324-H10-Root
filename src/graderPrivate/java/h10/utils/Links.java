package h10.utils;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions3;
import org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.match.MatcherFactories;
import org.tudalgo.algoutils.tutor.general.reflections.BasicPackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.PackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.Arrays;

/**
 * Defines the utilities link interaction for H10 testing.
 *
 * @author Nhan Huynh
 */
public class Links {

    /**
     * The string matcher factory for retrieving links.
     */
    private static final MatcherFactories.StringMatcherFactory STRING_MATCHER_FACTORY = BasicStringMatchers::identical;

    /**
     * The package to link.
     */
    private static final PackageLink PACKAGE_LINK = BasicPackageLink.of("h10");

    /**
     * Prevents instantiation.
     */
    private Links() {

    }

    /**
     * Returns the type link for the given package and class.
     *
     * @param p     the package to get the type link for
     * @param clazz the class in the package to get the type link for
     * @return the type link
     */
    public static TypeLink getTypeLink(Class<?> clazz) {
        return Assertions3.assertTypeExists(
            PACKAGE_LINK,
            STRING_MATCHER_FACTORY.matcher(clazz.getSimpleName())
        );
    }

    @SafeVarargs
    public static MethodLink getMethodLink(TypeLink type, String methodName, Matcher<MethodLink>... matchers) {
        return Assertions3.assertMethodExists(
            type,
            Arrays.stream(matchers).reduce(STRING_MATCHER_FACTORY.matcher(methodName), Matcher::and)
        );
    }

    @SafeVarargs
    public static MethodLink getMethodLink(Class<?> clazz, String methodName, Matcher<MethodLink>... matchers) {
        return getMethodLink(getTypeLink(clazz), methodName, matchers);
    }
}
