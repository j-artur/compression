package table;

import java.util.Iterator;
import java.util.Optional;

public class FcList<K, V> implements Iterable<Entry<K, V>> {
    private Entry<K, V> head;

    public FcList() {
        this.head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Entry<K, V> head() {
        return head;
    }

    public Optional<V> set(K key, V value) {
        if (isEmpty()) {
            head = new Entry<>(key, value);
            return Optional.empty();
        }

        Entry<K, V> current = head;

        if (current.key().equals(key))
            return Optional.of(current.setValue(value));

        while (current.next() != null) {
            current = current.next();

            if (current.key().equals(key))
                return Optional.of(current.setValue(value));
        }

        current.setNext(new Entry<>(key, value));
        return Optional.empty();
    }

    private Optional<Entry<K, V>> searchMF(K key) {
        Entry<K, V> current = head;
        Entry<K, V> previous = null;

        while (current != null) {
            if (current.key().equals(key)) {
                current.incrementFrequency();

                if (previous != null) {
                    while (previous.frequency() < current.frequency()) {
                        Entry<K, V> temp = current;
                        current = previous;
                        previous = temp;

                        current.setNext(previous.next());
                        previous.setNext(current);

                        if (previous == head)
                            head = current;
                    }
                }

                return Optional.of(current);
            }

            previous = current;
            current = current.next();
        }

        return Optional.empty();
    }

    public Optional<V> get(K key) {
        Optional<Entry<K, V>> node = searchMF(key);
        return node.map(Entry::value);
    }

    public Optional<V> remove(K key) {
        if (isEmpty())
            return Optional.empty();

        if (head.key().equals(key)) {
            if (head.next() == null) {
                V value = head.value();
                head = null;
                return Optional.of(value);
            } else {
                V value = head.value();
                head = head.next();
                return Optional.of(value);
            }
        }

        Entry<K, V> current = head;
        Entry<K, V> previous = null;

        while (current.next() != null) {
            previous = current;
            current = current.next();

            if (current.key().equals(key)) {
                V value = current.value();
                previous.setNext(current.next());
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private Entry<K, V> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Entry<K, V> next() {
                Entry<K, V> temp = current;
                current = current.next();
                return temp;
            }
        };
    }
}
