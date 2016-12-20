package finalexam.crypto;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

import midterm.*;
import midterm.exam.TextConv;
public class ECCryptoSystem extends ECC{
	public static TextConv txt = new TextConv();
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		System.out.println("Enter a : ");
		BigInteger a = sc.nextBigInteger();
		System.out.println("Enter b : ");
		BigInteger b = sc.nextBigInteger();
		System.out.println("Enter prime p : ");
		BigInteger p = sc.nextBigInteger();
		Point G = new Point(BigInteger.valueOf(2710),BigInteger.valueOf(930));
		G.printPoint();
		String s = sc1.nextLine();
		ECCryptoSystem ec = new ECCryptoSystem(a, b, p, G, s);
	}
	
	public ECCryptoSystem(BigInteger a,BigInteger b,BigInteger p,Point G,String s){
		int nA = (int) randomNumberGenerator(p.intValue());
		int nB = (int) randomNumberGenerator(p.intValue());
		System.out.println("Private Key of Alice nA : "+nA+"\nPrivate Key of Bob nB : "+nB);
		Point PA = findXP(nA, G, p, a);
		Point PB = findXP(nB, G, p, a);
		System.out.println("Public Key of Alice PA : ("+PA.x+","+PA.y+").");
		System.out.println("Public Key of Bob PB : ("+PB.x+","+PB.y+").");
		//********Encryption********
		txt.generateBlockSize(p);
		System.out.println("Enter text to be encrypted");
		String[] m = txt.createNum(s);
		String[] m1 = new String[m.length],m2 = new String[m.length];
		for(int i=0;i<m.length;i++){
			m1[i] = m[i].substring(0,m[i].length()/2);
			m2[i] = m[i].substring(m[i].length()/2,	m[i].length());
			System.out.println("m(1):"+m1[i]+"\tm(2):"+m2[i]);
		}
		System.out.println("Alice finds Y(0)=nA*G");
		Point y0 = findXP(nA, G, p, a);
		System.out.println("Y(0)=("+y0.x+","+y0.y+").");
		System.out.println("Alice computes Mask/Veil (c(1),c(2))=nA*PB");
		Point C = findXP(nA, PB, p, a);
		System.out.println("(c(1),c(2)) = ("+C.x+","+C.y+").");
		String[] y1= new String[m.length],y2 = new String[m.length];
		System.out.println("Alice computes y1=c1*m1 mod p and y2=c2*m2 mod p");
		for(int i=0;i<m.length;i++){
			y1[i] = init.findSquareMultiply(C.x.multiply(new BigInteger(y1[i])), BigInteger.ONE, p).toString();
			y2[i] = init.findSquareMultiply(C.y.multiply(new BigInteger(y2[i])), BigInteger.ONE, p).toString();
			System.out.println("y1 : "+y1[i]+"\ty2 : "+y2[i]);
		}
	}
	
	
	public static long randomNumberGenerator(int p2){
		Random rand = new Random();
		int randomNum = rand.nextInt(((p2-1) - 2) + 1) + 2;//Max -> p-1 Min -> 2
		return randomNum;
	}

}
