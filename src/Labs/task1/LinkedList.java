package Labs.task1;

public class LinkedList {

    private int size = 0;
    private Node first;
    private Node last;

    private static class Node {
        double data;
        Node prev;
        Node next;

        public Node(double data) {
            this.data = data;
        }
    }

    public LinkedList() {
        first = null;
        last = null;
    }

    public void add(double data) {
        Node newNode = new Node(data);
        if (first == null) {
            newNode.next = null;
            newNode.prev = null;
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    public boolean contains(double data) {
        for (int i = 0; i < size; i++) {
            if (get(i) == data) {
                return true;
            }
        }
        return false;
    }

    public double get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node result = first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }

        return result.data;
    }

    @Override
    public String toString() {
        Node current = first;
        StringBuilder str = new StringBuilder("[");
        if (current != null) {
            str.append(current.data);
            current = current.next;
        }
        while (current != null) {
            str.append(", ").append(current.data);
            current = current.next;
        }
        str.append("]");
        return str.toString();
    }

    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void remove(double element) {
        for (Node current = first; current.next != null; current = current.next) {
            if (current.next.data == element) {
                current.next = current.next.next;
                size--;
                return;
            }
        }
        first = first.next;
        size--;
    }
}
