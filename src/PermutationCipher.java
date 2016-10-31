import java.util.Arrays;
import java.util.Scanner;

public class PermutationCipher {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String key = sc.nextLine();
		String in = sc.nextLine();
		in = in.replaceAll("\\s", "").toUpperCase();
		//System.out.println(cryptography(in, key));
		System.out.println(cryptoAnalysis(in, key));
		
	}

	public static String cryptoAnalysis(String in,String key){
		int words = (int) Math.ceil((float)in.length()/(float)key.length());
		
		int[] k = new int[key.length()];
		for(int i=0;i<key.length();i++){
			k[i] = key.charAt(i);
		}
		Arrays.sort(k);
		String out = "";
		for(int i = 0;i<words;i++){
			for(int j=0;j<key.length();j++){
				out += in.charAt((i*key.length())+key.indexOf(k[j]));
			}
			out += " ";
		}
		
		
		return out;
	}
	
	public static String cryptography(String in,String key){
		int words = (int) Math.ceil((float)in.length()/(float)key.length());
		
		int last = in.length()%key.length();
		
		if(last !=0){
			for(int i=0;i<(key.length()-last);i++){
				in += "A";
			}
		}
		
		int[] k = new int[key.length()];
		for(int i=0;i<key.length();i++){
			k[i] = key.charAt(i);
		}
		System.out.println();
		Arrays.sort(k);
		int[] r = new int[key.length()];
		for(int i=0;i<key.length();i++){
			r[key.indexOf(k[i])] = i;
		}
		String out = "";
//		int l = 0;
		for(int i = 0;i<words;i++){
			for(int j=0;j<key.length();j++){
//				if(l>=in.length()){
//					out += "A";
//					l++;
//					continue;
//				}
				out += in.charAt((i*key.length())+r[j]);
//				l++;
			}
			out += " ";
		}
		
		
		return out;
	}
}
