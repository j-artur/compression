package table;

public class Entry<K, V> {
    private K key;
    private V value;
    private int frequency;
    private Entry<K, V> next;

    Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.frequency = 0;
    }

    public K key() {
        return key;
    }

    public V value() {
        return value;
    }

    V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    int frequency() {
        return frequency;
    }

    void incrementFrequency() {
        this.frequency++;
    }

    Entry<K, V> next() {
        return next;
    }

    void setNext(Entry<K, V> next) {
        this.next = next;
    }
}
