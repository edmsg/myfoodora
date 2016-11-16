package myfoodora;

public class Courier extends User {
	
	private String name;
	private String surname;
	private Coordinates position;
	private boolean isAvailable;
	private long phoneNumber;
	private int counter; //counts the number of delivered orders

	public Courier(String username, String name, String surname, long phoneNumber, MyFoodora sys){
		super(username, sys);
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.position = new Coordinates(0, 0);
		this.counter = 0;
		this.isAvailable = true;
		sys.addUser(this);
	}
	
	public boolean acceptDelivery(Order o){
		//TODO : create a routine to allow the courier to refuse the delivery
		boolean accept = true;
		if(name.equals("Joseph")){
			accept = false;
		}
		return accept;
	}
	
	
	

	public Coordinates getPosition() {
		return position;
	}

	public void setPosition(Coordinates position) {
		this.position = position;
	}

	public int getCounter() {
		return counter;
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}
