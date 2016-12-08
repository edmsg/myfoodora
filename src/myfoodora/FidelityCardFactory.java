package myfoodora;

/*Lecture 6 */

public class FidelityCardFactory {
	public static FidelityCard createFidelityCard (String fidelityCardType){
		if (fidelityCardType==null){
			return(null);
		}
		if (fidelityCardType.equalsIgnoreCase("BASIC FIDELITY CARD")){
			return (FidelityCard) new BasicFidelityCard();
		}
		if (fidelityCardType.equalsIgnoreCase("POINT FIDELITY CARD")){
			return (FidelityCard) new PointFidelityCard(0);
		}
		if (fidelityCardType.equalsIgnoreCase("LOTTERY FIDELITY CARD")){
			return (FidelityCard) new LotteryFidelityCard();
		}
		return null;
	}

}
