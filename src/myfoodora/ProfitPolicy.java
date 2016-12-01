package myfoodora;

public interface ProfitPolicy {
	public void computeVariablesToTargetProfit(double targetProfit); //set the service fee, markup percentage and deliveryCost to meet targetProfit	
	
}
