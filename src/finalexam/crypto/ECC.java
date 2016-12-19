package finalexam.crypto;

import java.math.BigInteger;
import java.util.ArrayList;
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
		//ecc.generatePoint(new Point(BigInteger.valueOf(12),BigInteger.valueOf(6)), BigInteger.valueOf(23), BigInteger.valueOf(2));
		//System.out.println(ecc.powBigInteger(BigInteger.valueOf(6), BigInteger.valueOf(2)));
		ecc.findRoot(BigInteger.valueOf(13), BigInteger.valueOf(7), BigInteger.valueOf(15), BigInteger.valueOf(3571));
	}
	
	public ArrayList<Point> generatePoint(Point P,BigInteger p,BigInteger a){
		ArrayList<Point> arrP = new ArrayList<Point>();
		Point Ptemp = P;
		P = twoP(P, p, a);
		arrP.add(Ptemp);
		arrP.add(P);
		while(Ptemp.x.compareTo(P.x)!=0){
			P=addPoints(Ptemp, P, p, a);
			arrP.add(P);
		}	
		for(Point q:arrP){
			q.printPoint();
		}
		System.out.println("Total Points/order:"+(arrP.size()+1));
		return arrP;
	}
	public BigInteger powBigInteger(BigInteger x, BigInteger y) {
		  if (y.compareTo(BigInteger.ZERO) < 0)
		    throw new IllegalArgumentException();
		  BigInteger z = x; 
		  BigInteger result = BigInteger.ONE;
		  byte[] bytes = y.toByteArray();
		  for (int i = bytes.length - 1; i >= 0; i--) {
		    byte bits = bytes[i];
		    for (int j = 0; j < 8; j++) {
		      if ((bits & 1) != 0)
		        result = result.multiply(z);
		      if ((bits >>= 1) == 0 && i == 0)
		        return result;
		      z = z.multiply(z);
		    }
		  }
		  return result;
		}
	public Point findRoot(BigInteger x,BigInteger a,BigInteger b,BigInteger p){
		BigInteger u = (x.pow(3).add(a.multiply(x))).add(b);
		BigInteger y =init.findSquareMultiply(u, BigInteger.valueOf(1), p);
		Point P = new Point();
		P.x = init.findSquareMultiply(powBigInteger(y, (p.add(BigInteger.valueOf(1)).divide(BigInteger.valueOf(4)))), BigInteger.ONE, p);
		P.y = p.subtract(P.x);
		if(init.findSquareMultiply(P.x.pow(2), BigInteger.ONE, p).compareTo(y)==0&&init.findSquareMultiply(P.y.pow(2), BigInteger.ONE, p).compareTo(y)==0){
			//P.x and P.y are root1 and root2 respectively
			return P;
		}
		return null;
		
		
	}
	
	
	//Find X times P using Double and Add algorithm.
	public Point findXP(int X,Point P,BigInteger p,BigInteger a){
		Point tP = P;
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
//		System.out.println("12P");
//		P.printPoint();
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
//		System.out.println("P+Q");
//		p2.printPoint();
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
//		System.out.println("2P");
//		p2.printPoint();
		return p2;
	}
}


