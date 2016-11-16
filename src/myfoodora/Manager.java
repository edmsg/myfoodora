package myfoodora;

/**
 * Managers oversee the system. They add/remove users and set the general parameters of the system.
 * @author benjamin
 *
 */

public class Manager extends User{
	private String name;
	private String surname;
	
	public Manager(String username, String name, String surname, MyFoodora sys){
		super(username, sys);
		this.name = name;
		this.surname = surname;
		sys.addUser(this); //the manager is automatically added to the manager list of the system
	}
	
	public Manager addManager(String username, String name, String surname){
		return new Manager(username, name, surname, getSys()); //the manager is automatically added to the system through its construction : no need to add it a second time
	}
	
	public Restaurant addRestaurant(String username, String name, Coordinates address){
		Restaurant r = new Restaurant(username, name, address, getSys());
		return r;
	}
	
	public Courier addCourier(String username, String name, String surname, long phoneNumber){
		Courier c = new Courier(username, name, surname, phoneNumber, getSys());
		return c;
	}
	
	public Customer addCustomer(String username, String name, String surname, Coordinates address, String email, long phoneNumber){
		Customer c = new Customer(username, name, surname, address, email, phoneNumber, getSys());
		return c;
	}
	
	public void removeUser(User u){
		getSys().removeUser(u);
	}
	
	

	public void setServiceFee(double serviceFee) {
		getSys().setServiceFee(serviceFee);
	}
	public void setMarkupPercentage(double markupPercentage) {
		getSys().setMarkupPercentage(markupPercentage);
	}
	public void setDeliveryCost(double deliveryCost) {
		getSys().setDeliveryCost(deliveryCost);
	}
	

}
