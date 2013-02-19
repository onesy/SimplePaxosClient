package org.onesy.Switcher;

import org.onesy.MsgProcessor.MsgBean;
import org.onesy.Orders.OrderBase;

public class ProcessClassSwitcher {
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
}
