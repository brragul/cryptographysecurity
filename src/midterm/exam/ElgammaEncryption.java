package midterm.exam;

import java.math.BigInteger;
import java.util.Scanner;

public class ElgammaEncryption {
	public static TextConv txt = new TextConv();
	public static Init obj = new Init();
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long p  = 104917;//sc.nextLong();
		Diffie_Hellman DH = new Diffie_Hellman(p);
		long k = 17;//DH.randomNumberGenerator((int)p);
		System.out.println("Random Key : "+k);
		
		String s = "some random things should go down here but i am typing some random things";//sc1.nextLine();
		String[] num = txt.createNum(s);//new String[]{"21"};//
		long g = obj.findGenerator(p);
		System.out.println("Generator : "+g);
		/******Encryption*******/
		BigInteger pB = obj.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(DH.bR), BigInteger.valueOf(p));
		
		BigInteger M = obj.findSquareMultiply(pB, BigInteger.valueOf(k), BigInteger.valueOf(p));
		BigInteger[] C = new BigInteger[num.length];
		int i = 0;
		System.out.println("Cipher Text : ");
		for(String n : num){
			if(BigInteger.valueOf(Long.parseLong(n)).compareTo(BigInteger.valueOf(p))==1){//Long.parseLong(ss)>p||Long.parseLong(ss)>q){
				System.out.println("Message is larger than p and q");
				return;
			}
			C[i] = obj.findSquareMultiply(M.multiply(BigInteger.valueOf(Long.parseLong(n))), BigInteger.ONE, BigInteger.valueOf(p));
			System.out.print(n+" ");
			i++;
		}
		BigInteger H = obj.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(k), BigInteger.valueOf(p));
		System.out.println("H : "+H);
		System.out.println("Send g and H to bob");
		/******Decryption*******/
		long q = p-1-DH.bR;
		
		BigInteger R = obj.findSquareMultiply(H, BigInteger.valueOf(q), BigInteger.valueOf(p));
		BigInteger[] D = new BigInteger[num.length];
		String[] fin = new String[num.length];
		System.out.println("Decrypted numbers : ");
		for(int j=0;j<C.length;j++){
			D[j] = obj.findSquareMultiply(C[j].multiply(R), BigInteger.ONE, BigInteger.valueOf(p));
			System.out.print(D[j]+" ");
			fin[j]= D[j].toString();
		}
		System.out.println(txt.createText(fin));

	}

}
