package navymail.rest.Travels;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws Exception {
		Travelers_missions t = new Travelers_missions();
		int[] a = t.showTraveler_missions(2014);
		System.out.println(Arrays.toString(a));
		System.out.println("JKJK");
	}
}
