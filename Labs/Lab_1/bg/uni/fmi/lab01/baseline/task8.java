package bg.uni.fmi.lab01.baseline;

import java.util.List;

public class task8 {
    public <T> void changeElement(List<T> l, T changed) {
        if(l.size() < 2) {
            return;
        }

        l.set(1, changed);
    }
}
