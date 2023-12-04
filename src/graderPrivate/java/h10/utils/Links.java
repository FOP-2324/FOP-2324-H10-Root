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

public class Links {

    private static final MatcherFactories.StringMatcherFactory STRING_MATCHER_FACTORY = BasicStringMatchers::identical;

    private static final PackageLink PACKAGE_LINK = BasicPackageLink.of("h10");

    private Links() {

    }

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
