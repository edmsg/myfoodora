package myfoodora;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.plaf.synth.SynthSeparatorUI;


public class CommandLineUserInterface {
	
	public MyFoodora initialize(){
		MyFoodora sys = LoadSave.loadSys();
		return sys;
	}
	
	public static void run(){
		
		
		Scanner sc = new Scanner(System.in);
		
		
		
		User current = null; //current user
		Restaurant selectedRestaurant = null;
		Order currentOrder = null;
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
							if(current instanceof Customer && !((Customer)current).getMessages().isEmpty()){
								System.out.println("You have new messages :\n" + ((Customer) current).lastMessages());
							}
						}
						else{
							System.out.println("Wrong password.");
						}
					}
				}
				else if(token.equals("registerCustomer")){
					
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
					
					System.out.println("Do you want to receive notifications of newly created special offers ? (y/n)");
					String specialOffers = sc.nextLine();
					
					
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
						if(specialOffers.equals("y")){
							created.startListeningNewOffers();
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
				else if(token.equals("registerCourier")){
					String name = st.nextToken();
					String surname = st.nextToken();
					String username = st.nextToken();
					double coord1 = Double.parseDouble(st.nextToken());
					double coord2 = Double.parseDouble(st.nextToken());
					long phoneNumber = Long.parseLong(st.nextToken());
					String password = st.nextToken();
					
					System.out.println("Confirm creation of courier : " + name + " " + surname + " (" + username + "), with phone number " + phoneNumber + " ? (y/n)");
					String entry = sc.nextLine();
					if(entry.equals("y")){
						Courier created = new Courier(username, name, surname, phoneNumber, password, sys);
						created.setPosition(new Coordinates(coord1, coord2));
						created.setAvailable(false);
					}
				}
				else if(token.equals("registerManager")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						String name = st.nextToken();
						String surname = st.nextToken();
						String username = st.nextToken();
						String password = st.nextToken();
						
						Manager created = new Manager(username, name, surname, password, sys);
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
						ArrayList<String> type = new ArrayList<>();
						while(st.hasMoreTokens()){
							type.add(st.nextToken());
						}
						
						Item i = new Item(dishName, price, type);
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
				else if(token.equals("showMeal")){
					if(selectedRestaurant != null){
						String name = st.nextToken();
						System.out.println(selectedRestaurant.getMenu().lookForMealByName(name).toString());
					}
					else if(current instanceof Restaurant){
						String name = st.nextToken();
						System.out.println((((Restaurant) current).getMenu().lookForMealByName(name)).toString());
					}
					else{
						System.out.println("This command can only be performed by a customer or a restaurant !");
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
				else if(token.equals("receiveSpecialOffers")){
					if(!(current instanceof Customer)){
						System.out.println("This command can only be performed by a customer.");
					}
					else{
						((Customer) current).startListeningNewOffers();
						System.out.println("From now on, you will recieve notifications of special offers.");
					}
				}
				else if(token.equals("stopReceivingSpecialOffers")){
					if(!(current instanceof Customer)){
						System.out.println("This command can only be performed by a customer.");
					}
					else{
						((Customer) current).stopListeningNewOffers();
						System.out.println("You will no longer receive notification of special offers.");
					}
				}
				else if(token.equals("showStatus")){
					if(!(current instanceof Customer)){
						System.out.println("This command can only be performed by a customer.");
					}
					else{
						System.out.println("Customer's status : ");
						System.out.println("Username : " + ((Customer) current).getUsername());
						System.out.println("Name : " + ((Customer) current).getName());
						System.out.println("Surname : " + ((Customer) current).getSurname());
						System.out.println("Address : (" + ((Customer) current).getAddress().getX() + ", " + ((Customer) current).getAddress().getY() + ")");
						System.out.print("Fidelity card type : ");
						FidelityCard fc = ((Customer) current).getFidelityCard();
						if(fc instanceof BasicFidelityCard){
							System.out.print("basic fidelity card");
						}
						else if(fc instanceof PointFidelityCard){
							System.out.print("points fidelity card (currently " + ((PointFidelityCard) fc).getPoints() + " points out of 100 to get a 10% discount)");
						}
						else if(fc instanceof LotteryFidelityCard){
							System.out.print("lottery fidelity card");
						}
						System.out.print("\n");
						System.out.println("Number of orders : " + ((Customer) current).getHistoryOfOrders().size());
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
					if(selectedRestaurant != null){
						System.out.println("Menu of the restaurant : " + selectedRestaurant.getName());
						System.out.println(selectedRestaurant.getMenu().menuText());
					}
					else if(current instanceof Restaurant){
						System.out.println(((Restaurant) current).getMenu().menuText());
					}
					else if(current instanceof Customer && selectedRestaurant == null){
						System.out.println("You have to select a restaurant first ! \"selectRestaurant <restaurantUsername>\"");
					}
					else{
						System.out.println("This command can only be performed by a customer or a restaurant");
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
				else if(token.equals("addDish2Order")){
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
				else if(token.equals("addMeal2Order")){
					if(currentOrder == null){
						System.out.println("You have to create an order first. (\"startOrder\")");
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
						System.out.println("You have to create an order first. (\"startOrder\")");
					}
					else{
						String name = st.nextToken();
						Item i = currentOrder.getRestaurant().getMenu().lookForItemByName(name);
						if(i != null){
							currentOrder.removeItemFromOrder(i);
							System.out.println("The item " + i.getName() + " was removed from the order. The price is now : " + currentOrder.getPrice());
						}
						else{
							System.out.println("The item " + name + " was not found in the order.");
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
							System.out.println("The meal " + name + " was not found in the order.");
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
				else if(token.equals("setSpecialOffer")){
					if(!(current instanceof Restaurant)){
						System.out.println("This command can only be performed by a restaurant.");
					}
					else{
						String mealName = st.nextToken();
						Meal m = ((Restaurant) current).getMenu().lookForMealByName(mealName);
						if(m != null){
							((Restaurant) current).setMealOfTheWeek(m);
							System.out.println(m.getName() + " was put on special offer.")	;
						}
					}
				}
				else if(token.equals("removeFromSpecialOffer")){
					if(!(current instanceof Restaurant)){
						System.out.println("This command can only be performed by a restaurant.");
					}
					else{
						String mealName = st.nextToken();
						Meal m = ((Restaurant) current).getMenu().lookForMealByName(mealName);
						if(m != null){
							((Restaurant) current).setNotMealOfTheWeek(m);
							System.out.println(m.getName() + " was removed from special offer.")	;
						}
					}
				}
				else if(token.equals("endOrder")){
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
						System.out.println("Do you want to confirm the order ? (y/n)");
						String entry = sc.nextLine();
						if(entry.equals("y")){
							currentOrder.computeFinalPrice();
							System.out.println("Cards gave you a reduction of " + currentOrder.getDiscountDueToCards() + ", bringing the price to " + currentOrder.getPrice());
							sys.newOrder(currentOrder);
						}
						else{
							System.out.println("Finalisation aborted.");
						}
					}
					
				}
				else if(token.equals("showOrdersToComplete")){
					if(!(current instanceof Restaurant)){
						System.out.println("This command can only be performed by a restaurant !");
					}
					else{
						for(Order o : ((Restaurant) current).getOrdersToComplete()){
							System.out.println(o.getName());
						}
					}
				}
				else if(token.equals("setOrderCompleted")){
					if(!(current instanceof Restaurant)){
						System.out.println("This command can only be performed by a restaurant !");
					}
					else{
						String orderName = st.nextToken();
						Order o = ((Restaurant) current).lookForOrderByName(orderName);
						if(o != null){
							((Restaurant) current).getOrdersToComplete().remove(o);
							System.out.println("Order " + o.getName() + " was listed as completed.");
						}
					}
				}
				else if(token.equals("findDeliverer")){
					if(!(current instanceof Restaurant)){
						System.out.println("This command can only be performed by a restaurant !");
					}
					else{
						Courier found = sys.findAvailableCourier(currentOrder);
						if(found != null){
							System.out.println("Courier found : " + found.getUsername());
							found.setAvailable(true);
						}
					}
				}
				else if(token.equals("onDuty")){
					if(!(current instanceof Courier)){
						System.out.println("This command can only be performed by a courier !");
					}
					else{
						((Courier) current).setAvailable(true);
						System.out.println("Your status was set on \"on duty\".");
					}
				}
				else if(token.equals("offDuty")){
					if(!(current instanceof Courier)){
						System.out.println("This command can only be performed by a courier !");
					}
					else{
						((Courier) current).setAvailable(false);
						System.out.println("Your status was set on \"off duty\".");
					}
				}
				else if(token.equals("deliveryFinished")){
					if(!(current instanceof Courier)){
						System.out.println("This command can only be performed by a courier !");
					}
					else{
						if(((Courier) current).endDelivery()){
							System.out.println("Your delivery was completed. Your counter is now at " + ((Courier) current).getCounter() + " deliveries.");
						}
					}
				}
				else if(token.equals("setPosition")){
					if(!(current instanceof Courier)){
						System.out.println("This command can only be performed by a courier !");
					}
					else{
						try{
							double coord1 = Double.parseDouble(st.nextToken());
							double coord2 = Double.parseDouble(st.nextToken());
							((Courier) current).setPosition(new Coordinates(coord1, coord2));
							System.out.println("New position set : " + coord1 + ", " + coord2);
							
						}catch(NumberFormatException e){
							System.out.println("Coordinates must be numbers !");
						}
					}
				}
				else if(token.equals("save")){
					LoadSave.saveSys(sys);
				}
				else if(token.equals("setDeliveryPolicy")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						String name = st.nextToken();
						if(name.equals("fastest")){
							DeliveryPolicyFastest dpf = new DeliveryPolicyFastest(sys);
							sys.setDeliveryPolicy(dpf);
							System.out.println("Delivery policy was set to fastest");
						}
						else if(name.equals("fair")){
							DeliveryPolicyFairOccupation dpfo = new DeliveryPolicyFairOccupation(sys);
							sys.setDeliveryPolicy(dpfo);
							System.out.println("Delivery policy was set to fair occupation");
						}
						else{
							System.out.println(name + " is not a valid delivery policy name. Available delivery policies are \"fastest\" or \"fair\".");
						}
					}
				}
				else if(token.equals("setProfitPolicy")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						try{
							String name = st.nextToken();
							double targetProfit = Double.parseDouble(st.nextToken());
							if(name.equals("deliveryCost")){
								TargetProfitDeliveryCost tpdc = new TargetProfitDeliveryCost(sys);
								((Manager) current).setProfitPolicy(tpdc);
								System.out.println("Target profit policy was set to target delivery cost.");
							}
							else if(name.equals("markupPercentage")){
								TargetProfitMarkupPercentage tpmp = new TargetProfitMarkupPercentage(sys);
								((Manager) current).setProfitPolicy(tpmp);
								System.out.println("Target profit policy was set to target markup percentage.");
							}
							else if(name.equals("serviceFee")){
								TargetProfitServiceFee tpsf = new TargetProfitServiceFee(sys);
								((Manager) current).setProfitPolicy(tpsf);
								System.out.println("Target profit policy was set to target service fee.");
							}
							else{
								System.out.println(name + " is not a valid target profit policy name. Available target profit policies are \"deliveryCost\", \"markupPercentage\" or \"serviceFee\".");
							}
							
							((Manager) current).setProfitVariablesToMeetTargetProfit(targetProfit);
							System.out.println("Profit variables are now : delivery cost = " + sys.getDeliveryCost() + ", markup percentage = " + sys.getMarkupPercentage() + ", service fee = " + sys.getServiceFee());
						}catch(NumberFormatException e){
							System.out.println("<targetProfit> must be a number");
						}
					}
				}
				else if(token.equals("setSortingPolicy")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						String name = st.nextToken();
						boolean reverse = false;
						if(st.hasMoreTokens()){
							reverse = true;
						}
						
						if(name.equals("halfMeal")){
							SortingPolicyHalfMeal sphm = new SortingPolicyHalfMeal(reverse, sys);
							((Manager) current).setSortingPolicy(sphm);
							System.out.println("Sorting policy was set to half meal.");
						}
						else if(name.equals("items")){
							SortingPolicyItems spi = new SortingPolicyItems(reverse, sys);
							((Manager) current).setSortingPolicy(spi);
							System.out.println("Sorting policy was set to items");
						}
						else{
							System.out.println(name + " is not a valid sorting policy name. Avalaible sorting policies are \"halfMeal\" or \"items\".");
						}
					}
				}
				else if(token.equals("associateCard")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						String username = st.nextToken();
						String type = st.nextToken();
						User c = sys.lookForUserByUsername(username);
						if(!(c instanceof Customer)){
							System.out.println("A card can be associated only to a customer.");
						}
						else{
							if(type.equals("basic")){
								FidelityCard bfc = FidelityCardFactory.createFidelityCard("basic");
								((Customer) c).setFidelityCard(bfc);
								System.out.println(username + " was associated a card of type " + type);
							}
							else if(type.equals("lottery")){
								FidelityCard lfc = FidelityCardFactory.createFidelityCard("lottery");
								((Customer) c).setFidelityCard(lfc);
								System.out.println(username + " was associated a card of type " + type);
							}
							else if(type.equals("point")){
								FidelityCard pfc = FidelityCardFactory.createFidelityCard("point");
								((Customer) c).setFidelityCard(pfc);
								System.out.println(username + " was associated a card of type " + type);
							}
							else{
								System.out.println(type + " is not a valid type : \"basic\", \"lottery\" or \"point\".");
							}
						}
					}
				}
				else if(token.equals("showCouriersDeliveries")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						System.out.println("Couriers sorted from the most to the least active :");
						System.out.println(((Manager) current).listCouriersSortedDeliveries());
					}
				}
				else if(token.equals("showRestaurantTop")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						System.out.println("Restaurants sorted from the most to the least selling :");
						System.out.println(((Manager) current).listRestaurantsSortedOrders());
					}
				}
				else if(token.equals("showCustomers")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						System.out.println("Customers registered : ");
						for(Customer c : sys.getCustomers()){
							System.out.println(c.getUsername());
						}
					}
				}
				else if(token.equals("showMenuItem")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						String name = st.nextToken();
						User r = sys.lookForUserByUsername(name);
						if(r instanceof Restaurant){
							System.out.println("Menu of the restaurant sorted w.r.t. the current sorting policy :");
							ArrayList<String> elements = sys.getSortingPolicy().sortedShippedOrders((Restaurant) r);
							for(String s : elements){
								System.out.println(s);
							}
						}
					}
				}
				else if(token.equals("showTotalProfit")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						if(st.hasMoreTokens()){
							//dates specified
							String dateStart = st.nextToken();
							String dateEnd = st.nextToken();
							
							try{
								Calendar start = Calendar.getInstance();
								start.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStart.substring(0,2)));
								start.set(Calendar.MONTH, Integer.parseInt(dateStart.substring(2,4)));
								start.set(Calendar.YEAR, Integer.parseInt(dateStart.substring(4)));
								start.set(Calendar.HOUR, 0);
								
								Calendar end = Calendar.getInstance();
								end.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateEnd.substring(0,2)));
								end.set(Calendar.MONTH, Integer.parseInt(dateEnd.substring(2,4)));
								end.set(Calendar.YEAR, Integer.parseInt(dateEnd.substring(4)));
								end.set(Calendar.HOUR, 0);
								
								
								System.out.println("Profit between " + dateStart.substring(0,2) + "/" + dateStart.substring(2,4) + "/" + dateStart.substring(4) + " and " + dateEnd.substring(0,2) + "/" + dateEnd.substring(2,4) + "/" + dateEnd.substring(4) + " : " + ((Manager) current).computeProfit(start, end));
							}catch(NumberFormatException e){
								System.out.println("Wrong date format : DDMMYYYY");
							}catch(IndexOutOfBoundsException e){
								System.out.println("Wrong date format : DDMMYYYY");
							}
						}
						else{
							//compute profit since creation
							System.out.println("Total profit since creation : " + ((Manager) current).computeTotalProfit());
						}
					}
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
				else if(token.equals("inactivateUser")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						String username = st.nextToken();
						User u = sys.lookForUserByUsername(username);
						if(u != null){
							((Manager) current).inactivateUser(u);
							System.out.println(u.getUsername() + " was inactivated.");
						}
					}
				}
				else if(token.equals("activateUser")){
					if(!(current instanceof Manager)){
						System.out.println("This command can only be performed by a manager !");
					}
					else{
						String username = st.nextToken();
						User u = sys.lookForInactiveUser(username);
						if(u != null){
							sys.addUser(u);
						}
					}
				}
				else if(token.equals("logout")){
					current = null;
					System.out.println("Logged out successfully.");
				}
				else if(token.equals("help")){
					if(st.hasMoreTokens()){
						CommandLineUserInterface.showHelp("f");
					}
					else{
						if(current instanceof Customer){
							CommandLineUserInterface.showHelp("c");
						}
						else if(current instanceof Courier){
							CommandLineUserInterface.showHelp("co");
						}
						else if(current instanceof Restaurant){
							CommandLineUserInterface.showHelp("r");
						}
						else if(current instanceof Manager){
							CommandLineUserInterface.showHelp("m");
						}
						else{
							CommandLineUserInterface.showHelp("x");
						}
					}
				}
				else if(token.equals("runTest")){
					String filename = st.nextToken();
					String filepath = System.getProperty("user.dir") + "/data/" + filename;
					
					try {
						sc = new Scanner(new File(filepath));
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
				}
				else if(token.equals("clearAll")){
					System.out.println("This command will delete every user and start an all new system. Are you sure you want to do this ? (y/n)");
					String entry = sc.nextLine();
					if(entry.equals("y")){
						sys = new MyFoodora();
						Manager ceo = new Manager("ceo", "ceo", "ceo", "123456789", sys);
						LoadSave.saveSys(sys);
						System.out.println("System was reinitialized.");
						System.exit(0);
					}
					
				}
				else if(token.equals("#")){
					// This is how comment lines start in the runTest files. These lines are ignored.
				}
				else{
					if(!token.equals("quit")){
						System.out.println(token + " : command unknown. Type \"help\" for more details.");
					}
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
		
		sc.close();
		
		
	}
	
	
	public static void showHelp(String type){
		//shows a list of commands : "f" for full, "c" for customer, "co" for courier, "r" for restaurants, "m" for managers
		System.out.println("List of commands available :");
		System.out.println("General commands :");
		System.out.println("help [full] : to show this help (if the optional argument \"full\" is here, every single command is displayed; otherwise, only the ones that can be used by the logged in user");
		System.out.println("quit <> : to exit the application");
		System.out.println("login <username> <password> : to login to the system");
		System.out.println("registerCustomer <firstname> <lastname> <username> <addressCoord1> <addressCoord2> <password> : to register a new customer to the system");
		System.out.println("registerRestaurant <name> <username> <addressCoord1> <addressCoord2> <password> : to register a new restaurant to the system");
		System.out.println("registerCourier <name> <surname> <username> <positionCoord1> <positionCoord2> <phoneNumber> <password> : to register a new courrier");
		System.out.println("save <> : to save the changes that have been performed");
		System.out.println("showUsers <> : to show the name of all the users registered in the system");
		System.out.println("logout <> : to log out of the system");
		if(type.equals("c") || type.equals("f")){
			System.out.println("\nCommands for customers :");
			System.out.println("showRestaurants <> : to show the restaurants registered into the system");
			System.out.println("selectRestaurant <restaurantUsername> : to select the restaurant in which perform an order");
			System.out.println("showMenu <> : shows the menu of the selected restaurant");
			System.out.println("startOrder <> : starts a new order");
			System.out.println("addDish2Order <dishName> : adds an item to the current order");
			System.out.println("addMeal2Order <mealName> : adds a meal to the current order");
			System.out.println("removeDishFromOrder <dishName> : removes a dish from the current order");
			System.out.println("removeMealFromOrder <mealName> : removes a meal from the current order");
			System.out.println("showOrder <> : show the current order (content and price)");
			System.out.println("showMeal <mealName> : to show the content of a meal in the selected restaurant menu");
			System.out.println("receiveSpecialOffers <> : to receive a notification when a new special offer is created");
			System.out.println("stopReceivingSpecialOffers <> : to stop receiving these notifications");
			System.out.println("endOrder <> : to review and confirm the order completed");
		}
		if(type.equals("r") || type.equals("f")){
			System.out.println("\nCommands for restaurants :");
			System.out.println("addDish <dishName> <dishCourse> <price> [<type1>, <type2>, <type3>...]: adds a new dish to the menu (dishCourse must be : \"s\" for starter, \"m\" for main dish, or \"d\" for dessert. Types are optionals.)");
			System.out.println("createMeal <nameMeal> <nameItem1> <nameItem2> [<nameItem3>] : create a new meal composed of the specified items (the third item is optional)");
			System.out.println("removeMeal <mealName> : to remove a meal from the menu");
			System.out.println("showMeal <mealName> : to show the content of a meal in the menu");
			System.out.println("removeDish <dishName> : to remove a dish from the menu");
			System.out.println("showMenu <> : for the logged in restaurant to show its own menu");
			System.out.println("setSpecialOffer <mealName> : set the meal as meal of the week");
			System.out.println("removeFromSpecialOffer <mealName> : remove the status of meal of the week for the meal");
			System.out.println("findDeliverer <> : finds an available deliverer for the current order, according to the current delivery policy");
			System.out.println("showOrdersToComplete <> : shows the orders that have been sent, but not prepared yet");
			System.out.println("setOrderCompleted <orderName> : to set the status of an order from \"to complete\" to \"completed\"");
			System.out.println("showShippedOrders <> : display a list of shipped orders corresponding to the current sorting policy");
		}
		if(type.equals("co") || type.equals("f")){
			System.out.println("onDuty <> : to set the state of the courier as \"on duty\"");
			System.out.println("offDuty <> : to set the state of the courier as \"off duty\"");
			System.out.println("deliveryFinished <> : to announce the current delivery was completed without issues.");
			System.out.println("setPosition <coord1> <coord2> : to set the courier's position");
		}
		if(type.equals("m") || type.equals("f")){
			System.out.println("\nCommands for managers :");
			System.out.println("removeUser <username> : to remove the user from the system");
			System.out.println("inactivateUser <username> : to inactivate (but not delete) a user in the system");
			System.out.println("activateUser <username> : to activate a user which was previously inactivated");
			System.out.println("registerManager <name> <surname> <username> <password> : to register a new manager to the system");
			System.out.println("setDeliveryPolicy {fastest, fair} : set the delivery policy used to find a courier");
			System.out.println("setProfitPolicy {deliveryCost, markupPercentage, serviceFee} : set the system's target profit policy to the corresponding policy");
			System.out.println("setSortingPolicy {halfMeal, items} [reverse] : set the system's sorting policy to the corresponding policy. Optional argument \"reverse\" to reverse the sorting order.");
			System.out.println("showCouriersDeliveries <> : show the list of the couriers of the fleet, sorted from the most to the least active.");
			System.out.println("showRestaurantTop <> : show the list of the restaurants sorted from the most to the least selling");
			System.out.println("showCustomers <> : to display the list of the customers registered in the system");
			System.out.println("showMenuItem <restaurantName> : to display elements of the menu of a given restaurant, sorted w.r.t. the current sorting policy");
			System.out.println("associateCard <username> {basic, lottery, point} : gives the user specified with <username> a fidelity card of corresponding type (basic, lottery or points)");
			System.out.println("showTotalProfit [<dateStart> <dateEnd>] : show total profit since creation (if no arguments) or between dates passed as optional arguments (date format : DDMMYYYY)");
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		CommandLineUserInterface.run();
	}
}
