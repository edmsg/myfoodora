package myfoodora;

import java.util.ArrayList;

public interface DeliveryPolicy{
	public Courier findAvailableCourier(Order o, ArrayList<Courier> couriers);
}
