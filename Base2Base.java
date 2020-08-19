import java.math.BigInteger;
import java.util.*;

public class Base2Base {

public static void main(String[] args) {
	
		long start = System.nanoTime();
		String symbols = "";
		StringBuilder builder = new StringBuilder();
		for (int i = 161; i < 65536; i++) {
			//System.out.println(i + ": " + String.valueOf((char) i));
			builder.append(String.valueOf((char) i));
		}
		symbols = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".concat(builder.toString());
		System.out.println("Total symbols: " + symbols.length());
		System.out.println("Took " + (System.nanoTime() - start) / 1000000.0 + " ms to initialize.");
		
		/* Find duplicates
		Map<Character,Integer> map = new HashMap<Character,Integer>();
		for (int i = 0; i < symbols.length(); i++) {
		  char c = symbols.charAt(i);
		  if (map.containsKey(c)) {
		    //int cnt = map.get(c);
		    //map.put(c, ++cnt);
		    System.out.println(c);
		  } else {
		    map.put(c, 1);
		  }
		}
		*/
		
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Enter Base (FROM): ");
		BigInteger base = scnr.nextBigInteger();
		System.out.println("Enter Base (TO): ");
		BigInteger base2 = scnr.nextBigInteger();
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
		
		int max = 0;
		BigInteger temp = number;
		while (temp.compareTo(BigInteger.ZERO) >= 0) {
			temp = temp.subtract(base2.pow(max));
			
			if (temp.compareTo(BigInteger.ZERO) >= 0)
				max++;
			
			if (number.subtract(base2.pow(max)).compareTo(BigInteger.ZERO) >= 0)
				max++;
		}
		
		if (base2.compareTo(BigInteger.ONE) == 0)
			max = number.intValue() + 1; 
		
		basedNumber = "";
		BigInteger n = number;
		
		for (int i = max; i > 0; i--) {
			BigInteger div = base2.pow(i-1);
			int result = n.divide(div).mod(base2).intValue();
			number.subtract(div.multiply(BigInteger.valueOf(result)));
			basedNumber += symbols.substring(result, result+1);
		}
		
		System.out.println("Your Number in Base " + base2.toString() + ":");
		System.out.println(basedNumber);

		scnr.close();
	}
	
}
