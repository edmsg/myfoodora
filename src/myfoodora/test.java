package myfoodora;

import java.util.ArrayList;

public class test {

	public static void main(String[] args) {
		String a = "aaa";
		String b = "aaa";
		
		ArrayList<String> l = new ArrayList<>();
		
		l.add(a);
		
		System.out.println(a == b);
		System.out.println(a.equals(b));
		System.out.println(l.contains(b));

	}

}
