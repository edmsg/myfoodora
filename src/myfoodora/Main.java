package myfoodora;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;


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
		
		
		
		User current = null; //current user
		Restaurant selectedRestaurant = null;
		Order currentOrder = null;
		String input = "";
		
		MyFoodora sys = LoadSave.loadSys();
		
		//Manager john = new Manager("john", "john", "doe", "aa", sys);
		
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
								i3 = ((Restaurant)current).getMenu().lookForItemByName(dish3);
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
				else if(token.equals("removeMeal")){
					if(!(current instanceof Restaurant)){
						System.out.println("This command can only be performed by restaurant !");
					}
					else{
						String name = st.nextToken();
						((Restaurant)current).removeMealFromMenu(((Restaurant)current).getMenu().lookForMealByName(name));
						System.out.println("The meal : " + name + " was successfully removed.");
					}
				}
				else if(token.equals("removeDish")){
					if(!(current instanceof Restaurant)){
						System.out.println("This command can only be performed by restaurant !");
					}
					else{
						String name = st.nextToken();
						Item i = ((Restaurant)current).getMenu().lookForItemByName(name);
						if(i != null){
							ArrayList<Meal> mealsContainingItem = new ArrayList<>();
							for(Meal m : ((Restaurant)current).getMenu().getMeals()){
								if(m.getItems().contains(i)){
									mealsContainingItem.add(m);
								}
							}
							if(!mealsContainingItem.isEmpty()){
								System.out.println("Caution ! The item you are trying to delete is contained in the fellowing meals :");
								for(Meal m : mealsContainingItem){
									System.out.println(m.getName() + "\n");
								}
								System.out.println("Deleting this item will also delete these meals. Are you sure you want to proceed ? (y/n)");
								String entry = sc.nextLine();
								if(entry.equals("y")){
									for(Meal m : mealsContainingItem){
										((Restaurant)current).removeMealFromMenu(m);
									}
									((Restaurant)current).removeItemFromMenu(i);
									
								}
								else{
									System.out.println("Deletion aborted.");
								}
							}
							else{
								((Restaurant)current).removeItemFromMenu(i);
								System.out.println("The item : " + name + " was successfully removed.");
							}
						}
					}
				}
				else if(token.equals("showRestaurants")){
					System.out.println("Available restaurants : ");
					for(Restaurant r : sys.getRestaurants()){
						System.out.println(r.getUsername());
					}
				}
				else if(token.equals("selectRestaurant")){
					String name = st.nextToken();
					selectedRestaurant = (Restaurant)sys.lookForUserByUsername(name);
					if(selectedRestaurant != null){
						System.out.println("You have selected the restaurant " + selectedRestaurant.getUsername() + ".");
					}
				}
				else if(token.equals("showMenu")){
					if(selectedRestaurant == null){
						System.out.println("No restaurant selected. You have to select a restaurant first !");
					}
					else{
						System.out.println("Menu of the restaurant : " + selectedRestaurant.getName());
						System.out.println(selectedRestaurant.getMenu().menuText());
					}
				}
				else if(token.equals("startOrder")){
					if(!(current instanceof Customer)){
						System.out.println("This command can only be performed by a customer !");
					}
					else if(selectedRestaurant == null){
						System.out.println("You have to select a restaurant first ! (\"showRestaurants\")");
					}
					else{
						currentOrder = new Order(new ArrayList<Item>(), new ArrayList<Meal>(), (Customer)current, selectedRestaurant, sys);
						System.out.println("New order started.");
					}
					
				}
				else if(token.equals("addDishToOrder")){
					if(currentOrder == null){
						System.out.println("You have to start an order first. (\"startOrder\")");
					}
					else{
						String name = st.nextToken();
						Item i = currentOrder.getRestaurant().getMenu().lookForItemByName(name);
						if(i != null){
							currentOrder.addItemToOrder(i);
							System.out.println("The dish " + name + " was added to the order. The price is now : " + currentOrder.getPrice() + ".");
						}
					}
				}
				else if(token.equals("addMealToOrder")){
					if(currentOrder == null){
						System.out.println("You have to create an order first. (\"createOrder\")");
					}
					else{
						String name = st.nextToken();
						Meal m = currentOrder.getRestaurant().getMenu().lookForMealByName(name);
						if(m != null){
							currentOrder.addMealToOrder(m);
							System.out.println("The meal " + name + " was added to the order. The price is now : " + currentOrder.getPrice() + ".");
						}
					}
				}
				else if(token.equals("removeDishFromOrder")){
					if(currentOrder == null){
						System.out.println("You have to create an order first. (\"createOrder\")");
					}
					else{
						String name = st.nextToken();
						Item i = currentOrder.getRestaurant().getMenu().lookForItemByName(name);
						if(i != null){
							currentOrder.removeItemFromOrder(i);
							System.out.println("The item " + i.getName() + " was removed from the order. The price is now : " + currentOrder.getPrice());
						}
						else{
							System.out.println("The item " + i.getName() + " was not found in the order.");
						}
					}
				}
				else if(token.equals("removeMealFromOrder")){
					if(currentOrder == null){
						System.out.println("You have to create an order first. (\"startOrder\")");
					}
					else{
						String name = st.nextToken();
						Meal m = currentOrder.getRestaurant().getMenu().lookForMealByName(name);
						if(m != null){
							currentOrder.removeMealFromOrder(m);
							System.out.println("The meal " + m.getName() + " was removed from the order. The price is now : " + currentOrder.getPrice());
						}
						else{
							System.out.println("The meal " + m.getName() + " was not found in the order.");
						}
					}
				}
				else if(token.equals("showOrder")){
					if(currentOrder == null){
						System.out.println("You have to create an order first. (\"startOrder\")");
					}
					else{
						System.out.println("Order contains for now : ");
						System.out.print("Items : ");
						for(Item i : currentOrder.getItems()){
							System.out.print(i.getName() + " ");
						}
						System.out.print("\nMeals : ");
						for(Meal m : currentOrder.getMeals()){
							System.out.print(m.getName() + " ");
						}
						System.out.println("\nThe price is now " + currentOrder.getPrice());
					}
				}
				else if(token.equals("showOwnMenu")){
					if(!(current instanceof Restaurant)){
						System.out.println("This command can only be performed by restaurant !");
					}
					else{
						System.out.println(((Restaurant)current).getMenu().menuText());
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
				else if(token.equals("removeUser")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						String userToRemove = st.nextToken();
						((Manager)current).removeUser(sys.lookForUserByUsername(userToRemove));
					}
				}
				else if(token.equals("logout")){
					current = null;
					System.out.println("Logged out successfully.");
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
