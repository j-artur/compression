package compression;

import bits.Bits;

public record CharCode(char character, int frequency, Bits bits) {
}
