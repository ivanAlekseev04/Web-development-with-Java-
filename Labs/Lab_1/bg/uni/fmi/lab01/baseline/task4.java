package bg.uni.fmi.lab01.baseline;

import java.util.List;

public class task4 {
    public String atIdx(List<String> l, int idx) {
        String toReturn = "";

        if(idx <= l.size() - 1) {
            int cnt = 0;
            for(var el : l) {
                if(cnt < idx) {
                    cnt++;
                } else {
                    toReturn = el;
                    break;
                }
            }
        }

        return toReturn;
    }
}
