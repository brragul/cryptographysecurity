package midterm.exam;

import java.util.HashMap;

public class TextConversion {
	int blockSize = 4,totalBlock=0;
	HashMap<String,String> lookup = new HashMap<String,String>(); 
	HashMap<String,String> reverselookup = new HashMap<String,String>(); 
	
	public TextConversion(){
		for(int i=65;i<91;i++){
			lookup.put(Character.toString(((char)i)), (i-65)<10?("0"+(i-65)):Integer.toString((i-65)));
			reverselookup.put((i-65)<10?("0"+(i-65)):Integer.toString((i-65)),Character.toString(((char)i)));
		}
	}
	
	public String convert(String s, boolean encode,int t){
		String out = "";
		int max = 0;
		if(encode){
			s = s.replace(" ", "");
			String[] blocks = createBlocks(s);
			int i=0;
			for(String b: blocks){
				blocks[i] =plainToNum(b.toUpperCase());
				if(blocks[i].length()>max)max=blocks[i].length();
				i++;
			}
			System.out.println();
			for(String b:blocks){
				System.out.print(b+" ");
				out +=padZeros(b, max);;
			}
			System.out.println("No of compressed blocks : "+blocks.length);
		}else{
			out = numToPlain(s, 3);
		}
		
		return out;
	}
	public static void main(String[] args) {
		TextConversion obj = new TextConversion();
		String enc = obj.convert("raviragul", true,0);
		String dec = obj.convert(enc, false,2);
		System.out.println(dec);	
	}
	
	String padZeros(String s,int max){
		int l=max-(s.length()%max);
			if(s.length()<max){
				for(int k=0;k<l;k++){
					s ="0"+s;
				}
			}		
		return s;
	}
	
	public String plainToNum(String s){
		s = s.toUpperCase();
		String[] s_arr = s.split("");
		String num = "";
		for(String tmp : s_arr){
			num += lookup.get(tmp);
		}
		return compress(num);
	}
	
	public String compress(String s){
		long out = 0;int j=0;
		for(int i=s.length()/2;i>0;i--){
			out += (Math.pow(26, i-1)*Long.parseLong(s.substring(j, j+2)));
			j +=2;
		}
		
		return Long.toString(out);
	}
	
	public String decompress(long i){
		long a =i%26;
		long n = (i-a)/26;
		String s = ((a<10)?("0"+Long.toString(a)):Long.toString(a));
		while(n>26){
			a = n%26;
			n = ((n-a)/26);
			s = ((a<10)?("0"+Long.toString(a)):Long.toString(a))+s;
		}
		s = ((n<10)?("0"+Long.toString(n)):Long.toString(n))+s;
		return s;
	}
	
	public String numToPlain(String s,int blks){
		int l = s.length()/blks;
		String out = "";
		for(int i=0;i<blks;i++){
			String b = s.substring(i*l, (i+1)*l);
			String t= decompress(Long.parseLong(b));
			for(int j=0;j<t.length();j+=2){
				out += reverselookup.get(t.substring(j,j+2));
			}
		}
		
		return out;
	}
	
	public String[] createDecodeBlocks(String s){
		int block = (int)Math.ceil((float)s.length()/(float)totalBlock);
		String[] s_arr = new String[totalBlock];
		for(int i=0;i<totalBlock;i++){
			s_arr[i]= s.substring(i*block,(i+1)*block);
		}
		
		return s_arr;
	}
	
	public String[] createBlocks(String s){
		totalBlock =  (int) Math.ceil(((float)s.length()/(float)blockSize));
		String[] s_arr;
		boolean lastEmpty = false;
		if(s.length()%blockSize!=0)lastEmpty = true;
		if(lastEmpty){
			int rem = blockSize-(s.length()%blockSize);
			String tmp = "";
			for(int i=0;i<rem;i++){
				tmp += "A";
			}
			s=s+tmp; 
		}
			s_arr =  new String[totalBlock];
			for(int i=0;i<s_arr.length;i++){
				s_arr[i]= s.substring(i*blockSize,(i+1)*blockSize);
			}
		return s_arr;
	}
	
	 
}
