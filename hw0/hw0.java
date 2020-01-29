public class hw0 {
	public static void main(String[] args) {
		Test.maxTest();
	}
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
				
			