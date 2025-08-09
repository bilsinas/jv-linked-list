package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;
        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value); // Додати в кінець
            return;
        }
        Node<T> newNode = new Node<>(value);
        Node<T> current = getNode(index);
        Node<T> prev = current.prev;

        newNode.next = current;
        newNode.prev = prev;
        current.prev = newNode;

        if (prev != null) {
            prev.next = newNode;
        } else {
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> toRemove = getNode(index);
        return unlink(toRemove);
    }

    private T unlink(Node<T> toRemove) {
        T oldValue = toRemove.value;
        Node<T> prev = toRemove.prev;
        Node<T> next = toRemove.next;

        if (prev == null) { // видаляємо перший елемент
            head = next;
        } else {
            prev.next = next;
            toRemove.prev = null;
        }

        if (next == null) { // видаляємо останній елемент
            tail = prev;
        } else {
            next.prev = prev;
            toRemove.next = null;
        }

        toRemove.value = null; // допомагаємо GC
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        if (object == null) {
            while (current != null) {
                if (current.value == null) {
                    unlink(current);
                    return true;
                }
                current = current.next;
            }
        } else {
            while (current != null) {
                if (object.equals(current.value)) {
                    unlink(current);
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) current = current.prev;
        }
        return current;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
}
