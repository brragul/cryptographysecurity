import java.util.Arrays;
import java.util.Scanner;

public class ColumnarDisposition {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String key = sc.nextLine();
		String in = sc.nextLine();
		in = in.replaceAll("\\s", "");
		System.out.println(cryptography(in, key));
		System.out.println(cryptAnalysis(in, key));
	}
	
	public static String cryptAnalysis(String in,String key){
		int[] k = new int[key.length()];
		for(int i=0;i<key.length();i++){
			k[i] = key.charAt(i);
		}
		Arrays.sort(k);
		
		int row = (int) Math.ceil((float)in.length()/(float)key.length());
		String[][] arr = new String[row][key.length()];
		int l = 0;
		for(int j=0;j<key.length();j++){
			for(int i =0;i<row;i++){
			arr[i][key.indexOf(k[j])]=in.substring(l, l+1);
			l++;
		}
	}
		String out = "";
		for(int i=0;i<row;i++){
			for(int j=0;j<key.length();j++){
				out += arr[i][j];
			}
		}
		
		return out;
	}
	
	
	public static String cryptography(String in,String key){
		int row = (int) Math.ceil((float)in.length()/(float)key.length());
		String[][] arr = new String[row][key.length()];
		int l=0;
		for(int i =0;i<row;i++){
			for(int j=0;j<key.length();j++){
				
				if(l>=in.length()){
					arr[i][j] = "A";
					l++;
					continue;
				}
				
					arr[i][j]=in.substring(l, l+1);
					l++;
			}
		}
		int[] k = new int[key.length()];
		for(int i=0;i<key.length();i++){
			k[i] = key.charAt(i);
		}
		Arrays.sort(k);
		String out ="";
		
			for(int j=0;j<key.length();j++){
				for(int i =0;i<row;i++){
				out += arr[i][key.indexOf(k[j])];
			}
				out += " ";
		}
			return out;
	}
}
