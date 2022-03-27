package DES;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class DesCrypt {

    static Scanner scanner = new Scanner(System.in);

    static StringBuilder content = new StringBuilder();

//    // 读取明文输入
//    public void readInput() {
//        // what data structure i should use to fulfill the demand taking 8 character once a time;
//        char[] input = scanner.next().toLowerCase().replace(" ", "").toCharArray();
//        m = new Message(input);
//        m.Update();
//    }
//
//    // 读取密匙
//    public void readKey() {
//        char[] input = scanner.next().toLowerCase().replace(" ", "").toCharArray();
//        k = new Key(input);
//        k.Update();
//    }

    // 对 size大小的 bits矩阵（实际是一维数组）进行 对应的置换
    public static int[] permute(int[] bits, int size_t, int[] permuteMatrix) {
        int size = permuteMatrix.length;
        int[] copy = new int[size_t];
        System.arraycopy(bits, 0, copy, 0, size_t);

        bits = new int[size];
        for( int i = 0 ; i < size ; i ++) {
            bits[i] = copy[permuteMatrix[i]-1];
        }
        return bits;
    }
    // 对密匙进行左移
    public static void LeftShift(Key k, int shift) {
        int[] m = k.getInfo();
        if( shift == 1 ) {
            int a0 = m[0], a28 = m[28];
            System.arraycopy(m, 1, m, 0,27);
            m[27] = a0;
            System.arraycopy(m,29,m,28,27);
            m[55] = a28;
        }
        if( shift == 2 ) {
            int a0 = m[0], a1 = m[1];
            int a28 = m[28], a29 = m[29];
            System.arraycopy(m, 2, m, 0,26);
            m[27] = a1; m[26] = a0;
            System.arraycopy(m,30,m,28,26);
            m[55] = a29; m[54] = a28;
        }
    }
    public static void RightShift(Key k, int shift) {
        int[] m = k.getInfo();
        if( shift == 1 ) {
            int a27 = m[27], a55 = m[55];
            System.arraycopy(m, 0, m, 1,27);
            m[0] = a27;
            System.arraycopy(m,28,m,29,27);
            m[28] = a55;
        }
        if( shift == 2 ) {
            int a27 = m[27], a26 = m[26];
            int a55 = m[55], a54 = m[54];
            System.arraycopy(m, 0, m, 2,26);
            m[0] = a26; m[1] = a27;
            System.arraycopy(m,28,m,30,26);
            m[28] = a54; m[29] = a55;
        }
    }
    // 第round的密匙生成函数
    public static int[] generateKey(Key k, int round, boolean encrypt) {
            int shift = 0;
            if( encrypt ) {
                shift = Data.DesRotations[round];
                LeftShift(k, shift); // 永久更改的
                return permute(k.getInfo(), k.getInfo().length, Data.PC2); // 暂时更改的
            } else {
                shift = Data.RightDesRotations[round];
                RightShift(k, shift); // 永久更改的
                return permute(k.getInfo(), k.getInfo().length, Data.PC2); // 暂时更改的
            }

    }
    // 返回两个整形数组（大小相等）的异或结果
    public static int[] XOR(int[] m, int[] DesKey) {
        if( m.length != DesKey.length ) {
            System.out.println(m.length + ' ' + DesKey.length);
            return null;
        }
        int size = m.length;
        int[] res = new int[size];
        for( int i = 0 ; i < size ; i ++ ) {
            res[i] = m[i]^DesKey[i];
        }
        return res;
    }
    // 将48位分成 8 个 6位
    public static int ToBinary(int[] RPT, int round) {
        int sixBit = 0;
        for( int i = round*6 ; i < round*6 + 6 ; i ++ ) {
                sixBit = sixBit << 1 | RPT[i];
        }
        return sixBit;
    }
    // 将s盒读取的输入转化为 4位2 进制表示
    public static void SBoxConvert(int[] RPT_32, int num, int round) {
        for( int i = 3, j = 4*round ; i >= 0 ; i --, j++ ) {
            RPT_32[j] = (num >> i) & 1;
        }
    }
    // s盒 将48位 -> 32位
    public static int[] SBox (int[] RPT) {

        int []RPT_32 = new int[32];
        for( int i = 0 ; i < 8 ; i ++ ) {
            int sixBit = ToBinary(RPT, i);
            SBoxConvert(RPT_32, Data.S[i][sixBit], i);
        }
        return RPT_32;
    }

    // f函数
    public static int[] F(int[] RPT, int[] DesKey ) {

        RPT = permute(RPT, RPT.length, Data.E);
        RPT = XOR(RPT, DesKey);
        RPT = SBox(RPT);
        RPT = permute(RPT, RPT.length, Data.P_Box);
        return RPT;

    }
    // 加密完成 将 合并 左32位 和右32位
    public static int[] Merge( int[] LPT, int[] RPT) {
        int []t = new int[64];
        System.arraycopy(LPT, 0, t, 0, 32);
        System.arraycopy(RPT, 0, t, 32, 32);
        return t;
    }
    // 将加密结果暂存在 content 中
    public static void save( char[] mess) {
        for (char ch : mess) {
            content.append(ch);
        }
    }
    //  加密主函数
    public static void encrypt(Message m, Key k, boolean encrypt) {

        //m.setBitM(permute(m.getInfo(), m.getInfo().length, Data.IP));

        int[] LPT = new int[32];
        int[] RPT = new int[32];
        System.arraycopy(m.getInfo(), 0, LPT, 0, 32);
        System.arraycopy(m.getInfo(), 32, RPT, 0, 32);

        int[] DesKey = permute(k.getInfo(), k.getInfo().length, Data.PC2);

        for ( int i = 1 ; i <= 16 ; i++ ) {
            int[] temp = Arrays.copyOf(RPT, RPT.length);
            RPT = XOR( LPT, F(RPT, DesKey));
            LPT = temp;
            DesKey = generateKey(k, i-1,encrypt);
        }
        m.setBitM(Merge(RPT, LPT));
        m.setBitM(permute(m.getInfo(), m.getInfo().length, Data.IPReverse));
    }


    public static void print(Message m) {
        m.print();
        System.out.println();
    }

    public static void main(String[] args) {
            Key k = new Key();
            Message m = new Message();
            m.Update();
            k.Update();
            System.out.println("初始化的矩阵");
            m.print();
            LeftShift(k, 1);
            encrypt(m, k, true);
            System.out.println("第一轮加密后的矩阵");
            m.print();
            encrypt(m, k, false);
            System.out.println("第一轮解密后的矩阵");
            RightShift(k, 1);
            m.print();
    }

}
