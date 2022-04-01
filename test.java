package DES;


import org.junit.Test;

import java.io.*;


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
            m.print();
            for( char ch : m.encodeOutput()) {
                st.append(ch);
            }
        }

        System.out.println(st.toString());
    }
    @Test
    public void IO () {
        try {
            FileOutputStream filestream = new FileOutputStream("MyGame.ser");
            ObjectOutputStream os = new ObjectOutputStream(filestream);
            os.writeObject(new Integer(1));
            os.writeObject(new Integer(1));
            os.writeObject(new Integer(1));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}