package org.onesy.Switcher;

import org.onesy.MsgProcessor.MsgBean;
import org.onesy.Orders.OrderBase;

public class ProcessClassSwitcher {
	/**
	 * @deprecated 已经废弃
	 * @param msgbean
	 * @return
	 */
	public static OrderBase getProcessObject(MsgBean msgbean){
		String classname = msgbean.msgType;
		String msg = msgbean.Msg;
		OrderBase orderObject = null;
		try {
			orderObject = (OrderBase) Class.forName(classname).newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderObject;
	}

	/**
	 * 通过反射获取OrderBase的子类对象
	 * @param IName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static OrderBase getInstructionByName(String IName){
		Class<OrderBase> orderClass = null;
		OrderBase order = null;
			try {
				orderClass = (Class<OrderBase>) Class.forName("org.onesy.Orders."+IName);
				order = orderClass.newInstance();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return order;
	}
}
