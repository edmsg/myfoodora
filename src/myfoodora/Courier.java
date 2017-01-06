package myfoodora;

/**
 * The couriers of the system, who are in charge of deliveries
 *
 */
public class Courier extends User implements java.io.Serializable{
	
	private static final long serialVersionUID = 4922340959316128316L;
	
	private String name;
	private String surname;
	private Coordinates position;
	private Order orderInDelivery;
	private boolean isAvailable;
	private long phoneNumber;
	private int counter; //counts the number of delivered orders

	public Courier(String username, String name, String surname, long phoneNumber, String password, MyFoodora sys){
		super(username, password, sys);
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.position = new Coordinates(0, 0);
		this.counter = 0;
		this.isAvailable = true;
		this.orderInDelivery = null;
		sys.addUser(this);
	}
	
	public boolean acceptDelivery(Order o){
		boolean accept = true;
		if(accept){
			startDelivery(o);
			orderInDelivery = o;
		}
		return accept;
	}
	
	public void startDelivery(Order o){
		isAvailable = false;
	}
	
	public boolean endDelivery(){
		boolean deliveryCompleted = false;
		if(orderInDelivery != null){
			counter ++;
			orderInDelivery = null;
			deliveryCompleted = true;
		}
		isAvailable = true;
		return deliveryCompleted;
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
	
	public void setCounter(int counter){
		this.counter = counter;
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}
