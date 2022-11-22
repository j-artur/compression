package heap;

import java.util.List;
import java.util.stream.*;

public class MinHeap<T extends Node> {
  private List<T> heap;

  public MinHeap(Stream<T> stream) {
    heap = stream.collect(Collectors.toList());
    heapify();
  }

  private void swap(int i, int j) {
    T tmp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, tmp);
  }

  private void moveUp(int i) {
    int j = (i - 1) / 2;

    if (j >= 0 && heap.get(i).key() < heap.get(j).key()) {
      swap(i, j);
      moveUp(j);
    }
  }

  private void moveDown(int i) {
    int j = 2 * i + 1;

    if (j >= heap.size())
      return;

    if (j < heap.size() - 1) {
      if (heap.get(j).key() > heap.get(j + 1).key())
        j++;
    }

    if (heap.get(j).key() < heap.get(i).key()) {
      swap(i, j);
      moveDown(j);
    }
  }

  private void heapify() {
    for (int i = (heap.size() - 1) / 2; i >= 0; i--) {
      moveDown(i);
    }
  }

  public void add(T it) {
    if (heap.isEmpty()) {
      heap.add(it);
    } else {
      heap.add(it);
      moveUp(heap.size() - 1);
    }
  }

  public T poll() {
    if (heap.isEmpty())
      return null;

    T it = heap.get(0);
    heap.set(0, heap.get(heap.size() - 1));
    heap.remove(heap.size() - 1);

    moveDown(0);

    return it;
  }

  public T peek() {
    if (heap.isEmpty())
      return null;

    return heap.get(0);
  }

  public Stream<T> stream() {
    return heap.stream();
  }

  public boolean isEmpty() {
    return heap.isEmpty();
  }

  public int size() {
    return heap.size();
  }
}
