package midterm.exam;

import java.math.BigInteger;
import java.util.Scanner;

public class ElgammaEncryption {
	public static TextConv txt = new TextConv();
	public static Init obj = new Init();
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		System.out.println("Enter Prime p : ");
		long p  = sc.nextLong();
		txt.generateBlockSize(BigInteger.valueOf(p));
		System.out.println("Block size : "+txt.blockLength);
		Diffie_Hellman DH = new Diffie_Hellman(p);
		DH.display();
		System.out.println("\n\n\n\n\n\n\n");
		long k = DH.randomNumberGenerator((int)p);
		System.out.println("Random Key : "+k);
		System.out.println("Enter text to be encrypted");
		String s = sc1.nextLine();
		String[] num = txt.createNum(s);
		long g = obj.findGenerator(p);
		System.out.println("Generator : "+g);
		/******Encryption*******/
		BigInteger pB = obj.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(DH.bR), BigInteger.valueOf(p));//Used b from diffe-hellman
		System.out.println("pB = g^bR mod p"+pB);
		BigInteger M = obj.findSquareMultiply(pB, BigInteger.valueOf(k), BigInteger.valueOf(p));
		System.out.println("M = pB^k mod p"+M);
		BigInteger[] C = new BigInteger[num.length];
		int i = 0;
		System.out.println("Cipher Text : ");
		for(String n : num){
			if(BigInteger.valueOf(Long.parseLong(n)).compareTo(BigInteger.valueOf(p))==1){//Long.parseLong(ss)>p||Long.parseLong(ss)>q){
				System.out.println("Message is larger than p and q");
				return;
			}
			C[i] = obj.findSquareMultiply(M.multiply(BigInteger.valueOf(Long.parseLong(n))), BigInteger.ONE, BigInteger.valueOf(p));
			System.out.println("num conv of txt :"+n+" Cipher"+C[i]);
			i++;
		}
		BigInteger H = obj.findSquareMultiply(BigInteger.valueOf(g), BigInteger.valueOf(k), BigInteger.valueOf(p));
		System.out.println("H = g^k mod p");
		System.out.println("H : "+H);
		System.out.println("Send g and H to bob");
		/******Decryption*******/
		long q = p-1-DH.bR;
		
		BigInteger R = obj.findSquareMultiply(H, BigInteger.valueOf(q), BigInteger.valueOf(p));
		System.out.println("\nDecryption\nR = H^q mod p"+R);
		BigInteger[] D = new BigInteger[num.length];
		String[] fin = new String[num.length];
		System.out.println("Decrypted numbers D=C*R mod p: ");
		for(int j=0;j<C.length;j++){
			D[j] = obj.findSquareMultiply(C[j].multiply(R), BigInteger.ONE, BigInteger.valueOf(p));
			System.out.print(D[j]+" ");
			fin[j]= D[j].toString();
		}
		System.out.println("\n"+txt.createText(fin));

	}

}
