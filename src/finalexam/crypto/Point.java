package finalexam.crypto;

import java.math.BigInteger;

public class Point{
	BigInteger x;
	BigInteger y;
	public Point(BigInteger x,BigInteger y){
		this.x = x;
		this.y = y;
	}
	public Point() {
	}
	public void printPoint(){
		System.out.println("Point x : "+x+"\nPoint y : "+y+"\n");
	}
}
