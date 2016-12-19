package finalexam.crypto;

import java.math.BigInteger;
import java.util.Stack;

import midterm.exam.Init;

public class ECC {
	Init init = new Init();
	public static void main(String[] args) {
		ECC ecc = new ECC();
		//ecc.twoP(new Point(BigInteger.valueOf(22),BigInteger.valueOf(12)), BigInteger.valueOf(23), BigInteger.valueOf(2));
		//ecc.addPoints(new Point(BigInteger.valueOf(0),BigInteger.valueOf(3)),new Point(BigInteger.valueOf(13),BigInteger.valueOf(1)), BigInteger.valueOf(23), BigInteger.valueOf(2));
		//ecc.findOrder(new Point(BigInteger.valueOf(2288),BigInteger.valueOf(1585)), BigInteger.valueOf(3571), BigInteger.valueOf(7));
		//ecc.findXP(150, new Point(BigInteger.valueOf(16),BigInteger.valueOf(3096)), BigInteger.valueOf(3571), BigInteger.valueOf(7));
	}
	
	//Find X times P using Double and Add algorithm.
	public Point findXP(int X,Point P,BigInteger p,BigInteger a){
		Point tP = P;
		String s = Integer.toBinaryString(X);
		int t=1,k=2;
		while(t<X){
			P = twoP(P, p, a);
			t +=t;
			if(!Integer.toBinaryString(t).equals(Integer.toBinaryString(X).substring(0, k))){
				t++;
				P=addPoints(tP, P, p, a);
			}
			k++;
		}
		System.out.println("12P");
		P.printPoint();
		return P;
	}
	
	public int findOrder(Point P,BigInteger p,BigInteger a){
		int order = 2;
		Point Ptemp = P;
		P = twoP(P, p, a);
		while(Ptemp.x.compareTo(P.x)!=0){
			P=addPoints(Ptemp, P, p, a);
			order++;
			System.out.println("Order -> "+order);
		}
		order++;
		System.out.println(order);
		return order;
	}
	
	public Point addPoints(Point P,Point Q,BigInteger p,BigInteger a){
		init.quo.clear();
		BigInteger s = Q.x.subtract(P.x);
		if(s.compareTo(BigInteger.valueOf(0))==-1){
			s = s.add(p);
		}
		BigInteger den = init.findModuloInverse(s, p);
		BigInteger num = Q.y.subtract(P.y);
		BigInteger alpha = init.findSquareMultiply(num.multiply(den),BigInteger.valueOf(1), p);
		Point p2 = new Point();
		p2.x = (((alpha.pow(2)).subtract(P.x)).subtract(Q.x)).mod(p);
		p2.y = ((alpha.multiply(P.x.subtract(p2.x))).subtract(P.y)).mod(p);
		System.out.println("P+Q");
		p2.printPoint();
		return p2;
	}
	public Point twoP(Point P,BigInteger p,BigInteger a){
		init.quo.clear();
		BigInteger denominator = init.findModuloInverse(P.y.multiply(BigInteger.valueOf(2)), p);
		BigInteger neumerator = a.add((P.x.pow(2)).multiply(BigInteger.valueOf(3)));
		BigInteger alpha = init.findSquareMultiply(neumerator.multiply(denominator), BigInteger.valueOf(1), p);
		Point p2 = new Point();
		p2.x = ((alpha.pow(2)).subtract(P.x.multiply(BigInteger.valueOf(2)))).mod(p);
		p2.y = ((alpha.multiply(P.x.subtract(p2.x))).subtract(P.y)).mod(p);
		System.out.println("2P");
		p2.printPoint();
		return p2;
	}
}

class Point{
	BigInteger x;
	BigInteger y;
	public Point(BigInteger x,BigInteger y){
		this.x = x;
		this.y = y;
	}
	public Point() {
	}
	public void printPoint(){
		System.out.println("Point x : "+x+"\nPoint y : "+y+"\n");
	}
}
