package midterm.exam;

import java.math.BigInteger;
import java.util.Scanner;

public class Elgamma_Signature {
	public static TextConv txt = new TextConv();
	public static Init obj = new Init();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Prime P : ");
		/******Key Generation*******/
		long p = 105019;//sc.nextLong();
		txt.generateBlockSize(BigInteger.valueOf(p));
		Diffie_Hellman DF = new Diffie_Hellman(p);
		long g = obj.findGenerator(p);
		long r =DF.randomNumberGenerator((int)p);
		BigInteger pBig = BigInteger.valueOf(p);
		BigInteger K = obj.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(r), pBig);
		System.out.println("generator g : "+g+"\n Random no r : "+r+"\n K : "+ K+"\ng,p and K are in public domain");
		/******Signing*******/
		long R = DF.randomNumberGenerator((int)p);
		while(obj.findGCD(BigInteger.valueOf(R), BigInteger.valueOf(p-1)).compareTo(BigInteger.ONE)!=0){
			R = DF.randomNumberGenerator((int)p);
		}
		System.out.println("Random Number R : "+R);
		
		BigInteger X = obj.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(R), pBig);
		System.out.println("X : "+X);
		System.out.println("Please enter text to be Signed");
		String s = "Do not send mails like plain text in email or you are gone";//sc1.nextLine();
		String[] M = txt.createNum(s);
		BigInteger[] Y = new BigInteger[M.length];
		System.out.print("Value of Y are : ");
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
		
		BigInteger[] A = new BigInteger[M.length];
		for(int i=0;i<A.length;i++){
			A[i] = obj.findSquareMultiply(obj.findSquareMultiply(K, X, pBig).multiply(obj.findSquareMultiply(X, Y[i], pBig)), BigInteger.ONE, pBig);
			System.out.print(A[i]+" ");
		}
		BigInteger[] A2 = new BigInteger[A.length];
		for(int i=0;i<A.length;i++){
			A2[i] = obj.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(Long.parseLong(M[i])), pBig);
			if(A[i].compareTo(A2[i])!=0){
				System.out.println("\nMessage is breached");
				return;
			}
		}
		System.out.println("\nMessage received correctly");
	}
		

}
