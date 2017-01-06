package myfoodora;

import java.rmi.server.UID;

public abstract class User implements java.io.Serializable {

	private static final long serialVersionUID = 6539345742582256678L;
	
	private String username;
	private String password;
	private UID uid;
	private MyFoodora sys;
	
	public User(String username, MyFoodora sys){
		this.username = username;
		this.password = "";
		this.uid = new UID();
		this.sys = sys;
	}
	
	public User(String username, String password, MyFoodora sys){
		this.username = username;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
