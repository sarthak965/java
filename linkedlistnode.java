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

 public static Node inserthead(Node head, String val) {
    Node temp = new Node (val, head);
    return temp;
  
 }
 public static Node inserttail(Node head, String val) {
    Node temp = new Node (val, null);
    Node temp2= head;
    while (temp2.next!=null) {
        temp2=temp2.next;
        // did it own my own without video help, so happy :)
    }
    temp2.next=temp;
    return head;
  
 }
 public static Node insertkth(Node head, int k, String val) {
    int count=0;
    Node temp=head;
   // Node temp3=head;
    while (temp!=null) {
        count++;
        if( count== k-1) {
            Node Newnode = new Node(val, temp.next);
                 temp.next=Newnode;
            
            break;
        }

        temp=temp.next;
    }
    
    return head;
 }

 public static Node entervaluebeforen(Node head, String val, String n) {
           Node prev=null; 
           Node temp=head;
           while (temp!=null) {
            if (temp.data==n) {
                Node newnode= new Node (val,temp.next);
                prev.next= newnode;
                break;
            }
            prev=temp;
            temp=temp.next;
           }
           
           return head;
 }
 public static void main(String[] args) {
    // Initialize an array
    String[] array = {"one", "two", "three", "four"};

    // Convert array to linked list
    Node head = arrayToLinkedList(array);
       Node temp= entervaluebeforen(head, "sarthak", "three");
       printLinkedList(temp);

    // Print the linked list
    
}

}
