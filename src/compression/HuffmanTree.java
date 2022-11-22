package compression;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import bits.Bit;
import bits.Bits;
import heap.MinHeap;

public class HuffmanTree {
    private Node root;
    final static char BLANK = '\0';

    public HuffmanTree(Stream<FcChar> items) {
        MinHeap<Node> queue = new MinHeap<>(items.map(it -> new Node(it.character(), it.frequency())));

        root = null;

        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            Node node = new Node(BLANK, left.frequency() + right.frequency());
            node.setLeft(left);
            node.setRight(right);
            queue.add(node);
        }

        root = queue.poll();
    }

    public Bits encode(char ch) {
        return encode(root, ch, new Bits());
    }

    private Bits encode(Node node, char ch, Bits bits) {
        if (node == null)
            return null;

        if (node.character() == ch)
            return new Bits(bits);

        Bits left = encode(node.left(), ch, new Bits(bits));
        if (left != null) {
            left.append(new Bit(0));
            return left;
        }

        Bits right = encode(node.right(), ch, new Bits(bits));
        if (right != null) {
            right.append(new Bit(1));
            return right;
        }

        return null;
    }

    public List<CharCode> codes() {
        return codes(root, new Bits());
    }

    private List<CharCode> codes(Node node, Bits bits) {
        if (node == null)
            return List.of();

        if (node.character() != BLANK)
            return new ArrayList<>(List.of(new CharCode(node.character(), node.frequency(), new Bits(bits))));

        List<CharCode> left = codes(node.left(), new Bits(bits));
        left.forEach(code -> code.bits().append(new Bit(0)));

        List<CharCode> right = codes(node.right(), new Bits(bits));
        right.forEach(code -> code.bits().append(new Bit(1)));

        left.addAll(right);
        return left;
    }

    public Bits encode(byte[] bytes) {
        Bits bits = new Bits();

        for (byte b : bytes) {
            char c = (char) b;
            bits.append(encode(c));
        }

        return bits;
    }

    public Stream<Node> nodes() {
        return nodes(root).stream();
    }

    private java.util.List<Node> nodes(Node node) {
        java.util.List<Node> nodes = new java.util.ArrayList<>();

        if (node == null)
            return nodes;

        nodes.add(node);
        nodes.addAll(nodes(node.left()));
        nodes.addAll(nodes(node.right()));

        return nodes;
    }
}
