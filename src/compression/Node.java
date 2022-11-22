package compression;

public class Node implements heap.Node {
    private char character;
    private int frequency;
    private Node left;
    private Node right;

    public Node(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public int frequency() {
        return frequency;
    }

    public char character() {
        return character;
    }

    Node left() {
        return left;
    }

    Node right() {
        return right;
    }

    void setLeft(Node left) {
        this.left = left;
    }

    void setRight(Node right) {
        this.right = right;
    }

    @Override
    public int key() {
        return frequency;
    }
}
