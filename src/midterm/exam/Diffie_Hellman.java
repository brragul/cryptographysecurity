package midterm.exam;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Diffie_Hellman {
	Init init = new Init();
	public long p,g,aR,bR;
	public BigInteger A,B,Ak,Bk;
	/*
	 * p -> prime
	 * g -> generator
	 * aR -> Alice's Random number
	 * bR -> Bob's Random number
	 * A -> Alice's key sent to bob
	 * B -> Bob's key sent to alice
	 * Ak,Bk -> Alice and Bob's secret key
	 * */
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("Prime p : ");
		long p = sc.nextLong();
		new Diffie_Hellman(p);
	}
	
	public Diffie_Hellman(long p) {
		g = init.findGenerator(p);
		System.out.println("The Generator is : "+g+"\n Make generator and prime public");
		aR = randomNumberGenerator((int)p);
		 A = init.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(aR), BigInteger.valueOf(p));
		System.out.println("Alice's Random number : "+aR);
		System.out.println("Alice sends A to Bob : "+A);
		bR = randomNumberGenerator((int)p);
		 B = init.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(bR), BigInteger.valueOf(p));
		System.out.println("Bob's Random number : "+bR);
		System.out.println("Bob sends B to Alice : "+B);
		System.out.println("Alice Finds the K(a,b) ");
		 Ak = init.findSquareMultiply(B, BigInteger.valueOf(aR), BigInteger.valueOf(p));
		System.out.println(Ak);
		System.out.println("Bob Finds the K(a,b) ");
		 Bk = init.findSquareMultiply(A, BigInteger.valueOf(bR), BigInteger.valueOf(p));
		System.out.println(Bk);
		
		
	}
	
	public static long randomNumberGenerator(int p2){
		Random rand = new Random();
		int randomNum = rand.nextInt(((p2-1) - 2) + 1) + 2;//Max -> p-1 Min -> 2
		return randomNum;
	}

}
