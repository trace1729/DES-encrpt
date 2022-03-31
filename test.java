package DES;


import org.junit.Test;


public class test {
    @Test
    public void tes () {
        StringBuilder st = new StringBuilder();
        Message m = new Message();
        Key k = new Key();

        k.Update();
        while( !m.encode_checkFull() ) {
            m.encode_update();
            DesCrypt.encrypt(m, k, true);
            for( char ch : m.encodeOutput()) {
                st.append(ch);
            }
        }

        Message m2 = new Message(st.toString().toCharArray());
        st = new StringBuilder();
        while( !m2.decode_checkFull() ) {
            m2.decode_update();
            DesCrypt.encrypt(m2, k, false);
            for( char ch : m2.decodeOutput()) {
                st.append(ch);
            }
        }
        System.out.println(st.toString());
    }
    @Test
    public void tes2 () {
        System.out.println(Integer.toBinaryString('中'));
    }
}