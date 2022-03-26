package DES;


public class Message implements DesInfo{
    final char COMPLEMENT = 'x';
    // store all info
    char[] data;
    // store 8 character
    char[] buf;
    // store bitwise info
    int [] bitM;
    // mark location
    int idx;
    int surplus;
    int round;

    @Override
    public int[] getInfo() {
        return bitM;
    }

    public void setBitM(int[] bitM) {
        this.bitM = bitM;
    }

    public Message () {
        data = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        buf = new char[8];
        bitM = new int[64];
        idx = 0;
        surplus = 0;
        round = 0;
    }

    public Message (char[] d) {
        data = d;
        buf = new char[8];
        bitM = new int[64];
        idx = 0;
        surplus = d.length % 8;
        round = 0;
    }

    private void load() {
        if( idx > data.length ) {
            return;
        }
        if (idx + 8 <= data.length) {
            System.arraycopy(data, idx, buf, 0, 8);
            idx += 8;
            round += 1;
        } else {
            int j = 0;
            for (int i = idx; i < data.length - idx; i++, j++) {
                buf[j] = data[i];
            }
            while (j < 8) buf[j++] = COMPLEMENT;
            idx = data.length - 1;
            round += 1;
        }

    }

    public void DecimalToBinary(char ch, int idx) {
        int Decimal = (int)ch;
        for( int i = 7, j = 0; i >= 0 ; i--, j++ ) {
            bitM[idx*8 + j] = (Decimal >> i) & 1;
        }
    }

    public void CharToBinary() {
        for (int i = 0; i < 8; i++) {
            DecimalToBinary(buf[i], i);
        }
    }

    public int BinaryToDecimal(int l, int r) {
        int decimal = 0;
        for( int i = l ; i < r ; i ++ ) {
            decimal = decimal << 1 | bitM[i];
        }
        return decimal;
    }

    public char[] BinaryToChar() {
        char[] t = new char[8];
        for (int i = 0; i < 8; i++) {
            char ch = (char)BinaryToDecimal(8*i, 8*i+8);
            t[i] = ch;
        }
        return t;
    }

    boolean checkFull() {
        return round >= (int)Math.floor(data.length / 8.0);
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
                System.out.printf("%d ", bitM[i*8 + j] );
            }
            System.out.println();
        }
    }
}
