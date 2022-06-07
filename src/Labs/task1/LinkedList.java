package Labs.task1;

public class LinkedList {

    private int size = 0;
    private Item first;
    private Item last;



    public LinkedList() {
        first = null;
        last = null;
    }

    public void add(double data) {
        Item newItem = new Item(data);
        if (first == null) {
            newItem.next = null;
            newItem.prev = null;
            first = newItem;
        } else {
            last.next = newItem;
            newItem.prev = last;
        }
        last = newItem;
        size++;
    }

    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    public void remove(double element) {
        for (Item current = first; current.next != null; current = current.next) {
            if (current.next.data == element) {
                current.next = current.next.next;
                size--;
                return;
            }
        }
        first = first.next;
        size--;
    }

    public double get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Item result = first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        System.out.println(result.data);
        return result.data;
    }

    public boolean isEmpty() {
        if(size == 0){
            return true;
        }
        else
            return false;
    }

    @Override
    public String toString() {
        Item current = first;
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

}
