package myfoodora;

import java.util.ArrayList;
import java.util.Calendar;

public class test {

	public static void main(String[] args) {
		
		Calendar c = Calendar.getInstance();
		System.out.println(c.getTime().toString());
		
		Calendar d = Calendar.getInstance();
		System.out.println(d.before(c));

	}

}
