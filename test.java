package DES;

import java.util.Arrays;

public class test {
    static final int[] PC1 = new int[]{
            57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4
    };

    static int[][] b = new int[2][];
    public static void test( int[] a){
        a = new int[1000];
    }
    public static void main(String [] args) {
        int[] a = new int[10];
        a = new int[]{0,1} ;
        test(a);
        System.out.println(PC1.length);
        b[0] = new int[]{1,2,3};
        b[1] = new int[]{1,2,3};

        System.out.println(b[0][1] + b[1][0]);

    }
}
