package myfoodora;

import java.util.ArrayList;
import java.util.Calendar;

public class TargetProfitMarkupPercentage implements ProfitPolicy, java.io.Serializable {
	
	private static final long serialVersionUID = -6289645212984602162L;
	
	private MyFoodora sys;
	
	public TargetProfitMarkupPercentage(MyFoodora sys){
		this.sys = sys;
	}
	
	/**
	 * Computes the markup percentage necessary to meet targetProfit, based on last month's orders, knowing that :
	 * targetProfit = sumOverOrders((markupPercentage*priceOfOrder) - reductionsDueToCards) + numberOfOrders*(serviceFee - deliveryCost)
	 */
	@Override
	public void computeVariablesToTargetProfit(double targetProfit) {
		Calendar oneMonthAgo = Calendar.getInstance();
		oneMonthAgo.add(Calendar.MONTH, -1); //retrieve one month to the current date to select orders only after this date
		
		ArrayList<Order> historyOfOrders = sys.getHistoryOfOrders();
		double sumDiscounts = 0; //will store the value of sumOverOrders(markupPercentage*priceOfOrder - reductionDueToCards)
		double sumPriceOfOrders = 0;
		int N = 0;
		
		for(Order o : historyOfOrders){
			//we only select the orders that took place in the last month
			if(oneMonthAgo.before(o.getDate())){
				sumPriceOfOrders += o.getDueToRestaurant();
				sumDiscounts += o.getDiscountDueToCards();
				N += 1;
			}
		}
		double markupPercentage = (targetProfit + sumDiscounts - N*(sys.getServiceFee() - sys.getDeliveryCost()))/(sumPriceOfOrders);
		
		sys.setMarkupPercentage(markupPercentage);
	}
	

}
