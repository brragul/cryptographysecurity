package midterm.exam;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class ZeroKnowledgeProof {
	static Init init = new Init();
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Prime P");
		BigInteger p = BigInteger.valueOf(7);//sc.nextBigInteger();
		System.out.println("Enter Prime Q");
		BigInteger q = BigInteger.valueOf(11);//sc.nextBigInteger();
		BigInteger n = p.multiply(q);
		BigInteger pri1 = BigInteger.valueOf(randomNumberGenerator(100));
		BigInteger pri2 = BigInteger.valueOf(randomNumberGenerator(100));
		System.out.println("TA chooses Alice's Private ID priv1,priv2 -> "+pri1+","+pri2);
		BigInteger pub1 = init.findModuloInverse(pri1.pow(2), n	);
		BigInteger pub2 = init.findModuloInverse(pri2.pow(2), n	);
		System.out.println("TA finds Public key of each user Alice pub1,pub2 -> "+pub1+","+pub2);
		System.out.println("Alice ID is public\n Nobody knows Alice's private id\nAlice's Action");
		System.out.println("Alice selects random numbers a,b,c");
		BigInteger a = BigInteger.valueOf(randomNumberGenerator(100));
		BigInteger b = BigInteger.valueOf(randomNumberGenerator(100));
		BigInteger c = BigInteger.valueOf(randomNumberGenerator(100));
		System.out.println("a,b,c -> "+a+","+b+","+c);
		System.out.println("Alice Computes A,B,C A=(a^2)mod n B=(b^2)mod n C=(c^2)mod n");
		BigInteger A = init.findSquareMultiply(a.pow(2), BigInteger.ONE, n);
		BigInteger B = init.findSquareMultiply(b.pow(2), BigInteger.ONE, n);
		BigInteger C = init.findSquareMultiply(c.pow(2), BigInteger.ONE, n);
		System.out.println("A,B,C -> "+A+","+B+","+C);
		int[][] E = new int[][]{{0,1},{1,0},{1,1}};
		System.out.println("Bob chooses a random 3x2 matrix and sends to alice");
		System.out.println("Alice computes M,P,Q and sends to Bob");
		BigInteger M = init.findSquareMultiply(a.multiply(pri1.pow(E[0][0]).multiply(pri2.pow(E[0][1]))),BigInteger.ONE,n);
		BigInteger P = init.findSquareMultiply(b.multiply(pri1.pow(E[1][0]).multiply(pri2.pow(E[1][1]))),BigInteger.ONE,n);
		BigInteger Q = init.findSquareMultiply(c.multiply(pri1.pow(E[2][0]).multiply(pri2.pow(E[2][1]))),BigInteger.ONE,n);
		System.out.println("M,P,Q -> "+M+","+P+","+Q);
		System.out.println("Bob's Verification ");
		BigInteger BobA = init.findSquareMultiply((M.pow(2)).multiply(pub1.pow(E[0][0]).multiply(pub2.pow(E[0][1]))),BigInteger.ONE,n);
		BigInteger BobB = init.findSquareMultiply((P.pow(2)).multiply(pub1.pow(E[1][0]).multiply(pub2.pow(E[1][1]))),BigInteger.ONE,n);
		BigInteger BobC = init.findSquareMultiply((Q.pow(2)).multiply(pub1.pow(E[2][0]).multiply(pub2.pow(E[2][1]))),BigInteger.ONE,n);
		System.out.println("BobA,BobB,BobC -> "+BobA+","+BobB+","+BobC);
		if(BobA.compareTo(A)==0&&BobB.compareTo(B)==0&&BobC.compareTo(C)==0){
			System.out.println("Bob Accepts Alice's Identity");
		}else{
			System.out.println("Bob knows it's not Alice");
		}
	}

	
	public static long randomNumberGenerator(int p2){
		Random rand = new Random();
		int randomNum = rand.nextInt(((p2-1) - 2) + 1) + 2;//Max -> p-1 Min -> 2
		return randomNum;
	}
}
