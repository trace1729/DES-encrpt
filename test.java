package DES;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class test {
    @Test
    public void tes () {
        List<Byte> lst = new ArrayList<Byte>();
        Message_file m = new Message_file();
        Key k = new Key();

        k.Update();
        while( !m.IsFull() ) {
            m.Update();
            DesCrypt.encrypt(m, k, true);
            m.print();
            for( byte e : m.BinaryTobyte()) {
                lst.add(e);
            }
        }
        byte [] out = new byte[lst.size()];
        int idx = 0;
        for( byte t : lst) {
            out[idx++] = t;
        }

        Message_file m2 = new Message_file(out);
        while( !m2.IsFull() ) {
            m2.Update();
            DesCrypt.encrypt(m2, k, false);
            m2.print();
            for( byte e : m2.BinaryTobyte()) {
                lst.add(e);
            }
        }
        System.out.println(lst.toString());
    }


}