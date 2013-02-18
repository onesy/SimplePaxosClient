package org.onesy.Orders;

public abstract class OrderBase {
	
	public String msgkind = null;

	public abstract String ProcessMsg(String Msg);
}
