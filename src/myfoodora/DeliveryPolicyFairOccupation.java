package myfoodora;

import java.util.ArrayList;

public class DeliveryPolicyFairOccupation implements DeliveryPolicy, java.io.Serializable {

	
	private static final long serialVersionUID = 1018847097949871706L;
	
	MyFoodora sys;
	
	public DeliveryPolicyFairOccupation(MyFoodora sys) {
		this.sys = sys;
	}
	
	@Override
	public Courier findAvailableCourier(Order o, ArrayList<Courier> couriers) {
		
		Courier bestCourier = null; //stores the available courier with the lowest number of deliveries on his counter
		
		for(Courier c : couriers){
			if(c.isAvailable()){
				if(bestCourier == null){
					bestCourier = c;
				}
				else{
					if(c.getCounter() < bestCourier.getCounter()){
						bestCourier = c;
					}
				}
			}
		}
		if(bestCourier == null){
			System.out.println("There was no courier available");
		}
		return bestCourier;
	}

}
