package myfoodora;

import java.util.ArrayList;
import java.util.Collections;

public class SortingPolicyHalfMeal implements SortingPolicy, java.io.Serializable {
	
	private static final long serialVersionUID = -4514366174899883201L;
	
	MyFoodora sys;
	boolean reverse; //if false, half meals are sorted from the most ordered to the least ordered. If true, it is the opposite.

	public SortingPolicyHalfMeal(boolean reverse, MyFoodora sys) {
		this.reverse = reverse;
		this.sys = sys;
	}
	
	
	@Override
	public ArrayList<String> sortedShippedOrders(Restaurant r) {
		Menu m = r.getMenu();
		ArrayList<HalfMeal> hm = new ArrayList<>();
		ArrayList<String> result = new ArrayList<>();
		
		for(Meal meal : m.getMeals()){
			if(meal instanceof HalfMeal){
				hm.add((HalfMeal) meal);
			}
		}
		
		while(!hm.isEmpty()){
			HalfMeal mostOrdered = mostOrderedHalfMeal(hm, r);
			hm.remove(mostOrdered);
			result.add(mostOrdered.toString());
		}
		if(reverse){
			Collections.reverse(result);
		}
		return result;
	}
	
	public HalfMeal mostOrderedHalfMeal(ArrayList<HalfMeal> hm, Restaurant r){
		if(hm.size() == 0){
			return null;
		}
		HalfMeal best = hm.get(0);
		
		for(HalfMeal m : hm){
			if(r.getMealCounter().get(m) > r.getMealCounter().get(best)){
				best = m;
			}
		}
		
		return best;
	}

}
