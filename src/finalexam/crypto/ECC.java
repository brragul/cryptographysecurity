package finalexam.crypto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import midterm.exam.Init;

public class ECC {
	Init init = new Init();
	public static void main(String[] args) {
		ECC ecc = new ECC();
		//ecc.twoP(new Point(BigInteger.valueOf(22),BigInteger.valueOf(12)), BigInteger.valueOf(23), BigInteger.valueOf(2));
		//ecc.addPoints(new Point(BigInteger.valueOf(0),BigInteger.valueOf(3)),new Point(BigInteger.valueOf(13),BigInteger.valueOf(1)), BigInteger.valueOf(23), BigInteger.valueOf(2));
		//ecc.findOrder(new Point(BigInteger.valueOf(9),BigInteger.valueOf(2377)), BigInteger.valueOf(7919), BigInteger.valueOf(7));
		//ecc.findXP(3646, new Point(BigInteger.valueOf(2639),BigInteger.valueOf(2029)), BigInteger.valueOf(3571), BigInteger.valueOf(7));
		//ecc.generatePoint(new Point(BigInteger.valueOf(6925),BigInteger.valueOf(575)), BigInteger.valueOf(7919), BigInteger.valueOf(2));
		//System.out.println(ecc.powBigInteger(BigInteger.valueOf(6), BigInteger.valueOf(2)));
		//ecc.findRoot(BigInteger.valueOf(6952), BigInteger.valueOf(2), BigInteger.valueOf(9), BigInteger.valueOf(7919));
		ecc.GenerateAllPoints(BigInteger.valueOf(3876), BigInteger.valueOf(4488), BigInteger.valueOf(7919));
		System.out.println("Highest order is "+ecc.highOrder);
		ArrayList<Point> al = ecc.orderPoint.get(ecc.highOrder);
		for(Point P: al){
			P.printPoint();
		}
	}
	HashMap<Integer,ArrayList<Point>> orderPoint = new HashMap<Integer,ArrayList<Point>>();
	public ArrayList<Point> GenerateAllPoints(BigInteger a,BigInteger b,BigInteger p){
		int count=0;
		Point tmp = null;
		ArrayList<Point> al = new ArrayList<Point>();
		for(BigInteger i = BigInteger.ZERO;p.compareTo(i)>0;i=i.add(BigInteger.ONE)){
			Point P = findRoot(i, a, b, p);
			if(P != null){
				//System.out.println("X : "+i+"\n"+"root1 : "+P.x+"\nroot2 : "+P.y+"\n");
				if(P.x != BigInteger.ZERO&&P.y != BigInteger.ZERO){
					
					Point p1=new Point(i,P.x);
					al.addAll(generatePoint(p1, p, a));
					addOrderPointToMap(al.size()+1, p1);
//					System.out.println("*****************New Point***************");
//					for(Point d: al){
//						d.printPoint();
//						
//					}
					al.clear();
					Point p2=new Point(i,P.y);
					al.addAll(generatePoint(p2, p, a));
					addOrderPointToMap(al.size()+1, p2);
//					System.out.println("*****************New Point***************");
//					for(Point d: al){
//						d.printPoint();
//						
//					}
					al.clear();
					//count = al.size();
				}
			}
//			if(count>1000){
//				break;
//			}
			
		}
		//System.out.println("Get a random Generator Point");
		//al.get((int) randomNumberGenerator(al.size())).printPoint();
		return al;
		
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
//		for(Point q:arrP){
//			q.printPoint();
//		}
		//Ptemp.printPoint();
		//System.out.println("order is "+(arrP.size()+1));
		highestOrder(arrP.size()+1);
		return arrP;
	}
	
	public Point findRoot(BigInteger x,BigInteger a,BigInteger b,BigInteger p){
		BigInteger u = (x.pow(3).add(a.multiply(x))).add(b);
		BigInteger y =init.findSquareMultiply(u, BigInteger.valueOf(1), p);
		Point P = new Point();
		P.x = init.findSquareMultiply(powBigInteger(y, (p.add(BigInteger.valueOf(1)).divide(BigInteger.valueOf(4)))), BigInteger.ONE, p);
		P.y = p.subtract(P.x);
		if(init.findSquareMultiply(P.x.pow(2), BigInteger.ONE, p).compareTo(y)==0&&init.findSquareMultiply(P.y.pow(2), BigInteger.ONE, p).compareTo(y)==0){
			//P.x and P.y are root1 and root2 respectively
			//P.printPoint();
			return P;
		}
		return null;
		
		
	}
	
	public void addOrderPointToMap(Integer i,Point p){
		ArrayList<Point> al = orderPoint.get(i);
		if(orderPoint.isEmpty()||al==null){
			al = new ArrayList<Point>();
			al.add(p);
			orderPoint.put(i, al);
		}else{
			al.add(p);
			orderPoint.put(i, al);
		}
	}
	
	int highOrder = 0;
	public void highestOrder(int order){
		if(order>highOrder){
			highOrder = order;
		}
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
		}
		order++;
		//System.out.println(order);
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
	

	public long randomNumberGenerator(int p2){
		Random rand = new Random();
		int randomNum = rand.nextInt(((p2-1) - 2) + 1) + 2;//Max -> p-1 Min -> 2
		return randomNum;
	}
	
	//To find BigInteger power BigInteger
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
}


