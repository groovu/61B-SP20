package hw0;

public class hw0 {
  public static void main(String[] args) {
      //max(new int[] { 0, -5, 2, 14, 10 });
      //threeSum(new int[]{-6, 2, 4});
      //threeSumDistinct(new int[]{-6, 2, 4});
  }

  public static int max(int[] a) {
      // check that array is not empty.
      int max = 0;
      for (int i = 0; i < a.length;  i += 1) {
          if (a[i] > max) {
              max = a[i];
          }
      }
    return max;
    // works
  }
  
  // is my type correct below?
  public static boolean threeSum(int[] a) {
    //maybe try a naive approach
    //System.out.println(a[0]);
    Boolean check = false;
    for (int x = 0; x < a.length; x +=1) {
      if (check == true) {
        break;
      } else {
      for (int y = 0; y < a.length; y +=1) {
        for (int z = 0; z < a.length; z +=1) {
          if (a[x] + a[y] + a[z] == 0) {
            check = true;
            }
          }
        }
      }
    }
  return check; 
  }
  public static boolean threeSumDistinct(int[] a) {
    //maybe try a naive approach
    //System.out.println(a[0]);
    Boolean check = false;
    for (int x = 0; x < a.length; x +=1) {
      if (check == true) {
        break;
      } else {
      for (int y = 1; y < a.length; y +=1) {
        for (int z = 2; z < a.length; z +=1) {
          if (a[x] + a[y] + a[z] == 0) {
            check = true;
            }
          }
        }
      }
    }
  return check; 
  }  
}