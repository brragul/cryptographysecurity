package midterm.exam;

import java.util.HashMap;

public class TextConv {

	public static void main(String[] args) {
		TextConv obj = new TextConv();
		String[] num =obj.createNum("raviragul");
		System.out.println(num[0]);
		System.out.println(obj.createText(num));
	}
	
	public String[] createNum(String s){
		s =s.toUpperCase().replace(" " ,"");
		int totalBlocks = (int) Math.ceil(((float)s.length()/(float)blockLength));
		if(s.length()%blockLength!=0){
			int t = blockLength-(s.length()%blockLength);
			for(int i=0;i<t;i++){
				s = s+"E";
			}
		}
		String[] out = new String[totalBlocks];
		for(int i=0;i<totalBlocks;i++){
			out[i]= compressData(textToNum(s.substring(i*blockLength,(i+1)*blockLength)));
			out[i] = (out[i].length()%2==0)?out[i]:"0"+out[i];
		}
		return out;
		
	}
	
	public String createText(String[] s_arr){
		String out ="";
		for(String s : s_arr){
			String d = decompress(Long.parseLong(s));
			for(int j=0;j<d.length();j+=2){
				out += reverselookup.get(d.substring(j,j+2));
			}
		}
		
		return out;
	}
	
	public String decompress(long i){
		long a =i%26;
		long n = (i-a)/26;
		String s = ((a<10)?("0"+Long.toString(a)):Long.toString(a));
		while(n>=26){
			a = n%26;
			n = ((n-a)/26);
			s = ((a<10)?("0"+Long.toString(a)):Long.toString(a))+s;
		}
		s = ((n<10)?("0"+Long.toString(n)):Long.toString(n))+s;
		return s;
	}
	
	public String textToNum(String s){
		s = s.toUpperCase();
		String[] s_arr = s.split("");
		String num = "";
		for(String tmp : s_arr){
			num += lookup.get(tmp);
		}
		return num;
	}
	
	public String compressData(String s){
		long out = 0;int j=0;
		for(int i=s.length()/2;i>0;i--){
			out += (Math.pow(26, i-1)*Long.parseLong(s.substring(j, j+2)));
			j +=2;
		}
		return Long.toString(out);
	}
	
	public void initLookUp(){
		for(int i=65;i<91;i++){
			lookup.put(Character.toString(((char)i)), (i-65)<10?("0"+(i-65)):Integer.toString((i-65)));
			reverselookup.put((i-65)<10?("0"+(i-65)):Integer.toString((i-65)),Character.toString(((char)i)));
		}
//		lookup.put(" ", "26");
//		reverselookup.put("26", " ");
	}
	
	public TextConv(){
		initLookUp();
	}
	
	
	int blockLength=3;
	HashMap<String,String> lookup = new HashMap<String,String>(); 
	HashMap<String,String> reverselookup = new HashMap<String,String>(); 

}
