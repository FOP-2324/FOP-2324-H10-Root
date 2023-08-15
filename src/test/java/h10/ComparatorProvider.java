package h10;

import java.util.Comparator;

public class ComparatorProvider {

    public static Comparator<Integer> provideIntegerComparator() {
        return Comparator.naturalOrder();
    }

    public static Comparator<String> provideStringLengthComparator() {
        return (s1, s2) -> {
            if(s1.length() > s2.length()) {
                return 1;
            }
            else if(s1.length() < s2.length()) {
                return -1;
            }
            return 0;
        };
    }

    public static Comparator<Double> provideDoubleComparator() {
        return Comparator.naturalOrder();
    }
}
