import java.util.*;



public class Main {
    public static void  main(String[] args) {
   
        Scanner scn= new Scanner(System.in);
        int n=scn.nextInt();
        
        int[] arr= new int[n];
        for (int i=0; i<n; i++) {
            arr[i]=scn.nextInt();
      }
      int target=scn.nextInt();
      System.out.print(binary(arr, target));
     
    }

    public static int binary (int[] arr, int target) {
       
     
         
         int  low=0;
         int  high=arr.length-1;
          while (low<=high) {
             int mid= (low+high)/2;
             if (arr[mid]==target) {
                return mid;
             } else if (arr[mid]>target) {
                high=mid-1;
             } else {
                low=mid+1;
             }
          }
          return -1;
    }
  
}