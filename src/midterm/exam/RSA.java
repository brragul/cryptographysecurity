package midterm.exam;

import java.math.BigInteger;
import java.util.Scanner;

public class RSA {
	public static Init obj = new Init();
	public static TextConv txt = new TextConv();
	public static void main(String[] args) {
		RSA rsa = new RSA();
		Scanner sc = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		
		System.out.println("Enter prime p : ");
		BigInteger p = BigInteger.valueOf(746969l*746981l);//sc.nextBigInteger();BigInteger.valueOf(746969l);//
		System.out.println("p : "+p);
		System.out.println("Enter prime q : ");
		BigInteger q = BigInteger.valueOf(747037l*747049l);//BigInteger.valueOf(747037l);//
		System.out.println("q : "+q);
		if(p.compareTo(q)==1){
			txt.generateBlockSize(q);
		}else{
			txt.generateBlockSize(p);
		}
		System.out.println("Block Size"+txt.blockLength);
		BigInteger n = p.multiply(q);
		System.out.println("N : "+n);
		BigInteger totien = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		System.out.println("totient : "+totien);
		BigInteger e = rsa.find_coprime(totien);
		System.out.println("E : "+e);
		obj.quo.clear();
		System.out.println("Share e and n with Alice");
		BigInteger d = obj.findModuloInverse(e,totien);
		if(obj.findSquareMultiply(d.multiply(e), BigInteger.ONE, totien).compareTo(BigInteger.ONE)!=0){
			System.out.println("d*e mod totient not 1");
		}
		System.out.println("D : "+d);
		System.out.println("Please enter text to be encrypted");
		String s = sc1.nextLine();
		String[] num = txt.createNum(s);
		/******Encryption*******/
		BigInteger[] dataToSend = new BigInteger[num.length];
		int i = 0;
		System.out.print("Compressed Data : ");
		for(String ss : num){
			if(BigInteger.valueOf(Long.parseLong(ss)).compareTo(p)==1||BigInteger.valueOf(Long.parseLong(ss)).compareTo(q)==1){//Long.parseLong(ss)>p||Long.parseLong(ss)>q){
				System.out.println("Message is larger than p and q");
				return;
			}
			dataToSend[i] = obj.findSquareMultiply(new BigInteger(ss),e,n);
			System.out.print(ss+" ");
			i++;
		}
		System.out.println("\nData to be sent by Alice after encryption : ");
		for(BigInteger l: dataToSend){
			System.out.print(l+" ");
		}
		/******Decryption*******/
		System.out.println("\nBob's decrypting received data using d");
		BigInteger[] decryptedData = new BigInteger[num.length];i=0;
		String[] fin = new String[num.length];
		for(BigInteger l : dataToSend){
			decryptedData[i] = obj.findSquareMultiply(l, d, n);
			System.out.print(decryptedData[i]+" ");
			fin[i]= decryptedData[i].toString();
			i++;
		}
		String data = txt.createText(fin);
		System.out.println("\n"+data);
		
	}
	
	public  BigInteger find_coprime(BigInteger t){
		BigInteger e=BigInteger.ZERO;
		for(int j=2;j<100;j++){
			e = obj.findGCD(t,BigInteger.valueOf(j));
			if(e.equals(BigInteger.valueOf(1))){
				return BigInteger.valueOf(j);
			}
		}
		return BigInteger.ZERO;
		
	}

}
