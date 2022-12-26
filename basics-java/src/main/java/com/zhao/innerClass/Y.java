package com.zhao.innerClass;

public class Y{
	public static void main(String[] args) {
		X x = new X();
		X.Z z = x.new Z();
//		X.W w = new X.W();
	}
}
