package finalexam.crypto;

import java.util.Stack;

public class SecretSharing {
	public static void main(String[] args){
		String eqn = "1x2+-2x1+-5x0";
		Integer[] x =findXK(7);
		Integer[] y =findYK(x,eqn);
		System.out.println();
	}
	
	public static Integer[] findYK(Integer[] x,String eqn){
		Integer[] y = new Integer[x.length];
		
		String[] e = eqn.split("\\+");
		int[] left = new int[e.length];
		int[] right = new int[e.length];
		for(int k=0;k<e.length;k++){
			String[] t = e[k].split("x");
			left[k] = Integer.parseInt(t[0]);
			right[k] = Integer.parseInt(t[1]);
		}
		for(int k=0;k<x.length;k++){
			if(y[k]==null)y[k]=0;
			for(int j=0;j<left.length;j++){
				y[k] += (int)Math.pow(x[k], right[j])*left[j];
			}
		}
	
		return y;
	}
	
	
	public static Integer[] findXK(int n){
		
		Integer[] x = new Integer[n+1];
		x[0]=0;
		for(int i=1;i<n+1;i++){
			x[2*i-1]=i;
			x[2*i]=-i;
			if(2*i>=n-1){
				if(n%2==1){
					x[2*i+1]=i+1;
				}
				break;
			}
		}
		
		return x;
	}
	
}
