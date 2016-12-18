package midterm.exam;

import java.math.BigInteger;
import java.util.Scanner;

public class Elgamma_Signature {
	public static TextConv txt = new TextConv();
	public static Init obj = new Init();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		System.out.println("Prime P : ");
		/******Key Generation*******/
		long p = sc.nextLong();
		txt.generateBlockSize(BigInteger.valueOf(p));
		Diffie_Hellman DF = new Diffie_Hellman(p);
		long g = obj.findGenerator(p);
		long r =DF.randomNumberGenerator((int)p);
		BigInteger pBig = BigInteger.valueOf(p);
		BigInteger K = obj.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(r), pBig);
		System.out.println("generator g : "+g+"\n Random no r : "+r+"\n K =g^r mod p: "+ K+"\ng,p and K are in public domain");
		/******Signing*******/
		long R = DF.randomNumberGenerator((int)p);
		while(obj.findGCD(BigInteger.valueOf(R), BigInteger.valueOf(p-1)).compareTo(BigInteger.ONE)!=0){
			R = DF.randomNumberGenerator((int)p);
		}
		System.out.println("Random Number R : "+R);
		
		BigInteger X = obj.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(R), pBig);
		System.out.println("X =g^R mod p : "+X);
		System.out.println("Please enter text to be Signed");
		String s =sc1.nextLine();
		String[] M = txt.createNum(s);
		BigInteger[] Y = new BigInteger[M.length];
		System.out.print("Value of Y are rX+RY mod p: ");
		for(int i=0;i<M.length;i++){
			Y[i] = X.multiply(BigInteger.valueOf(r));
			Y[i] = BigInteger.valueOf(Long.parseLong(M[i])).subtract(Y[i]);
			obj.quo.clear();
			BigInteger temp = obj.findModuloInverse(BigInteger.valueOf(R), BigInteger.valueOf(p-1));
			Y[i] = temp.multiply(Y[i]);
			Y[i] = Y[i].mod(BigInteger.valueOf(p-1));
			System.out.print(Y[i]+" ");
		}
		System.out.println("\n(X,Y) is signature of M\nA : ");
		System.out.println("A = K^X*X^Y mod p");
		BigInteger[] A = new BigInteger[M.length];
		for(int i=0;i<A.length;i++){
			A[i] = obj.findSquareMultiply(obj.findSquareMultiply(K, X, pBig).multiply(obj.findSquareMultiply(X, Y[i], pBig)), BigInteger.ONE, pBig);
			System.out.print(A[i]+" ");
		}
		BigInteger[] A2 = new BigInteger[A.length];
		System.out.println("A2 = g^M mod p");
		for(int i=0;i<A.length;i++){
			A2[i] = obj.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(Long.parseLong(M[i])), pBig);
			System.out.print(A2[i]+" ");
			if(A[i].compareTo(A2[i])!=0){
				System.out.println("\nMessage is breached");
				return;
			}
		}
		System.out.println("\nMessage received correctly");
	}
		

}
