package myfoodora;

/**
 * Managers oversee the system. They add/remove users and set the general parameters of the system.
 * @author benjamin
 *
 */

public class Manager extends User{
	private MyFoodora sys; //link to the system
	private String name;
	private String surname;
	
	public Manager(String username, String name, String surname, MyFoodora sys){
		super(username);
		this.name = name;
		this.surname = surname;
		this.sys = sys;
		sys.addUser(this); //the manager is automatically added to the manager list of the system
	}
	
	public Manager addManager(String username, String name, String surname){
		return new Manager(username, name, surname, sys); //the manager is automatically added to the system through its construction : no need to add it a second time
	}
	
	public Restaurant addRestaurant(String username, String name, Coordinates address){
		Restaurant r = new Restaurant(username, name, address);
		sys.addUser(r);
		return r;
	}
	
	public Courier addCourier(String username, String name, String surname, long phoneNumber){
		Courier c = new Courier(username, name, surname, phoneNumber);
		sys.addUser(c);
		return c;
	}
	
	public Customer addCustomer(String username, String name, String surname, Coordinates address, String email, long phoneNumber){
		Customer c = new Customer(username, name, surname, address, email, phoneNumber, sys);
		sys.addUser(c);
		return c;
	}
	
	public void removeUser(User u){
		sys.removeUser(u);
	}
	
	

	public void setServiceFee(double serviceFee) {
		sys.setServiceFee(serviceFee);
	}
	public void setMarkupPercentage(double markupPercentage) {
		sys.setMarkupPercentage(markupPercentage);
	}
	public void setDeliveryCost(double deliveryCost) {
		sys.setDeliveryCost(deliveryCost);
	}
	

}
