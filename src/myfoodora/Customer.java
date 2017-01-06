package myfoodora;

import java.util.ArrayList;

/**
 * The customers of the system place orders and pays them
 *
 */
public class Customer extends User implements Observer, java.io.Serializable {
	
	private static final long serialVersionUID = -2271603740213650215L;
	
	private String name;
	private String surname;
	private Coordinates address;
	private String email;
	private long phoneNumber;
	private FidelityCard fidelityCard;
	private ArrayList<Order> historyOfOrders;
	private ArrayList<String> messages; //to store the new offers until the customer checks in
	
	public Customer(String username, String name, String surname, Coordinates address, String password, MyFoodora sys){
		super(username, password, sys);
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.email = "";
		this.phoneNumber = -1L;
		this.fidelityCard = new BasicFidelityCard();
		this.historyOfOrders = new ArrayList<>();
		this.messages = new ArrayList<>();
		sys.addUser(this);
	}
	
	public Customer(String username, String name, String surname, Coordinates address, String email, long phoneNumber, String password, MyFoodora sys){
		super(username, password, sys);
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.fidelityCard = new BasicFidelityCard();
		this.historyOfOrders = new ArrayList<>();
		this.messages = new ArrayList<>();
		sys.addUser(this);
	}
	
	public Order createOrderAndCommand(ArrayList<Item> items, ArrayList<Meal> meals, Restaurant restaurant){
		
		Order o = new Order(items, meals, this, restaurant, getSys());
		getSys().newOrder(o);
		return o;
	}
	
	public void command(Order o){
		getSys().newOrder(o);
	}
	
	public void receiveConfirmation(Order o){
		System.out.println("The order is confirmed and will be prepared soon.");
		historyOfOrders.add(o);
	}
	
	@Override
	public void update(String message) {
		this.messages.add(message);
	}
	
	/**
	 * Checks for the messages stored since the last connection, and returns them. Then the messages are deleted.
	 * @return messages received since last connection (new offers from restaurants)
	 */
	public String lastMessages(){
		String result = "";
		for(String m : messages){
			result += m + "\n";
		}
		messages.removeAll(messages);
		return result;
	}
	
	/**
	 * This function registers the user as listening to new special offers
	 */
	public void startListeningNewOffers() {
		getSys().registerObserver(this);
	}
	
	/**
	 * This function unregisters the user as listening to new special offers
	 */
	public void stopListeningNewOffers() {
		getSys().removeObserver(this);
	}
	

	public ArrayList<Order> getHistoryOfOrders() {
		return historyOfOrders;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public FidelityCard getFidelityCard() {
		return fidelityCard;
	}

	public void setFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCard = fidelityCard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Coordinates getAddress() {
		return address;
	}
	
	
	
}
