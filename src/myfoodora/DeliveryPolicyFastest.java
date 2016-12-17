package myfoodora;

import java.util.ArrayList;

public class DeliveryPolicyFastest implements DeliveryPolicy, java.io.Serializable{
	
	private static final long serialVersionUID = -1527305446352781277L;
	
	MyFoodora sys;
	
	public DeliveryPolicyFastest(MyFoodora sys) {
		this.sys = sys;
	}
		
	@Override
	public Courier findAvailableCourier(Order o, ArrayList<Courier> couriers) {
		Courier closestCourier = null;
		Restaurant r = o.getRestaurant();
		double closestDistance = -1;
		
		for(Courier c : couriers){
			if(c.isAvailable()){
				if(closestCourier == null){
					closestCourier = c;
					closestDistance = c.getPosition().distance(r.getAddress());
				}
				else{
					if(c.getPosition().distance(r.getAddress()) < closestDistance){
						closestCourier = c;
						closestDistance = c.getPosition().distance(r.getAddress());
					}
				}
			}
		}
		if(closestCourier == null){
			System.out.println("There was no courier available");
		}
		return closestCourier;
	}
	
		
}
