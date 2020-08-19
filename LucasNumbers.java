import java.math.BigInteger;

public class LucasNumbers {

	public static void main(String[] args) {
		for (int i = 1; i < 100; i++) {
			BigInteger m = BigInteger.valueOf(i);
			int n = m.pow(4).add(m.pow(2)).mod(m.add(BigInteger.ONE)).compareTo(BigInteger.ZERO);
			if (n == 1) System.out.println(i);
		}
	}

}
