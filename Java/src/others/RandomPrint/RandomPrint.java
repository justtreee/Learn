package others.RandomPrint;

import java.util.Random;

/**
 * Created by treee on -2018/3/7-
 */

//从自然数1到1000中，随机取900个不重复的数并打印出来。
public class RandomPrint {
    public static void main(String[] args) {
        int[] ans = new int[900];
        int[] b = new int[1000];
        for(int i=0; i<1000; i++){
            b[i] = i+1;
        }

        Random random = new Random();
        int blen = 1000;
        for(int i=0; i<900; i++){
            int index = random.nextInt(blen--);
            ans[i] = b[index];
            b[index] = b[blen];
        }

        for (int i : ans){
            System.out.printf(i +"\t");
        }
    }
}
