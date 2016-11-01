package midterm.exam;

import java.util.Scanner;

public class RSA {
	public Init obj = new Init();
	public static void main(String[] args) {
		RSA rsa = new RSA();
		Scanner sc = new Scanner(System.in);
		long p = sc.nextInt();
		long q = sc.nextInt();
		long n = p * q;
		long totien = (p-1)*(q-1);
		long e = rsa.find_coprime(totien);
		if(e == 0){
			System.out.println("No co-primes found");
			return;
		}
		System.out.println(e);
		

	}
	
	public  long find_coprime(long t){
		long e=0;
		for(int j=2;j<100;j++){
			e = obj.findGCD(t,j);
			if(e==1){
				return e;
			}
		}
		return 0;
		
	}

}
