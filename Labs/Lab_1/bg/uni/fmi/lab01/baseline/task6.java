package bg.uni.fmi.lab01.baseline;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class task6 {
    public boolean findString(List<String> l, String s) {
        Set<String> strs = new HashSet<>(l);
        return strs.contains(s);
    }
}
