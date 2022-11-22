package bits;

public record Bit(boolean bit) {
    public Bit(int bit) {
        this(bit == 1);
    }

    public Bit(Bit bit) {
        this(bit.bit);
    }

    public int toInt() {
        return bit ? 1 : 0;
    }

    @Override
    public String toString() {
        return bit ? "1" : "0";
    }
}
