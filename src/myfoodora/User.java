package myfoodora;

import java.rmi.server.UID;

public class User {
	
	private String username;
	private UID uid;
	private MyFoodora sys;
	
	public User(String username, MyFoodora sys){
		this.username = username;
		this.uid = new UID();
		this.sys = sys;
	}
	
	public String getUsername(){
		return username;
	}
	
	public MyFoodora getSys(){
		return sys;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
