package midterm.exam;

import java.util.ArrayList;
import java.util.List;

public class Init {
	
	public static void main(String[] args){
		Init obj = new Init();
//		System.out.println(obj.findPrimeFactors(104970));
//		System.out.println(obj.isPrime(105018));
		System.out.println(obj.findGCD(1160718174, 316258250));
	}
	public  int modulo(int p,int q){
		return p%q;
	}
	
	public int findModuloInverse(int a,int p){
		if(findGCD(a, p)!=1){
			System.out.println("GCD of both numbers is not 1");
			return 0;
		}
		
		return 0;
	}
	
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
