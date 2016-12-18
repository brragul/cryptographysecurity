package finalexam.crypto;

import java.math.BigInteger;
import midterm.exam.Init;

public class ECC {
	Init init = new Init();
	public static void main(String[] args) {
		ECC ecc = new ECC();
		//ecc.twoP(new Point(BigInteger.valueOf(4),BigInteger.valueOf(9)), BigInteger.valueOf(23), BigInteger.valueOf(2));
		//ecc.addPoints(new Point(BigInteger.valueOf(8),BigInteger.valueOf(13)),new Point(BigInteger.valueOf(8),BigInteger.valueOf(10)), BigInteger.valueOf(23), BigInteger.valueOf(2));
		ecc.findOrder(new Point(BigInteger.valueOf(8),BigInteger.valueOf(13)), BigInteger.valueOf(23), BigInteger.valueOf(2));
	}
	public int findXP(int X,Point P,BigInteger p){
		
		
		
		return 0;
	}
	
	public int findOrder(Point P,BigInteger p,BigInteger a){
		int order = 1;
		Point Ptemp = P;
		P = twoP(P, p, a);
		while(Ptemp.x!=P.x){
			order++;
			P=addPoints(Ptemp, P, p, a);
		}
		System.out.println(order);
		return order;
	}
	
	public Point addPoints(Point P,Point Q,BigInteger p,BigInteger a){
		init.quo.clear();
		BigInteger den = init.findModuloInverse(Q.x.subtract(P.x), p);
		BigInteger num = Q.y.subtract(P.y);
		BigInteger alpha = init.findSquareMultiply(num.multiply(den),BigInteger.valueOf(1), p);
		Point p2 = new Point();
		p2.x = (((alpha.pow(2)).subtract(P.x)).subtract(Q.x)).mod(p);
		p2.y = ((alpha.multiply(P.x.subtract(p2.x))).subtract(P.y)).mod(p);
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
