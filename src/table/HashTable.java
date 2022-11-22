package table;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class HashTable<K, V> implements Iterable<Entry<K, V>> {
    int size;
    FcList<K, V>[] entries;

    // Creates an array of entries with the given size
    public HashTable(int size) {
        this.size = size;
        entries = new FcList[size];
        for (int i = 0; i < size; i++) {
            entries[i] = new FcList<>();
        }
    }

    // Hashes the key to an index in the entries array
    private int hash(K key) {
        return ((key.hashCode() % size) + size) % size;
    }

    // Gets the value associated with the given key
    public Optional<V> get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = hash(key);
        FcList<K, V> list = entries[index];

        return list.get(key);
    }

    // Sets the value for the given key, and returns the old value
    public Optional<V> set(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }

        int index = hash(key);
        FcList<K, V> list = entries[index];

        return list.set(key, value);
    }

    // Removes the entry with the given key, and returns the value
    public Optional<V> remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = hash(key);
        FcList<K, V> list = entries[index];

        return list.remove(key);
    }

    // Returns an iterator for the entries array
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private HashTable<K, V> table;
            private int index;
            private Iterator<Entry<K, V>> listIterator;

            {
                table = HashTable.this;
                index = 0;
                listIterator = table.entries[index].iterator();
            }

            public boolean hasNext() {
                if (listIterator.hasNext()) {
                    return true;
                }

                for (int i = index + 1; i < table.size; i++) {
                    if (!table.entries[i].isEmpty()) {
                        return true;
                    }
                }

                return false;
            }

            public Entry<K, V> next() {
                if (listIterator.hasNext()) {
                    return listIterator.next();
                }

                for (int i = index + 1; i < table.size; i++) {
                    if (!table.entries[i].isEmpty()) {
                        index = i;
                        listIterator = table.entries[i].iterator();
                        return listIterator.next();
                    }
                }

                return null;
            }
        };
    }

    // Stream
    public Stream<Entry<K, V>> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }
}
