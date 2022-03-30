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
        StringBuilder st = new StringBuilder("abcd");
        st.replace(st.length()-1, st.length(), "");
        System.out.println(st.toString());
    }
}
