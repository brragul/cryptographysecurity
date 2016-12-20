package finalexam.crypto;
import java.math.BigInteger;
import java.util.Scanner;

import midterm.*;
public class ECCryptoSystem {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a : ");
		BigInteger a = sc.nextBigInteger();
		System.out.println("Enter b : ");
		BigInteger b = sc.nextBigInteger();
		System.out.println("Enter prime p : ");
		BigInteger p = sc.nextBigInteger();
	}

}
