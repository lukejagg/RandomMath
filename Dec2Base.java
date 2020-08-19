import java.math.BigInteger;
import java.util.Scanner;

public class Dec2Base {

	static String symbols = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZαβγδεζηθικλµνξπρστυφχψω";
	
	public static void main(String[] args) {
		
//		String[] symbols = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
//				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Enter Base: ");
		BigInteger base = scnr.nextBigInteger();
		System.out.println("Enter Number: ");
		BigInteger number = scnr.nextBigInteger();
		
		int max = 0;
		BigInteger temp = number;
		while (temp.compareTo(BigInteger.ZERO) >= 0) {
			temp = temp.subtract(base.pow(max));
			
			if (temp.compareTo(BigInteger.ZERO) >= 0)
				max++;
			
			if (number.subtract(base.pow(max)).compareTo(BigInteger.ZERO) >= 0)
				max++;
		}
		
		if (base.compareTo(BigInteger.ONE) == 0)
			max = number.intValue() + 1; 
		
		String basedNumber = "";
		BigInteger n = number;
		
		for (int i = max; i > 0; i--) {
			BigInteger div = base.pow(i-1);
			int result = n.divide(div).mod(base).intValue();
			number.subtract(div.multiply(BigInteger.valueOf(result)));
			basedNumber += symbols.substring(result, result+1);
		}
		
		System.out.println("Your Number in Base " + base.toString() + ":");
		System.out.println(basedNumber + ".");
		
		scnr.close();
		
	}

}
