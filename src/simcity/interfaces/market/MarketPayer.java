package simcity.interfaces.market;

public interface MarketPayer {
	abstract void msgPleasePay(MarketCashier c, double payment, int orderNum);
}
