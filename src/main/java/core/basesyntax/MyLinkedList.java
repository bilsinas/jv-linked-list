package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
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
            tail.setNext(newNode);
            newNode.setPrev(tail);
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
        Node<T> prev = current.getPrev();

        newNode.setNext(current);
        newNode.setPrev(prev);
        current.setPrev(newNode);

        if (prev != null) {
            prev.setNext(newNode);
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
        return getNode(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = getNode(index);
        final T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> toRemove = getNode(index);
        return unlink(toRemove);
    }

    private T unlink(Node<T> toRemove) {
        final T oldValue = toRemove.getValue();
        Node<T> prev = toRemove.getPrev();
        Node<T> next = toRemove.getNext();

        if (prev == null) {
            head = next;
        } else {
            prev.setNext(next);
            toRemove.setPrev(null);
        }

        if (next == null) {
            tail = prev;
        } else {
            next.setPrev(prev);
            toRemove.setNext(null);
        }

        toRemove.setValue(null);
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        if (object == null) {
            while (current != null) {
                if (current.getValue() == null) {
                    unlink(current);
                    return true;
                }
                current = current.getNext();
            }
        } else {
            while (current != null) {
                if (object.equals(current.getValue())) {
                    unlink(current);
                    return true;
                }
                current = current.getNext();
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
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        return current;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
