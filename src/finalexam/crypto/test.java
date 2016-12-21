package finalexam.crypto;

import java.math.BigInteger;

import midterm.exam.TextConv;

public class test {
	public static TextConv txt = new TextConv();

	public static void main(String[] args) {
		
	
		//p.mod(BigInteger.valueOf(4))!=BigInteger.valueOf(3)||q.mod(BigInteger.valueOf(4))!=BigInteger.valueOf(3)
		System.out.println(BigInteger.valueOf(11).mod(BigInteger.valueOf(4)));
	}

}
