package DES;


public class Message {
    final char COMPLEMENT = 'x';
    // store all info
    char[] data;
    // store 8 character
    char[] buf;
    // store bitwise info
    byte [] bitM;
    // mark location
    int idx;
    int surplus;
    int round;

    byte[] getBitM() {
        return bitM;
    }

    void setBitM(byte[] bitM) {
        this.bitM = bitM;
    }

    Message () {
        data = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h','a', 'b', 'c', 'd'};
        buf = new char[8];
        bitM = new byte[64];
        idx = 0;
        surplus = 8 - (data.length % 8);
        round = 0;
    }

    Message (char[] d) {
        data = d;
        buf = new char[8];
        bitM = new byte[64];
        idx = 0;
        surplus = 8 - (d.length % 8) ; // 15 % 8 = 7 (8-7 = 1)
        round = 0;
    }

    private void load() {
        if( idx > data.length ) {
            return;
        }
        if (idx + 8 <= data.length) { // 如果剩下的密文 字符大于等于 8
            System.arraycopy(data, idx, buf, 0, 8);
            idx += 8;
            round += 1;
        } else { // 如果剩下的字符小与等于 8
            int j = 0;
            for (int i = idx; i < data.length ; i++, j++) {
                buf[j] = data[i];  //
            }
            while (j < 8) buf[j++] = COMPLEMENT;
            idx = data.length - 1;
            round += 1;
        }

    }

    void DecimalToBinary(char ch, int idx) {
        int Decimal = (int)ch;
        for( int i = 7, j = 0; i >= 0 ; i--, j++ ) {
            bitM[idx*8 + j] = (byte)((Decimal >> i) & 1);
        }
    }

    void CharToBinary() {
        for (int i = 0; i < 8; i++) {
            DecimalToBinary(buf[i], i);
        }
    }

    private int BinaryToDecimal(int l, int r) {
        int decimal = 0;
        for( int i = l ; i < r ; i ++ ) {
            decimal = decimal << 1 | bitM[i];
        }
        return decimal;
    }

    /**
    将bitM的数组转化为字符
     */
    char[] BinaryToChar() {
        char[] t = new char[8];
        for (int i = 0; i < 8; i++) {
            char ch = (char)BinaryToDecimal(8*i, 8*i+8);
            t[i] = ch;
        }
        return t;
    }

    boolean checkFull() {
        return round >= (int)Math.ceil(data.length / 8.0); // 向上取整
    }

    public void Update() {
        if( !checkFull() ) {
            load();
            CharToBinary();
        }
    }


    public void print() {
        for( int i = 0 ; i < 8 ; i ++) {
            for( int j = 0 ; j < 8 ; j ++ ) {
                System.out.printf("%d ",bitM[i*8 + j] );
            }
            if( i == 3 || i == 7)
                System.out.println();
            else
                System.out.print(" ");
        }
        System.out.println();
    }
}
