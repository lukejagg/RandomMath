import java.math.BigInteger;
import java.util.Scanner;

public class Base2Dec {

	public static void main(String[] args) {
		
		String symbols = Dec2Base.symbols;
		
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Enter Base: ");
		BigInteger base = scnr.nextBigInteger();
		System.out.println("Enter Based Number: ");
		scnr.nextLine();
		while (!scnr.hasNextLine()) { }
		String basedNumber = scnr.nextLine();
		
		int b = basedNumber.length()-1;
		
		BigInteger number = BigInteger.ZERO;
		for (int i = 0; i < basedNumber.length(); i++) {
			int value = 0;
			for (int s = 0; s < symbols.length(); s++) {
				if (symbols.substring(s, s+1).equals(basedNumber.substring(i, i+1))) {
					value = s;
					break;
				}
			}
			number = number.add(BigInteger.valueOf(value).multiply(base.pow(b-i)));
		}
		
		System.out.println(number.toString());
		
		scnr.close();
		
	}

}
