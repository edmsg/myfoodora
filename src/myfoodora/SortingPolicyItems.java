package myfoodora;

import java.util.ArrayList;
import java.util.Collections;

public class SortingPolicyItems implements SortingPolicy, java.io.Serializable {
	
	private static final long serialVersionUID = 180298799033135623L;
	
	MyFoodora sys;
	boolean reverse; //if false, items are sorted from the most ordered to the least ordered. If true, it is the opposite.

	public SortingPolicyItems(boolean reverse, MyFoodora sys) {
		this.reverse = reverse;
		this.sys = sys;
	}
	
	
	@Override
	public ArrayList<String> sortedShippedOrders(Restaurant r) {
		Menu m = r.getMenu();
		ArrayList<Item> items = m.getItems();
		ArrayList<String> result = new ArrayList<>();
		
		while(!items.isEmpty()){
			Item mostOrdered = mostOrderedItem(items, r);
			items.remove(mostOrdered);
			result.add(mostOrdered.toString());
		}
		if(reverse){
			Collections.reverse(result);
		}
		return result;
	}
	
	public Item mostOrderedItem(ArrayList<Item> items, Restaurant r){
		if(items.size() == 0){
			return null;
		}
		Item best = items.get(0);
		
		for(Item i : items){
			if(r.getItemCounter().get(i) > r.getItemCounter().get(best)){
				best = i;
			}
		}
		
		return best;
	}

}
