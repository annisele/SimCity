package simcity.interfaces.market;

public interface MarketPayer {
	abstract void msgPleasePay(String marketName, double payment, int orderNum);
}
