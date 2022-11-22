package bits;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bits {
    List<Bit> bits;

    public Bits(List<Bit> bits) {
        this.bits = bits;
    }

    public Bits(Bits bits) {
        this.bits = new ArrayList<>(bits.bits);
    }

    public Bits() {
        this(new ArrayList<>());
    }

    public static Bits fromBytes(byte[] bytes) {
        List<Bit> bits = new ArrayList<>();

        for (byte b : bytes)
            for (int i = 7; i >= 0; i--)
                bits.add(new Bit((b >> i) & 1));

        return new Bits(bits);
    }

    public static Bits fromString(String string) {
        return Bits.fromBytes(string.getBytes());
    }

    public Bits append(Bit bit) {
        bits.add(bit);
        return this;
    }

    public Bits append(List<Bit> bits) {
        this.bits.addAll(bits);
        return this;
    }

    public Bits append(Bits bits) {
        return append(bits.bits);
    }

    public int size() {
        return bits.size();
    }

    @Override
    public String toString() {
        return bits.stream().map(Bit::toString).collect(Collectors.joining());
    }
}
