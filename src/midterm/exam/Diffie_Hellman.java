package midterm.exam;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Diffie_Hellman {
	static Init init = new Init();
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Prime p : ");
		long p = 307;//sc.nextLong();
		long g = init.findGenerator(p);
		
		System.out.println("The Generator is : "+g+"\n Make generator and prime public");
		long aR = randomNumberGenerator((int)p);
		BigInteger A = init.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(aR), BigInteger.valueOf(p));
		System.out.println("Alice's Random number : "+aR);
		System.out.println("Alice sends A to Bob : "+A);
		long bR = randomNumberGenerator((int)p);
		BigInteger B = init.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(bR), BigInteger.valueOf(p));
		System.out.println("Bob's Random number : "+bR);
		System.out.println("Bob sends B to Alice : "+B);
		System.out.println("Alice Finds the K(a,b) ");
		BigInteger Ak = init.findSquareMultiply(B, BigInteger.valueOf(aR), BigInteger.valueOf(p));
		System.out.println(Ak);
		System.out.println("Bob Finds the K(a,b) ");
		BigInteger Bk = init.findSquareMultiply(A, BigInteger.valueOf(bR), BigInteger.valueOf(p));
		System.out.println(Bk);
		
		
	}
	
	public static long randomNumberGenerator(int p){
		Random rand = new Random();
		int randomNum = rand.nextInt(((p-1) - 2) + 1) + 2;//Max -> p-1 Min -> 2
		return randomNum;
	}

}
