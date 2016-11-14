package myfoodora;

import java.rmi.server.UID;

public class User {
	
	private String username;
	private UID uid;
	
	public User(String username){
		this.username = username;
		this.uid = new UID();
	}
	
	public String getUsername(){
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
