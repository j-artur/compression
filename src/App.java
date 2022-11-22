import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import bits.Bits;
import compression.*;
import debug.Debug;
import debug.Debug.Color;
import table.HashTable;

public class App {
    public static void main(String[] args) throws Exception {
        while (true) {
            Debug.log(Color.BLUE_BRIGHT, "Select an option:");
            Debug.log(Color.BLUE_BRIGHT, "1 - Compress text from file");
            Debug.log(Color.BLUE_BRIGHT, "2 - Compress text from console");
            Debug.log(Color.BLUE_BRIGHT, "0 - Exit");
            String input = Debug.input(Color.WHITE, "Option: ", Color.BLUE_BRIGHT);

            String text;

            switch (input) {
                case "1": {
                    String path = Debug.input(Color.WHITE, "File path: ", Color.BLUE_BRIGHT);

                    if (Files.notExists(Path.of(path))) {
                        Debug.log(Color.RED, "File does not exist");
                        continue;
                    }

                    text = Files.readString(Path.of(path));

                    break;
                }
                case "2":
                    text = Debug.input(Color.WHITE, "Text: ", Color.YELLOW_BRIGHT);
                    break;
                case "0":
                    return;
                default:
                    Debug.log(Color.RED, "Invalid option");
                    continue;
            }

            // create a table to store the frequency of each character
            HashTable<Character, Integer> table = new HashTable<>(20);

            // count the frequency of each character and store it in the table
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                table.set(c, table.get(c).orElse(0) + 1);
            }

            // create a stream of frequency-counted characters
            Stream<FcChar> stream = table.stream().map(entry -> new FcChar(entry.key(), entry.value()));

            // create a Huffman tree from the stream
            HuffmanTree huffmanTree = new HuffmanTree(stream);

            // print the original text
            Bits original = Bits.fromBytes(text.getBytes());
            Debug.log(Color.YELLOW, "Original data: " + original);
            Debug.log(Color.YELLOW, "Size: " + original.size() + " bits");

            // print the codes
            for (CharCode code : huffmanTree.codes()) {
                Debug.log(Color.BLUE, Debug.Char(code.character()) + " (" + code.frequency() + ") => " + code.bits());
            }

            // print the encoded text
            Bits compressed = huffmanTree.encode(text.getBytes());
            Debug.log(Color.GREEN, "Compressed data: " + compressed);
            Debug.log(Color.GREEN, "Size: " + compressed.size() + " bits");

            // print the compression ratio
            float ratio = (compressed.size() / (float) original.size());
            Debug.log(Color.BLUE_BRIGHT, "Compression ratio: " + String.format("%.2f", ratio * 100.f) + "%");
            Debug.log(Color.BLUE_BRIGHT, "----------------------------------------");
        }
    }

}
