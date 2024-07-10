class Node {
    String data;
    Node next;
    Node(String data1, Node next1) {
        this.data = data1;  // Initialize data with the provided value
        this.next = next1;  // Initialize next with the provided reference
    }
    Node(String data) {
        this.data = data;
        this.next = null;
    }
}

public class linkedlistnode {
    public static void main(String[] args) {
        // Initialize an array
        String[] array = {"one", "two", "three", "four"};

        // Convert array to linked list
        Node head = arrayToLinkedList(array);
         //  Node temp=removetail(head);
           printLinkedList(head);

        // Print the linked list
        
    }

    public static Node arrayToLinkedList(String[] array) {
        if (array.length == 0) {
            return null;
        }

        Node head = new Node(array[0]);
        Node current = head;

        for (int i = 1; i < array.length; i++) {
            current.next = new Node(array[i]);
            current = current.next;
        }

        return head;
    }

    public static void printLinkedList(Node head) {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
    }

    public static Node removetail(Node head) {

        if (head == null || head.next == null)  return null;
            
        Node temp= head;
        while ( temp.next.next != null) {
            temp=temp.next;
        }
        temp.next=null;
        return head;
    

    }
}
