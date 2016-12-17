package myfoodora;

import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.omg.CORBA.INITIALIZE;

public class Main {
	
	public MyFoodora initialize(){
		MyFoodora sys = LoadSave.loadSys();
		return sys;
	}
	
	public static void run(){
		/*
		//<START> TO BE MODIFIED :
		
		MyFoodora sys = new MyFoodora();
		Courier jodev = new Courier("jodev", "Joseph", "Dev", 000111222, sys);
		Courier alex = new Courier("alex", "Alex", "Dela", 012012012, sys);
		Courier yvan = new Courier("vania", "Yvan", "Romich", 012012012, sys);
		Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
		Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, sys);
		Manager antoine = new Manager("Me Q", "Antoine", "Cr", sys);
		
		alex.setPassword("bonjour");
		
		//<END> TO BE MODIFIED
		*/
		
		//MyFoodora sys = null; //TODO : load from serialized !
		
		Scanner sc = new Scanner(System.in);
		
		User current = null;
		String input = "";
		
		MyFoodora sys = LoadSave.loadSys();
		
		System.out.println("Welcome to myFoodora. Type a command.");
		
		while(!input.equals("quit")){
			input = sc.nextLine();
			
			StringTokenizer st = new StringTokenizer(input);
			
			try{
				String token = st.nextToken();
				if(token.equals("login")){
					String usr = st.nextToken();
					current = sys.lookForUserByUsername(usr);
					if(current != null){
						String pswd = st.nextToken();
						if(current.getPassword().equals(pswd)){
							System.out.println("Welcome " + usr + " ! You are correctly logged in.");
						}
						else{
							System.out.println("Wrong password.");
						}
						
					}
				}
				else if(token.equals("registerClient")){
					
					String firstName = st.nextToken();
					String lastName = st.nextToken();
					String username = st.nextToken();
					double coord1 = Double.parseDouble(st.nextToken());
					double coord2 = Double.parseDouble(st.nextToken());
					String password = st.nextToken();
					String email = "";
					long phoneNumber = -1;
					
					System.out.println("Optional - give an email address (or \"skip\") :");
					String entry = sc.nextLine();
					if(!entry.equals("skip")){
						email = entry;
					}
					
					System.out.println("Optional - give  phone number (or \"skip\") :");
					entry = sc.nextLine();
					if(!entry.equals("skip")){
						phoneNumber = Long.parseLong(entry);
					}
					
					System.out.println("Confirm creation of customer : " + firstName + " " + lastName + " (username : " + username +"), living at (" + coord1 + ", " + coord2 + ") ? (y/n)");
					entry = sc.nextLine();
					if(!entry.equals("n")){
						Customer created = new Customer(username, firstName, lastName, new Coordinates(coord1, coord2), password, sys);
						if(!email.equals("")){
							created.setEmail(email);
						}
						if(phoneNumber != -1){
							created.setPhoneNumber(phoneNumber);
						}
					}
					else{
						System.out.println("Customer creation was aborted.");
					}
				}
				else if(token.equals("registerRestaurant")){
					
					String name = st.nextToken();
					String username = st.nextToken();
					double coord1 = Double.parseDouble(st.nextToken());
					double coord2 = Double.parseDouble(st.nextToken());
					String password = st.nextToken();
					
					
					System.out.println("Confirm creation of restaurant : " + name + " (username : " + username +"), located at (" + coord1 + ", " + coord2 + ") ? (y/n)");
					
					String entry = sc.nextLine();
					if(!entry.equals("n")){
						Restaurant created = new Restaurant(username, name, new Coordinates(coord1, coord2), password, sys);
					}
					else{
						System.out.println("Restaurant creation was aborted.");
					}
					
				}
				else if(token.equals("addDish")){
					if(!(current instanceof Restaurant)){
						System.out.println("This command can only be performed by restaurant !");
					}
					else{
						String dishName = st.nextToken();
						String dishCategory = st.nextToken();
						double price = Double.parseDouble(st.nextToken());
						
						Item i = new Item(dishName, price);
						((Restaurant)current).addItemToMenu(i, dishCategory);
						System.out.println("The dish " + dishName + " was added to " + dishCategory +", and costs " + price + ".");
					}
				}
				else if(token.equals("createMeal")){
					if(!(current instanceof Restaurant)){
						System.out.println("This command can only be performed by restaurant !");
					}
					else{
						String name = st.nextToken();
						String dish1 = st.nextToken();
						String dish2 = st.nextToken();
						String dish3 = null;
						if(st.hasMoreTokens()){
							dish3 = st.nextToken();
						}
						if(st.hasMoreTokens()){
							System.out.println("Error : meals can only contain 2 (half-meals) or 3 (full-meals) dishes.");
						}
						else{
							ArrayList<Item> items = new ArrayList<>();
							Item i1 = ((Restaurant)current).getMenu().lookForItemByName(dish1);
							Item i2 = ((Restaurant)current).getMenu().lookForItemByName(dish2);
							Item i3 = null;
							if(dish3 != null){
								i3 = ((Restaurant)current).getMenu().lookForItemByName(dish2);
							}
							if(i1 == null || i2 == null || (dish3 != null && i3 == null)){
								System.out.println("Error : the items were not all in menu");
							}
							else{
								items.add(i1);
								items.add(i2);
								if(i3 != null){
									items.add(i3);
								}
								
								Meal created = ((Restaurant)current).makeNewMeal(name, items, false);
								((Restaurant)current).addMealToMenu(created);
								System.out.println("Meal : " + name + " was correctly added to menu.");
							}
							
							
						}
					}
					
				}
				else if(token.equals("save")){
					LoadSave.saveSys(sys);
				}
				else if(token.equals("showUsers")){
					System.out.println("Customers : ");
					for(Customer c : sys.getCustomers()){
						System.out.println(c.getUsername());
					}
					System.out.println("Restaurants : ");
					for(Restaurant r : sys.getRestaurants()){
						System.out.println(r.getUsername());
					}
					System.out.println("Couriers : ");
					for(Courier c : sys.getCouriers()){
						System.out.println(c.getUsername());
					}
					System.out.println("Managers : ");
					for(Manager m : sys.getManagers()){
						System.out.println(m.getUsername());
					}
				}
				else if(token.equals("test")){
					
				}
				else{
					System.out.println("Command unknown. Type \"help\" for more details.");
				}
			}catch(NoSuchElementException e){
				System.out.println("Command unknown. Type \"help\" for more details.");
			}
			
		}
		
		System.out.println("Quitting... Save changes ? (y/n)");
		
		input = sc.nextLine();
		
		if(!input.equals("n")){
			LoadSave.saveSys(sys);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Main.run();
	}
}
