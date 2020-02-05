/** Multidimensional array 
 *  @author Zoe Plaxco
 */

public class MultiArr {

    /**
    {{“hello”,"you",”world”} ,{“how”,”are”,”you”}} prints:
    Rows: 2
    Columns: 3
    
    {{1,3,4},{1},{5,6,7,8},{7,9}} prints:
    Rows: 4
    Columns: 4
     Are there really 4 columns?  I can't index into [0][3]; right?
    */
    public static void printRowAndCol(int[][] arr) {
        //TODO: Your code here!
        System.out.println("Rows: " + arr.length);
        //System.out.println("Columns: " + arr[i].length);
        //Problem, you gotta find what i is.
        //columns, in each row, check the length.  return max value.
        int columns = 0;
        for (int i = 0; i < arr.length; i +=1) {
            if (arr[i].length > columns) {
                columns = arr[i].length;
            }
        }
        System.out.println("Columns: " + columns);
    }

    /**
    @param arr: 2d array
    @return maximal value present anywhere in the 2d array
    */
    public static int maxValue(int[][] arr) {
        int max = 0;
        for (int i = 0; i < arr.length; i += 1) {
            for (int j = 0; j < arr[i].length; j += 1){
                if (arr[i][j] > max) {
                    max = arr[i][j];
                }
            }
        }
        return max;
    }

    /**Return an array where each element is the sum of the 
    corresponding row of the 2d array*/
    public static int[] allRowSums(int[][] arr) {
        int[] arraySum = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int sum = 0;
            for (int j = 0; j < arr[i].length; j +=1){
                sum += arr[i][j];
            }
            arraySum[i] = sum;
        }
        return arraySum;
    }
}