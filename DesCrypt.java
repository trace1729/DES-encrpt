package DES;
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
        for( int i = 0 ; i < shift ; i++ ) {
            for( int j = 0 ; j < 2 ; j++ ) {
                // 0 27 28 55
                int t = m[0 + 28*j + i];
                m[0 + 28*j + i] = m[27 + 28*j - i];
                m[27 + 28*j - i] = t;
            }
        }
    }

    // 第round的密匙生成函数
    public static int[] generateKey(Key k, int round) {
        int shift = Data.DesRotations[round];
        LeftShift(k, shift);
        return permute(k.getInfo(), k.getInfo().length, Data.PC2);
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
    public static void encrypt(Message m, Key k) {

        permute(m.getInfo(), m.getInfo().length, Data.IP);

        int[] LPT = new int[32];
        int[] RPT = new int[32];
        System.arraycopy(m.getInfo(), 0, LPT, 0, 32);
        System.arraycopy(m.getInfo(), 32, RPT, 0, 32);

        for ( int i = 1 ; i <= 16 ; i++ ) {
            int[] DesKey = generateKey(k, i-1);
            int[] temp = RPT;
            RPT = XOR( LPT, F(RPT, DesKey) );
            LPT = temp;
        }
        m.setBitM(Merge(RPT, LPT));
        permute(m.getInfo(), m.getInfo().length, Data.IPReverse);

    }

    public static void print(Message m) {
        m.print();
        System.out.println();
    }

    public static void main(String[] args) {
        Key k = new Key();
        Message m = new Message();
        m.Update(); k.Update();
        encrypt(m, k);
        encrypt(m, k);
    }

}
