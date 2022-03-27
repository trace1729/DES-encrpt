package DES;

public class Key extends Message implements DesInfo{

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
}
