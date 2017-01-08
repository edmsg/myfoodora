package myfoodora;

import java.io.*;

public class LoadSave {
	
	public static void saveSys(MyFoodora sys){
		try{
			FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/data/myFoo.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(sys);
			
			oos.close();
			fos.close();
			System.out.println("System was saved without issue.");
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static MyFoodora loadSys(){
		MyFoodora sys = null;
		
		try{
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/data/myFoo.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			sys = (MyFoodora) ois.readObject();
			
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			System.out.println("Class not found. The loading of the system failed.");
			e.printStackTrace();
		}
		
		System.out.println("System was loaded without issue.");
		return sys;
	}
}
