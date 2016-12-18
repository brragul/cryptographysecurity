package midterm.exam;

import java.util.Scanner;

public class AES {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the text to do AES SubBytes and ShiftRows");
		String in = "2C 83 29 83 B3 00 3B ED 52 2F 83 2F 00 83 B1 3B";//sc.nextLine();
		System.out.println("Input the 16 byte s-box");
		//String sbox = sc.nextLine();
		String[] i_arr = in.split(" ");
		//String[] s_arr = sbox.split(" ");
 		String[][] arr = new String[4][4];
 		String[][] arr_sbox = new String[4][4];
		int k = 0;
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				arr[i][j] = i_arr[k];
				//arr_sbox[i][j] = s_arr[k];
				k++;
			}
		
		}
		//arr = arr_sbox; //The subbytes will replace values on lookup table(sbox)
		arr = shiftRows(arr);
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				System.out.print(arr[i][j]+" ");
			}
		}
	}
	
	static String[][] shiftRows(String[][] arr){
		String[][] out = new String[4][4];
		out[0] = arr[0];
		for(int i = 1;i<4;i++){
			int j;
			for(j=0;j<4-i;j++){
				out[i][j] = arr[i][j+i];
			}
			
			int k=0;
			for(int q = 4-i;q<4;q++){
				out[i][j] = arr[i][k];
				k++;j++;
			}
		}
		
		return out;
	}
	
	static String[][] subBytes(String[][] arr,String[] sbox){
		for(int i = 1;i<4;i++){
			for(int j=0;j<4-i;j++){
				arr[i][j] = sbox[Integer.parseInt(arr[i][j])];
			}
		}
		return arr;
	}
}
