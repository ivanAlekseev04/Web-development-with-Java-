package bg.uni.fmi.lab01.baseline;

import java.util.Arrays;

public class task1 {
    public int task1(int[] arr, int idx) throws Exception {
        int counter = 0;

        for(int i = 0; i < arr.length; i++) {
            if(counter == idx) {
                return arr[i - 1];
            }

            if(i + 1 == arr.length && counter < idx) {
                throw new Exception("Too less elements");
            }

            if(arr[i] % 2 != 0) {
                counter++;
            }
        }

        return 2;
    }
    public int task1_alt(int[] arr, int idx) throws Exception {
        int[] temp = Arrays.stream(arr).filter(e -> e % 2 != 0).toArray();

        if(temp.length < idx) {
            throw new Exception("....");
        }

        return temp[idx - 1];
    }
}
