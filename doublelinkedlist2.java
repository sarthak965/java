

class Node {
    int data;
    Node next;
    Node back;

    Node  (int data1, Node next1, Node back1) {
        this.data=data1;
        this.next=next1;
        this.back=back1;
    }
    Node(int data) {
        this.data = data;
        this.next = null;
        this.back = null; 
    }
} 

public class doublelinkedlist2 {
  

    public static Node  arraytodll (int[] arr) {
        Node head= new Node(arr[0]);
        Node temp=head;
         for (int i=1; i<arr.length;i++) {
            Node newNode= new Node(arr[i]);
            newNode.back=temp;
            temp.next=newNode;
            temp=temp.next;
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
    
    
    
    public static Node deletehead(Node head) {
        Node temp=head;
       head=head.next;
       head.back=null;
       temp.next=null;
        return head;
    }

  
    
    public static Node deletekth(Node head, int k) {
        if (head== null) {
            return null;
         }
        if (k==1) {
            Node temp=head;
            head=head.next;
            head.back=null;
            temp.next=null;
            return head;
        }
        int count=0;
        Node temp=head;
       // Node front=temp.next;
        Node prev=null;
        while (temp!=null) {
            count++;
            if (count==k) {
               if (temp.next==null) {
                prev.next=null;
                break;
               }
               
              prev.next=temp.next;
              temp.next.back=prev;
              temp.next=null;
              temp.back=null;
              break;
                }
            
            prev=temp;
            temp=temp.next;
          //  front=front.next;
        }
        return head;

          
    }
    public static Node deletetail(Node head) {

        if (head==null || head.next==null) {
            return null;
            
        }

        Node temp=head;
        while (temp.next != null)  {
           temp=temp.next;
        }
        Node newnode=temp.back;
        newnode.next=null;
        temp.back=null;
             return head;
     }
    public static Node deletedata(Node head, int k) {

        if (head==null ) {
            return null;
        }
        if (head.data==k) {
            Node temp = head;
            head=head.next;
            return head;
        }
        Node temp=head;
        Node prev=null;
        Node front = head.next;
        while (temp!= null) {

            if ( temp.next==null ) {
                deletetail(head);
                break;
            }
            if (temp.data==k) {
                prev.next=front;
                front.back=prev;
                temp.next=null;
                temp.back=null;
                break;
            }
            prev=temp;
              temp=temp.next;
              
              front=front.next;
        }
        return head;

        
    }
    public static Node inserthead(Node  head, int k) {
        Node temp = new Node(k, head, null);
        head.back=temp;
        return temp;
    }
    public static Node inserttail(Node head, int K) {
     Node   temp=head;
        while (temp.next!= null) {
            temp=temp.next;
        }
        Node newtail = new Node(K, null, temp);
         temp.next= newtail;
         return head;
    }
    public static void main(String[] args) {
        int[] arr = {7,5,6,8,2,3};
        Node head = arraytodll(arr);
      //  Node temp= deletehead(head);
       // Node temp= deletetail(head);
      Node temp = inserttail(head,234);
        printLinkedList(temp);
     
      //  System.out.print(head+" ");
    }
    
}
