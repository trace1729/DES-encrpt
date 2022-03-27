package DES;

import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;

public class desLancher {
    @Test
    public void testIp() {
        Message m = new Message();
        m.Update();
        int[] arr = new int[64];
        System.arraycopy(m.bitM, 0, arr, 0 ,64);

        m.setBitM(DesCrypt.permute(m.getInfo(), m.getInfo().length, Data.IP));
        m.setBitM(DesCrypt.permute(m.getInfo(), m.getInfo().length, Data.IPReverse));

        Assert.assertArrayEquals(m.getInfo(), arr);
    }
    @Test
    public void testLeftShift() {
        Key k = new Key();
        int k0 = k.getInfo()[0];
        int k27 = k.getInfo()[27];
        int k28 = k.getInfo()[28];
        int k55 = k.getInfo()[55];

        DesCrypt.LeftShift(k, 1);
        Assert.assertEquals(k0, k.getInfo()[27]);
        Assert.assertEquals(k27, k.getInfo()[0]);
        Assert.assertEquals(k28, k.getInfo()[55]);
        Assert.assertEquals(k55, k.getInfo()[28]);
    }

    @Test
    public void testXOR() {
        int[] a = new int[]{0, 1, 0, 1, 0, 1};
        int[] b = new int[]{1, 0, 1, 0, 0, 1};

        int[] res = DesCrypt.XOR(a, b);
        System.out.println(Arrays.toString(res));
    }

    @Test
    public void testToBinary() {
        // input a digit and check the six digit outcome( check the array)
        int[] test1 = new int[] {0,1,0,1,0,1};
        int[] test2 = new int[] {1,0,0,0,1,0};
        int[] test4 = new int[] {1,0,0,0,0,0};
        Assert.assertEquals(DesCrypt.ToBinary(test1, 0), 21);
        Assert.assertEquals(DesCrypt.ToBinary(test2, 0), 34);
        Assert.assertEquals(DesCrypt.ToBinary(test4, 0), 32);
        int[] test3 = new int[] {0,1,0,1,0,1,1,0,0,0,1,0,0,1,0,1,0,1,1,0,0,0,1,0 };
        Assert.assertEquals(DesCrypt.ToBinary(test3, 0), 21);
        Assert.assertEquals(DesCrypt.ToBinary(test3, 3), 34);
    }

    @Test
    public void testSBoxConvert() {
        int[] ans1 = new int[] {1,1,1,1};
        int[] ans2 = new int[] {1,0,1,0};
        int[] ans4 = new int[] {1,0,0,0};
        int[] test = new int[4];
        DesCrypt.SBoxConvert(test, 15, 0);
        Assert.assertArrayEquals(test, ans1);
        DesCrypt.SBoxConvert(test, 10, 0);
        Assert.assertArrayEquals(test, ans2);
        DesCrypt.SBoxConvert(test, 8, 0);
        Assert.assertArrayEquals(test, ans4);

        int[] test2 = new int[16];
        int[] ans3 = new int[] {
           1,1,1,1, 1,1,1,1 ,1,0,1,0, 1,0,1,0
        } ;
        DesCrypt.SBoxConvert(test2, 15, 0);
        DesCrypt.SBoxConvert(test2, 15, 1);
        DesCrypt.SBoxConvert(test2, 10, 2);
        DesCrypt.SBoxConvert(test2, 10, 3);
        Assert.assertArrayEquals(test2, ans3);
    }

    @Test
    public void testDtoB() {
        Message m = new Message();
        m.Update(); // bitM has abcdefg bitwise code
        char[] test = m.BinaryToChar();
        Assert.assertArrayEquals(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}, test);

    }
    @Test
    public void testGenerateKey() {
        Key k = new Key();
        k.Update();
        k.setBitM(DesCrypt.permute(k.getInfo(), k.getInfo().length, Data.PC1));
        int[] test = new int[56];
        System.arraycopy(k.getInfo(), 0,test,0,56);
        DesCrypt.LeftShift(k, 1);
        DesCrypt.RightShift(k, 1);
        Assert.assertArrayEquals(test, k.getInfo());
        DesCrypt.LeftShift(k, 2);
        DesCrypt.RightShift(k, 2);
        Assert.assertArrayEquals(test, k.getInfo());
        DesCrypt.LeftShift(k, 1);
        DesCrypt.LeftShift(k, 2);
        DesCrypt.RightShift(k, 1);
        DesCrypt.RightShift(k, 2);
        Assert.assertArrayEquals(test, k.getInfo());

    }

    @Test
    public void testMain() {
        Key k = new Key();
        Message m = new Message();
        m.Update();
        k.Update();
        DesCrypt.LeftShift(k, 1);
        DesCrypt.encrypt(m, k, true);
        DesCrypt.encrypt(m, k, false);
        DesCrypt.RightShift(k, 1);
        Key k2 = new Key();
        k2.Update();
        Assert.assertArrayEquals(k.getInfo(), k2.getInfo());
    }

}
