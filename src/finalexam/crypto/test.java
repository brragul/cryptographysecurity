package finalexam.crypto;

import java.math.BigInteger;

import midterm.exam.TextConv;

public class test {
	public static TextConv txt = new TextConv();

	public static void main(String[] args) {
		
		BigInteger p = BigInteger.valueOf(3751);
		txt.generateBlockSize(p);
		String s = "cryptography and security";
		String[] m = txt.createNum(s);
		String[] m1 = new String[m.length],m2 = new String[m.length];
		for(int i=0;i<m.length;i++){
			m1[i] = m[i].substring(0,m[i].length()/2);
			m2[i] = m[i].substring(m[i].length()/2,	m[i].length());
		}
		System.out.println("asdf");
	}

}
