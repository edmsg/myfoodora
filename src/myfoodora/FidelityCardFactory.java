package myfoodora;

/*Lecture 6 */

public class FidelityCardFactory implements java.io.Serializable {
	
	private static final long serialVersionUID = -8681177123424042002L;

	public static FidelityCard createFidelityCard (String fidelityCardType){
		if (fidelityCardType==null){
			return(null);
		}
		if (fidelityCardType.equalsIgnoreCase("BASIC FIDELITY CARD")){
			return new BasicFidelityCard();
		}
		if (fidelityCardType.equalsIgnoreCase("POINT FIDELITY CARD")){
			return new PointFidelityCard();
		}
		if (fidelityCardType.equalsIgnoreCase("LOTTERY FIDELITY CARD")){
			return new LotteryFidelityCard();
		}
		return null;
	}

}


