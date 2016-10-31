import java.util.Scanner;

public class CesarCipher {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		//System.out.println(cryptograph(s.toUpperCase(), 14));
		
		for(int i=1;i<=26;i++){
			System.out.println(cryptograph(s.toUpperCase(), i));
         }
	}
	public static String cryptAnalysis(String s,int n){
		char[] c = s.toCharArray();
		String out = "";
		for(char p:c){
			if(p==' '){
				out += " ";
				continue;
			}
			if(((char)p)-n<65){
				out += Character.toString((char) (((char)p)-n+26));
				continue;
			}
			out += Character.toString((char) (((char)p)-n));
		}
		
		
		return out;
	}
	
	public static String cryptograph(String s,int n){
		char[] c = s.toCharArray();
		String out = "";
		for(char p:c){
			if(p==' '){
				out += " ";
				continue;
			}
			if(((char)p)+n>90){
				out += Character.toString((char) (((char)p)+n-26));
				continue;
			}
			out += Character.toString((char) (((char)p)+n));
		}
		
		return out;
	}

}
