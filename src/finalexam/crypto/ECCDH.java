package finalexam.crypto;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

import midterm.*;
import midterm.exam.TextConv;
public class ECCDH extends ECC{
	public static TextConv txt = new TextConv();
	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		Scanner sc1 = new Scanner(System.in);
//		System.out.println("Enter a : ");
//		BigInteger a = sc.nextBigInteger();
//		System.out.println("Enter b : ");
//		BigInteger b = sc.nextBigInteger();
//		System.out.println("Enter prime p : ");
//		BigInteger p = sc.nextBigInteger();
//		Point G = new Point(BigInteger.valueOf(2710),BigInteger.valueOf(930));
//		G.printPoint();
//		String s = sc1.nextLine();
//		ECCryptoSystem ec = new ECCryptoSystem(a, b, p, G, s,null,null);
		new ECCDH(BigInteger.valueOf(2), BigInteger.valueOf(9), BigInteger.valueOf(7919), new Point(BigInteger.valueOf(6925),BigInteger.valueOf(575)), "trump won the election",null,null);
	}
	
	public ECCDH(BigInteger a,BigInteger b,BigInteger p,Point G,String s,Integer nA,Integer nB){
		if(nA == null||nB==null)
		{
		 nA = (int) randomNumberGenerator(p.intValue());
		 nB = (int) randomNumberGenerator(p.intValue());
		}
		Integer orderOfPoint = findOrder(G, p, a);
		//Check if the private keys multiplied with generator Point will point to infinity
		if(nA%orderOfPoint==0||nB%orderOfPoint==0){
			throw new IllegalArgumentException("****Error Bad Private Key****");
		}
		
		System.out.println("Private Key of Alice nA : "+nA+"\nPrivate Key of Bob nB : "+nB);
		Point PA = findXP(nA, G, p, a);
		Point PB = findXP(nB, G, p, a);
		System.out.println("Public Key of Alice PA : ("+PA.x+","+PA.y+").");
		System.out.println("Public Key of Bob PB : ("+PB.x+","+PB.y+").");
		//********Encryption********
		System.out.println("\n\nEncryption\n\n");
		txt.generateBlockSize(p);
		System.out.println("Enter text to be encrypted");
		String[] m = txt.createNum(s);
		String[] m1 = new String[m.length],m2 = new String[m.length];
		for(int i=0;i<m.length;i++){
			m1[i] = m[i].substring(0,m[i].length()/2);
			m2[i] = m[i].substring(m[i].length()/2,	m[i].length());
			System.out.println("m(1):"+m1[i]+"\tm(2):"+m2[i]);
		}
		System.out.println("Alice finds y0=nA*G");
		Point y0 = findXP(nA, G, p, a);
		System.out.println("Y0 =("+y0.x+","+y0.y+").");
		System.out.println("Alice computes Mask/Veil (c(1),c(2))=nA*PB");
		Point C = findXP(nA, PB, p, a);
		System.out.println("Shared Secret Key : [c(1),c(2)] = ("+C.x+","+C.y+").");
		String[] y1= new String[m.length],y2 = new String[m.length];
		//String[] y1= new String[]{"9"},y2 = new String[]{"1"};
		System.out.println("Alice computes y1=c1*m1 mod p and y2=c2*m2 mod p");
		for(int i=0;i<m.length;i++){
			y1[i] = init.findSquareMultiply(C.x.multiply(new BigInteger(m1[i])), BigInteger.ONE, p).toString();
			y2[i] = init.findSquareMultiply(C.y.multiply(new BigInteger(m2[i])), BigInteger.ONE, p).toString();
			System.out.println("y1 : "+y1[i]+"\ty2 : "+y2[i]);
		}
		System.out.println("Alice Sends [y0,y1,y2] to bob");
		//********Decryption********
		System.out.println("\n\nDecryption\n\n");
		System.out.println("Bob computes nB*y0 = [c(1),c(2)]");
		Point C1 = findXP(nB, y0, p, a);
		System.out.println("Found by Bob [c(1),c(2)] = ("+C1.x+","+C1.y+").");
		BigInteger c1Inv = init.findModuloInverse(C1.x, p);
		BigInteger c2Inv = init.findModuloInverse(C1.y, p);
		String[] out = new String[m.length];
		for(int i=0;i<m.length;i++){
			String mes1 = init.findSquareMultiply(new BigInteger(y1[i]).multiply(c1Inv), BigInteger.ONE, p).toString();
			String mes2 = init.findSquareMultiply(new BigInteger(y2[i]).multiply(c2Inv), BigInteger.ONE, p).toString();
			out[i] = mes1+mes2;
			System.out.println("Decrypted message : "+out[i]);
		}
		String fin = txt.createText(out);
		System.out.println(fin);
		
	}

}
