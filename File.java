package DES;

import java.io.*;

public class File {
    public static void main (String[] args) {
        try {
            FileWriter writer = new FileWriter("foo.txt");
            writer.write("hello world");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
