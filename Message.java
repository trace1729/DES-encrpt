package DES;


import javax.print.DocFlavor;
import java.awt.*;

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
        String m = "ewekwekwkekwkekwkekwekwe";
        data = m.toCharArray();
        buf = new char[16]; // encode 4bit  decode need 16
        // bit
        bitM = new byte[64];
        idx = 0;
        surplus = 4 - (data.length % 4);
        round = 0;
    }

    Message (char[] d) {
        data = d;
        buf = new char[16];
        bitM = new byte[64];
        idx = 0;
        surplus = 4 - (d.length % 4) ; // 15 % 8 = 7 (8-7 = 1)
        round = 0;
    }

    private void encode_load() {
        if( idx > data.length ) {
            return;
        }
        if (idx + 4 <= data.length) { // 如果剩下的密文 字符大于等于 8
            System.arraycopy(data, idx, buf, 0, 4);
            idx += 4;
            round += 1;
        } else { // 如果剩下的字符小与等于 8
            int j = 0;
            for (int i = idx; i < data.length ; i++, j++) {
                buf[j] = data[i];  //
            }
            while (j < 4) buf[j++] = COMPLEMENT;
            idx = data.length - 1;
            round += 1;
        }

    }

    // 不需要考虑位数不足的情况，在加密的时候已经补齐
    private void decode_load() {
        if( idx >= data.length ) {
            return;
        }
        if (idx + 16 <= data.length) { // 如果剩下的密文 字符大于等于 4
            System.arraycopy(data, idx, buf, 0, 16);
            idx += 16;
            round += 1;
        }
    }

    private void convertUnicodeToBinary(char ch, int round) {
        for( int i = 15, j = 0 ; i >= 0 ; i--, j++ ) {
            bitM[ round*16+j ] = (byte)(ch >> i & 1);
        }
    }


    public void phrase_encode_message() {
        for( int i = 0 ; i < 4 ; i ++ ) {
            convertUnicodeToBinary(buf[i], i);
        }
    }

    private char convertBinaryToHex(int l, int r) {
        int hex_value = 0;
        for(int i = l ; i < r ; i ++) {
            hex_value = hex_value << 1 | bitM[i];
        }
        return Data.hex[hex_value];
    }

    public char[] encodeOutput() {
        char[] output = new char[16];
        for( int i = 0 ; i < 16 ; i ++) {
            output[i] = convertBinaryToHex(i*4, i*4+4);
        }
        return output;
    }

    private char convertBinaryToUnicode(int l, int r) {
        int unicode_value = 0;
        for (int i = l ; i < r ; i ++) {
            unicode_value = unicode_value << 1 | bitM[i];
        }
        return (char)unicode_value;
    }

    public char[] decodeOutput() {
        char[] output = new char[4];
        for( int i = 0 ; i < 4 ; i ++) {
            output[i] = convertBinaryToUnicode(i*16, i*16+16);
        }
        return output;
    }

    private int getHexValue(int ch) {
        if( ch <= '9' &&  ch >= '0' ) {
            return ch - '0';
        } else {
          switch (ch) {
              case 'A':return 10;
              case 'B':return 11;
              case 'C':return 12;
              case 'D':return 13;
              case 'E':return 14;
              case 'F':return 15;
          }
        }
        return -1;
    }

    private void convertHexToBinary(char ch, int round) {
        int hex_value = getHexValue(ch);
        for( int i = 3, j = 0; i >= 0 ; i--, j++ ) {
            bitM[round*4 + j] = (byte)((hex_value >> i) & 1);
        }
    }


    public void phrase_decode_message() {
        for( int i = 0; i < 16 ; i ++ ) {
            convertHexToBinary(buf[i], i);
        }
    }

    public void encode_update() {
        if( !encode_checkFull() ) {
            encode_load();
            phrase_encode_message();
        }
    }

    public void decode_update() {
        if(!decode_checkFull()) {
            decode_load();
            phrase_decode_message();
        }
    }

    boolean encode_checkFull() {
        return round >= (int)Math.ceil(data.length / 4.0); // 向上取整
    }

    boolean decode_checkFull() {
        return round >= (int)Math.ceil(data.length / 16.0); // 向上取整
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
