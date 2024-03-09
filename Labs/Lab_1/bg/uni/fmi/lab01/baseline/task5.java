package bg.uni.fmi.lab01.baseline;

import java.util.List;

public class task5 {
    public void removeThird(List<String> l) {
        if(l.size() >= 3) {
            l.remove(2);
        }
    }
}
