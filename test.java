package DES;

public class test {
    static final int[] PC1 = new int[]{
            57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4
    };
    public static void test( int[] a){
        a = new int[1000];
    }
    public static void main(String [] args) {
        int[] a = new int[10];
        a = new int[]{0,1} ;
        test(a);
        System.out.println(PC1.length);
    }
}
