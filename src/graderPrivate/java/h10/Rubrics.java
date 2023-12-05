package h10;

import h10.utils.RubricOrder;
import org.junit.jupiter.api.DisplayName;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.tudalgo.algoutils.tutor.general.jagr.RubricUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Rubrics {

    private Rubrics() {

    }

    private static Class<?> getType(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getConstructor();
            Object obj = constructor.newInstance();
            Method method = clazz.getMethod("getClassType");
            return (Class<?>) method.invoke(obj);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static List<Method> getMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>(List.of(clazz.getDeclaredMethods()));
        if (methods.isEmpty() || clazz.getSuperclass() == null) {
            return methods;
        }
        methods.addAll(getMethods(clazz.getSuperclass()));
        return methods;
    }

    public static Criterion criteria(Class<?> clazz) {
        return criteria(clazz, Map.of());
    }

    public static Criterion criteria(Class<?> clazz, Map<Integer, String> notGraded) {
        String title = clazz.getAnnotation(DisplayName.class).value();
        Class<?> type = getType(clazz);
        Stream<Map.Entry<Integer, Criterion>> criteria = getMethods(clazz).stream()
            .filter(method -> method.isAnnotationPresent(RubricOrder.class))
            .filter(method -> List.of(method.getAnnotation(RubricOrder.class).classes()).contains(type))
            .map(method -> {
                RubricOrder rubric = method.getAnnotation(RubricOrder.class);
                int index = List.of(rubric.classes()).indexOf(type);
                int number = rubric.criteria()[index];
                return Map.entry(
                    number,
                    RubricUtils.criterion(
                        method.getAnnotation(DisplayName.class).value(),
                        JUnitTestRef.ofMethod(method))
                );
            })
            .sorted(Map.Entry.comparingByKey());
        Stream<Map.Entry<Integer, Criterion>> others = notGraded.entrySet().stream()
            .map(entry -> Map.entry(entry.getKey(), Criterion.builder().shortDescription(entry.getValue()).build()));
        return Criterion.builder()
            .shortDescription(title)
            .addChildCriteria(
                Stream.concat(criteria, others)
                    .sorted(Comparator.comparingInt(Map.Entry::getKey))
                    .map(Map.Entry::getValue)
                    .toArray(Criterion[]::new)
            ).build();
    }
}
