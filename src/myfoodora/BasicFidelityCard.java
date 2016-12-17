package myfoodora;

/**
 * Basic fidelity card is the card given by default, at registration, to any user. This card simply allows to access to special offers that are provided by the restaurant.
 * @author antoine
 *
 */

public class BasicFidelityCard implements FidelityCard, java.io.Serializable {
	
	private static final long serialVersionUID = -1230949202485830924L;

	public double computeNewPrice(double price){
		return price;
	}

}