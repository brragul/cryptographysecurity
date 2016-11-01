package midterm.exam;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Init {
	
	public static void main(String[] args){
		Init obj = new Init();
//		System.out.println(obj.findPrimeFactors(104970));
//		System.out.println(obj.isPrime(105018));
		//System.out.println(obj.findGCD(25678, 2004));
		//System.out.println(obj.findModuloInverse(BigInteger.valueOf(442),BigInteger.valueOf(11001475644l)));
		System.out.println(obj.findSquareMultiply(BigInteger.valueOf(442), BigInteger.valueOf(5), BigInteger.valueOf(11001685421l)));
	}
	
	public BigInteger findSquareMultiply(BigInteger u,BigInteger d,BigInteger p){
		
		BigInteger A = BigInteger.valueOf(1); 
		while(d.compareTo(BigInteger.valueOf(0))==1){
			BigInteger b = d.mod(BigInteger.valueOf(2));
			if(b.equals(BigInteger.valueOf(1))){
				A = A.multiply(u);
				A = A.mod(p);
			}
			if(d.compareTo(BigInteger.valueOf(0))==1)
			{
			d = d.subtract(b);//(d-b)/2;
			d= d.divide(BigInteger.valueOf(2));
			u = u.pow(2);
			u = u.mod(p);
			}else{
				return A;
			}
		}
		
		return A;
	}
	
	public BigInteger findModuloInverse(BigInteger a,BigInteger p){
		findGCD(a,p);
		BigInteger[] qq = new BigInteger[quo.size()];
		qq = quo.toArray(qq);
		BigInteger s0=BigInteger.ZERO,s1=BigInteger.ONE,s2=BigInteger.ZERO;
		for(int j=qq.length-1;j>=0;j--){
			s2 = s1.multiply(qq[j]);
			s2 = s2.add(s0);//s0+(s1*qq[j]);
			s0=s1;
			s1=s2;
		}
		if((quo.size()-1)%2==0){
			return p.subtract(s2);
		}
		return s2;
	}
	
	List<BigInteger> quo = new ArrayList<BigInteger>();
	
	public BigInteger findGCD(BigInteger t,BigInteger b){
		if(t.compareTo(b)==-1){
			BigInteger temp =t;
			t=b;
			b=temp;
		}
		BigInteger r = t.mod(b);// (t%b);
		BigInteger q =  t.divide(b);//(t/b);
		if(r.equals(BigInteger.valueOf(0))){
			return  b;
		}
		quo.add(q);
		return findGCD(b,r);
		
	}
	
	public List<Integer> findPrimeFactors(int n){
		List<Integer> li = new ArrayList<Integer>();
		for(int i=2;i<=(n/2);i++){
			if(n%i==0 && isPrime(i)){
				li.add(i);
			}
		}
		return li;
	}
	
	public boolean isPrime(int p){
		int sqrt = (int) Math.sqrt(p);
		if(p==2)return true;
		if(p%2==0)return false;
		for(int i=3;i<sqrt;i+=2){
			if(p%i==0){
				return false;
			}
		}
		return true;
	}
}
