package midterm.exam;

import java.math.BigInteger;
import java.util.HashMap;

public class TextConv {
	
	public static void main(String[] args){
		TextConv t = new TextConv();
		//t.generateBlockSize(BigInteger.valueOf(746959l));
		//System.out.println(t.blockLength);
		String[] arr = t.createNum("security");//new String[]{"319144","304718"};
		System.out.println();
		for(String s: arr){
			System.out.print(s+" ");
		}
		System.out.println(t.createText(arr));
	}
	
	//Convert String to Number
	public String[] createNum(String s){
		s =s.toUpperCase().replace(" " ,"");
		int totalBlocks = (int) Math.ceil(((float)s.length()/(float)blockLength));
		if(s.length()%blockLength!=0){
			int t = blockLength-(s.length()%blockLength);
			for(int i=0;i<t;i++){
				s = s+"Z";
			}
		}
		String[] out = new String[totalBlocks];
		for(int i=0;i<totalBlocks;i++){
			out[i]= compressData(textToNum(s.substring(i*blockLength,(i+1)*blockLength)));
			out[i] = (out[i].length()%2==0)?out[i]:"0"+out[i];
		}
		return out;
		
	}
	
	/*public String createText(String[] s_arr){
		String out ="";
		System.out.println("\n");
		for(String s : s_arr){
			String d = decompress(Long.parseLong(s.length()%2!=0?"0"+s:s));
			System.out.print(d+" ");
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
	}*/
	
	//convert Number to String
	public String createText(String[] s_arr){
		String out ="";
		System.out.println("\n");
		for(String s : s_arr){
			String d = decompress(new BigInteger(s));//Long.parseLong(s.length()%2!=0)?"0"+s:s));
			d =d.length()%2!=0?"0"+d:d;
			System.out.print(d+" ");
			for(int j=0;j<d.length();j+=2){
				out += reverselookup.get(d.substring(j,j+2));
			}
		}
		
		return out;
	}
	
	public String decompress(BigInteger i){
		BigInteger a =i.mod(BigInteger.valueOf(26));
		BigInteger n = (i.subtract(a)).divide(BigInteger.valueOf(26));
		String s = ((a.compareTo(BigInteger.valueOf(10))==-1)?("0"+a.toString()):a.toString());
		while(n.compareTo(BigInteger.valueOf(26))==1||n.compareTo(BigInteger.valueOf(26))==0){
			a = n.mod(BigInteger.valueOf(26));
			n = ((n.subtract(a)).divide(BigInteger.valueOf(26)));
			s = ((a.compareTo(BigInteger.valueOf(10))==-1)?("0"+a.toString()):a.toString())+s;
		}
		s = ((n.compareTo(BigInteger.valueOf(10))==-1)?("0"+n.toString()):n.toString())+s;
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
		BigInteger out = BigInteger.ZERO;int j=0;
		for(int i=s.length()/2;i>0;i--){
			//out = out.add(BigInteger.valueOf((long) ((Math.pow(26, i-1)).*Long.parseLong(s.substring(j, j+2)))));
			BigInteger tmp = new BigInteger(s.substring(j, j+2));
			BigInteger tmp1 = BigInteger.valueOf((long)Math.pow(26, i-1));
			out = out.add(tmp.multiply(tmp1));
			j +=2;
		}
		return out.toString();
	}
	
	public void initLookUp(){
		for(int i=65;i<91;i++){
			lookup.put(Character.toString(((char)i)), (i-65)<10?("0"+(i-65)):Integer.toString((i-65)));
			reverselookup.put((i-65)<10?("0"+(i-65)):Integer.toString((i-65)),Character.toString(((char)i)));
		}
	}
	
	public TextConv(){
		initLookUp();
	}
	
	//Find a block size with value of compressed  less than prime p
	public void generateBlockSize(BigInteger p){
		String s = "Z";
		blockLength++;
		String[] ss = createNum(s);
		while(new BigInteger(ss[0]).compareTo(p)==-1){
			blockLength++;
			s += "Z";
			ss=createNum(s);
		}
		blockLength--;
	}
	
	
	int blockLength=4;
	HashMap<String,String> lookup = new HashMap<String,String>(); 
	HashMap<String,String> reverselookup = new HashMap<String,String>(); 

}
