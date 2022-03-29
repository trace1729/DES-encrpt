package DES;

public class Key extends Message {

    public Key() {
        super();
    }
    public Key(char[] t) {
        super(t);
    }

    private void load() {
        System.arraycopy(data, idx, buf, 0, 8);
    }

    public void Update() {
        load();
        CharToBinary();
        this.setBitM(DesCrypt.permute(bitM, bitM.length, Data.PC1));
    }

    public void test() {
        bitM = new int[]{
                0, 0, 0, 1, 0, 0, 1, 1,
                0, 0, 1, 1, 0, 1, 0, 0,
                0, 1, 0, 1, 0, 1, 1, 1,
                0, 1, 1, 1, 1, 0, 0, 1,
                1, 0, 0, 1, 1, 0, 1, 1,
                1, 0, 1, 1, 1, 1, 0, 0,
                1, 1, 0, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 0, 0, 0, 1
        };

    }

    public void print() {
        for( int i = 0 ; i < 8 ; i ++) {
            System.out.printf("第%d - %d位： ", i*7+1, i*7+7) ;
            for( int j = 0 ; j < 7 ; j ++ ) {
                System.out.printf("%d", bitM[i*7 + j] );
            }
            System.out.print(" ");
        }
        System.out.println();
    }
}
