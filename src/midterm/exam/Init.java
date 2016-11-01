package midterm.exam;

import java.util.ArrayList;
import java.util.List;

public class Init {
	
	public static void main(String[] args){
		Init obj = new Init();
//		System.out.println(obj.findPrimeFactors(104970));
//		System.out.println(obj.isPrime(105018));
		//System.out.println(obj.findGCD(25678, 2004));
		//System.out.println(obj.findModuloInverse(3,104891));
		System.out.println(obj.findSquareMultiply(1789, 45673, 104917));
	}
	
	public long findSquareMultiply(int u,int m,int p){
		
		long A = 1; 		
		while(m>0){
			int b = m%2;
			if(b==1){
				A = (A*u)%p;
			}
			m = (m-b)/2;
			u = (int) (Math.pow(u,2)%p);
		}
		
		return A;
	}
	
	public int findModuloInverse(int a,int p){
		findGCD(a,p);
		Integer[] qq = new Integer[quo.size()];
		qq = quo.toArray(qq);
		int s0=0,s1=1,s2=0;
		for(int j=qq.length-1;j>=0;j--){
			s2 = s0+(s1*qq[j]);
			s0=s1;
			s1=s2;
		}
		if((quo.size()-1)%2==0){
			return p-s2;
		}
		return s2;
	}
	
	List<Integer> quo = new ArrayList<Integer>();
	
	public int findGCD(int a,int b){
		if(a<b){
			int temp =a;
			a=b;
			b=temp;
		}
		int r = a%b;
		int q = a/b;
		if(r==0){
			return b;
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
