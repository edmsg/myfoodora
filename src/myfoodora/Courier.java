package myfoodora;

public class Courier extends User {
	
	private String name;
	private String surname;
	private Coordinates position;
	private boolean state; //0 : off duty, 1 : on duty
	private long phoneNumber;
	private int counter; //counts the number of delivered orders

	public Courier(String username, String name, String surname, long phoneNumber){
		super(username);
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.position = new Coordinates(0, 0);
		this.counter = 0;
	}
}
