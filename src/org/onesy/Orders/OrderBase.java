package org.onesy.Orders;

public abstract class OrderBase {
	/**
	 * 每个orderBase的子类必须在调用ProcessMsg的时候更改
	 */
	public String msgkind = null;

	public abstract String ProcessMsg(String Msg);
} 
