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
           Node temp=deleteel(head, "four");
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
  public static Node deletekvalue(Node head, int k) {
     if (head== null) {
        return null;
     }
     if (k==1) {
        Node temp=head;
        head=head.next;
        return head;
     }

     int count =0;
     Node temp=head;
     while (temp!=null) {
        count++; 
        if (count==k-1){
            temp.next=temp.next.next;
            break;
        }
        temp=temp.next;
     }
     return head;
  }

  public static Node deleteel(Node head, String el) {
    if (head== null) {
       return null;
    }
    if (head.data==el) {
       Node temp=head;
       head=head.next;
       return head;
    }

    Node prev=null;
    Node temp=head;
    while (temp!=null) {
       
       if (temp.data==el){
           prev.next=prev.next.next;
           break;
       }
       prev= temp;
       temp=temp.next;
    }
    return head;
 }
}
